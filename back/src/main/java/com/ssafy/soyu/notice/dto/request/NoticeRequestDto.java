package com.ssafy.soyu.notice.dto.request;

import com.ssafy.soyu.notice.domain.NoticeType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * noticeType = 알림 종류 설정(ENUM)<br/>
 * title = 알림 제목<br/>
 * content = 알림 내용
 */
@Data
@RequiredArgsConstructor
public class NoticeRequestDto {
  private final NoticeType noticeType;
  private final String title;
  private final String content;
}
