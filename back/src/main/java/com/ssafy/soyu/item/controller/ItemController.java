package com.ssafy.soyu.item.controller;
import com.ssafy.soyu.item.domain.request.ItemCreateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.item.Service.ItemService;
import com.ssafy.soyu.item.domain.Item;
import com.ssafy.soyu.item.domain.ItemCategories;
import com.ssafy.soyu.item.domain.response.ItemResponse;
import com.ssafy.soyu.item.domain.request.ItemStatusRequest;
import com.ssafy.soyu.item.domain.request.ItemUpdateRequest;
import com.ssafy.soyu.item.domain.request.ReserveItemRequest;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;
  private final ItemService itemService;

  // 아이템 1개 조회 {itemId}
  @GetMapping("/item/{itemId}")
  public ResponseEntity<?> getItem(@PathVariable("itemId") Long itemId ) {
    Item item = itemRepository.findById(itemId).get();
    ItemResponse itemResponse = new ItemResponse
        (item.getId(), item.getMember().getId(), item.getTitle(), item.getContent(), item.getRegDate()
        ,item.getPrice(), item.getItemStatus(), item.getItemCategories());

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponse);
  }

  // 모든 아이템 조회 List<Item>
  @GetMapping("/items")
  public ResponseEntity<?> getItems() {
    List<Item> items = itemRepository.findAll();
    List<ItemResponse> itemResponses = getItemResponses(items);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }

  // 아이템 리스트 조회 {memberId} (회원이 등록한 물건 가져오기)
  @GetMapping("/item/my")
  public ResponseEntity<?> getItemByMemberId(@RequestHeader(value = "Authorization", required = false) String bearerToken) {
    Long memberId = jwtTokenProvider.getMemberIdFromToken(memberService.getToken(bearerToken));

    List<Item> items = itemRepository.findItemByMember(memberRepository.getReferenceById(memberId));
    List<ItemResponse> itemResponses = getItemResponses(items);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }

  // 아이템 키워드 조회 List<Item>
  @GetMapping("/item/keyword/{keyword}")
  public ResponseEntity<?> getItemByKeyWord(@PathVariable("keyword") String keyword) {
    List<Item> items = itemRepository.findItemByKeyWord(keyword);
    List<ItemResponse> itemResponses = getItemResponses(items);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }

  // 수정 필요
  // enum 처리하는 방법 공부한 후에 적용 !
  // 아이템 카테고리 별 조회 List<Item>
  @GetMapping("/item/category/{category}")
  public ResponseEntity<?> getItemByCategory(@PathVariable("category") ItemCategories itemCategories) {

    List<Item> items = itemRepository.findItemByItemCategories(itemCategories);
    List<ItemResponse> itemResponses = getItemResponses(items);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }

  // 아이템 생성
  @PostMapping("item")
  public ResponseEntity<?> createItem(HttpServletRequest request,
                                      @RequestBody ItemCreateRequest itemRequest) {
    log.info(String.valueOf(itemRequest));
    Long memberId = (Long) request.getAttribute("memberId");

    itemService.save(memberId, itemRequest);
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  // 아이템 수정
  @PostMapping("item/update")
  public ResponseEntity<?> updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest) {
    // 수정 들어오는 데이터 정하고
    // 수정 들어오는 데이터 정해서 쿼리문 날린다 !
    itemService.update(itemUpdateRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  // 아이템 삭제 아래랑 같이 구현
  // 아이템 상태 변경 (수정))= 아이템 삭제 ! (enum 타입으로 변경할것이기에 soft delete)
  @PutMapping("item/status")
  public ResponseEntity<?> updateStatus(@RequestBody ItemStatusRequest itemStatusRequest) {
    log.info(String.valueOf(itemStatusRequest));
    itemService.updateStatus(itemStatusRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  // make response List
  private static List<ItemResponse> getItemResponses(List<Item> items) {
    return items.stream()
        .map(i -> new ItemResponse(i.getId(), i.getMember().getId(), i.getTitle(), i.getContent(), i.getRegDate()
            ,i.getPrice(), i.getItemStatus(), i.getItemCategories()))
        .collect(Collectors.toList());
  }

  // 찜 내용은 찜 컨트롤러에서 진행하는게 좋을듯 하다 ->

  // 찜 만들기

  // 찜한 아이템 조회

  // 찜한 아이템 삭제

  // 거래 약속 잡기
  @PostMapping("/item/reserve")
  public ResponseEntity<?> reserveItem(@RequestBody ReserveItemRequest reserveItemRequest){
    itemService.makeReserve(reserveItemRequest.getChatId());
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

}
