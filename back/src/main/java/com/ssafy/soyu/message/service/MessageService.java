package com.ssafy.soyu.message.service;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.message.entity.Message;
import com.ssafy.soyu.message.dto.request.MessageRequest;
import com.ssafy.soyu.message.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;
  private final MemberRepository memberRepository;
  public void save(MessageRequest messageRequest) {

    // 각 저장소에서 저장을위해 가져와야 한다
    Chat chat = chatRepository.getReferenceById(messageRequest.getChatId());

    Member member = memberRepository.getReferenceById(messageRequest.getMemberID());

    Message message = new Message(chat, member, messageRequest.getContent());

    messageRepository.save(message);
  }
}
