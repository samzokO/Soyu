package com.ssafy.soyu.history.service;

import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.dto.request.HistoryRequestDto;
import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.itemfile.ItemFileRepository;
import com.ssafy.soyu.locker.LockerRepository;
import com.ssafy.soyu.locker.LockerStatus;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
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

  /**
   * 구매 내역 생성<br/> memberId & itemId 필요
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
}
