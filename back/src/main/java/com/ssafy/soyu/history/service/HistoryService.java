package com.ssafy.soyu.history.service;

import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.dto.request.HistoryRequestDto;
import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.item.ItemRepository;
import com.ssafy.soyu.itemfile.ItemFileRepository;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import java.util.List;
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

  @Transactional
  public void creatHistory(HistoryRequestDto request){
    Member member = memberRepository.getOne(request.getMemberId());
    Item item = itemRepository.getOne(request.getItemId());

    History history = new History(item, member);

    historyRepository.save(history);
  }

  public List<PurchaseResponseDto> getPurchaseHistory(Long memberId) {
    return historyRepository.findByMemberId(memberId)
        .stream()
        .map(h -> new PurchaseResponseDto(h, itemFileRepository.findByItemId(h.getItem().getId()).get()))
        .collect(Collectors.toList());
  }

  public List<SaleResponseDto> getSaleHistory(Long memberId) {
    return itemRepository.findByMemberId(memberId)
        .stream()
        .map(i -> new SaleResponseDto(i, itemFileRepository.findByItemId(i.getId()).get()))
        .collect(Collectors.toList());
  }

}
