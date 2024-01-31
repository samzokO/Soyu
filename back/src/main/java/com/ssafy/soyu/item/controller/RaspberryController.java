package com.ssafy.soyu.item.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
@Controller
public class RaspberryController {

    @MessageMapping("/raspberry")
    public void handleSensorData(String message) {
        System.out.println("Received message: " + message);
    }
}
