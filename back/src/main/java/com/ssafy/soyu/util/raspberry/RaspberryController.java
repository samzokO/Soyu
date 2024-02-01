package com.ssafy.soyu.util.raspberry;

import com.ssafy.soyu.util.raspberry.dto.response.RaspberryRequestResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
@Controller
public class RaspberryController {

    @MessageMapping("/raspberry")
    public void handleSensorData(String message) {
        System.out.println("Received message: " + message);
    }

    @MessageMapping("/raspberry/json")
    public void handleJsonData(RaspberryRequestResponse request) {

    }
}
