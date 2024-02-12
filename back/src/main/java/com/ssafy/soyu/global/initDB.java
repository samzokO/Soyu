package com.ssafy.soyu.global;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.favorite.entity.Favorite;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.message.entity.Message;
import com.ssafy.soyu.station.entity.Station;
import com.ssafy.soyu.util.fcm.entity.Fcm;
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

//  @PostConstruct
//  public void init() {
//    initService.dbInit1();
//  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;
    public void dbInit1() {
      Member member1 = new Member("jun", "최준영", "팔거에유", "01011111111");
      member1.updateMember(1L,"농협", "1234567890123", false, null);
      Member member2 = new Member("sung", "이성현", "나만믿어", "01022222222");
      member2.updateMember(2L,"국민", "1234567890123", false, null);
      Member member3 = new Member("jin", "엄진식", "감자스프", "01033333333");
      member3.updateMember(3L,"신한", "1234567890123", false, null);
      Member member4 = new Member("ho", "이호진", "경매빌런", "010-4444-4444");
      member4.updateMember(4L,"우리", "1234567890123", false, null);
      Member member5 = new Member("son", "손주현", "손다르크", "010-5555-4444");
      member5.updateMember(5L,"기업", "1234567890123", false, null);
      Member member6 = new Member("jae", "이재신", "예뻤어", "010-6666-4444");
      member6.updateMember(6L,"하나", "1234567890123", false, null);
      Member member7 = new Member("none", "비회원", "비회원", "010-9999-9999");
      member7.updateMember(7L,"신협", "1234567890123", false, null);

      em.persist(member1);
      em.persist(member2);
      em.persist(member3);
      em.persist(member4);
      em.persist(member5);
      em.persist(member6);
      em.persist(member7);

      Item item1 = new Item(member1, "아이패드", "아이패드를 판매합니다 새상품 입니다.", LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.DP);
      Item item2 = new Item(member1, "말의 품격", "책을 판매합니다 좋은 책입니다..", LocalDateTime.now(),1, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item3 = new Item(member2, "갤럭시패드", "갤럭시패드를 판매합니다 새상품 입니다.", LocalDateTime.now(),1, ItemCategories.ELECTRONICS, ItemStatus.TRADE_RESERVE);
      Item item4 = new Item(member2, "어린왕자", "책을 판매합니다 어린완자 입니다.", LocalDateTime.now(), 1, ItemCategories.BOOKS, ItemStatus.ONLINE);
      Item item5 = new Item(member3, "샤오미패드", "샤오미패드를 판매합니다 새상품 입니다.",LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.ONLINE);
      Item item6 = new Item(member6, "동화책", "책을드를 판매합니다.", LocalDateTime.now(), 1, ItemCategories.BOOKS, ItemStatus.ONLINE);

      em.persist(item1);
      em.persist(item2);
      em.persist(item3);
      em.persist(item4);
      em.persist(item5);
      em.persist(item6);

      Likes likes1 = new Likes(member1, item1, true);
      Likes likes2 = new Likes(member1, item2, true);
      Likes likes3 = new Likes(member1, item3, true);
      Likes likes4 = new Likes(member2, item1, true);
      Likes likes5 = new Likes(member3, item1, true);

      em.persist(likes1);
      em.persist(likes2);
      em.persist(likes3);
      em.persist(likes4);
      em.persist(likes5);

      Chat chat1 = new Chat(item1, member1, member2);
      Chat chat2 = new Chat(item3, member2, member3);
      Chat chat3 = new Chat(item5, member3, member1);
      em.persist(chat1);
      em.persist(chat2);
      em.persist(chat3);

      History history1 = new History(item3, member3);
      em.persist(history1);

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

      Locker locker1 = new Locker(station1, item2,null, LockerStatus.DP_READY, LocalDateTime.now(), 1);
      Locker locker2 = new Locker(station1, item3, "111111", LockerStatus.TRADE_READY, LocalDateTime.now(),2);
      Locker locker3 = new Locker(station1, item1, "222222", LockerStatus.WITHDRAW, LocalDateTime.now(), 3);
      Locker locker4 = new Locker(station2, item4,null, LockerStatus.DP_READY, LocalDateTime.now(), 1);
      Locker locker5 = new Locker(station2, item5, "333333", LockerStatus.TRADE_READY, LocalDateTime.now(),2);
      Locker locker6 = new Locker(station2, item6, "444444", LockerStatus.WITHDRAW, LocalDateTime.now(), 3);

      em.persist(locker1);
      em.persist(locker2);
      em.persist(locker3);
      em.persist(locker4);
      em.persist(locker5);
      em.persist(locker6);

      Favorite favorite1 = new Favorite(member1, station1, true);
      Favorite favorite2 = new Favorite(member1, station2, true);
      Favorite favorite3 = new Favorite(member2, station1, false);
      em.persist(favorite1);
      em.persist(favorite2);
      em.persist(favorite3);

      Fcm fcm1 = new Fcm(member6, "ebxmeFd6ujUmZZ7_NQ9Ok7:APA91bGHqjHUi_2MDPJ-O5U5BPPsnPLYdZeWz3Q5kvErlsMJttSXW2WIazYneUbXCt1nU1VXO4Yf2F5VeelMyo-A5HX5iFvfvvnHDerDb63xqtvnIMm23iY4y2EexQYs6fippHamBpy9");
      Fcm fcm2 = new Fcm(member3, "dPjaLIQsyz5vbzA8yOYLVY:APA91bHqxmrnCgitJbJKO52hBrxYdjYdV9P-yils76ZN5nqQzjYchtdLFtoXf--rUzBcfDdJHu9OFZnIy3CyHv93TEdoz0wEH_2Lc4AbY3T9dAy9qVlGuARy9PCdMvNHt8cIfwHbcNgQ");
      em.persist(fcm1);
      em.persist(fcm2);
    }
  }

}
