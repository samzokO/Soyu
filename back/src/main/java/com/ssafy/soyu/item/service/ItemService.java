package com.ssafy.soyu.item.service;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.dto.request.ItemCreateRequest;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.dto.request.ItemStatusRequest;
import com.ssafy.soyu.item.dto.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.locker.LockerRepository;
import com.ssafy.soyu.locker.LockerStatus;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.domain.NoticeType;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionProperties;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final ChatRepository chatRepository;
  private final HistoryRepository historyRepository;
  private final PayActionProperties payActionProperties;
  private final LockerRepository lockerRepository;

  private final NoticeService noticeService;


  public Item getItem(Long itemId) {
    return itemRepository.findItemById(itemId);
  }

  public List<Item> getItems() {
    return itemRepository.findAll();
  }

  public List<Item> getItemByMemberId(Long memberId) {
    return itemRepository.findItemByMember(memberRepository.getReferenceById(memberId));
  }

  public List<Item> getItemByKeyword(String keyword) {
    return itemRepository.findItemByKeyWord(keyword);
  }

  public List<Item> getItemByCategory(ItemCategories itemCategories) {
    return itemRepository.findItemByItemCategories(itemCategories);
  }

  public void save(Long memberId, ItemCreateRequest itemRequest) {

    Member member = memberRepository.getReferenceById(memberId);

    Item item = new Item(member, itemRequest.getTitle(), itemRequest.getContent(), LocalDateTime.now(), itemRequest.getPrice(), itemRequest.getItemCategories(), ItemStatus.ONLINE);

    itemRepository.save(item);
  }

  public void update(ItemUpdateRequest itemUpdateRequest) {
    // 바꾸려는 아이템 객체를 가져온다
    Item item = itemRepository.getReferenceById(itemUpdateRequest.getItemId());

    // item 의 값을 변경해서 더티체킹을 통한 업데이트를 진행한다
    item.updateItem(itemUpdateRequest.getTitle(), itemUpdateRequest.getContent(), itemUpdateRequest.getPrice(), itemUpdateRequest.getItemCategories());
  }

  public void updateStatus(ItemStatusRequest itemStatusRequest) {
    Item item = itemRepository.getReferenceById(itemStatusRequest.getItemId());

    // 더티 체킹을 통한 upaate
    item.updateItemStatus(itemStatusRequest.getItemStatus());
  }

  @Transactional
  public void makeReserve(Long chatId, Long lockerId, LocalDateTime reserveTime){
    Chat chat = chatRepository.findById(chatId).get();
    Item item = chat.getItem();

    //온라인으로 판매중인 물건인지 체크
    if(item.getItemStatus() != ItemStatus.ONLINE){
      throw new CustomException(ErrorCode.ITEM_NOT_ONLINE);
    }
    //보관함 이용가능한지 상태 확인
    if(lockerRepository.findById(lockerId).get().getStatus() != LockerStatus.AVAILABLE){
      throw new CustomException(ErrorCode.IN_USE_LOCKER);
    }

    //아이템 상태 변경
    itemRepository.updateStatus(item.getId(), ItemStatus.RESERVE); //아이템 상태 변경
    String today = getCurrentDateTime();
    History history = new History(item, chat.getBuyer());

    String code = generateRandomCode();

    Locker locker = new Locker(item, code, LockerStatus.RESERVED, reserveTime);
    lockerRepository.save(locker);

    //구매내역에 추가
    historyRepository.save(history);

    //주문번호 업데이트
    String orderNumber = today + history.getId();
    historyRepository.updateOrderNumber(history.getId(), orderNumber);

    //payAction API
//    String orderUri = payActionProperties.getOrderUri();
//    WebClient webClient = WebClient.create(orderUri);
//    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//    parameters.add("apikey", payActionProperties.getApiKey());
//    parameters.add("secretkey", payActionProperties.getSecretKey());
//    parameters.add("mall_id", payActionProperties.getStoreId());
//    parameters.add("order_number", orderNumber); // 주문 번호 수정 필요
//    parameters.add("order_amount", item.getPrice().toString());
//    parameters.add("order_date", today);
//    parameters.add("orderer_name", chat.getBuyer().getName());
//    parameters.add("orderer_phone_number", chat.getBuyer().getMobile());
//    parameters.add("orderer_email", chat.getBuyer().getEmail());
//    parameters.add("billing_name", chat.getBuyer().getName());
//
//    //payAction에
//    webClient.post()
//            .uri(orderUri)
//            .bodyValue(parameters)
//            .retrieve()
//            .bodyToMono(String.class)
//            .block();
    noticeService.createNotice(chat.getSeller().getId(), new NoticeRequestDto(NoticeType.RESERVE, "거래 예약이 완료 되었습니다", "보관함에 가서 코드를 입력하세요\n코드: "+code));
    noticeService.createNotice(chat.getBuyer().getId(), new NoticeRequestDto(NoticeType.RESERVE, "거래 예약이 완료 되었습니다", "판매자가 물품을 넣은 후 다시 알려드릴게요! :)"));
  }

  public String getCurrentDateTime() {
    LocalDateTime now = LocalDateTime.now();
    ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    return zonedDateTime.format(formatter);
  }

  public void deleteReserve(Long historyId) {
    History history = historyRepository.findById(historyId).get();
    ItemStatus status = ItemStatus.from("ONLINE");
    itemRepository.updateStatus(history.getItem().getId(), status); //아이템 상태 변경

    //histroy 변경
    historyRepository.updateIsDelete(historyId);

    //payAction 매칭 취소
    String orderExcludeUri = payActionProperties.getOrderExcludeUri();
    WebClient webClient = WebClient.create(orderExcludeUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", history.getOrder_number());

    webClient.post()
            .uri(orderExcludeUri)
            .bodyValue(parameters)
            .retrieve()
            .bodyToMono(String.class)
            .block();
  }

  public void depositMoney(DepositInfoRequest depositInfoRequest) {
    Long historyId = Long.parseLong(depositInfoRequest.getOrder_number().substring(25));
    History history = historyRepository.findById(historyId).get();
    Item item = history.getItem();

    // 아이템 판매 완료
    itemRepository.updateStatus(item.getId(), ItemStatus.SOLD);
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
}
