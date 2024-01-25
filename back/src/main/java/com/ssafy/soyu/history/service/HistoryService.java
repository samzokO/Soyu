package com.ssafy.soyu.history.service;

import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.ItemRepository;
import com.ssafy.soyu.itemfile.ItemFileRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final ItemRepository itemRepository;
  private final ItemFileRepository itemFileRepository;

  public List<PurchaseResponseDto> getPurchaseHistory(Long memberId) {
    return historyRepository.findByMemberId(memberId)
        .stream()
        .map(h -> new PurchaseResponseDto(h,  itemFileRepository.findByItemId(h.getItem().getId())))
        .collect(Collectors.toList());
  }

//  public SaleResponseDto getSaleHistory(Long memberId) {
//    itemRepository.
//  }
}
