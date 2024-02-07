package com.ssafy.soyu.util.payaction;

import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.raspberry.RaspberryUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
@Transactional
@RequiredArgsConstructor
public class PayActionUtil {

  private final NoticeService noticeService;
  private final HistoryRepository historyRepository;
  private final ItemRepository itemRepository;
  private final LockerRepository lockerRepository;
  private final RaspberryUtil raspberryUtil;
  private final PayActionProperties payActionProperties;

  public void makePayAction(String orderNumber, Integer price, String orderDate, Member member) {
    String orderUri = payActionProperties.getOrderUri();
    WebClient webClient = WebClient.create(orderUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", orderNumber); // 주문 번호 수정 필요
    parameters.add("order_amount", price.toString());
    parameters.add("order_date", orderDate);
    parameters.add("orderer_name", member.getName());
    parameters.add("orderer_phone_number", member.getMobile());
    parameters.add("orderer_email", member.getEmail());
    parameters.add("billing_name", member.getName());

    //payAction에
    webClient.post()
        .uri(orderUri)
        .bodyValue(parameters)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public void makeNoneMemberPayAction(String orderNumber, Integer price, String orderDate,
      Long historyId) {
    String orderUri = payActionProperties.getOrderUri();
    WebClient webClient = WebClient.create(orderUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", orderNumber); // 주문 번호 수정 필요
    parameters.add("order_amount", price.toString());
    parameters.add("order_date", orderDate);
    parameters.add("orderer_name", String.valueOf(historyId));
    parameters.add("orderer_phone_number", "010-0000-0000");
    parameters.add("orderer_email", "soyu@soyu.com");
    parameters.add("billing_name", String.valueOf(historyId));

    //payAction에
    webClient.post()
        .uri(orderUri)
        .bodyValue(parameters)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public void deletePayAction(String orderNumber) {
    String orderExcludeUri = payActionProperties.getOrderExcludeUri();
    WebClient webClient = WebClient.create(orderExcludeUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", orderNumber);

    webClient.post()
        .uri(orderExcludeUri)
        .bodyValue(parameters)
        .retrieve()
        .bodyToMono(String.class).block();
  }

  public String getCurrentDateTime(LocalDateTime time) {
    ZonedDateTime zonedDateTime = time.atZone(ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    return zonedDateTime.format(formatter);
  }

  // 6자리 랜덤 숫자 코드를 생성하는 메소드
  public String generateRandomCode() {
    Random random = new Random();
    StringBuilder code = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int digit = random.nextInt(10); // 0부터 9까지의 숫자 생성
      code.append(digit); // 생성된 숫자를 문자열에 추가
    }

    return code.toString();
  }

  public void depositMoney(DepositInfoRequest depositInfoRequest) {
    Long orderId = Long.parseLong(depositInfoRequest.getOrder_number().substring(25));
    History history = historyRepository.findById(orderId).get();
    Item item = history.getItem();

    // 아이템 판매 완료
    itemRepository.updateStatus(item.getId(), ItemStatus.SOLD);

    //locker 상태 변경 및 다른 상태 없애기
    Locker locker = lockerRepository.findByItemId(item.getId()).get();
    lockerRepository.updateLocker(locker.getId(), LockerStatus.AVAILABLE, null, null, null);

    //판매 완료 알림 전송
    if (history.getMember().getId() == 1) //DP
      noticeService.createNotice(item.getMember().getId(),
          new NoticeRequestDto(item, NoticeType.DP_SELL));
    else //Trade
      noticeService.createNoticeWithSender(item.getMember().getId(), history.getMember().getId(),
          new NoticeRequestDto(item, NoticeType.RESERVE_SELL));

    raspberryUtil.sendMessageToRaspberryPi(
        raspberryUtil.makeRaspberryResponse(item.getId(), locker.getLockerNum(),
            LockerStatus.SUBTRACT, item.getPrice()));
  }
}
