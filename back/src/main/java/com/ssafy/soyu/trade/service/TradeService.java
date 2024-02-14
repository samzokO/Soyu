package com.ssafy.soyu.trade.service;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.client.ClientUtil;
import com.ssafy.soyu.util.client.dto.response.ClientRequestResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {

  //Repository
  private final ItemRepository itemRepository;
  private final ChatRepository chatRepository;
  private final HistoryRepository historyRepository;
  private final LockerRepository lockerRepository;
  //Service
  private final NoticeService noticeService;
  //Util
  private final ClientUtil raspberryUtil;
  private final PayActionUtil payActionUtil;

  @Transactional
  public void makeReserve(Long chatId, Long lockerId){
    Chat chat = chatRepository.findById(chatId).get();
    Item item = chat.getItem();

    //온라인으로 판매중인 물건인지 체크
    if(item.getItemStatus() != ItemStatus.ONLINE){
      throw new CustomException(ErrorCode.ITEM_NOT_ONLINE);
    }
    //보관함 이용가능한지 상태 확인
    Locker locker = lockerRepository.findById(lockerId).get();
    if(locker.getStatus() != LockerStatus.AVAILABLE){
      throw new CustomException(ErrorCode.IN_USE_LOCKER);
    }

    History existHistory = historyRepository.findByItemIdNotDeleted(item.getId());
    if(existHistory != null){
      throw new CustomException(ErrorCode.ALREADY_RESERVED_ITEM);
    }

    //아이템 상태 변경
    itemRepository.updateStatus(item.getId(), ItemStatus.TRADE_RESERVE); //아이템 상태 변경

    //locker 변경
    String code = payActionUtil.generateRandomCode();
    LocalDateTime now = LocalDateTime.now();
    String today = payActionUtil.getCurrentDateTime(now);
    lockerRepository.updateLocker(lockerId, LockerStatus.TRADE_RESERVE, now, item.getId(), code);

    //구매내역에 추가
    History history = new History(item, chat.getBuyer());
    historyRepository.save(history);

    //주문번호 업데이트
    String orderNumber = today + history.getId();
    itemRepository.updateOrderNumber(item.getId(), orderNumber);

    //payAction API
    payActionUtil.makePayAction(orderNumber, item.getPrice(), today, chat.getBuyer());

    //판매자 알림 noticeType = RESERVE (code 포함)
    noticeService.createNotice(chat.getSeller().getId(), new NoticeRequestDto(item, NoticeType.RESERVE, code));
    //구매자 알림 noticeType = RESERVE
    noticeService.createNotice(chat.getBuyer().getId(), new NoticeRequestDto(item, NoticeType.RESERVE));

    //라즈베리 파이에 신호 json 신호 보내기
    ClientRequestResponse response = raspberryUtil.makeClientResponse(item.getId(), locker.getLockerNum(), LockerStatus.RESERVE, item.getPrice());
    raspberryUtil.sendMessageToClient(response);
  }

  /**
   * 구매자 취소
   * @param historyId 구매내역 ID
   * @param memberId 구매자 ID
   */
  @Transactional
  public void deleteBuyReserve(Long historyId, Long memberId) {
    History history = historyRepository.findById(historyId).get();

    //아이템 상태 변경
    Item item = history.getItem();

    //histroy 변경
    historyRepository.updateIsDelete(historyId);

    //Locker 변경
    Optional<Locker> optionalLocker = lockerRepository.findByItemId(item.getId());
    if(optionalLocker.isPresent()){
      Locker locker = optionalLocker.get();
      LockerStatus lockerStatus = null;
      ItemStatus itemStatus = null;

      //보관한 물건 취소 했을 때
      if(locker.getStatus() == LockerStatus.TRADE_READY) {
        lockerStatus = LockerStatus.WITHDRAW;
        itemStatus = ItemStatus.WITHDRAW;
        //판매자 알림
        noticeService.createNoticeWithSender(item.getMember().getId(), memberId, new NoticeRequestDto(item, NoticeType.CONVERT));
        String code = payActionUtil.generateRandomCode();
        noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.CHANGE_STATUS, code));
      }
      //물건 넣기 전 취소
      else if(locker.getStatus() == LockerStatus.TRADE_RESERVE){
        lockerStatus = LockerStatus.AVAILABLE;
        itemStatus = ItemStatus.ONLINE;
        noticeService.createNoticeWithSender(item.getMember().getId(), memberId, new NoticeRequestDto(item, NoticeType.RESERVE_CANCEL));
      }

      //구매자 알림 noticeType = RESERVE_CANCEL
      noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.RESERVE_CANCEL));

      //locker 상태 변경, 코드 삭제 등
      lockerRepository.updateLocker(locker.getId(), lockerStatus, null, item.getId(), null);

      //item 상태 변경
      itemRepository.updateStatus(item.getId(), itemStatus);

      //라즈베리 파이에 json 신호 보내기
      ClientRequestResponse response = raspberryUtil.makeClientResponse(item.getId(), locker.getLockerNum(), lockerStatus, item.getPrice());
      raspberryUtil.sendMessageToClient(response);
    }
    //payAction 매칭 취소 후 주문번호 삭제
    payActionUtil.deletePayAction(item.getOrderNumber());
    itemRepository.deleteOrderNumber(item.getId());
  }

  /**
   * 구매 내역에서 보관함 예약 코드 확인
   *
   * @param memberId 구매자의 식별자
   * @param itemId   보관함의 식별자
   * @return code
   */
  public String getPurchaseCode(Long memberId, Long itemId) {
    Optional<History> h = historyRepository.checkMatchHistory(memberId, itemId);
    if (h.isEmpty()) {
      throw new CustomException(ErrorCode.NO_MATCH_HISTORY);
    }

    Optional<String> code = lockerRepository.getCode(itemId);
    if (code.isPresent()) {
      return code.get();
    }
    throw new CustomException(ErrorCode.NOT_READY_YET);
  }

  /**
   * 거래 예약 취소(판매자)
   * @param memberId 판매자 ID
   * @param itemId 물품 ID
   */
  public void deleteSaleReserve(Long memberId, Long itemId) {
    Item item = itemRepository.findById(itemId).get();

    //1. 유저의 판매 아이템이 맞는지
    if (item.getId() == null || !Objects.equals(item.getMember().getId(), memberId)) {
      throw new CustomException(ErrorCode.IS_NOT_YOURS);
    }

    History history = historyRepository.findByItemIdNotDeleted(itemId);

    //histroy 변경
    historyRepository.updateIsDelete(history.getId());

    Optional<Locker> optionalLocker = lockerRepository.findByItemId(item.getId());
    if(optionalLocker.isPresent()){
      Locker locker = optionalLocker.get();
      NoticeType noticeType = null;
      LockerStatus lockerStatus = null;
      ItemStatus itemStatus = null;

      //보관한 물건 취소 했을 때
      if(locker.getStatus() == LockerStatus.TRADE_READY) {
        noticeType = NoticeType.CONVERT;
        lockerStatus = LockerStatus.WITHDRAW;
        itemStatus = ItemStatus.WITHDRAW;
      }
      //물건 넣기 전 취소
      else if(locker.getStatus() == LockerStatus.TRADE_RESERVE){
        noticeType = NoticeType.RESERVE_CANCEL;
        lockerStatus = LockerStatus.AVAILABLE;
        itemStatus = ItemStatus.ONLINE;
      }
      //판매자 알림 noticeType = CONVERT / RESERVE_CANCEL
      noticeService.createNotice(memberId, new NoticeRequestDto(item, noticeType));
      //구매자 알림
      noticeService.createNoticeWithSender(history.getMember().getId(), memberId, new NoticeRequestDto(item, NoticeType.RESERVE_CANCEL));

      //locker 상태 변경, 코드 삭제 등
      lockerRepository.updateLocker(locker.getId(), lockerStatus, null, item.getId(), null);

      //item 상태 변경
      itemRepository.updateStatus(item.getId(), itemStatus);

      //라즈베리 파이에 json 신호 보내기
      ClientRequestResponse response = raspberryUtil.makeClientResponse(item.getId(), locker.getLockerNum(), lockerStatus, item.getPrice());
      raspberryUtil.sendMessageToClient(response);
    }
    //payAction 매칭 취소 후 주문번호 삭제
    payActionUtil.deletePayAction(item.getOrderNumber());
    itemRepository.deleteOrderNumber(item.getId());
  }

  public String getSaleCode(Long memberId, Long itemId) {
    Optional<String> code = lockerRepository.getSaleCode(memberId, itemId);
    if(code.isEmpty())
      throw new CustomException(ErrorCode.IS_NOT_YOURS);
    return code.get();
  }
}
