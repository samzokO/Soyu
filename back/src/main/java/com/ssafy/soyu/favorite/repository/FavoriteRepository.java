package com.ssafy.soyu.favorite.repository;

import com.ssafy.soyu.favorite.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Query("select f, s from Favorite f "
      + "join fetch f.station s "
      + "where f.member.id = :memberId")
  List<Object[]> findByMemberId(Long memberId);

  @Query("select f from Favorite f "
      + "where f.member.id = :memberId AND f.station.id = :stationId")
  Optional<Favorite> isExist(Long memberId, Long stationId);
}
