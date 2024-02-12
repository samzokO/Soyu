package com.ssafy.soyu.history.service;

import static com.ssafy.soyu.image.controller.ImageController.getImageResponse;

import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.likes.repository.LikesRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final ItemRepository itemRepository;
  private final LikesRepository likesRepository;

  /**
   * history 테이블에서 조회하는 구매 내역
   *
   * @param memberId 사용자 식별자
   * @return PurchaseResponseDto
   */
  public List<PurchaseResponseDto> getPurchaseHistory(Long memberId) {
    return historyRepository.findByMemberId(memberId)
        .stream()
        .map(h -> new PurchaseResponseDto(h, getImageResponse(h.getItem().getImage()), likesRepository.countLikeByItemId(h.getItem().getId())))
        .collect(Collectors.toList());
  }

  /**
   * item 테이블에서 조회하는 판매 내역
   *
   * @param memberId 사용자 식별자
   * @return SaleResponseDto
   */
  public List<SaleResponseDto> getSaleHistory(Long memberId) {
    return itemRepository.findByMemberId(memberId)
        .stream()
        .map(i -> new SaleResponseDto(i, getImageResponse(i.getImage()), likesRepository.countLikeByItemId(i.getId())))
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
