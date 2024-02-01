package com.ssafy.soyu.item.service;

import static com.ssafy.soyu.item.entity.Item.createItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.dto.request.ItemCreateRequest;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.dto.request.ItemStatusRequest;
import com.ssafy.soyu.item.dto.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.locker.dto.response.RaspberryRequestResponse;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionProperties;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
  private final SimpMessagingTemplate messagingTemplate;

  String uploadImagePath = "C:/board/upload/imageUpload";

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

  public void save(Long memberId, ItemCreateRequest itemRequest, List<MultipartFile> files)
      throws IOException {
    List<Image> images = new ArrayList<Image>();

    if (files != null) {
      String today = new SimpleDateFormat("yyMMdd").format(new Date());
      String saveFolder = uploadImagePath + File.separator + today;

      // 위에서 제작한 경로로 디렉터리를 만든다 (날짜별)
      File folder = new File(saveFolder);
      if (!folder.exists())
        folder.mkdirs();

      for (MultipartFile file : files) {
        Image image = new Image();
        String originalFileName = file.getOriginalFilename();
        if (!originalFileName.isEmpty()) {
          String saveFileName = UUID.randomUUID().toString() // UUID 사용으로 고유한 파일의 이름 지정
              + originalFileName.substring(originalFileName.lastIndexOf('.')); // 확장자 확인

          image.makeImage(today, originalFileName, saveFileName);

          file.transferTo(new File(folder, saveFileName)); // 해당 folder에 해당 이름의 파일로 이동한다
        }
        images.add(image);
        // images -> 저장해야 한다
      }
    }
    Member member = memberRepository.getReferenceById(memberId);
    Item item = createItem(member, itemRequest.getTitle(), itemRequest.getContent(), LocalDateTime.now(), itemRequest.getPrice(), itemRequest.getItemCategories(),
        ItemStatus.ONLINE, images);
    Item now_item = itemRepository.save(item);





  }

  public void update(ItemUpdateRequest itemUpdateRequest) {
    // 바꾸려는 아이템 객체를 가져온다
    Item item = itemRepository.findItemById(itemUpdateRequest.getItemId());

    // item 의 값을 변경해서 더티체킹을 통한 업데이트를 진행한다
    item.updateItem(itemUpdateRequest.getTitle(), itemUpdateRequest.getContent(), itemUpdateRequest.getPrice(), itemUpdateRequest.getItemCategories());
  }

  public void updateStatus(ItemStatusRequest itemStatusRequest) {
    Item item = itemRepository.findItemById(itemStatusRequest.getItemId());

    // 더티 체킹을 통한 upaate
    item.updateItemStatus(itemStatusRequest.getItemStatus());
  }

  @Transactional
  public void makeReserve(Long chatId, Long lockerId){
    Chat chat = chatRepository.findById(chatId).get();
    Item item = chat.getItem();

    //온라인으로 판매중인 물건인지 체크
    if(item.getItemStatus() != ItemStatus.ONLINE){
      throw new CustomException(ErrorCode.ITEM_NOT_ONLINE);
    }
    //보관함 이용가능한지 상태 확인
    Locker locker = lockerRepository.findById(lockerId).get();
    if(locker.getStatus() != LockerStatus.AVAILABLE){
      throw new CustomException(ErrorCode.IN_USE_LOCKER);
    }

    //아이템 상태 변경
    itemRepository.updateStatus(item.getId(), ItemStatus.TRADE_RESERVE); //아이템 상태 변경

    //locker 변경
    String code = generateRandomCode();
    LocalDateTime now = LocalDateTime.now();
    String today = getCurrentDateTime(now);
    lockerRepository.updateLocker(lockerId, LockerStatus.TRADE_RESERVE, now, item.getId(), code);

    //구매내역에 추가
    History history = new History(item, chat.getBuyer());
    historyRepository.save(history);

    //주문번호 업데이트
    String orderNumber = today + history.getId();
    itemRepository.updateOrderNumber(item.getId(), orderNumber);

    //payAction API
    String orderUri = payActionProperties.getOrderUri();
    WebClient webClient = WebClient.create(orderUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", orderNumber); // 주문 번호 수정 필요
    parameters.add("order_amount", item.getPrice().toString());
    parameters.add("order_date", today);
    parameters.add("orderer_name", chat.getBuyer().getName());
    parameters.add("orderer_phone_number", chat.getBuyer().getMobile());
    parameters.add("orderer_email", chat.getBuyer().getEmail());
    parameters.add("billing_name", chat.getBuyer().getName());

    //payAction에
    webClient.post()
            .uri(orderUri)
            .bodyValue(parameters)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    noticeService.createNotice(chat.getSeller().getId(), new NoticeRequestDto(item, NoticeType.RESERVE, code));
    noticeService.createNotice(chat.getBuyer().getId(), new NoticeRequestDto(item, NoticeType.RESERVE));

    //라즈베리 파이에 신호 json 신호 보내기
    RaspberryRequestResponse response = makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.TRADE_RESERVE, item.getPrice());
    sendMessageToRaspberryPi("/sub/raspberry", response);
  }

  private RaspberryRequestResponse makeRaspberryResponse(Long id, Integer lockerNum, LockerStatus tradeReserve, Integer price) {
    return  RaspberryRequestResponse.builder()
            .itemId(id)
            .lockerNum(lockerNum)
            .status(tradeReserve)
            .price(price).build();
  }

  public void sendMessageToRaspberryPi(String destination, RaspberryRequestResponse response) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
    headerAccessor.setContentType(new MimeType("application", "json"));
    headerAccessor.setLeaveMutable(true);

    messagingTemplate.convertAndSend(destination, response, headerAccessor.getMessageHeaders());
  }


  public String getCurrentDateTime(LocalDateTime time) {
    ZonedDateTime zonedDateTime = time.atZone(ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    return zonedDateTime.format(formatter);
  }

  /**
   * 구매자 취소
   */
  @Transactional
  public void deleteReserve(Long historyId, Long memberId) {
    History history = historyRepository.findById(historyId).get();

    //아이템 상태 변경
    Item item = history.getItem();

    //histroy 변경
    historyRepository.updateIsDelete(historyId);

    //Locker 변경
    Optional<Locker> optionalLocker = lockerRepository.findByItemId(item.getId());
    if(optionalLocker.isPresent()){
      Locker locker = optionalLocker.get();
      NoticeType noticeType = null;
      LockerStatus lockerStatus = null;
      ItemStatus itemStatus = null;

      //보관한 물건 취소 했을 때
      if(locker.getStatus() == LockerStatus.TRADE_READY) {
        noticeType = NoticeType.CONVERT;
        lockerStatus = LockerStatus.WITHDRAW;
        itemStatus = ItemStatus.WITHDRAW;
      }
      //물건 넣기 전 취소
      else if(locker.getStatus() == LockerStatus.TRADE_RESERVE){
        noticeType = NoticeType.RESERVE_CANCEL;
        lockerStatus = LockerStatus.AVAILABLE;
        itemStatus = ItemStatus.ONLINE;
      }
      noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, noticeType)); //판매자에게 알림
      noticeService.createNotice(memberId, new NoticeRequestDto(item, NoticeType.RESERVE_CANCEL)); //구매자

      //locker 상태 변경, 코드 삭제 등
      lockerRepository.updateLocker(locker.getId(), lockerStatus, null, item.getId(), null);

      //item 상태 변경
      itemRepository.updateStatus(item.getId(), itemStatus);

      //라즈베리 파이에 json 신호 보내기
      RaspberryRequestResponse response = makeRaspberryResponse(item.getId(), locker.getLockerNum(), LockerStatus.WITHDRAW, item.getPrice());
      sendMessageToRaspberryPi("/sub/raspberry", response);
    }
    //payAction 매칭 취소 후 주문번호 삭제
    deletePayAction(item.getOrderNumber());
    itemRepository.deleteOrderNumber(item.getId());
  }

  public void depositMoney(DepositInfoRequest depositInfoRequest) {
    Long orderId = Long.parseLong(depositInfoRequest.getOrder_number().substring(25));
    History history = historyRepository.findById(orderId).get();
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

  public void deletePayAction(String orderNumber){
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
        .bodyToMono(String.class)
        .block();
  }
}
