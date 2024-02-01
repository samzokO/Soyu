package com.ssafy.soyu.notice.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.*;

import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.notice.dto.response.NoticeResponseDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "알림 조회 성공", content = @Content(schema = @Schema(implementation = NoticeResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "알림 조회 실패")
  })
  public ResponseEntity<?> selectNotice(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    return getResponseEntity(SuccessCode.OK, noticeService.findNotice(memberId));
  }

  @DeleteMapping("/{noticeId}")
  @Operation(summary = "알림 삭제", description = "사용자 ID와 알림 ID를 이용해 등록된 알림을 삭제(soft)합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "알림 삭제 성공"),
      @ApiResponse(responseCode = "400", description = "알림 삭제 실패")
  })
  public ResponseEntity<?> deleteNotice(HttpServletRequest request, @PathVariable Long noticeId) {
    Long memberId = (Long) request.getAttribute("memberId");

    //알림 유저 매칭 여부 확인
    noticeService.checkNotice(memberId, noticeId);

    noticeService.deleteNotice(noticeId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PutMapping("/{noticeId}")
  @Operation(summary = "알림 확인", description = "사용자 ID와 알림 ID를 이용해 알림을 확인 처리합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "알림 확인 성공"),
      @ApiResponse(responseCode = "400", description = "알림 확인 실패")
  })
  public ResponseEntity<?> readNotice(HttpServletRequest request, @PathVariable Long noticeId) {
    Long memberId = (Long) request.getAttribute("memberId");

    //알림 유저 매칭 여부 확인
    noticeService.checkNotice(memberId, noticeId);

    noticeService.readNotice(noticeId);
    return getResponseEntity(SuccessCode.OK);
  }
}
