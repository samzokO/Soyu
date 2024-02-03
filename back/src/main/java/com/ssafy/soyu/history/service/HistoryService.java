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
import java.util.Objects;
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
            itemRepository.findImageByItem(h.getItem().getId())))
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
        .map(i -> new SaleResponseDto(i, itemRepository.findImageByItem(i.getId())))
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

}
