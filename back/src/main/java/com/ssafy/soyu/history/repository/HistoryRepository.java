package com.ssafy.soyu.history.repository;

import com.ssafy.soyu.history.entity.History;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends JpaRepository<History, Long> {

  @Query("select h from History h join fetch h.member m join fetch h.item i where h.member.id = :memberId order by i.regDate desc")
  Optional<History> findByMemberId(@Param("memberId") Long memberId);

  @Modifying
  @Query(value = "UPDATE History h SET h.is_Delete = true WHERE h.id IN :historyIdList")
  void updateIsDelete(@Param("historyIdList") List<Long> historyIdList);

  @Modifying
  @Query("UPDATE History  h SET h.is_Delete = true WHERE h.id = :id")
  void updateIsDelete(@Param("id") Long id);

  @Query("SELECT h from History h where h.item.id = :id and h.is_Delete = false")
  History findByItemIdNotDeleted(@Param("id") Long id);

  @Query("SELECT h FROM History h " +
      "WHERE h.member.id = :memberId AND h.item.id = :itemId AND h.is_Delete = false")
  Optional<History> checkMatchHistory(@Param("memberId") Long memberId, @Param("itemId") Long itemId);
}
