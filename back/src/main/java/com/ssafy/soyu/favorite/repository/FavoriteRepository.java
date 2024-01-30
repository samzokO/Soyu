package com.ssafy.soyu.favorite.repository;

import com.ssafy.soyu.favorite.domain.Favorite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Query("select f, s, l from Favorite f "
      + "join fetch f.station s "
      + "join fetch s.lockers l "
      + "where f.member.id = :memberId ")
  Optional<Object[]> findByMemberId(Long memberId);
}
