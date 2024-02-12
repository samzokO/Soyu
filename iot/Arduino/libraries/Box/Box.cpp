//Box.cpp
#include<Box.h>

Box::Box() : led(), lcd(0x00, 16,2)
{}

Box::Box(int lcdAddr, int ledPinNum, int lockPinNum, int pdlcPinNum)
    :
    lcd(lcdAddr, 16, 2),                 // LCD i2c 주소, 열, 행 크기
    led(15, ledPinNum), // LED 소자 갯수, led릴레이핀 번호
    lockPin(lockPinNum),                       
    pdlcPin(pdlcPinNum)
    {   
        Serial.println("CALL Box Constructor! ");
        initialize();
        Serial.println("END box Constructor! @ ");
    }


void Box::initialize() {
    Serial.println("CALL initialize");
    lcd.init();
    lcd.backlight();
    led.setBrightness(255); // 0~255
    ledOff();
}

void Box::ledOn(){
    led.clear();
    led.begin();
    Serial.println("call ledOn()");
    for (int i = 0; i < 15 ; i++) {
        led.setPixelColor(i, 255, 255, 150);
    }
    led.show();
}

void Box::ledOff(){
    led.clear();
    led.begin();
    for (int i = 0; i < 15 ; i++) {
        led.setPixelColor(i, 0, 0, 0);
    }
    led.show();
}
 
void Box::ledBlink(){
    Timer timer;
    timer.start();
    ledOff();
    bool isOn = false;
    while(timer.read()<=8000){
        int sec = timer.read()/1000;
        if(sec%2==0 && !isOn) {
            ledOn();
            isOn = true;
        }
        else if(sec%2==1 &&  isOn) {
            ledOff();
            isOn = false;
        }
    }
    ledOn();
}

void Box::lcdPrice(long price){
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("PRICE");
    lcd.setCursor(16-String(price).length(),1); // 오른쪽 정렬
    lcd.print(price);
}
               
void Box::lcdContent(const char* content){
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print(content);
}

void Box::lcdTimer(){

    long currentTime = updateTimer();

    if(currentTime==-1){
        lcd.clear();
        preTime=-1;
        return;
    }

    if(preTime != currentTime){
        preTime = currentTime;
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print("Remaining sec...");
        lcd.setCursor(16-String(currentTime).length(),1);
        lcd.print(currentTime);
    }
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
    ledOff();
    lock();
    invisible();
    lcdContent("EMPTY");
}

void Box::reserve() {
    ledOff();
    lock();
    invisible();
    lcdContent("RESERVED");
}

void Box::dpInsert(int price) {
    unlock();
    lcdContent("Here!");
    ledBlink();
    lcdTimer(20);
    lock();
    dpReady(price);
}

void Box::dpReady(int price) {
    ledOn();
    lock();
    visible();
    lcdPrice(price);
}

void Box::tradeInsert() {
    unlock();
    lcdContent("Here!");
    ledBlink();
    lcdTimer(20);
    lock();
    tradeReady();
}

void Box::tradeReady() {
    ledOff();
    lock();
    invisible();
    lcdContent("KEEPING");
}

void Box::tradeCheck() {
    lock();
    visible();
    lcdContent("CHECKING..");
    ledBlink();
    lcdTimer(15);
    invisible();
    tradeReady();
}

void Box::subtract() {
    unlock();
    lcdContent("Here!");
    ledBlink();
    lcdTimer(15);
    lock();
    available();
}

void Box::withdraw() {
    lock();
    invisible();
    ledOff();
    lcdContent("RETURN");
}

void Box::startTimer(long sec) {
    timerStart = millis();
    timerDuration = sec;
}

long Box::updateTimer() {
    if(timerStart==-1) return -1;

    long dueSec = ( millis() - timerStart) / 1000;
    long remainSec = timerDuration-dueSec;

    if(remainSec>=0) 
        return remainSec;
    else 
    {
        stopTimer();
        return -1;
    }
}

void Box::stopTimer() {
    timerStart=-1;    
}