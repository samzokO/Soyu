package com.example.demo.arduino.obj;

import lombok.Data;

@Data
public class Locker {

    long itemId;
    int itemPrice;
    String lastCommand;
    long recordTime;    //기록 시점의 시스템 시간을 저장

    public Locker(){
        itemId=0;
        itemPrice=0;
        lastCommand = "AVAILABLE";
        recordTime=-999;
    }
}
