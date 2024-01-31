package com.ssafy.soyu.chat.service;
import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.chat.dto.request.ChatRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

  private final ChatRepository chatRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  public List<Chat> findChatByUserId(Long memberId) {
    return chatRepository.findChatByUserId(memberId);
  }

  public Chat findChatById(Long chatId) {
    return chatRepository.findChatById(chatId);
  }

  public Chat save(ChatRequest chatRequest) {
    // 구매자 가져오고, 판매자 가져오고, Item 가져와서 넣는다
    Item item = itemRepository.getReferenceById(chatRequest.getItemId());
    Member buyer = memberRepository.getReferenceById(chatRequest.getBuyerId());
    Member seller = memberRepository.getReferenceById(chatRequest.getSellerId());

    // 이미 채팅방이 존재하는지 확인 (구매자ID 와 물건ID 를 활용)
    Chat alreadyChat = chatRepository.findChatByItemAndBuyer(item, buyer);

    // 이미 기록이 존재한다면 !
    if (alreadyChat != null) {
      return alreadyChat;
    }

    // 새로운 채팅방이 생성된다
    Chat chat = new Chat(item, buyer, seller);
    return chatRepository.save(chat);
  }
}
