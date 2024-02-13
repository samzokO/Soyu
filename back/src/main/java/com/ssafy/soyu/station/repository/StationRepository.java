package com.ssafy.soyu.station.repository;

import com.ssafy.soyu.station.entity.Station;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {

  @Query("SELECT s, f.status " +
      "FROM Station s " +
      "LEFT JOIN Favorite f ON f.member.id = :memberId AND f.station.id = s.id")
  List<Object[]> findAllWithMemberId(@Param("memberId") Long memberId);

  @Query("SELECT s, f.status " +
      "FROM Station s " +
      "LEFT JOIN Favorite f ON f.member.id = :memberId AND f.station.id = s.id " +
      "WHERE s.id = :stationId")
  List<Object[]> findOneWithMemberId(@Param("memberId") Long memberId, @Param("stationId") Long stationId);

  @Query("select s from Station s where s.id = :stationId")
  Station findStationById(@Param("stationId") Long stationId);
}
