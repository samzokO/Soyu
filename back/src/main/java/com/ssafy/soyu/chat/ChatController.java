package com.ssafy.soyu.chat;

import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

  private final ChatRepository chatRepository;
  private final ChatService chatService;

  // 채팅방 목록 조회
  @GetMapping("chats/{userId}")
  public ResponseEntity<?> getChats(@PathVariable Long userId) {
    List<Chat> chats = chatRepository.findChatByUserId(userId);

    List<ChatResponse> chatResponse = chats.stream()
        .map(c -> new ChatResponse(c.getItem().getId(), c.getBuyer().getId(), c.getSeller().getId(), 
            c.getLastMessage(), c.getLastDate(), c.getIsChecked(), c.getLastChecked()))
        .collect(Collectors.toList());
    
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, chatResponse);
  }

  // 이미 생성된 채팅방 가져오기
  @GetMapping("chat/{chatId}")
  public ResponseEntity<?> getChat(@PathVariable Long chatId) {
    Chat chat = chatRepository.findChatById(chatId);

    ChatResponse chatResponse = new ChatResponse(chat.getItem().getId(), chat.getBuyer().getId(), chat.getSeller().getId(),
        chat.getLastMessage(), chat.getLastDate(), chat.getIsChecked(), chat.getLastChecked());

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, chatResponse);
  }

  // 새로운 채팅방 만들기
  @PostMapping("chat")
  public ResponseEntity<?> createChat(@RequestBody ChatRequest chatRequest) {
    chatService.save(chatRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

}
