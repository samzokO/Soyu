package com.ssafy.soyu.chat.controller;

import static com.ssafy.soyu.message.controller.MessageController.getMessageResponses;

import com.ssafy.soyu.chat.Chat;
import com.ssafy.soyu.chat.dto.response.ChatResponse;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.chat.dto.request.ChatRequest;
import com.ssafy.soyu.chat.dto.response.ChatListResponse;
import com.ssafy.soyu.chat.service.ChatService;
import com.ssafy.soyu.message.dto.response.MessageResponse;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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
@Tag(name = "Chat 컨트롤러", description = "Chat API 입니다.")
public class ChatController {

  private final ChatRepository chatRepository;
  private final ChatService chatService;

  // 유저의 채팅방 목록 조회
  @GetMapping("chats")
  public ResponseEntity<?> getChats(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    List<Chat> chats = chatService.findChatByUserId(memberId);
    List<ChatListResponse> chatResponse = getChatResponses(chats);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, chatResponse);
  }

  // 이미 생성된 채팅방 가져오기
  @GetMapping("chat/{chatId}")
  public ResponseEntity<?> getChat(@PathVariable("chatId") Long chatId) {
    Chat chat = chatService.findChatById(chatId);
    List<MessageResponse> messageResponses = getMessageResponses(chat.getMessage());
    ChatResponse chatResponse = getResponse(chat, messageResponses);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, chatResponse);
  }

  // 새로운 채팅방 만들기
  @PostMapping("chat")
  public ResponseEntity<?> createChat(@RequestBody ChatRequest chatRequest) {
    chatService.save(chatRequest);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }

  // make chat Response
  private static ChatResponse getResponse(Chat chat, List<MessageResponse> messageResponses) {
    return new ChatResponse(chat.getItem().getId(), chat.getBuyer().getId(), chat.getSeller().getId(),
        chat.getLastMessage(), chat.getLastDate(), chat.getIsChecked(), chat.getLastChecked(), messageResponses);
  }

  //make chat Responses
  private static List<ChatListResponse> getChatResponses(List<Chat> chats) {
    return chats.stream()
        .map(c -> new ChatListResponse(c.getItem().getId(), c.getBuyer().getId(), c.getSeller().getId(),
            c.getLastMessage(), c.getLastDate(), c.getIsChecked(), c.getLastChecked()))
        .collect(Collectors.toList());
  }
}
