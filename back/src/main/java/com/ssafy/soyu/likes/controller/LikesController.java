package com.ssafy.soyu.likes.controller;
import static com.ssafy.soyu.item.controller.ItemController.getItemResponses;

import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.service.LikesService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Likes 컨트롤러", description = "Likes API 입니다.")
@Slf4j
public class LikesController {
  private final LikesService likesService;

  // 찜 조회
  @GetMapping("likes")
  public ResponseEntity<?> getLikes(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
    List<Likes> likes = likesService.getLikes(memberId);
    List<Item> items = new ArrayList<>();
    for (Likes like : likes) {
      items.add(like.getItem());
    }
    List <ItemResponse> itemResponses = getItemResponses(items);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, itemResponses);
  }

  // 찜 등록 and 찜 on off 토클 기능
  @GetMapping("likes/{itemId}")
  public ResponseEntity<?> onOffLikes(@PathVariable("itemId")Long itemId, HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
    likesService.onOff(itemId, memberId);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

}


