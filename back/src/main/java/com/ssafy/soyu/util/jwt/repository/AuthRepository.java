package com.ssafy.soyu.util.jwt.repository;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.util.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<RefreshToken, Long>{

    @Modifying
    @Query("update RefreshToken set token = :newToken where member = :member")
    int updateRefreshTokenFindByMember(Member member, String newToken);


    @Modifying
    @Query("update RefreshToken set token = :newToken where token = :oldToken")
    int updateRefreshTokenFindByToken(String oldToken, String newToken);
}
