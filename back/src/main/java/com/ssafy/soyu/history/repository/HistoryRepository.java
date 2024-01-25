package com.ssafy.soyu.history.repository;

import com.ssafy.soyu.history.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
