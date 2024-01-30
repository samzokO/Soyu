package com.ssafy.soyu.station.repository;

import com.ssafy.soyu.station.domain.Station;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {

  @Query("SELECT s, CASE WHEN f.id IS NOT NULL THEN true ELSE false END AS isFavorite " +
      "FROM Station s " +
      "LEFT JOIN Favorite f ON f.member.id = :memberId AND f.station.id = s.id")
  List<Object[]> findAllWithMemberId(@Param("memberId") Long memberId);

  @Query("SELECT DISTINCT s FROM Station s " +
      "JOIN FETCH s.lockers ls " +
      "WHERE s.id = :stationId")
  Optional<Station> findOneWithMemberId(@Param("stationId") Long stationId);
}
