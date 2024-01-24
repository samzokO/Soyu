package com.ssafy.soyu.util.fcm.repository;

import com.ssafy.soyu.util.fcm.domain.Fcm;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FcmRepository extends JpaRepository<Fcm, Long> {

  @Query(value = "select f from Fcm f where f.member.id = :memberId")
  Optional<Fcm> findByMemberId(Long memberId);
}
