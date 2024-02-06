package com.ssafy.soyu.chat.repository;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {

  // 유저의 채팅방 목록
  @EntityGraph(attributePaths = {"buyer", "seller"})
  @Query("select c from Chat c where c.buyer.id = :userId or c.seller.id = :userId")
  List<Chat> findChatByUserId(@Param("userId") Long userId);

  // 채팅방 상세 조회
  @EntityGraph(attributePaths = {"buyer", "seller", "item"})
  @Query("select c from Chat c left join fetch c.message m where c.id = :chatId")
  Chat findChatById(@Param("chatId") Long chatId);

  Chat findChatByItemAndBuyer(Item item, Member member);

}
