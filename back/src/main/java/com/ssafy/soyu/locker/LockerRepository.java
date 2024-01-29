package com.ssafy.soyu.locker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
  @Query("SELECT count(*) " +
      "FROM Locker l " +
      "WHERE l.station.id = :stationId AND l.status != 'EMPTY'")
  Integer countNotEmptyLocker(Long stationId);

  @Query("SELECT l FROM Locker l WHERE l.station.id = :stationId")
  List<Locker> findByStationId(@Param("stationId") Long stationId);
}
