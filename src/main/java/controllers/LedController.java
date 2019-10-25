package controllers;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import handler.JsonMessageHandler;
import io.javalin.http.Context;
import models.Led;

public class LedController {

    private final static GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(Led.getPin(), "led", PinState.LOW);;

    public void get(Context ctx) {
        led.toggle();
        ctx.result( new JsonMessageHandler(new String[][] {{"status", (led.isLow() ? "high" : "low")}}).toString() );
    }
}
