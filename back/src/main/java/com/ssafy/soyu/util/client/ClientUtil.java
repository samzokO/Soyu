package com.ssafy.soyu.util.client;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.entity.NoticeType;
import com.ssafy.soyu.notice.service.NoticeService;
import com.ssafy.soyu.util.client.dto.response.ClientRequestResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientUtil {
    private final SimpMessagingTemplate messagingTemplate;
    private final NoticeService noticeService;
    private final ItemRepository itemRepository;


    public ClientRequestResponse makeClientResponse(Long itemId, Integer lockerNum, LockerStatus tradeReserve, Integer price) {
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

    public void willDiscountNotice(ClientRequestResponse request) {
        Item item = itemRepository.findItemById(request.getItemId());
        noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.WILL_DISCOUNT));
    }

    @Transactional
    public void discountNotice(ClientRequestResponse request) {
        Item item = itemRepository.findItemById(request.getItemId());
        itemRepository.updateDiscountPrice(request.getItemId(), request.getPrice());
        noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.DISCOUNT, request.getPrice()));
    }

    public void withdrawNotice(ClientRequestResponse request) {
        Item item = itemRepository.findItemById(request.getItemId());
        noticeService.createNotice(item.getMember().getId(), new NoticeRequestDto(item, NoticeType.WITHDRAW_EXPIRED));
    }
}
