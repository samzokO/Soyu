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
import com.ssafy.soyu.util.raspberry.RaspberryUtil;
import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
  private final RaspberryUtil raspberryUtil;
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

    noticeService.createNotice(chat.getSeller().getId(), new NoticeRequestDto(item, NoticeType.RESERVE, code));
    noticeService.createNotice(chat.getBuyer().getId(), new NoticeRequestDto(item, NoticeType.RESERVE));

    //라즈베리 파이에 신호 json 신호 보내기
    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.TRADE_RESERVE, item.getPrice());
    raspberryUtil.sendMessageToRaspberryPi("/sub/raspberry", response);
  }

  /**
   * 구매자 취소
   */
  @org.springframework.transaction.annotation.Transactional
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
      noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, noticeType)); //판매자에게 알림
      noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.RESERVE_CANCEL)); //구매자

      //locker 상태 변경, 코드 삭제 등
      lockerRepository.updateLocker(locker.getId(), lockerStatus, null, item.getId(), null);

      //item 상태 변경
      itemRepository.updateStatus(item.getId(), itemStatus);

      //라즈베리 파이에 json 신호 보내기
      RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), lockerStatus, item.getPrice());
      raspberryUtil.sendMessageToRaspberryPi("/sub/raspberry", response);
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
   * @return
   */
  public String getPurchaseCode(Long memberId, Long itemId) {
    Optional<History> h = historyRepository.checkMatchHistory(memberId, itemId);
    if (!h.isPresent()) {
      throw new CustomException(ErrorCode.NO_MATCH_HISTORY);
    }

    Optional<String> code = lockerRepository.getCode(itemId);
    if(code.isPresent()){
      throw new CustomException(ErrorCode.NOT_READY_YET);
    }

    return code.get();
  }
}
