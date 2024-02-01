package com.ssafy.soyu.item.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.item.dto.request.*;
import com.ssafy.soyu.item.service.ItemService;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.dto.response.ItemResponse;

import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Item 컨트롤러", description = "Item API 입니다.")
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/{itemId}")
  @Operation(summary = "물품 단건 조회", description = "물품 ID를 이용해 세부 정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 단건 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "물품 단건 조회 실패")
  })
  public ResponseEntity<?> getItem(@PathVariable("itemId") Long itemId) {
    Item item = itemService.getItem(itemId);
    if (item == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    ItemResponse itemResponse = getItemResponse(item);

    return getResponseEntity(SuccessCode.OK, itemResponse);
  }

  @GetMapping("/items")
  @Operation(summary = "모든 물품 조회", description = "판매중인 모든 물품을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "모든 물품 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "모든 물품 조회 실패")
  })
  public ResponseEntity<?> getItems() {
    List<Item> items = itemService.getItems();
    if (items == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 물품이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return getResponseEntity(SuccessCode.OK, itemResponses);
  }

  //HistoryController 내부 판매 내역 조회와 유사 -> 하나로 통일해야 함 논의 필요
  @GetMapping("/my")
  @Operation(summary = "사용자 등록 물품 조회", description = "사용자 ID를 이용해 사용자가 등록한 모든 물품을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "등록 물품 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "등록 물품 조회 실패")
  })
  public ResponseEntity<?> getItemByMemberId(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    List<Item> items = itemService.getItemByMemberId(memberId);
    if (items == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 물품이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return getResponseEntity(SuccessCode.OK, itemResponses);
  }

  @GetMapping("/keyword/{keyword}")
  @Operation(summary = "물품 키워드 검색", description = "키워드를 이용해 물품을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 키워드 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "물품 키워드 조회 실패")
  })
  public ResponseEntity<?> getItemByKeyWord(@PathVariable("keyword") String keyword) {
    List<Item> items = itemService.getItemByKeyword(keyword);
    if (items == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 물품이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return getResponseEntity(SuccessCode.OK, itemResponses);
  }

  @GetMapping("/category/{category}")
  @Operation(summary = "물품 카테고리 검색", description = "카테고리를 이용해 물품을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 카테고리 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "물품 카테고리 조회 실패")
  })
  public ResponseEntity<?> getItemByCategory(
      @PathVariable("category") ItemCategories itemCategories) {
    if (itemCategories == null) {
      throw new CustomException(ErrorCode.NO_MATCH_CATEGORY);
    }
    List<Item> items = itemService.getItemByCategory(itemCategories);
    if (items == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    List<ItemResponse> itemResponses = getItemResponses(items);
    // 물품이 없다면 null 값이 넘어간다 -> 에러처리 불 필요
    return getResponseEntity(SuccessCode.OK, itemResponses);
  }

  @PostMapping("")
  @Operation(summary = "물품 등록", description = "ItemCreateRequest를 이용해 물품을 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 등록 성공"),
      @ApiResponse(responseCode = "400", description = "물품 등록 실패")
  })
  public ResponseEntity<?> createItem(HttpServletRequest request,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @ModelAttribute(value = "requestCreateItem") ItemCreateRequest itemRequest, BindingResult bindingResult)
      throws IOException {
    log.info(String.valueOf(itemRequest));
    Long memberId = (Long) request.getAttribute("memberId");
    if (files == null) {
      throw new CustomException(ErrorCode.NO_HAVE_IMAGE);
    }
    if (bindingResult.hasErrors()) {
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.save(memberId, itemRequest, files);

    return getResponseEntity(SuccessCode.OK);
  }

  @PutMapping("")
  @Operation(summary = "물품 수정", description = "ItemUpdateRequest를 이용해 물품을 수정합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 정보 수정 성공"),
      @ApiResponse(responseCode = "400", description = "물품 정보 수정 실패")
  })
  public ResponseEntity<?> updateItem(@Validated @RequestBody ItemUpdateRequest itemUpdateRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info(bindingResult.toString());
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.update(itemUpdateRequest);

    return getResponseEntity(SuccessCode.OK);
  }

  @PutMapping("/status")
  @Operation(summary = "물품 상태 변경", description = "ItemStatusRequest를 이용해 물품의 상태를 변경합니다.(삭제 포함)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "물품 상태 변경 성공"),
      @ApiResponse(responseCode = "400", description = "물품 상태 변경 실패")
  })
  public ResponseEntity<?> updateStatus(@Validated @RequestBody ItemStatusRequest itemStatusRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.info(bindingResult.toString());
      throw new CustomException(ErrorCode.INPUT_EXCEPTION);
    }
    itemService.updateStatus(itemStatusRequest);

    return getResponseEntity(SuccessCode.OK);
  }

  // make response List
  public static ItemResponse getItemResponse(Item item) {
    return new ItemResponse
        (item.getId(), item.getMember().getId(), item.getMember().getNickName(), item.getTitle(), item.getContent(),
            item.getRegDate()
            , item.getPrice(), item.getItemStatus(), item.getItemCategories());
  }

  public static List<ItemResponse> getItemResponses(List<Item> items) {
    return items.stream()
        .map(i -> new ItemResponse(i.getId(), i.getMember().getId(), i.getMember().getNickName(),i.getTitle(), i.getContent(),
            i.getRegDate()
            , i.getPrice(), i.getItemStatus(), i.getItemCategories()))
        .collect(Collectors.toList());
  }

  @PostMapping("/reserve")
  @Operation(summary = "거래 약속 생성", description = "ReserveItemRequest를 이용해 거래 약속을 생성합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래약속 생성 성공"),
      @ApiResponse(responseCode = "400", description = "거래약속 생성 실패")
  })
  public ResponseEntity<?> reserveItem(@RequestBody ReserveItemRequest request) {
    itemService.makeReserve(request.getChatId(), request.getLockerId());
    return getResponseEntity(SuccessCode.OK);
  }

  @DeleteMapping("/reserve")
  @Operation(summary = "거래 약속 취소", description = "DeleteReserveRequest를 이용해 거래 약속을 취소합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래약속 취소 성공"),
      @ApiResponse(responseCode = "400", description = "거래약속 취소 실패")
  })
  public ResponseEntity<?> deleteReserve(@RequestBody DeleteReserveRequest deleteReserveRequest,
      HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    itemService.deleteReserve(deleteReserveRequest.getHistoryId(), memberId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PostMapping("/match")
  @Operation(summary = "입금 매칭", description = "DepositIndoRequest를 이용해 입금 매칭을 확인합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "입금매칭 성공"),
      @ApiResponse(responseCode = "400", description = "입금매칭 실패")
  })
  public ResponseEntity<?> depositMoney(@RequestBody DepositInfoRequest depositInfoRequest) {
    itemService.depositMoney(depositInfoRequest);
    return getResponseEntity(SuccessCode.OK);
  }
}
