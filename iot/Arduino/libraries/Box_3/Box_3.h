// Box_3.h
#ifndef Box_3_h
#define Box_3_h

#include <Wire.h>
#include <LiquidCrystal_I2C.h>

#include <Adafruit_NeoPixel.h>
#include <avr/power.h> 

#include <Arduino.h>

class Box {

public:

    LiquidCrystal_I2C lcd;          // LCD 화면
    Adafruit_NeoPixel led;          // LED 조명
    int lockPin;                    // 잠금장치 릴레이 핀 번호
    int pdlcPin;                    // PDLC 릴레이 핀 번호
    
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

    // [ 잠금장치 ]
    void lock();                        // 잠궈
    void unlock();                      // 풀어

    // [ PDLC ]
    void visible();                     // 보여
    void invisible();                   // 안보여

    /*
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
    */
};

#endif

