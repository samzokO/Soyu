package com.ssafy.soyu.locker.service;

import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;

import com.ssafy.soyu.item.service.ItemService;
import com.ssafy.soyu.likes.service.LikesService;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.util.raspberry.RaspberryUtil;
import com.ssafy.soyu.util.raspberry.dto.request.ReserveDpRequestDto;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.locker.dto.response.LockerBuyResponse;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LockerService {

  // Service
  private final ItemService itemService;
  private final NoticeService noticeService;
  private final LikesService likesService;
  // Repository
  private final ItemRepository itemRepository;
  private final LockerRepository lockerRepository;
  private final HistoryRepository historyRepository;
  private final MemberRepository memberRepository;
  // Util
  private final PayActionUtil payActionUtil;
  private final RaspberryUtil raspberryUtil;

  public List<LockerListResponse> getLockers(Long stationId) {
    List<Locker> lockerList = lockerRepository.findByStationIdWithItem(stationId);

    return lockerList.stream()
        .map(l -> {
          Item item = l.getItem();
          ItemResponse itemResponse = null;
          if (item != null) {
            itemResponse = getItemResponse(item);
          }
          return new LockerListResponse(l.getId(), itemResponse, l.getCode(), l.getIsLight(),
              l.getIsVisible(), l.getStatus(), l.getLockerNum(), l.getTime());
        }).collect(Collectors.toList());
  }

  public void checkLocker(Long lockerId) {
    Locker locker = lockerRepository.findById(lockerId).get();
    if (locker.getStatus() != LockerStatus.AVAILABLE) {
      throw new CustomException(ErrorCode.IN_USE_LOCKER);
    }
  }

  @Transactional
  public void insertSellCode(String code) {
    Optional<Locker> optionalLocker = lockerRepository.findByCode(code);
    if (!optionalLocker.isPresent()) {
      throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
    }

    Locker locker = optionalLocker.get();
    Item item = locker.getItem();
    LockerStatus status = null;

    //거래 예약 코드였을 때
    if(locker.getStatus() == LockerStatus.TRADE_RESERVE){
      String newCode = payActionUtil.generateRandomCode();

      //락커 상태 변경
      lockerRepository.updateLockerStatusAndCode(locker.getId(), LockerStatus.TRADE_READY, newCode);

      //구매자에게 코드 알림
      History history = historyRepository.findByItemIdNotDeleted(item.getId());
      noticeService.createNotice(history.getMember().getId(),
              new NoticeRequestDto(history.getItem(), NoticeType.BUY, newCode));

      status = LockerStatus.TRADE_INSERT;
    }

    //DP 코드 였을 때
    if(locker.getStatus() == LockerStatus.DP_RESERVE){
      lockerRepository.updateLockerStatusAndCode(locker.getId(), LockerStatus.DP_READY, null);
      itemRepository.updateStatus(item.getId(), ItemStatus.DP);

      status = LockerStatus.DP_INSERT;
    }

    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), status, item.getPrice());
    raspberryUtil.sendMessageToRaspberryPi(response);
  }

  public LockerBuyResponse insertBuyCode(Long stationId, String code) {
    //DP 판매 코드를 입력한 경우
    Item item = null;
    Integer lockerNum = null;
    Optional<Locker> optionalLocker;
    if (code.length() != 6) {
      optionalLocker = lockerRepository.findByLocation(stationId, Integer.parseInt(code));
      if (optionalLocker.isPresent()) {
        Locker locker = optionalLocker.get();
        item = locker.getItem();
        if (locker.getStatus() == LockerStatus.AVAILABLE) {
          throw new CustomException(ErrorCode.EMPTY_ITEM_LOCKER);
        }
        if (locker.getStatus() != LockerStatus.DP_READY) {
          throw new CustomException(ErrorCode.NOT_DP_ITEM);
        }
      }
    } else { //거래 예약 구매자인 경우
      optionalLocker = lockerRepository.findByCode(code);

      if (!optionalLocker.isPresent()) {
        throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
      }
      Locker locker = optionalLocker.get();
      lockerNum = locker.getLockerNum();
      item = locker.getItem();
      RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.TRADE_CHECK, item.getPrice());
      raspberryUtil.sendMessageToRaspberryPi(response);
    }
    return new LockerBuyResponse(item.getId(), lockerNum);
  }

  @Transactional
  public void insertWithdrawCode(String code) {
    Optional<Locker> optionalLocker = lockerRepository.findByCode(code);

    if (!optionalLocker.isPresent()) {
      throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
    }
    Locker locker = optionalLocker.get();
    Item item = locker.getItem();
    itemRepository.updateStatus(item.getId(), ItemStatus.ONLINE);
    lockerRepository.updateLocker(locker.getId(), LockerStatus.AVAILABLE, null, null, null);

    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.SUBTRACT, item.getPrice());
    raspberryUtil.sendMessageToRaspberryPi(response);
  }

  /**
   * DP 예약 로직<br/> 1. DP 조건 충족 & 본인 여부 & 빈자리 여부 확인<br/> 2. Item, Locker 상태 변경<br/> 3. payAction
   * 등록<br/> 4. Notice 전송<br/>
   *
   * @param memberId 판매자 식별자
   * @param dp       ItemId, lockerId
   */
  @Transactional
  public void dpReserve(Long memberId, ReserveDpRequestDto dp) {
    Item item = itemService.getItem(dp.getItemId());
    Locker locker = lockerRepository.findById(dp.getLockerId()).get();
    Long nonMember = (long)1;

    //1. DP 조건을 충족했는가
    // 현재 시간과 차이 확인
    LocalDateTime currentTime = LocalDateTime.now();
    Duration duration = Duration.between(item.getRegDate(), currentTime);
    long hoursElapsed = duration.toHours();
    // 찜 카운트
    Integer count = likesService.getLikeCountByItemId(dp.getItemId());
    if (hoursElapsed < 24 || count < 3) {
      throw new CustomException(ErrorCode.IMPOSSIBLE_ITEM_DP);
    }

    //2. 본인의 물품이 정말 맞는가
    if (!Objects.equals(item.getMember().getId(), memberId)) {
      throw new CustomException(ErrorCode.IS_NOT_YOURS);
    }

    //3. 보관함이 정말 빈자리인가
    if (locker.getStatus() != LockerStatus.AVAILABLE) {
      throw new CustomException(ErrorCode.IN_USE_LOCKER);
    }

    //4. 아이템 상태 변경
    itemRepository.updateStatus(dp.getItemId(), ItemStatus.DP_RESERVE);

    //5. 보관함 상태 변경
    String code = payActionUtil.generateRandomCode();
    lockerRepository.updateLocker(dp.getLockerId(), LockerStatus.DP_RESERVE, currentTime,
        dp.getItemId(), code);

    History history = historyRepository.save(new History(item, memberRepository.findById(nonMember).get()));

    //6. payAction에 등록
    String today = payActionUtil.getCurrentDateTime(LocalDateTime.now());
    String orderNumber = today + history.getId();
    itemRepository.updateOrderNumber(item.getId(), orderNumber);

    //payAction API
    payActionUtil.makeNoneMemberPayAction(orderNumber, item.getPrice(), today, history.getId());

    //7. 알림 전송 해야 됨
    noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.RESERVE, code));

    //8. 라즈베리 파이 신호 주기
    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.RESERVE, item.getPrice());
    raspberryUtil.sendMessageToRaspberryPi(response);
  }

  /**
   * DP 취소 == 회수 요청 Service<br/> 1.오프라인 DP 중이던 물품<br/> 2.거래 예약이 취소 된 물품<br/>
   *
   * @param memberId 판매자 식별자
   * @param itemId   회수 물품 식별자
   */
  public void withdrawReserve(Long memberId, Long itemId) {
    Item item = itemRepository.findItemById(itemId);
    Locker locker = lockerRepository.findByItemId(itemId).get();
    ItemStatus is = item.getItemStatus();
    LockerStatus ls = locker.getStatus();
    LocalDateTime now = LocalDateTime.now();
    String code = payActionUtil.generateRandomCode();

    //1. 유저의 판매 아이템이 맞는지
    if (item == null || !Objects.equals(item.getMember().getId(), memberId)) {
      throw new CustomException(ErrorCode.IS_NOT_YOURS);
    }

    RaspberryRequestResponse response = null;
    //2. 보관함 상태와 아이템 상태를 비교하여 무결성 확인
    //2-1. DP 중인 물품인지 확인
    if (is == ItemStatus.DP && ls == LockerStatus.DP_READY) {
      //3. 아이템 상태 변경
      itemRepository.updateStatus(itemId, ItemStatus.WITHDRAW);
      //4. 보관함 상태 변경
      lockerRepository.updateLocker(locker.getId(), LockerStatus.WITHDRAW, now, itemId, code);
      payActionUtil.deletePayAction(item.getOrderNumber());

      //5. 알림 전송(회수 코드 포함)
      noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.WITHDRAW, code));
      response = raspberryUtil.makeRaspberryResponse(itemId, locker.getLockerNum(), LockerStatus.WITHDRAW, item.getPrice());
    }
    //2-2. DP 예약 물건인지 확인
    else if(is == ItemStatus.DP_RESERVE && ls == LockerStatus.DP_RESERVE){
      //3. 아이템 상태 변경
      itemRepository.updateStatus(itemId, ItemStatus.ONLINE);
      //4. 보관함 상태 변경
      lockerRepository.updateLocker(locker.getId(), LockerStatus.AVAILABLE, null, null, null);
      payActionUtil.deletePayAction(item.getOrderNumber());
      response = raspberryUtil.makeRaspberryResponse(itemId, locker.getLockerNum(), LockerStatus.AVAILABLE, item.getPrice());
    }
    //2-3. DP 중 아니고 회수대기도 아니면 회수 신청 불가능
    else if (is != ItemStatus.WITHDRAW && ls != LockerStatus.WITHDRAW) {
      throw new CustomException(ErrorCode.IMPOSSIBLE_WITHDRAW);
    }

    //아이템 주문 번호 삭제
    itemRepository.updateOrderNumber(itemId, null);
    //라즈베리파이에 신호 보내기
    raspberryUtil.sendMessageToRaspberryPi(response);
  }

  // make response List
  public static com.ssafy.soyu.item.dto.response.ItemResponse getItemResponse(Item item) {
    return new com.ssafy.soyu.item.dto.response.ItemResponse
        (item.getId(), item.getMember().getId(), item.getMember().getNickName(), item.getTitle(), item.getContent(),
            item.getRegDate()
            , item.getPrice(), item.getItemStatus(), item.getItemCategories(), item.getImage());
  }

  /**
   * 거래 예약 물품 구매 결정 Service<br/> 거래중이던 물품<br/>
   *
   * @param isBuy
   * @param itemId 구매 물품 식별자
   */
  public void reserveBuyDecision(boolean isBuy, Long itemId) {
    Locker locker = lockerRepository.findByItemId(itemId).get();
    Item item = locker.getItem();


    LockerStatus status = LockerStatus.TRADE_READY;

    //구매 안함 눌렀을 때, 페이액션 삭제, 구매 내역 삭제, 아이템, 락커, 라즈베리 파이 신호
    if(!isBuy){
      noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.BUYER_CANCEL));

      //페이액션 삭제
      payActionUtil.deletePayAction(item.getOrderNumber());

      //아이템 주문번호 삭제
      itemRepository.deleteOrderNumber(itemId);

      //히스토리 삭제
      historyRepository.updateIsDelete(historyRepository.findByItemIdNotDeleted(itemId).getId());

      //아이템 상태 변경
      itemRepository.updateStatus(itemId, ItemStatus.WITHDRAW);

      //락커 상태 변경
      status = LockerStatus.WITHDRAW;
      lockerRepository.updateLocker(locker.getId(), LockerStatus.WITHDRAW, null, itemId, null);

    }
    //라즈베리 파이에 json 신호 보내기
    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(), status, item.getPrice());
    raspberryUtil.sendMessageToRaspberryPi(response);
  }

  /**
   * DP 물품 구매 결정 Service<br/> DP중이던 물품 구매<br/>
   *
   * @param itemId 구매 물품 식별자
   */
  public Long dpBuyDecision(Long itemId) {
    History history = historyRepository.findByItemIdNotDeleted(itemId);
    Member member = history.getItem().getMember();
    noticeService.createNotice(member.getId(), new NoticeRequestDto(history.getItem(), NoticeType.DP_SELL));
    return history.getId();
  }

  /**
   * 거래 예약 물품 DP 전환 Service<br/>거래 예약 물품 판매 실패 후 DP 전환 <br/>
   *
   * @param memberId 판매자 식별자
   * @param itemId 구매 물품 식별자
   */
  public void changeToDP(Long memberId, Long itemId) {
    Optional<Locker> optionalLocker = lockerRepository.findByItemId(itemId);
    //락커에 있는 물건이 아닐 때
    if(!optionalLocker.isPresent()){
      throw new CustomException(ErrorCode.NOT_IN_LOCKER);
    }

    Locker locker = optionalLocker.get();
    Item item = locker.getItem();

    //물품올린 사람과 아이디가 다를 때
    if(item.getMember().getId() != memberId){
      throw new CustomException(ErrorCode.IS_NOT_YOURS);
    }

    // item 상태 변경
    itemRepository.updateStatus(itemId, ItemStatus.DP);

    // 비회원으로 history 등록
    Long nonMember = (long)1;
    History history = historyRepository.save(new History(item, memberRepository.findById(nonMember).get()));

    //payAction에 등록
    String today = payActionUtil.getCurrentDateTime(LocalDateTime.now());
    String orderNumber = today + history.getId();
    itemRepository.updateOrderNumber(item.getId(), orderNumber);

    payActionUtil.makeNoneMemberPayAction(orderNumber, item.getPrice(), today, history.getId());

    // locker 상태 변경 및 코드 삭제
    lockerRepository.updateLockerStatusAndCode(locker.getId(), LockerStatus.DP_READY, null);

    //라즈베리 파이에 json 신호 보내기
    RaspberryRequestResponse response = raspberryUtil.makeRaspberryResponse(itemId, locker.getLockerNum(), LockerStatus.DP_READY, locker.getItem().getPrice());
    raspberryUtil.sendMessageToRaspberryPi(response);

  }
}
