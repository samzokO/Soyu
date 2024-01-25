package com.ssafy.soyu.favorite;

import com.ssafy.soyu.locker.LockerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
