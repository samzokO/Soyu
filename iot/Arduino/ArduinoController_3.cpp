// ArduinoControllor_3.cpp
#include <ArduinoJson.h>
#include <Box_3.h>
// #include <MemoryFree.h>

Box boxes[3];

void setup() {
  Serial.begin(9600);

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
}
/*
void setup() {
  Serial.begin(9600);

  boxes[0] = Box(0x25, 8, 3, 6);
  boxes[1] = Box(0x26, 9, 4);
  boxes[2] = Box(0x27, 10, 5);
  boxes[0].initialize();
  boxes[1].initialize();
  boxes[2].initialize();

  boxes[0].led.begin();
  boxes[1].led.begin();
  boxes[2].led.begin();

  pinMode(boxes[0].lockPin, OUTPUT);
  pinMode(boxes[1].lockPin, OUTPUT);
  pinMode(boxes[2].lockPin, OUTPUT);
  pinMode(boxes[0].pdlcPin, OUTPUT);
}
*/
void loop() {
  if (Serial.available()) {
    char jsonData[256];
    Serial.readBytesUntil('\n', jsonData, sizeof(jsonData));
    jsonData[sizeof(jsonData) - 1] = '\0';

    const size_t capacity = JSON_OBJECT_SIZE(3) + 60;
    DynamicJsonDocument doc(capacity);

    if (!parseJson(jsonData, doc)) return;

    int num = doc["num"].as<int>() ;
    long price = doc["price"].as<long>();
    char command[20];
    strncpy(command, doc["command"].as<const char*>(), sizeof(command) - 1);
    command[sizeof(command) - 1] = '\0';

    Serial.println(F("#############-Received request-#############"));
    Serial.println(jsonData);
    Serial.println(F("############################################"));

    if(strcmp(command, "AVAILABLE") == 0) {
      boxes[num].available();
    } else if(strcmp(command, "RESERVE") == 0) {
      boxes[num].reserve();
    } else if(strcmp(command, "DP_INSERT") == 0 || strcmp(command, "TRADE_INSERT") == 0 ) {
      boxes[num].insert();
    } else if(strcmp(command, "DP_READY") == 0) {
      boxes[num].dpReady(price);
    } else if(strcmp(command, "TRADE_READY") == 0) {
      boxes[num].tradeReady();
    } else if(strcmp(command, "TRADE_CHECK") == 0) {
      boxes[num].tradeCheck();
    } else if(strcmp(command, "SUBTRACT") == 0) {
      boxes[num].subtract();
    } else if(strcmp(command, "WITHDRAW") == 0) {
      boxes[num].withdraw();
    } else if(strcmp(command, "LED_ON") == 0){
      boxes[num].ledOn();
    } else if(strcmp(command, "LED_OFF") == 0){
      boxes[num].ledOff();
    } else if(strcmp(command, "TIMER") == 0){
      boxes[num].lcdTimer(price);
    }else{
      Serial.print(F("ERROR : not defined command - "));
      Serial.println(command);
      return;
    }
  }

}


bool parseJson(char* jsonData, DynamicJsonDocument &doc) {
  DeserializationError error = deserializeJson(doc, jsonData);
  if (error) {
    Serial.print(F("deserializeJson() 실패: "));
    Serial.println(error.c_str());
    return false;
  }
  return true;
}
