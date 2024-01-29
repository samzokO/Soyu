package com.ssafy.soyu.global;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.message.entity.Message;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class initDB {

  private final InitService initService;

  @PostConstruct
  public void init() {
    initService.dbInit1();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;
    public void dbInit1() {
      Member member1 = new Member("jun", "준영", "팔거에유", "01011111111");
      Member member2 = new Member("sung", "성현", "나만믿어", "01022222222");
      Member member3 = new Member("jin", "진식", "감자스프", "01033333333");
      Member member4 = new Member("ho", "호진", "경매빌런", "010-4444-4444");
      em.persist(member1);
      em.persist(member2);
      em.persist(member3);
      em.persist(member4);

      Item item1 = new Item(member1, "아이패드", "아이패드를 판매합니다 새상품 입니다.", LocalDateTime.now(), 100000, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item2 = new Item(member1, "말의 품격", "책을 판매합니다 좋은 책입니다..", LocalDateTime.now(),100000, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item3 = new Item(member2, "갤럭시패드", "갤럭시패드를 판매합니다 새상품 입니다.", LocalDateTime.now(),100000, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item4 = new Item(member2, "어린왕자", "책을 판매합니다 어린완자 입니다.", LocalDateTime.now(), 100000, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item5 = new Item(member3, "샤오미패드", "샤오미패드를 판매합니다 새상품 입니다.",LocalDateTime.now(), 100000, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item6 = new Item(member3, "동화책", "책을드를 판매합니다.", LocalDateTime.now(), 100000, ItemCategories.BOOKS, ItemStatus.ONLINE);

      em.persist(item1);
      em.persist(item2);
      em.persist(item3);
      em.persist(item4);
      em.persist(item5);
      em.persist(item6);

      Chat chat1 = new Chat(item1, member1, member2);
      Chat chat2 = new Chat(item3, member2, member3);
      Chat chat3 = new Chat(item5, member3, member1);
      em.persist(chat1);
      em.persist(chat2);
      em.persist(chat3);

      Message message1 = new Message(chat1, member2, "안녕하세요 jun 님" );
      Message message2 = new Message(chat1, member1, "안녕하세요 sung 님" );
      Message message3 = new Message(chat1, member2, "물품 구매 희망합니다." );

      em.persist(message1);
      em.persist(message2);
      em.persist(message3);

    }
  }

}
