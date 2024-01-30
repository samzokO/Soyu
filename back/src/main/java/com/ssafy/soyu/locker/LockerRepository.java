package com.ssafy.soyu.locker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
  @Query("SELECT count(*) " +
      "FROM Locker l " +
      "WHERE l.station.id = :stationId AND l.status != 'AVAILABLE'")
  Integer countNotEmptyLocker(Long stationId);

  @Query("SELECT l FROM Locker l LEFT JOIN FETCH l.item i WHERE l.station.id = :stationId")
  List<Locker> findByStationIdWithItem(@Param("stationId") Long stationId);

  Optional<Locker> findByItemId(Long id);

  @Modifying
  @Query("UPDATE Locker SET status = :status, time = :time, item.id = :itemId, code = :code WHERE id= :lockerId")
  void updateLocker(@Param("lockerId") Long lockerId, @Param("status") LockerStatus status,
                           @Param("time") LocalDateTime time, @Param("itemId") Long itemId, @Param("code") String code);
}
