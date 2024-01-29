package com.ssafy.soyu.history.repository;

import com.ssafy.soyu.history.domain.History;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends JpaRepository<History, Long> {

  @Query("select h from History h where h.member.id = :memberId")
  Optional<History> findByMemberId(Long memberId);

  @Modifying
  @Query(value = "UPDATE History h SET h.is_Delete = true WHERE h.id IN :historyIdList")
  void updateIsDelete(List<Long> historyIdList);

  @Modifying
  @Query("UPDATE History h SET h.is_Delete = true WHERE h.id = :id")
  void updateIsDelete(@Param("id") Long id);

  @Modifying
  @Query("UPDATE History h SET h.order_number = :order_number WHERE h.id = :id")
  void updateOrderNumber(@Param("id") Long id, @Param("order_number") String order_number);
}
