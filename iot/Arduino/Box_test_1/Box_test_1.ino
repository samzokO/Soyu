// ArduinoControllor_2.cpp
#include <ArduinoJson.h>
#include <Box_2.h>

Box boxes[] = {
  Box(0x25, 8, 2, 12), // I2C, LED, Lock, PDLC
  Box(0x26, 9, 2),     // I2C, LED, Lock
  Box(0x27, 10, 2)     // I2C, LED, Lock
};

void setup() { 
  Serial.begin(9600);
  Serial.println("## setup ############");
  pinMode(boxes[0].lockPin, OUTPUT);
  pinMode(boxes[1].lockPin, OUTPUT);
  pinMode(boxes[2].lockPin, OUTPUT);
  pinMode(boxes[0].pdlcPin, OUTPUT);
}

void loop() {
  long currentTime = millis();
  Serial.println(currentTime);

  if (Serial.available()) {
    String jsonData = Serial.readStringUntil('\n');
    const size_t capacity = JSON_OBJECT_SIZE(3) + 60;
    DynamicJsonDocument doc(capacity);

    //데이터 파싱 오류 시 종료 (시리얼에 에러 출력)
    if (!parseJson(jsonData, doc)) return;

    int num = doc["num"].as<int>() - 1 ; // 락커 넘버 인덱스에 맞춰서 저장.
    long price = doc["price"].as<long>();
    String command = doc["status"].as<String>();

    Serial.println(jsonData);
    Serial.println(command);

    boxes[num].updateInfo(command, price);

    if(command=="RESERVE"){
      boxes[num].needBlink=false;
      boxes[num].reserve();
    }
    else if(command=="DP_INSERT"){
      boxes[num].needBlink=true;
      boxes[num].dpInsert();
    }
    else if(command=="TRADE_INSERT"){
      boxes[num].needBlink=true;
      boxes[num].tradeInsert();
    }
    else if(command=="TRADE_CHECK"){
      boxes[num].needBlink=true;
      boxes[num].tradeCheck();
    }
    else if(command=="TRADE_READY"){
      boxes[num].needBlink=false;
      boxes[num].tradeReady();
    }
    else if(command=="SUBTRACT"){
      boxes[num].needBlink=true;
      boxes[num].subtract();
    }
    else if(command=="WITHDRAW"){
      boxes[num].needBlink=false;
      boxes[num].withdraw();
    }else{
      Serial.print("ERROR : not defined command - ");
      Serial.println(command);
      return;
    }
  }

  for(int i=0; i<3; i++) {
    long due = ( currentTime - boxes[i].time_lastCommand ) / 1000;

    if(due == boxes[i].time_duration) continue;

    boxes[i].time_duration = due;

    // blink 필요 and 명령으로부터 지난 시간이 6초 이하일때 blink하기(끝나고 On상태)
    if(boxes[i].needBlink && due<=6){
      if(boxes[i].time_duration%2 ==0) boxes[i].ledOn();
      else boxes[i].ledOff();
    }

    String com = boxes[i].lastCommand;
    long timerLimit = 30;

    //시간에 따라 자동으로 상태를 변경해야할 경우
    if(com=="DP_INSERT"){
      if(due<=timerLimit) boxes[i].lcdTimer(timerLimit-due);
      else boxes[i].dpReady();
    }else if(com=="TRADE_INSERT"){
      if(due<=timerLimit) boxes[i].lcdTimer(timerLimit-due);
      else boxes[i].tradeReady();
    }else if(com=="SUBTRACT"){
      if(due<=timerLimit) boxes[i].lcdTimer(timerLimit-due);
      else boxes[i].available();
    }


  }
}


bool parseJson(String jsonData, DynamicJsonDocument &doc) {
    DeserializationError error = deserializeJson(doc, jsonData);
    if (error) {
        Serial.print(F("deserializeJson() 실패: "));
        Serial.println(error.c_str());
        return false;
    }
    return true;
}
