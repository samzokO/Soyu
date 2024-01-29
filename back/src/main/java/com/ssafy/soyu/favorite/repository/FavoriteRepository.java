package com.ssafy.soyu.favorite.repository;

import com.ssafy.soyu.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
