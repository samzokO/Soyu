package com.ssafy.soyu.history.repository;

import com.ssafy.soyu.history.domain.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoryRepository extends JpaRepository<History, Long> {

//  @Query("select h from History h where h.member.")
  List<History> findByMemberId(Long memberId);
}
