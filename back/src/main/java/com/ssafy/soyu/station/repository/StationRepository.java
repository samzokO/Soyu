package com.ssafy.soyu.station.repository;

import com.ssafy.soyu.station.domain.Station;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {

  @Query("SELECT s, " +
      "CASE WHEN f.id IS NOT NULL THEN true ELSE false END AS isFavorite " +
      "FROM Station s " +
      "JOIN FETCH s.lockers l " +
      "LEFT JOIN Favorite f ON s.id = f.station.id AND f.member.id = :memberId " +
      "GROUP BY s.id")
  List<Object[]> findAllWithMemberId(@Param("memberId") Long memberId);

  @Query("SELECT s, l, CASE WHEN f.id IS NOT NULL THEN true ELSE false END AS isFavorite " +
      "FROM Station s " +
      "LEFT JOIN Locker l ON s.id = l.station.id " +
      "LEFT JOIN Favorite f ON s.id = f.station.id AND f.member.id = :memberId " +
      "WHERE s.id = :stationId")
  Optional<Object[]> findOneWithMemberId(Long memberId, Long stationId);
}
