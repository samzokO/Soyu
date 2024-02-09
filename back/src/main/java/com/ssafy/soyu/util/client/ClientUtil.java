package com.ssafy.soyu.util.client;

import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.util.client.dto.response.ClientRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

@Component
@RequiredArgsConstructor
public class ClientUtil {
    private final SimpMessagingTemplate messagingTemplate;

    public ClientRequestResponse makeRaspberryResponse(Long itemId, Integer lockerNum, LockerStatus tradeReserve, Integer price) {
        return  ClientRequestResponse.builder()
                .itemId(itemId)
                .lockerNum(lockerNum)
                .status(tradeReserve)
                .price(price).build();
    }

    public void sendMessageToClient(ClientRequestResponse response) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setContentType(new MimeType("application", "json"));
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSend("/sub/client", response, headerAccessor.getMessageHeaders());
    }
}
