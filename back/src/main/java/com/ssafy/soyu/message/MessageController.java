package com.ssafy.soyu.message;

import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public class MessageController {

  private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
  private final MessageService messageService;
  private final MessageRepository messageRepository;

  // ws 요청
  // 메세지 pub 요청 받고 sub 로 뿌려주기 ( 내부에 메시지 DB 에 저장하는 로직 추가)
  // sub/message
  @MessageMapping("message")
  public void message (@RequestBody MessageRequest messageRequest) {
    // 들어온 메세지 DB 저장
    messageService.save(messageRequest);

    // sub 로 들어온 요청 pub 으로 뿌려주기
    template.convertAndSend("pub/message/" + messageRequest.chatId, messageRequest);
  }

  // http 요청
  // 채팅방 번호로 모든 메세지 불러오기
  @GetMapping("/messages/{chatId}")
  public ResponseEntity<?> getMessages(@PathVariable("chatId") Long chatId) {
    List<Message> messages = messageRepository.findMessagesByChatId(chatId);

    List<MessageResponse> messageResponses = messages.stream()
        .map(m -> new MessageResponse(m.getChat().getId(), m.getMember().getId(), m.getContent()))
        .collect(Collectors.toList());

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, messageResponses);
  }
}
