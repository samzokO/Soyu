// ArduinoControllor_2.cpp
#include <ArduinoJson.h>
#include <Box_2.h>
// #include <MemoryFree.h>

Box boxes[3];
long timerLimit;

const size_t JSON_CAPACITY = JSON_OBJECT_SIZE(3) + 20;

void setup() { 
  Serial.begin(9600);

  timerLimit = 10;

  boxes[0] = Box(0x25, 8, 2, 5);
  boxes[1] = Box(0x26, 9, 3);
  boxes[2] = Box(0x27, 10, 4);
  boxes[0].initialize();
  boxes[1].initialize();
  boxes[2].initialize();

  pinMode(boxes[0].lockPin, OUTPUT);
  pinMode(boxes[1].lockPin, OUTPUT);
  pinMode(boxes[2].lockPin, OUTPUT);
  pinMode(boxes[0].pdlcPin, OUTPUT);

  // pinMode(LED_BUILTIN, OUTPUT);
}

bool parseJson(char* jsonData, StaticJsonDocument<JSON_CAPACITY>& doc) {
  DeserializationError error = deserializeJson(doc, jsonData);
  if (error) {
    Serial.print(F("deserializeJson() 실패: "));
    Serial.println(error.c_str());z
    return false;
  }
  return true;
}

void loop() {

  if (Serial.available()) {
    // for (int i = 0; i < 10; i++) {
    //   digitalWrite(LED_BUILTIN, HIGH);   // LED 켜기
    //   delay(250);                        // 0.25초 대기
    //   digitalWrite(LED_BUILTIN, LOW);    // LED 끄기
    //   delay(250);                        // 0.25초 대기
    // }

    char jsonData[80]; 
    Serial.readBytesUntil('\n', jsonData, sizeof(jsonData)); 
    jsonData[sizeof(jsonData) - 1] = '\0'; 

    // const size_t JSON_CAPACITY = JSON_OBJECT_SIZE(4) + 200;
    StaticJsonDocument<JSON_CAPACITY> doc;

    if (!parseJson(jsonData, doc)) return;

    int num = doc["lockerNum"].as<int>() - 1;
    long price = doc["price"].as<long>();
    char command[20]; 
    strncpy(command, doc["status"].as<const char*>(), sizeof(command) - 1);
    command[sizeof(command) - 1] = '\0';

    Serial.println("#############-Received request-#############");
    Serial.println(jsonData);
    Serial.println("############################################");

    if (strcmp(command, "RESERVE") == 0) {
      boxes[num].reserve();
    } else if (strcmp(command, "DP_INSERT") == 0) {
      boxes[num].dpInsert(price);
    } else if (strcmp(command, "DP_READY") == 0) {
      boxes[num].dpReady(price);
    } else if (strcmp(command, "TRADE_INSERT") == 0) {
      boxes[num].tradeInsert(price);
    } else if (strcmp(command, "TRADE_CHECK") == 0) {
      boxes[num].tradeCheck();
    } else if (strcmp(command, "TRADE_READY") == 0) {
      boxes[num].tradeReady();
    } else if (strcmp(command, "SUBTRACT") == 0) {
      boxes[num].subtract(); 
    } else if (strcmp(command, "WITHDRAW") == 0) {
      boxes[num].withdraw();
    }
  else {
  Serial.print("ERROR : not defined command - ");
  Serial.println(command);
  }

  while (Serial.available() > 0) {
   Serial.read(); // 버퍼에 남아 있는 데이터를 모두 읽어냅니다.  
  }

  }



  long currentTime = millis();
  // Serial.println(freeMemory());

  for(int i=0; i<3; i++) {
    long due = ( currentTime - boxes[i].time_lastCommand ) / 1000;

    if(due == boxes[i].time_duration || due>timerLimit+1) continue;
    
    boxes[i].time_duration = due;
    // Serial.println(due);

    // blink 필요 and 명령으로부터 지난 시간이 8초 이하일때 blink하기(끝나고 On상태)
    if(boxes[i].needBlink && due<=8){
      if(due%2 ==0) boxes[i].ledOn();
      else boxes[i].ledOff();
    }

    //시간에 따라 자동으로 상태를 변경해야할 경우
    if(strcmp(boxes[i].lastCommand, "DP_INSERT") == 0){
      if(due <= timerLimit) boxes[i].lcdTimer(timerLimit - due);
      else boxes[i].dpReady(boxes[i].itemPrice);
    } else if(strcmp(boxes[i].lastCommand, "TRADE_INSERT") == 0){
      if(due <= timerLimit) boxes[i].lcdTimer(timerLimit - due);
      else boxes[i].tradeReady();
    } else if(strcmp(boxes[i].lastCommand, "SUBTRACT") == 0){
      if(due <= timerLimit) boxes[i].lcdTimer(timerLimit - due);
      else boxes[i].available();
    }



  }
}