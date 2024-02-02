package com.ssafy.soyu.util.fcm.repository;

import com.ssafy.soyu.util.fcm.entity.Fcm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FcmRepository extends JpaRepository<Fcm, Long> {

  @Query(value = "select f from Fcm f where f.member.id = :memberId")
  List<Fcm> findByMemberId(@Param("memberId") Long memberId);
}
