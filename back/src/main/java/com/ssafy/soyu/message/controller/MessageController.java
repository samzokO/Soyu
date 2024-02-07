package com.ssafy.soyu.message.controller;

import com.ssafy.soyu.message.entity.Message;
import com.ssafy.soyu.message.repository.MessageRepository;
import com.ssafy.soyu.message.dto.request.MessageRequest;
import com.ssafy.soyu.message.dto.response.MessageResponse;
import com.ssafy.soyu.message.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Message 컨트롤러", description = "Message API 입니다.")
@Slf4j
@RestController
public class MessageController {

  private final SimpMessageSendingOperations template; //특정 Broker로 메세지를 전달
  private final MessageService messageService;
  private final MessageRepository messageRepository;

  // ws 요청
  // 메세지 sub 요청 받고 pub 로 뿌려주기 ( 내부에 메시지 DB 에 저장하는 로직 추가)
  // sub/message
  @MessageMapping("/message")
  public void message (@RequestBody MessageRequest messageRequest) {
    messageService.save(messageRequest);

    log.info("sender :" + messageRequest.getMemberId() + " 님이 보낸 메세지 " + messageRequest.toString());

    // pub 로 들어온 요청 sub 으로 뿌려주기
    template.convertAndSend("/sub/message/" + messageRequest.getChatId(), messageRequest);
  }

  public static List<MessageResponse> getMessageResponses(List<Message> messages) {
    return messages.stream()
        .map(m -> new MessageResponse(m.getMember().getId(), m.getContent()))
        .collect(Collectors.toList());
  }
}
