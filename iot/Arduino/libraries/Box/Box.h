// Box.h
#ifndef Box_h
#define Box_h

#include <Wire.h>
#include <LiquidCrystal_I2C.h>

#include <Adafruit_NeoPixel.h>
#include <avr/power.h> 

#include <Timer.h>
#include <Arduino.h>

class Box {

public:

    LiquidCrystal_I2C lcd;          // LCD 화면
    Adafruit_NeoPixel led;          // LED 조명 
    int lockPin;                    // 잠금장치 릴레이 핀 번호
    int pdlcPin;                    // PDLC 릴레이 핀 번호
    

    long timerStart = -1;   // 타이머가 시작됐을때의 시각 저장 -1:비활성
    long timerDuration = -1; // 타이머로 잴 시간을 저장
    long preTime = -1;



public:

    Box();
    Box(int lcdAddr, int ledPinNum, int lockPinNum, int pdlcPinNum=-1);
    void initialize();

    // [ LED 조명 ] 
    void ledOn();                          // led조명 On
    void ledOff();                         // led조명 Off
    void ledBlink();                       // led조명 5초간 Blink 이후 On
    
    // [ LCD 디스플레이 ] 
    void lcdPrice(long price);             // lcd디스플레이 가격 표시 
    void lcdContent(const char* content);  // lcd디스플레이 내용 표시 ex. Reserved, Withdraw
    void lcdTimer();                       // lcd디스플레이 타이머 표시

    // [ 잠금장치 ]
    void lock();                        // 잠궈
    void unlock();                      // 풀어

    // [ PDLC ]
    void visible();                     // 보여
    void invisible();                   // 안보여

    // [ 상태 ]  1
    void available();               // AVAILABLE

    void reserve();                 // DP_RESERVE, TRADE_RESERVE

    void dpInsert(int price);       // DP_INSERT
    void dpReady(int price);        // DP_READY

    void tradeInsert();             // TRADE_INSERT
    void tradeReady();              // TRADE_READY
    void tradeCheck();              // TRADE_CHECK

    void subtract();                // DP_SUBTRACT, TRADE_SUBTRACT, WITHDRAW_SUBTRACT
    void withdraw();                // WITHDRAW

    // [ 타이머 관련 ]
    void startTimer(long sec);              // 시간 재기 시작
    long updateTimer();             // 시간이 얼마나 남았는지 반환 
    void stopTimer();               // 시간 재기 끝


};

#endif // Box_h

