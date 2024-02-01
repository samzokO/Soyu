package com.ssafy.soyu.history.service;

import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.dto.request.HistoryRequestDto;
import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.itemfile.ItemFileRepository;
import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.raspberry.RaspberryUtil;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final ItemRepository itemRepository;
  private final ItemFileRepository itemFileRepository;
  private final MemberRepository memberRepository;
  private final LockerRepository lockerRepository;
  private final PayActionUtil payActionUtil;
  private final NoticeService noticeService;
  private final RaspberryUtil raspberryUtil;

  /**
   * 구매 내역 생성<br/>
   * memberId & itemId 필요
   *
   * @param request HistoryRequestDto
   */
  @Transactional
  public void creatHistory(HistoryRequestDto request) {
    Member member = memberRepository.getOne(request.getMemberId());
    Item item = itemRepository.getOne(request.getItemId());

    History history = new History(item, member);

    historyRepository.save(history);
  }

  /**
   * history 테이블에서 조회하는 구매 내역
   *
   * @param memberId 사용자 식별자
   * @return PurchaseResponseDto
   */
  public List<PurchaseResponseDto> getPurchaseHistory(Long memberId) {
    return historyRepository.findByMemberId(memberId)
        .stream()
        .map(h -> new PurchaseResponseDto(h,
            itemFileRepository.findByItemId(h.getItem().getId()).get()))
        .collect(Collectors.toList());
  }

  /**
   * item 테이블에서 조회하는 구매 내역
   *
   * @param memberId 사용자 식별자
   * @return SaleResponseDto
   */
  public List<SaleResponseDto> getSaleHistory(Long memberId) {
    return itemRepository.findByMemberId(memberId)
        .stream()
        .map(i -> new SaleResponseDto(i, itemFileRepository.findByItemId(i.getId()).get()))
        .collect(Collectors.toList());
  }

  /**
   * history 테이블의 is_delete = true
   *
   * @param historyIdList 구매 내역의 식별자 목록
   */
  public void deleteHistory(List<Long> historyIdList) {
    historyRepository.updateIsDelete(historyIdList);
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

  public void deleteSaleReserve(Long memberId, Long itemId) {

  }

  /**
   * 구매자 취소
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
      RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.WITHDRAW, item.getPrice());
      raspberryUtil.sendMessageToRaspberryPi("/sub/raspberry", response);
    }
    //payAction 매칭 취소 후 주문번호 삭제
    payActionUtil.deletePayAction(item.getOrderNumber());
    itemRepository.deleteOrderNumber(item.getId());
  }

  public void depositMoney(DepositInfoRequest depositInfoRequest) {
    Long orderId = Long.parseLong(depositInfoRequest.getOrder_number().substring(25));
    History history = historyRepository.findById(orderId).get();
    Item item = history.getItem();

    // 아이템 판매 완료
    itemRepository.updateStatus(item.getId(), ItemStatus.SOLD);
  }
}
