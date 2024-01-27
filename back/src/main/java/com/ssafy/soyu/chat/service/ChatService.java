package com.ssafy.soyu.chat.service;
import com.ssafy.soyu.chat.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.chat.dto.request.ChatRequest;
import com.ssafy.soyu.item.domain.Item;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.member.domain.Member;
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

  public void save(ChatRequest chatRequest) {
    // 구매자 가져오고, 판매자 가져오고, Item 가져와서 넣는다
    System.out.println(chatRequest.getItemId());
    Item item = itemRepository.getReferenceById(chatRequest.getItemId());
    Member buyer = memberRepository.getReferenceById(chatRequest.getBuyerId());
    Member seller = memberRepository.getReferenceById(chatRequest.getSellerId());
    Chat chat = new Chat(item, buyer, seller);
    chatRepository.save(chat);
  }
}
