//Box_3.cpp
#include<Box_3.h>

Box::Box() : led(), lcd(0x00, 16,2)
{}

Box::Box(int lcdAddr, int ledPinNum, int lockPinNum, int pdlcPinNum)
    :
    lcd(lcdAddr, 16, 2),                 // LCD i2c 주소, 열, 행 크기
    led(15, ledPinNum),                 // LED 소자 갯수, led핀 번호
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
}

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
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("PRICE");
    lcd.setCursor(16 - strlen(str), 1);
    lcd.print(str);
}
               
void Box::lcdContent(char* content) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(content);
}

void Box::lcdTimer(long sec){
    char buffer[20];
    snprintf(buffer, sizeof(buffer), "%ld", sec);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Remaining sec...");
    lcd.setCursor(16 - strlen(buffer), 1); // 숫자를 오른쪽 정렬
    lcd.print(buffer);
}

void Box::lock() {
    digitalWrite(lockPin, HIGH); 
}

void Box::unlock() {
    digitalWrite(lockPin, LOW);
}

void Box::visible() {
    if(pdlcPin!=-1) digitalWrite(pdlcPin, HIGH); 
}

void Box::invisible() {
    if(pdlcPin!=-1) digitalWrite(pdlcPin, LOW);
}


void Box::available() {
    Serial.println(F("CALL available()"));
    ledOff();
    lock();
    invisible();
    lcdContent("AVAILABLE");
}

void Box::reserve() {
    Serial.println(F("CALL reserve()"));
    lcdContent("RESERVED");
}

void Box::insert() {
    Serial.println(F("CALL insert()"));
    unlock();   
}

void Box::dpReady(long price) {
    Serial.println(F("CALL dpReady()"));
    lock();
    visible();
    ledOn();
    lcdPrice(price);
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
    visible();
    lcdContent("CHECKING..");
}

void Box::subtract() {
    Serial.println(F("CALL subtract()"));
    unlock();
}

void Box::withdraw() {
    lock();
    invisible();
    ledOff();
    lcdContent("RETURN");
}
