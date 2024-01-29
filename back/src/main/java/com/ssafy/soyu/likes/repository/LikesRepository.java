package com.ssafy.soyu.likes.repository;

import com.ssafy.soyu.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

}
