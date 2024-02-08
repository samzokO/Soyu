package com.ssafy.soyu.favorite.repository;

import com.ssafy.soyu.favorite.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Query("select f, s from Favorite f "
      + "join fetch f.station s "
      + "where f.member.id = :memberId AND f.status = true")
  List<Object[]> findByMemberId(Long memberId);

  @Query("SELECT f FROM Favorite f "
      + "WHERE f.member.id = :memberId AND f.station.id = :stationId")
  Favorite findByMemberAndStation(@Param("memberId") Long memberId, @Param("stationId") Long stationId);
}
