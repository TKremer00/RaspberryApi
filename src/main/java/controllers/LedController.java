package controllers;

import com.pi4j.io.gpio.*;
import io.javalin.http.Context;
import models.Led;

public class LedController {

    private final static GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput led;

    public void get(Context ctx) {
        if(led == null) {
            led = gpio.provisionDigitalOutputPin(Led.getPin(), "led", PinState.LOW);
        }
        led.toggle();
        ctx.result("[{}]");
    }
}
