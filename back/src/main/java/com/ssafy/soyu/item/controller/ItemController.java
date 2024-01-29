package com.ssafy.soyu.item.controller;
import com.ssafy.soyu.item.dto.request.*;
import com.ssafy.soyu.item.service.ItemService;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.dto.response.ItemResponse;

import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item 컨트롤러", description = "Item API 입니다.")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

  private final ItemService itemService;

  // 아이템 1개 조회 {itemId}
  @GetMapping("/item/{itemId}")
  public ResponseEntity<?> getItem(@PathVariable("itemId") Long itemId ) {
    Item item = itemService.getItem(itemId);
    ItemResponse itemResponse = getItemResponse(item);
    // 아이템이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponse);
  }

  // 모든 아이템 조회 List<Item>

  @GetMapping("/items")
  public ResponseEntity<?> getItems() {
    List<Item> items = itemService.getItems();
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 아이템이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }
  // 아이템 리스트 조회 {memberId} (회원이 등록한 물건 가져오기)

  @GetMapping("/item/my")
  public ResponseEntity<?> getItemByMemberId(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
    List<Item> items = itemService.getItemByMemberId(memberId);
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 아이템이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }
  // 아이템 키워드 조회 List<Item>

  @GetMapping("/item/keyword/{keyword}")
  public ResponseEntity<?> getItemByKeyWord(@PathVariable("keyword") String keyword) {
    List<Item> items = itemService.getItemByKeyword(keyword);
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 아이템이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }
  // 아이템 카테고리 별 조회 List<Item>

  @GetMapping("/item/category/{category}")
  public ResponseEntity<?> getItemByCategory(@PathVariable("category") ItemCategories itemCategories) {

    List<Item> items = itemService.getItemByCategory(itemCategories);
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 아이템이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }
  // 아이템 생성

  @PostMapping("item")
  public ResponseEntity<?> createItem(HttpServletRequest request,
      @Validated @RequestBody ItemCreateRequest itemRequest, BindingResult bindingResult) {
    log.info(String.valueOf(itemRequest));
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
    if (bindingResult.hasErrors()) {
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.save(memberId, itemRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
  // 아이템 수정

  @PostMapping("item/update")
  public ResponseEntity<?> updateItem(@Validated @RequestBody ItemUpdateRequest itemUpdateRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.update(itemUpdateRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
  // 아이템 삭제 아래랑 같이 구현
  // 아이템 상태 변경 (수정))= 아이템 삭제 ! (enum 타입으로 변경할것이기에 soft delete)

  @PutMapping("item/status")
  public ResponseEntity<?> updateStatus(@Validated @RequestBody ItemStatusRequest itemStatusRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.updateStatus(itemStatusRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
  // make response List

  public static ItemResponse getItemResponse(Item item) {
    return new ItemResponse
        (item.getId(), item.getMember().getId(), item.getTitle(), item.getContent(), item.getRegDate()
            , item.getPrice(), item.getItemStatus(), item.getItemCategories());
  }

  public static List<ItemResponse> getItemResponses(List<Item> items) {
    return items.stream()
        .map(i -> new ItemResponse(i.getId(), i.getMember().getId(), i.getTitle(), i.getContent(), i.getRegDate()
            ,i.getPrice(), i.getItemStatus(), i.getItemCategories()))
        .collect(Collectors.toList());
  }

  // 거래 약속 잡기
  @PostMapping("/item/reserve")
  public ResponseEntity<?> reserveItem(@RequestBody ReserveItemRequest reserveItemRequest){
    itemService.makeReserve(reserveItemRequest.getChatId());
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  @DeleteMapping("/item/reserve")
  public ResponseEntity<?> deleteReserve(@RequestBody DeleteReserveRequest deleteReserveRequest){
    itemService.deleteReserve(deleteReserveRequest.getHistoryId());
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  //입금 매칭
  @PostMapping("/item/match")
  public ResponseEntity<?> depositMoney(@RequestBody DepositInfoRequest depositInfoRequest){
    itemService.depositMoney(depositInfoRequest);
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
}
