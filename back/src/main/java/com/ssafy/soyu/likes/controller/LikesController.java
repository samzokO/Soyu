package com.ssafy.soyu.likes.controller;
import static com.ssafy.soyu.item.controller.ItemController.getItemListResponses;
import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.item.dto.response.ItemListResponse;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.service.LikesService;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "Likes 컨트롤러", description = "Likes API 입니다.")
@Slf4j
public class LikesController {
  private final LikesService likesService;

  @GetMapping("")
  @Operation(summary = "찜 목록 조회", description = "ItemCreateRequest를 이용해 물품을 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "찜 목록 조회 성공", content = @Content(schema = @Schema(implementation = ItemResponse.class))),
      @ApiResponse(responseCode = "400", description = "찜 목록 조회 실패")
  })
  public ResponseEntity<?> getLikes(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    List<Likes> likes = likesService.getLikes(memberId);
    List<Item> items = new ArrayList<>();
    for (Likes like : likes) {
      items.add(like.getItem());
    }
    List <ItemListResponse> itemListResponses = getItemListResponses(items);

    return getResponseEntity(SuccessCode.OK, itemListResponses);
  }

  @GetMapping("/{itemId}")
  @Operation(summary = "찜 등록 On & Off", description = "물품 ID와 유저 ID를 이용해 찜 정보를 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "찜 등록 성공"),
      @ApiResponse(responseCode = "400", description = "찜 등록 실패")
  })
  public ResponseEntity<?> onOffLikes(@PathVariable("itemId")Long itemId, HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    likesService.onOff(itemId, memberId);

    return getResponseEntity(SuccessCode.OK);
  }

}


