package com.ssafy.soyu.notice.controller;

import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.fcm.service.FcmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name = "Notice 컨트롤러", description = "Notice API 입니다.")
public class NoticeController {

  private final FcmService fcmService;
  private final NoticeService noticeService;

  @GetMapping("")
  public ResponseEntity<?> selectNotice(@RequestAttribute Long memberId){
    return new ResponseEntity<>(noticeService.findNotice(memberId), HttpStatus.OK);
  }

  @DeleteMapping("")
  public ResponseEntity<?> deleteNotice(@RequestAttribute Long noticeId){
    noticeService.deleteNotice(noticeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("")
  public ResponseEntity<?> readNotice(@RequestAttribute Long noticeId){
    noticeService.readNotice(noticeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
