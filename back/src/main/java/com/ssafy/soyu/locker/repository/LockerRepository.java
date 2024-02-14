package com.ssafy.soyu.locker.repository;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
  @Query("SELECT count(*) " +
      "FROM Locker l " +
      "WHERE l.station.id = :stationId AND l.status != 'AVAILABLE'")
  Integer countNotEmptyLocker(@Param("stationId") Long stationId);

  Optional<Locker> findByItemId(Long id);

  @Modifying
  @Query("UPDATE Locker SET status = :status, time = :time, item.id = :itemId, code = :code WHERE id= :lockerId")
  void updateLocker(@Param("lockerId") Long lockerId, @Param("status") LockerStatus status,
                           @Param("time") LocalDateTime time, @Param("itemId") Long itemId, @Param("code") String code);

  @Modifying
  @Query("UPDATE Locker SET status = :status, code = :code WHERE id= :lockerId")
  void updateLockerStatusAndCode(@Param("lockerId") Long lockerId, @Param("status") LockerStatus status, @Param("code") String code);

  Optional<Locker> findByCode(String code);

  @Query("SELECT l FROM Locker l WHERE l.station.id =:stationId and l.lockerNum= :location")
  Optional<Locker> findByLocation(@Param("stationId") Long stationId, @Param("location") Integer location);

  @Query("SELECT l.code FROM Locker l " +
      "WHERE l.item.id = :itemId AND l.status = 'TRADE_READY' ")
  Optional<String> getCode(@Param("itemId") Long itemId);

  @Query("SELECT l.code from Locker l "
      + "where l.item.id = :itemId and l.item.member.id = :memberId "
      + "and l.status = 'TRADE_RESERVE'")
  Optional<String> getSaleCode(@Param("memberId") Long memberId, @Param("itemId") Long itemId);

  @EntityGraph(attributePaths = {"station"})
  Locker findLockerByItem(Item item);

  @Query("SELECT l.code from Locker l "
          + "where l.item.id = :itemId and l.item.member.id = :memberId "
          + "and l.status = 'WITHDRAW'")
  Optional<String> getWithdrawCode(@Param("memberId") Long memberId, @Param("itemId") Long itemId);
}
