package com.example.demo.arduino.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;

public class ArduinoSerialCommunicator {

    private SerialPort serialPort;

    public ArduinoSerialCommunicator(String portDescription) {
        serialPort = SerialPort.getCommPort(portDescription);
        serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        serialPort.clearDTR();
    }

    public boolean openConnection() {
        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
            return true;
        } else {
            System.out.println("Unable to open the port.");
            return false;
        }
    }

    public void sendDataToArduino(int num, int price, String command) {
        JSONObject json = new JSONObject();
        json.put("num", num);
        json.put("price", price);
        json.put("command", command);
        String jsonString = json.toString() + "\n"; // 아두이노에서 개행 문자를 통해 메시지의 끝을 인식하게 합니다.

        serialPort.writeBytes(jsonString.getBytes(), jsonString.getBytes().length);
    }

    public boolean closeConnection() {
        if (serialPort.closePort()) {
            System.out.println("Port closed successfully.");
            return true;
        } else {
            System.out.println("Unable to close the port.");
            return false;
        }
    }

    // 메인 메소드를 테스트용으로 포함할 수 있습니다.
    public static void main(String[] args) {
        ArduinoSerialCommunicator communicator = new ArduinoSerialCommunicator("COM3");
        communicator.sendDataToArduino(123, 456, "TestCommand");
    }
}
