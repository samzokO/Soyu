//Box_2.cpp
#include<Box_2.h>

Box::Box() : led(), lcd(0x00, 16,2)
{}

Box::Box(int lcdAddr, int ledPinNum, int lockPinNum, int pdlcPinNum)
    :
    lcd(lcdAddr, 16, 2),                 // LCD i2c 주소, 열, 행 크기
    led(15, ledPinNum),                 // LED 소자 갯수, led릴레이핀 번호
    lockPin(lockPinNum),                       
    pdlcPin(pdlcPinNum)
    {   
        Serial.println(F("CALL Box Constructor! "));
    }


void Box::initialize() {
    Serial.println(F("CALL initialize"));
    lcd.init();
    lcd.backlight();
    led.setBrightness(255); // 0~255
    led.begin();
    invisible();
    available();
}

// void Box::updateInfo(String command, long price) {
//     itemPrice = price;
//     lastCommand = command;
//     time_lastCommand = millis();
//     time_duration = 0;
// }

void Box::ledOn(){
    Serial.println(F("call ledOn()"));
    for (int i = 0; i < 15 ; i++) {
        led.setPixelColor(i, 255, 255, 150);
    }
    led.show();
}

void Box::ledOff(){
    for (int i = 0; i < 15 ; i++) {
        led.setPixelColor(i, 0, 0, 0);
    }
    led.show();
}
 
void Box::lcdPrice(long price) {
    char str[10]; // 충분한 크기의 char 배열 선언
    snprintf(str, sizeof(str), "%ld", price); // 숫자를 문자열로 변환
    lcdClear();
    lcd.setCursor(0, 0);
    lcd.print("PRICE");
    int length = strlen(str);
    lcd.setCursor(16 - length, 1);
    lcd.print(str);
}
               
void Box::lcdContent(char* content) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(content);
}

void Box::lcdTimer(long sec){
    lcdClear();
    lcd.setCursor(0,0);
    lcd.print("Remaining sec...");
    lcd.setCursor(16-String(sec).length(),1);
    lcd.print(sec);
}

void Box::lcdClear(){
    lcd.clear();
}

void Box::lock() {
    digitalWrite(lockPin, HIGH); 
}

void Box::unlock() {
    digitalWrite(lockPin, LOW);
}

void Box::visible() {
    if(pdlcPin!=-1) digitalWrite(pdlcPin, LOW); 
}

void Box::invisible() {
    if(pdlcPin!=-1) digitalWrite(pdlcPin, HIGH);
}

void Box::available() {
    Serial.println(F("CALL available()"));
    itemPrice = 0;
    strncpy(lastCommand, "", sizeof(lastCommand) - 1);
    time_lastCommand = -99;    
    time_duration = 0;  
    needBlink = false;
    ledOff();
    lock();
    invisible();
    lcd.clear();
    lcdContent("EMPTY");
}

void Box::reserve() {
    Serial.println(F("CALL reserve()"));
    lcd.clear();
    lcdContent("RESERVED");
}

void Box::dpInsert(long price) {
    // Serial.println(F("CALL dpInsert() !!!!!!!!!!"));
    itemPrice = price;
    strncpy(lastCommand, "DP_INSERT", sizeof(lastCommand) - 1);
    Serial.println("???");
    Serial.println(lastCommand);
    Serial.println("???");
    time_lastCommand = millis();    
    time_duration = 0;
    needBlink = true;
    unlock();
}

void Box::dpReady(long price) {
    Serial.println(F("CALL dpReady()"));
    lock();
    visible();
    ledOn();
    lcdPrice(price);
}

void Box::tradeInsert(long price) {
    Serial.println(F("CALL tradeInsert()"));
    itemPrice = price;
    strncpy(lastCommand, "TRADE_INSERT", sizeof(lastCommand) - 1);
    time_lastCommand = millis();    
    time_duration = 0;
    needBlink = true;
    unlock();
}

void Box::tradeReady() {
    Serial.println(F("CALL tradeReady()"));
    ledOff();
    lock();
    invisible();
    lcdContent("KEEPING");
}

void Box::tradeCheck() {
    Serial.println(F("CALL tradeCheck()"));
    strncpy(lastCommand, "TRADE_CHECK", sizeof(lastCommand) - 1);
    time_lastCommand = millis();
    time_duration = 0;
    needBlink = true;
    visible();
    lcdContent("CHECKING..");
}

void Box::subtract() {
    Serial.println(F("CALL subtract()"));
    strncpy(lastCommand, "SUBTRACT", sizeof(lastCommand) - 1);
    time_lastCommand = millis();
    time_duration = 0;
    needBlink = true;
    unlock();
}

void Box::withdraw() {
    lock();
    invisible();
    ledOff();
    lcdContent("RETURN");
}
