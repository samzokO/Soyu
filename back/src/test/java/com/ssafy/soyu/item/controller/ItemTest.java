package com.ssafy.soyu.item.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.station.entity.Station;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  E2E(End-to-End) 테스트는 사용자의 관점에서 시스템을 테스트
 *  사용자가 실제로 어플리케이션을 사용하는 방식을 모방하여 수행됩니다.
 *  사용자가 시스템을 통해 수행할 수 있는 모든 작업을 단계별로 살핀다.
 *  각 단계가 예상대로 작동하는지 확인
 */
@SpringBootTest
@AutoConfigureMockMvc
class ItemTest {
  @Autowired
  PayActionUtil payActionUtil;
  @Autowired
  private MockMvc mvc;

  @BeforeEach
  public void setUp() {
    // 테스트에 필요한 데이터를 삽입합니다.
  }

  /**
   * 오프라인 보관함 거래 로직
   */
  @Test
  void makeOfflineTrade() throws Exception {
    // Station
    Station station = new Station("스테이션", 36.3552f, 127.298f, "삼성화재 유성연수원");

    // 구매자 판매자 회원 가입
    Member seller = Member.builder()
        .email("seller@naver.com")
        .name("판매자")
        .nickname("판매자")
        .mobile("010-2222-2222")
        .build();

    Member buyer = Member.builder()
        .email("buyer@naver.com")
        .name("구매자")
        .nickname("구매자")
        .mobile("010-1111-1111")
        .build();

    // 판매자 물품 등록
    Item item = new Item(seller, "갤럭시패드", "갤럭시패드를 판매합니다 새상품 입니다.", LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.DP);


    String SellCode = payActionUtil.generateRandomCode();

    // 판매자 locker 에 물품 등록
    Locker locker = new Locker(station, item,null, LockerStatus.DP_READY, LocalDateTime.now(), 1);
    item.updateItemStatus(ItemStatus.DP_RESERVE);

    // 구매자 구매 코드 확인
    String BuyCode = payActionUtil.generateRandomCode();

    // PDLC 확인
    locker.updateLocker(LockerStatus.TRADE_CHECK);

    // 구매자 입금 (헤더에 가지고 있는 토큰 추가)
//    DepositInfoRequest depositInfoRequest = new DepositInfoRequest();
//    mvc.perform(post("/api/depositMoney") // Change to your actual API path
//            .header("Authorization", "Bearer " + "")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(depositInfoRequest)))
//        .andExpect(status().isOk());

    // locker 문 열림
    locker.updateLocker(LockerStatus.AVAILABLE);

    // 거래 완료
    item.updateItemStatus(ItemStatus.SOLD);

    assertEquals(ItemStatus.SOLD, item.getItemStatus());
  }

  @Test
  void makeDPTrade() throws Exception {
    // Station
    Station station = new Station("스테이션", 36.3552f, 127.298f, "삼성화재 유성연수원");

    // 판매자 회원 가입
    Member seller = Member.builder()
        .email("seller@naver.com")
        .name("판매자")
        .nickname("판매자")
        .mobile("010-2222-2222")
        .build();

    // 판매자 물품 등록
    Item item = new Item(seller, "갤럭시패드", "갤럭시패드를 판매합니다 새상품 입니다.", LocalDateTime.now(), 1, ItemCategories.ELECTRONICS, ItemStatus.DP);

    // 판매자 물건 오프라인 DP
    Locker locker = new Locker(station, item,null, LockerStatus.DP_READY, LocalDateTime.now(), 1);
    item.updateItemStatus(ItemStatus.DP);

    // 구매자 오프라인 물건 입금 헤더에 가지고 있는 토큰 추가
//    DepositInfoRequest depositInfoRequest = new DepositInfoRequest();
//    mvc.perform(post("/api/depositMoney") // Change to your actual API path
//            .header("Authorization", "Bearer " + "")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(depositInfoRequest)))
//        .andExpect(status().isOk());

    // locker 상태 변경 & 아이템 상태 변경
    locker.updateLocker(LockerStatus.AVAILABLE);
    item.updateItemStatus(ItemStatus.SOLD);

    assertEquals(ItemStatus.SOLD, item.getItemStatus());

  }

}