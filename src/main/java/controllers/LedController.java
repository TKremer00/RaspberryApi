package controllers;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import handler.JsonMessage;
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

        ctx.result(new JsonMessage(new String[][] {{"status", (led.isHigh() ? "high" : "low")}}).toString());
    }
}
