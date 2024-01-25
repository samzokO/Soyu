package com.ssafy.soyu.chat;
import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

  private final ChatRepository chatRepository;
//  private final MemberRepository memberRepository;
//  private final ItemRepository itemRepository;
  public void save(ChatRequest chatRequest) {
    // 구매자 가져오고, 판매자 가져오고, Item 가져와서 넣는다

    Item item = null;

    Member buyer = null;

    Member seller = null;

    Chat chat = new Chat(item, buyer, seller);

    chatRepository.save(chat);
  }
}
