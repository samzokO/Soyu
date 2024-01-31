package com.ssafy.soyu.favorite.repository;

import com.ssafy.soyu.favorite.domain.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Query("select f, s from Favorite f "
      + "join fetch f.station s "
      + "where f.member.id = :memberId")
  List<Object[]> findByMemberId(Long memberId);
}
