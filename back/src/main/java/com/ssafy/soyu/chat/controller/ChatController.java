package com.ssafy.soyu.chat.controller;

import static com.ssafy.soyu.image.controller.ImageController.getImageResponse;
import static com.ssafy.soyu.message.controller.MessageController.getMessageResponses;
import static com.ssafy.soyu.profileImage.dto.response.ProfileImageResponse.getProfileImageResponse;
import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.chat.dto.response.ChatIdResponse;
import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.dto.response.ChatResponse;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.chat.dto.request.ChatRequest;
import com.ssafy.soyu.chat.dto.response.ChatListResponse;
import com.ssafy.soyu.chat.service.ChatService;
import com.ssafy.soyu.message.dto.response.MessageResponse;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @GetMapping("chats")
  @Operation(summary = "채팅방 목록 조회", description = "사용자 ID를 이용해 채팅방 목록을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공", content = @Content(schema = @Schema(implementation = ChatListResponse.class)))
  public ResponseEntity<?> getChats(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    List<Chat> chats = chatService.findChatByUserId(memberId);
    List<ChatListResponse> chatResponse = getChatResponses(chats);

    return getResponseEntity(SuccessCode.OK, chatResponse);
  }

  @GetMapping("chat/{chatId}")
  @Operation(summary = "채팅방 상세 조회", description = "채팅방 ID를 이용해 채팅방 세부 내용을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "채팅 상세 조회 성공", content = @Content(schema = @Schema(implementation = ChatResponse.class)))
  public ResponseEntity<?> getChat(@PathVariable("chatId") Long chatId) {
    Chat chat = chatService.findChatById(chatId);
    List<MessageResponse> messageResponses = getMessageResponses(chat.getMessage());
    ChatResponse chatResponse = getChatAndMessageResponse(chat, messageResponses);

    return getResponseEntity(SuccessCode.OK, chatResponse);
  }

  @PostMapping("chat")
  @Operation(summary = "채팅방 생성", description = "아이템 ID, 판매자와 구매자 ID를 이용해 채팅방을 생성합니다.")
  @ApiResponse(responseCode = "200", description = "채팅방 생성 성공", content = @Content(schema = @Schema(implementation = ChatIdResponse.class)))
  public ResponseEntity<?> createChat(@RequestBody ChatRequest chatRequest) {
    Chat chat = chatService.save(chatRequest);

    return getResponseEntity(SuccessCode.OK, new ChatIdResponse(chat.getId()));
  }

  // make chat Response
  private static ChatResponse getChatAndMessageResponse(Chat chat, List<MessageResponse> messageResponses) {
    return new ChatResponse(chat.getItem().getId(), chat.getItem().getTitle(), chat.getItem().getPrice() ,getImageResponse(chat.getItem().getImage()),
        chat.getSeller().getId(), chat.getSeller().getNickName(),getProfileImageResponse(chat.getSeller().getProfileImage()),
        chat.getBuyer().getId(), chat.getBuyer().getNickName(), getProfileImageResponse(chat.getBuyer().getProfileImage()),
        chat.getLastMessage(), chat.getLastDate(), chat.getIsChecked(), chat.getLastChecked(), messageResponses);
  }

  //make chat Responses
  private static List<ChatListResponse> getChatResponses(List<Chat> chats) {
    return chats.stream()
        .map(c -> new ChatListResponse(c.getId(), c.getItem().getId(), getImageResponse(c.getItem().getImage()),
            c.getSeller().getId(), c.getSeller().getNickName(),
            c.getBuyer().getId(), c.getBuyer().getNickName(),
            c.getLastMessage(), c.getLastDate(), c.getLastChecked(), c.getIsChecked()))
        .collect(Collectors.toList());
  }
}
