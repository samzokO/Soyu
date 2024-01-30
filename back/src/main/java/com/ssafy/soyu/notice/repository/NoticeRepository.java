package com.ssafy.soyu.notice.repository;

import com.ssafy.soyu.notice.domain.Notice;
import com.ssafy.soyu.notice.dto.response.NoticeResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  @Query("select n from Notice n where n.member.id = : memberId")
  List<Notice> findByMemberId(@Param("memberId") Long memberId);

  @Query("update Notice n set n.status = 'DELETE' where n.id = :noticeId")
  void deleteNoticeByNoticeId(@Param("noticeId") Long noticeId);

  @Query("update Notice n set n.status = 'READ' where n.id = :noticeId")
  void readNoticeByNoticeId(@Param("noticeId") Long noticeId);
}
