package com.ssafy.soyu.notice.repository;

import com.ssafy.soyu.notice.entity.Notice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  @Query("select n from Notice n join fetch n.receiver m join fetch n.item i where n.receiver.id = :receiverId")
  List<Notice> findByMemberId(@Param("receiverId") Long receiverId);

  @Query("update Notice n set n.status = 'DELETE' where n.id = :noticeId")
  void deleteNoticeByNoticeId(@Param("noticeId") Long noticeId);

  @Query("update Notice n set n.status = 'READ' where n.id = :noticeId")
  void readNoticeByNoticeId(@Param("noticeId") Long noticeId);

  @Query("select n from Notice n where n.receiver.id = :receiverId AND n.id = :noticeId")
  Notice checkNoticeMatchMember(@Param("receiverId") Long receiverId, @Param("noticeId") Long noticeId);
}
