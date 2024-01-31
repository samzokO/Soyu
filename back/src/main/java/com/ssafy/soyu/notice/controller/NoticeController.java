package com.ssafy.soyu.notice.controller;

import static com.ssafy.soyu.util.response.ErrorResponseEntity.*;

import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name = "Notice 컨트롤러", description = "Notice API 입니다.")
public class NoticeController {

  private final NoticeService noticeService;

  @GetMapping("")
  @Operation(summary = "알림 조회", description = "사용자 ID를 이용해 전체 알림을 조회합니다.")
  public ResponseEntity<?> selectNotice(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    return new ResponseEntity<>(noticeService.findNotice(memberId), HttpStatus.OK);
  }

  /**
   * 알림 삭제 처리<br/>
   * 사용자 - 알림 매칭 여부 확인
   * @param request 사용자 식별자 정보
   * @param noticeId 확인할 알림의 식별자
   */
  @DeleteMapping("/{noticeId}")
  @Operation(summary = "알림 삭제", description = "사용자 ID와 알림 ID를 이용해 등록된 알림을 삭제(soft)합니다.")
  public ResponseEntity<?> deleteNotice(HttpServletRequest request, @PathVariable Long noticeId) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    //알림 유저 매칭 여부 확인
    try {
      noticeService.checkNotice(memberId, noticeId);
    } catch (CustomException e) {
      return toResponseEntity(ErrorCode.NOT_MATCH_NOTICE);
    }

    noticeService.deleteNotice(noticeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * 알림 읽음 처리<br/>
   * 사용자 - 알림 매칭 여부 확인
   * @param request 사용자 식별자 정보
   * @param noticeId 확인할 알림의 식별자
   */
  @PutMapping("/{noticeId}")
  @Operation(summary = "알림 확인", description = "사용자 ID와 알림 ID를 이용해 알림을 확인 처리합니다.")
  public ResponseEntity<?> readNotice(HttpServletRequest request, @PathVariable Long noticeId) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    //알림 유저 매칭 여부 확인
    try {
      noticeService.checkNotice(memberId, noticeId);
    } catch (CustomException e) {
      return toResponseEntity(ErrorCode.NOT_MATCH_NOTICE);
    }

    noticeService.readNotice(noticeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
