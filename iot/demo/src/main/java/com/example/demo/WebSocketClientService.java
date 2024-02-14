package com.example.demo;

import com.example.demo.arduino.obj.Locker;
import com.example.demo.arduino.serial.ArduinoSerialCommunicator;
import com.example.demo.dto.RaspberryRequestResponse;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

@Service
@EnableWebSocket
@EnableScheduling
public class WebSocketClientService {

    private final String url = "ws://i10b311.p.ssafy.io:8080/api/stomp/client";
    //private final String url = "ws://192.168.31.148:8080/stomp/client";
    private final String topic = "/sub/client";
    private long systemTime = 0; // 시스템 시작으로부터 지난 시간을 저장(초) -> 알림에 사용
    private final long TIMER_LIMIT = 20; // 상태 변화에 필요한 시간
    private final long TWELVE_HOURS = 60*60*12 ; // 12시간
    private Locker[] lockers;

    private StompSession stompSession;
    private WebSocketStompClient stompClient;
    private ThreadPoolTaskScheduler taskScheduler;
    private ArduinoSerialCommunicator arduinoCommunicator;

    public void connect() {
        init(); // 초기화 메서드 호출

        try {
            stompSession = stompClient.connect(url, new StompSessionHandlerAdapter() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    session.subscribe(topic, new StompFrameHandler() {
                        @Override
                        public Type getPayloadType(StompHeaders headers) {
                            return RaspberryRequestResponse.class; // 서버로부터 받는 메시지 타입
                        }

                        @Override
                        public void handleFrame(StompHeaders headers, Object payload) {
                            RaspberryRequestResponse response = (RaspberryRequestResponse) payload;
                            System.out.println("Received message: " + response);

                            long itemId = response.getItemId();
                            int lockerNum = response.getLockerNum() - 1; // 배열 인덱스에 맞추기~
                            int price = response.getPrice();
                            String command = response.getStatus().name();

                            lockers[lockerNum].setItemId(itemId);
                            lockers[lockerNum].setItemPrice(price);
                            lockers[lockerNum].setLastCommand(command);
                            lockers[lockerNum].setRecordTime(systemTime);

                            // AVAILABLE
                            // RESERVE
                            // WITHDRAW
                            // SUBTRACT     : 30초 후 AVAILABLE 전환      blink
                            // DP_INSERT    : 30초 후 DP_READY  전환      blink
                            // DP_READY
                            // TRADE_INSERT : 30초 후 TRADE_READY 전환    blink
                            // TRADE_READY
                            // TRADE_CHECK  : 별도 요청 전까지 상태 유지     blink
                            //arduinoCommunicator.sendDataToArduino(lockerNum, price, command);
                        }
                    });
                }
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //connenct 되었을때 (웹소켓 서버와 연결됏을때) 초기화
    public void init() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(taskScheduler);
        arduinoCommunicator = new ArduinoSerialCommunicator("COM4");
        arduinoCommunicator.openConnection();
        lockers = new Locker[3];
        lockers[0] = new Locker();
        lockers[1] = new Locker();
        lockers[2] = new Locker();
    }

    // 시간에 따른 변화를 수행하는 메서드, 1초마다 반복
    @Scheduled(fixedRate = 1000)
    public void executeTask() {
        System.out.println("System time in seconds: " + systemTime++);
        if(systemTime <3 ) return; // init() 메서드 호출 전 동작 방지

        // 마지막 명령 시간 확인하면서 경과된 시간에 따라 제어하기
        for (int num = 0; num < 3; num++) {

            String command = lockers[num].getLastCommand();
            long duration = systemTime - lockers[num].getRecordTime();

            if(duration == 1) {
                arduinoCommunicator.sendDataToArduino(num, lockers[num].getItemPrice(), command);
                continue;
            }

            // blink 필요할 경우 수행
//            if (duration <= 8)blink(command, num, duration);

            // 타이머 필요할 때 수행(INSERT, SUBTRACT, CHECK)
            if (duration <= TIMER_LIMIT) showTimer(command, num, duration);

            // 상태변화 (INSERT->READY, SUBTRACT->AVAILABLE 수행)
            if (duration == TIMER_LIMIT + 1) statusChange(lockers[num], num);

            // 12시간 알림 수행
            if (duration == TWELVE_HOURS && command.equals("DP_READY")) send12Alert(lockers[num], num);

            // 24시간 알림, 가격변화 수행
            if (duration == 2*TWELVE_HOURS){
              if(command.equals("DP_READY")) send24Alert(lockers[num], num);
              else if(command.equals("WITHDRAW")) sendWarningAlert(lockers[num],num);
            }

        }
    }

    public void blink(String command, int lockerNum, long duration) {
        if (command.equals("DP_INSERT") || command.equals("TRADE_INSERT") || command.equals("TRADE_CHECK") || command.equals("SUBTRACT")) {
            if (duration % 2 == 0) arduinoCommunicator.sendDataToArduino(lockerNum, 0, "LED_ON");
            else arduinoCommunicator.sendDataToArduino(lockerNum, 0, "LED_OFF");
        }
    }

    public void showTimer(String command, int lockerNum, long duration) {
        long remainTime = TIMER_LIMIT-duration;
        if (command.equals("DP_INSERT") || command.equals("TRADE_INSERT") || command.equals("SUBTRACT")) {
            // 타이머 표시할 때 어차피 가격 안필요하니까 남은시간 보낼거
            arduinoCommunicator.sendDataToArduino(lockerNum, (int)remainTime, "TIMER");
        }
    }

    public void statusChange(Locker locker, int lockerNum) {
        String command = locker.getLastCommand();

        if (command.equals("DP_INSERT")) {
            lockers[lockerNum].setLastCommand("DP_READY");
            arduinoCommunicator.sendDataToArduino(lockerNum, locker.getItemPrice(), "DP_READY");
        } else if (command.equals("TRADE_INSERT")) {
            lockers[lockerNum].setLastCommand("TRADE_READY");
            arduinoCommunicator.sendDataToArduino(lockerNum, locker.getItemPrice(), "TRADE_READY");
        } else if (command.equals("SUBTRACT")) {
            lockers[lockerNum] = new Locker();
            arduinoCommunicator.sendDataToArduino(lockerNum, locker.getItemPrice(), "AVAILABLE");
        }
    }

    // DP 물품 12시간 지났을때 할인한다고 알려주기 : /pub/client/discount/notice
    public void send12Alert(Locker locker, int lockerNum) {
        if (stompSession != null && stompSession.isConnected()) {
            String destination = "/pub/client/discount/notice";
            RaspberryRequestResponse response = new RaspberryRequestResponse();
            response.setLockerNum(lockerNum+1);
            response.setPrice(locker.getItemPrice());
            response.setItemId(locker.getItemId());
//            response.setStatus(locker.getLastCommand()); 없어도됌 -> 주현이한테 물어봐서 확인한번 해보기
            stompSession.send(destination, response);
            System.out.println("DP물품 12시간 경과 송신 성공" + destination);
        } else {
            System.out.println("DP물품 12시간 경과 송신 실패");
        }
    }

    // 24시간 지낫을때 할인됐다고 알려주기 : /pub/client/discount
    public void send24Alert(Locker locker, int lockerNum) {
        if (stompSession != null && stompSession.isConnected()) {
            String destination = "/pub/client/discount";
            int newPrice = (int)((double)locker.getItemPrice()*0.95);
            RaspberryRequestResponse response = new RaspberryRequestResponse();
            response.setLockerNum(lockerNum+1);
            response.setPrice(newPrice);
            response.setItemId(locker.getItemId());
            stompSession.send(destination, response);
            arduinoCommunicator.sendDataToArduino(lockerNum, newPrice, locker.getLastCommand());
            System.out.println("DP물품 24시간 경과 송신 성공" + destination);
        } else {
            System.out.println("DP물품 24시간 경과 송신 실패");
        }
    }

    // 회수 24시간 지났을때 알려주기 : /pub/client/withdraw
    public void sendWarningAlert(Locker locker, int lockerNum) {
        if (stompSession != null && stompSession.isConnected()) {
            String destination = "/pub/client/withdraw";
            RaspberryRequestResponse response = new RaspberryRequestResponse();
            response.setLockerNum(lockerNum+1);
            response.setPrice(locker.getItemPrice());
            response.setItemId(locker.getItemId());
            stompSession.send(destination, response);
            System.out.println("WITHDRAW 물품 24시간 경과 송신 성공" + destination);
        } else {
            System.out.println("WITHDRAW 물품 24시간 경과 송신 실패");
        }
    }

    public void sendMessage(RaspberryRequestResponse response) {
        if (stompSession != null && stompSession.isConnected()) {
            String destination = "/pub/client/json";
            stompSession.send(destination, response);
            System.out.println("Message sent to " + destination);
        } else {
            System.out.println("Not connected. Message not sent.");
        }
    }
}
