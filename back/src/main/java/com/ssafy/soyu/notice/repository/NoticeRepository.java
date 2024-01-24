package com.ssafy.soyu.notice.repository;

import com.ssafy.soyu.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
