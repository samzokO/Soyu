package com.ssafy.soyu.util.jwt.repository;

import com.ssafy.soyu.util.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);
}
