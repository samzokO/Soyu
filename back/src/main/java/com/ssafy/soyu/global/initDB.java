package com.ssafy.soyu.global;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.message.entity.Message;
import com.ssafy.soyu.station.domain.Station;
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
      Member member1 = new Member("jun", "최준영", "팔거에유", "01011111111");
      Member member2 = new Member("sung", "이성현", "나만믿어", "01022222222");
      Member member3 = new Member("jin", "엄진식", "감자스프", "01033333333");
      Member member4 = new Member("ho", "이호진", "경매빌런", "010-4444-4444");
      Member member5 = new Member("son", "손주현", "손다르크", "010-5555-4444");
      Member member6 = new Member("jae", "이재신", "예뻤어", "010-6666-4444");
      em.persist(member1);
      em.persist(member2);
      em.persist(member3);
      em.persist(member4);
      em.persist(member5);
      em.persist(member6);

      Item item1 = new Item(member1, "아이패드", "아이패드를 판매합니다 새상품 입니다.", LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item2 = new Item(member1, "말의 품격", "책을 판매합니다 좋은 책입니다..", LocalDateTime.now(),1, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item3 = new Item(member2, "갤럭시패드", "갤럭시패드를 판매합니다 새상품 입니다.", LocalDateTime.now(),1, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item4 = new Item(member2, "어린왕자", "책을 판매합니다 어린완자 입니다.", LocalDateTime.now(), 1, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item5 = new Item(member3, "샤오미패드", "샤오미패드를 판매합니다 새상품 입니다.",LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item6 = new Item(member3, "동화책", "책을드를 판매합니다.", LocalDateTime.now(), 1, ItemCategories.BOOKS, ItemStatus.ONLINE);

      em.persist(item1);
      em.persist(item2);
      em.persist(item3);
      em.persist(item4);
      em.persist(item5);
      em.persist(item6);

      Likes likes1 = new Likes(member1, item1, true);
      Likes likes2 = new Likes(member1, item2, true);
      Likes likes3 = new Likes(member1, item3, true);

      em.persist(likes1);
      em.persist(likes2);
      em.persist(likes3);

//      History history1 = new History(item1, member2);
//      History history2 = new History(item2, member3);
//      History history3 = new History(item3, member4);
//      em.persist(history1);
//      em.persist(history2);
//      em.persist(history3);

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

      Station station1 = new Station("스테이션1", 36.3552f, 127.298f, "삼성화재 유성연수원");
      Station station2 = new Station("스테이션2", 36.3552f, 127.298f, "삼성화재 유성연수원");
      em.persist(station1);
      em.persist(station2);

      Locker locker1 = new Locker(station1, "1번");
      Locker locker2 = new Locker(station1, "2번");
      Locker locker3 = new Locker(station1, "3번");
      Locker locker4 = new Locker(station2, "1번");
      Locker locker5 = new Locker(station2, "2번");
      Locker locker6 = new Locker(station2, "3번");
      em.persist(locker1);
      em.persist(locker2);
      em.persist(locker3);
      em.persist(locker4);
      em.persist(locker5);
      em.persist(locker6);
    }
  }

}
