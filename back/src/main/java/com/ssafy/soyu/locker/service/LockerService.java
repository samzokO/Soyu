package com.ssafy.soyu.locker.service;

import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;

import com.ssafy.soyu.item.service.ItemService;
import com.ssafy.soyu.likes.service.LikesService;
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
import com.ssafy.soyu.util.payaction.PayActionProperties;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import com.ssafy.soyu.util.soyu.SoyuProperties;
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
  // Properties
  private final PayActionProperties payActionProperties;
  private final SoyuProperties soyuProperties;
  // Util
  private final PayActionUtil payActionUtil;

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
    String newCode = payActionUtil.generateRandomCode();
    lockerRepository.updateLockerStatusAndCode(locker.getId(), LockerStatus.TRADE_READY, newCode);

    //구매자에게 코드 알림
    History history = historyRepository.findByItemIdNotDeleted(locker.getItem().getId());
    noticeService.createNotice(history.getMember().getId(),
        new NoticeRequestDto(history.getItem(), NoticeType.BUY, newCode));
  }

  public LockerBuyResponse insertBuyCode(Long stationId, String code) {
    //DP 판매 코드를 입력한 경우
    Locker locker = null;
    Optional<Locker> optionalLocker;
    if (code.length() != 6) {
      optionalLocker = lockerRepository.findByLocation(stationId, Integer.parseInt(code));
      if (optionalLocker.isPresent()) {
        locker = optionalLocker.get();
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
      locker = optionalLocker.get();
    }

    Item item = locker.getItem();
    ItemResponse itemResponse = getItemResponse(item);
    return new LockerBuyResponse(soyuProperties.getBankName(), soyuProperties.getAccountNumber(),
        itemResponse);

  }

  @Transactional
  public void insertWithdrawCode(String code) {
    Optional<Locker> optionalLocker = lockerRepository.findByCode(code);

    if (!optionalLocker.isPresent()) {
      throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
    }
    Locker locker = optionalLocker.get();
    lockerRepository.updateLocker(locker.getId(), LockerStatus.AVAILABLE, null, null, null);
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

    //payAction API
    payActionUtil.makeNoneMemberPayAction(orderNumber, item.getPrice(), today, history.getId());

    //7. 알림 전송 해야 됨
    noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.RESERVE, code));
  }

  /**
   * 회수 요청 Service<br/> 1.오프라인 DP 중이던 물품<br/> 2.거래 예약이 취소 된 물품<br/>
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

    //2. 보관함 상태와 아이템 상태를 비교하여 무결성 확인
    //2-1. DP 중인 물품인지 확인
    if (is.equals(ItemStatus.DP) && ls.equals(LockerStatus.DP_READY)) {
      //3. 아이템 상태 변경
      itemRepository.updateStatus(itemId, ItemStatus.WITHDRAW);
      //4. 보관함 상태 변경
      lockerRepository.updateLocker(locker.getId(), LockerStatus.WITHDRAW, now, itemId, code);
      payActionUtil.deletePayAction(item.getOrderNumber());
    }
    //2-1. DP 중 아니고 회수대기도 아니면 회수 신청 불가능
    else if (!(is.equals(ItemStatus.WITHDRAW) && ls.equals(LockerStatus.WITHDRAW))) {
      throw new CustomException(ErrorCode.IMPOSSIBLE_WITHDRAW);
    }

    //5. 알림 전송(회수 코드 포함)
    noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.WITHDRAW, code));
  }

  // make response List
  public static com.ssafy.soyu.item.dto.response.ItemResponse getItemResponse(Item item) {
    return new com.ssafy.soyu.item.dto.response.ItemResponse
        (item.getId(), item.getMember().getId(), item.getMember().getNickName(), item.getTitle(), item.getContent(),
            item.getRegDate()
            , item.getPrice(), item.getItemStatus(), item.getItemCategories(), item.getImage());
  }
}
