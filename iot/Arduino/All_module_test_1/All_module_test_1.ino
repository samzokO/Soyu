#include <Box.h>

// Box 객체를 담을 배열 선언
Box boxes[3];
int temp =0;

void setup() {
  Serial.begin(9600);
  Serial.println();
  Serial.println("@@@@@start@@@@@");
  Serial.println();
  // boxes[0] = Box(0x25, 8, 2);
  // boxes[1] = Box(0x26, 9, 3);
  // boxes[2] = Box(0x27, 10, 4);
  for (int i = 0; i < 3; i++) {
    pinMode(boxes[i].lockPin, OUTPUT);
    pinMode(boxes[i].pdlcPin, OUTPUT);
    // boxes[i].ledOff();
  }
}

void loop() {
  if(temp==0){
    boxes[0].ledOff();
    boxes[1].ledOff();
    boxes[2].ledOff();
    Serial.println(">>>>>>>>>>>>>>>>>>>>");

    Serial.println(temp++);
    boxes[0].lcdContent("test 0");

    boxes[1].lcdContent("test 1");
    
    boxes[2].lcdContent("test 2");

    // boxes[1].ledOn();
    // boxes[2].ledOn();

    boxes[0].ledBlink();
    boxes[0].lcdContent("hi i'm soyu");
    // boxes[0].lcdPrice(80000);
    // boxes[1].lcdPrice(5);

    //타이머는 하나씩밖에 실행 안된다.
    // boxes[1].lcdTimer(10);
    // boxes[2].lcdTimer(20);

    Serial.println("<<<<<<<<<<<<<<<<<<<<");
  }
}
