package models;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class Touch {

    private  final static Pin PIN = RaspiPin.GPIO_02;

    public static Pin getPin() {
        return PIN;
    }
}
