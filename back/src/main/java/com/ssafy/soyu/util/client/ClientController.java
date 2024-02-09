package com.ssafy.soyu.util.client;

import com.ssafy.soyu.util.client.dto.response.ClientRequestResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
@Controller
public class ClientController {

    @MessageMapping("/raspberry/json")
    public void handleJsonData(ClientRequestResponse request) {
        System.out.println(request);
    }
}
