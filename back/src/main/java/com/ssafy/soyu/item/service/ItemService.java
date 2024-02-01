package com.ssafy.soyu.item.service;

import static com.ssafy.soyu.item.entity.Item.createItem;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.history.entity.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.dto.request.ItemCreateRequest;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.dto.request.ItemStatusRequest;
import com.ssafy.soyu.item.dto.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.raspberry.RaspberryUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final ChatRepository chatRepository;
  private final HistoryRepository historyRepository;
  private final LockerRepository lockerRepository;
  private final NoticeService noticeService;
  private final RaspberryUtil raspberryUtil;
  private final PayActionUtil payActionUtil;

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

}
