package com.ssafy.soyu.util.client;

import com.ssafy.soyu.util.client.dto.response.ClientRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientUtil clientUtil;

    @MessageMapping("/client/withdraw")
    public void withdrawNotice(ClientRequestResponse request) {
        clientUtil.withdrawNotice(request);
    }

    @MessageMapping("/client/discount/notice")
    public void willDiscountNotice(ClientRequestResponse request) {
        clientUtil.willDiscountNotice(request);
    }

    @MessageMapping("/client/discount")
    public void discountNotice(ClientRequestResponse request){
        clientUtil.discountNotice(request);
    }
}