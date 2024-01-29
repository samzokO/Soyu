package com.ssafy.soyu.favorite.controller;

import com.ssafy.soyu.favorite.service.FavoriteService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.ErrorResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

  private final FavoriteService favoriteService;

  /**
   * 스테이션 즐겨찾기 등록
   * @param request 사용자 식별자
   * @param stationId 즐겨찾기 할 스테이션 식별자
   * @return USER | STATION_NOT_FOUND || OK
   */
  @PostMapping("")
  public ResponseEntity<?> register(HttpServletRequest request, @RequestParam Long stationId) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return ErrorResponseEntity.toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    try {
      favoriteService.registFavorite(memberId, stationId);
    } catch (CustomException e) {
      return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  /**
   * 즐겨찾기 삭제 (Hard Delete)
   * @param favoriteId
   * @return FAVORITE_NOT_FOUND || OK
   */
  @DeleteMapping("")
  public ResponseEntity<?> delete(@RequestParam Long favoriteId){
    try {
      favoriteService.deleteFavorite(favoriteId);
    } catch (CustomException e){
      return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
}
