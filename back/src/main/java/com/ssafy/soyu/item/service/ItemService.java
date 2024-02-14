package com.ssafy.soyu.item.service;

import static com.ssafy.soyu.item.controller.ItemController.getItemListResponses;
import static com.ssafy.soyu.item.controller.ItemController.getItemResponse;
import static com.ssafy.soyu.item.entity.Item.createItem;
import static com.ssafy.soyu.locker.controller.LockerController.getLockerStationResponse;
import static io.grpc.InternalChannelz.id;

import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.dto.response.ItemListResponse;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.dto.request.ItemCreateRequest;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.dto.request.ItemStatusRequest;
import com.ssafy.soyu.item.dto.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.repository.LikesRepository;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final LikesRepository likesRepository;
  private final LockerRepository lockerRepository;

  @Value("${file.path.upload-images}")
  String uploadImagePath;

  /**
   * 단건 물품 조회<br/>유저 정보에 따라 찜 여부 포함
   *
   * @param memberId 유저 식별자
   * @param itemId   물품 식별자
   * @return ItemResponse
   */
  public ItemResponse getItem(Long memberId, Long itemId) {
    Item item = itemRepository.findItemById(itemId);

    if (item == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }

    Member member;
    Likes likes = null;
    if (memberId != null) {
      member = memberRepository.findMemberById(memberId);
      likes = likesRepository.findLikesByItemAndMember(item, member);
    }
    ItemResponse itemResponse = getItemResponse(item);

    // 찜 확인
    itemResponse.setLikesStatus(likes != null ? likes.getStatus() : false);

    // 스테이션 정보 조회
    Locker locker = lockerRepository.findLockerByItem(item);
    itemResponse.setLockerStationResponse(getLockerStationResponse(locker));
    return itemResponse;
  }

  /**
   * 전체 물품 조회<br/> 삭제 / 판매 완료 물품 제외
   *
   * @return ItemListResponse List
   */
  public List<ItemListResponse> getItems() {
    List<Item> items = itemRepository.findItemAll();
    if (items == null) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }
    List<ItemListResponse> itemResponses =
        items.stream()
            .map(o -> getItemListResponses(o, likesRepository.countLikeByItemId(o.getId())))
            .collect(Collectors.toList());
    Collections.reverse(itemResponses);
    return itemResponses;
  }

  /**
   * 키워드 물품 조회
   *
   * @param keyword 검색어
   * @return ItemListResponse List
   */
  public List<ItemListResponse> getItemByKeyword(String keyword) {
    if (keyword.isEmpty()) {
      return new ArrayList<>();
    }

    List<Item> items = itemRepository.findItemByKeyWord(keyword);
    if (items.isEmpty()) {
      return new ArrayList<>();
    }

    return items.stream()
        .map(o -> getItemListResponses(o, likesRepository.countLikeByItemId(o.getId())))
        .collect(Collectors.toList());
  }

  /**
   * 카테고리 물품 조회
   *
   * @param itemCategories 검색 카테고리
   * @return ItemListResponse List
   */
  public List<ItemListResponse> getItemByCategory(ItemCategories itemCategories) {
    List<ItemListResponse> itemResponses = itemRepository.findItemByItemCategories(itemCategories)
        .stream()
        .map(o -> getItemListResponses(o, likesRepository.countLikeByItemId(o.getId())))
        .collect(Collectors.toList());

    if (itemResponses.isEmpty()) {
      throw new CustomException(ErrorCode.NO_RESULT_ITEM);
    }

    return itemResponses;
  }

  /**
   * 물품 등록
   *
   * @param memberId    판매자 식별자
   * @param itemRequest 물품 등록 요청 Dto
   * @param files       물품의 이미지들
   * @throws IOException 예외처리
   */
  public void save(Long memberId, ItemCreateRequest itemRequest,
      List<MultipartFile> files) throws IOException {
    Member member = memberRepository.getReferenceById(memberId);

    if(member.getAccount_number() == null || member.getBank_name() == null) {
      throw new CustomException(ErrorCode.NO_HAVE_ACCOUNT);
    }
    Item item = createItem(member, itemRequest.getTitle(), itemRequest.getContent(),
        LocalDateTime.now(), itemRequest.getPrice(), itemRequest.getItemCategories(),
        ItemStatus.ONLINE);
    Item now_item = itemRepository.save(item);

    List<Image> images = new ArrayList<>();

    if (files != null) {
      String today = new SimpleDateFormat("yyMMdd").format(new Date());
      String saveFolder = uploadImagePath + File.separator + today;

      // 위에서 제작한 경로로 디렉터리를 만든다 (날짜별)
      File folder = new File(saveFolder);
      if (!folder.exists()) {
        folder.mkdirs();
      }

      for (MultipartFile file : files) {
        Image image = new Image();
        String originalFileName = file.getOriginalFilename();
        if (!originalFileName.isEmpty()) {
          String saveFileName = UUID.randomUUID().toString() // UUID 사용으로 고유한 파일의 이름 지정
              + originalFileName.substring(originalFileName.lastIndexOf('.')); // 확장자 확인
          image.makeImage(now_item, today, originalFileName, saveFileName);
          file.transferTo(new File(folder, saveFileName)); // 해당 folder에 해당 이름의 파일로 이동한다
        }
        images.add(image);
        // images -> 저장해야 한다
      }
    }
    now_item.setImage(images);
  }

  /**
   * 물품 정보 수정
   *
   * @param itemUpdateRequest 수정 요청 Dto
   */
  public void update(ItemUpdateRequest itemUpdateRequest) {
    // 바꾸려는 아이템 객체를 가져온다
    Item item = itemRepository.findItemById(itemUpdateRequest.getItemId());

    // item 의 값을 변경해서 더티체킹을 통한 업데이트를 진행한다
    item.updateItem(itemUpdateRequest.getTitle(), itemUpdateRequest.getContent(),
        itemUpdateRequest.getPrice(), itemUpdateRequest.getItemCategories());
  }

  public void updateStatus(ItemStatusRequest itemStatusRequest) {
    Item item = itemRepository.findItemById(itemStatusRequest.getItemId());

    // 더티 체킹을 통한 update
    item.updateItemStatus(itemStatusRequest.getItemStatus());
  }
}
