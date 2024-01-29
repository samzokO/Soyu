package com.ssafy.soyu.locker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockerRepository extends JpaRepository<Locker, Long> {
  @Query("SELECT count(*) " +
      "FROM Locker l " +
      "WHERE l.station.id = :stationId AND l.status != 'EMPTY'")
  Integer countNotEmptyLocker(Long stationId);
}
