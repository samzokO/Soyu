// Box_2.h
#ifndef Box_2_h
#define Box_2_h

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
    
    long itemPrice = 0;
    char lastCommand[20] = "";
    long time_lastCommand = -99;    // 가장 마지막 명령이 도착한 시각을 저장
    long time_duration = 0;  // 가장 마지막 명령으로부터 지난 시간을 저장
    boolean needBlink = false;

    // 명령 중에 시간을 재야하는 명령
    // insert (blink, lcdTimer)       -> 시간 지난 후 자동으로 다음 상태
    // subtract (blink, lcdTimer)     -> 시간 지난 후 자동으로 잠금
    // trade_check (blink)            -> 명령을 통해 다음 차단 상태로

public:

    Box();
    Box(int lcdAddr, int ledPinNum, int lockPinNum, int pdlcPinNum=-1);
    
    void initialize();
    void updateInfo(String info, long price);
    
    // [ LED 조명 ] 
    void ledOn();                          // led조명 On
    void ledOff();                         // led조명 Off
    
    // [ LCD 디스플레이 ] 
    void lcdPrice(long price);             // lcd디스플레이 가격 표시 
    void lcdContent(char* content);  // lcd디스플레이 내용 표시 ex. Reserved, Withdraw
    void lcdTimer(long sec);                       // lcd디스플레이 타이머 표시
    void lcdClear();

    // [ 잠금장치 ]
    void lock();                        // 잠궈
    void unlock();                      // 풀어

    // [ PDLC ]
    void visible();                     // 보여
    void invisible();                   // 안보여

    // [ 상태 ] 
    void available();               // AVAILABLE
    void reserve();                 // DP_RESERVE, TRADE_RESERVE
    
    void dpInsert(long price);       // DP_INSERT
    void dpReady(long price);        // DP_READY

    void tradeInsert(long price);             // TRADE_INSERT
    void tradeReady();              // TRADE_READY
    void tradeCheck();              // TRADE_CHECK

    void subtract();                // DP_SUBTRACT, TRADE_SUBTRACT, WITHDRAW_SUBTRACT
    void withdraw();                // WITHDRAW
};

#endif

