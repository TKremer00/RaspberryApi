package models;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class Led {
    private  final static Pin PIN = RaspiPin.GPIO_01;

    public static Pin getPin() {
        return PIN;
    }
}
