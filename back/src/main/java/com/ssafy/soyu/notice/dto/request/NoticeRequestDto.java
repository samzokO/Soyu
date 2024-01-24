package com.ssafy.soyu.notice.dto.request;

import com.ssafy.soyu.notice.domain.NoticeType;
import lombok.Data;

/**
 * noticeType = 알림 종류 설정(ENUM)<br/>
 * title = 알림 제목<br/>
 * content = 알림 내용
 */
@Data
public class NoticeRequestDto {
  private NoticeType noticeType;
  private String title;
  private String content;
}
