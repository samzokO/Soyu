package com.ssafy.soyu.util.fcm.repository;

import com.ssafy.soyu.util.fcm.domain.Fcm;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FcmRepository {

  private final EntityManager em;

  /**
   * FCM 토큰 저장
   * @param fcm DB에 저장할 token
   */
  public void save(Fcm fcm) {
    em.persist(fcm);
  }

  /**
   * merberId를 이용한 FCM 토큰 조회
   * @param memberId 유저 식별자
   * @return select query
   */
  public List<Fcm> findAllByMemberId(Long memberId) {
    return em.createQuery(
        "select f from Fcm f" +
            " join fetch f.member m" +
            " where m.id = :memberId").getResultList();
  }
}
