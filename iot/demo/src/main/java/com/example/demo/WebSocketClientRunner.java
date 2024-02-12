package com.example.demo;

import com.example.demo.dto.LockerStatus;
import com.example.demo.dto.RaspberryRequestResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class WebSocketClientRunner implements CommandLineRunner {

    private final WebSocketClientService webSocketClientService;

    public WebSocketClientRunner(WebSocketClientService webSocketClientConfig) {
        this.webSocketClientService = webSocketClientConfig;
    }

    // DemoApplication을 실행할 때 한번만 실행되는 로직 -> connect 수행
    @Override
    public void run(String... args) throws Exception {
        webSocketClientService.connect();
        System.out.println("WebSocket Client is trying to connect...");

        // 이거는 그냥 내가 클라이언트 -> 서버로 보내는 로직 테스트 해보려고 여기다가 넣었는데 오빠가 필요한 곳에 이런식으로 해서 넘겨주면 될 듯 함.
        RaspberryRequestResponse response = new RaspberryRequestResponse();
        response.setItemId((long)100);
        response.setPrice(100);
        response.setLockerNum(100);
        response.setStatus(LockerStatus.AVAILABLE);
        webSocketClientService.sendMessage(response);
    }
}
