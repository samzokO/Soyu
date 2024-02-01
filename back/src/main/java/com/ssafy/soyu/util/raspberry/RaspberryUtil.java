package com.ssafy.soyu.util.raspberry;

import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

@Component
@RequiredArgsConstructor
public class RaspberryUtil {
    private final SimpMessagingTemplate messagingTemplate;

    public RaspberryRequestResponse makeRaspberryResponse(Long id, Integer lockerNum, LockerStatus tradeReserve, Integer price) {
        return  RaspberryRequestResponse.builder()
                .itemId(id)
                .lockerNum(lockerNum)
                .status(tradeReserve)
                .price(price).build();
    }

    public void sendMessageToRaspberryPi(String destination, RaspberryRequestResponse response) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setContentType(new MimeType("application", "json"));
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSend(destination, response, headerAccessor.getMessageHeaders());
    }
}
