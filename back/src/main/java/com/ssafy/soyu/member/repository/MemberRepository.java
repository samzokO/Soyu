package com.ssafy.soyu.member.repository;

import com.ssafy.soyu.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Modifying
    @Query("UPDATE Member SET bank_name = :bankName, account_number = :accountNumber WHERE id = :id")
    int updateAccount(@Param("id") Long id, @Param("bankName") String bankName, @Param("accountNumber") String accountNumber);

    @Modifying
    @Query("UPDATE Member SET isWithdraw = true where id = :memberId ")
    void updateWithDraw(@Param("memberId") Long memberId);

    Optional<Member> findByNickName(String nickName);

    @Modifying
    @Query("UPDATE Member SET nickName = :nickName WHERE id= :memberId")
    void updateNickName(@Param("memberId") Long memberId, @Param("nickName") String nickName);

    @EntityGraph(attributePaths = {"profileImage"})
    @Query("SELECT m FROM Member m Where m.id = :memberId")
    Member findMemberById(@Param("memberId") Long memberId);
}
