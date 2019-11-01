package controllers;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import handler.JsonMessageHandler;
import models.Led;
import java.util.concurrent.CompletableFuture;


public class LedController {

    private final static GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(Led.getPin(), "led", PinState.HIGH);

    public CompletableFuture<String> get() {
        return CompletableFuture.supplyAsync( () -> {
            led.toggle();
            return new JsonMessageHandler(new String[][] {{"status", (led.isLow() ? "high" : "low")}}).toString();
        });
    }

    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(() -> new JsonMessageHandler(new String[][] {{"status", (led.isLow() ? "high" : "low")}}).toString());
    }

    public CompletableFuture<String> blink() {
        return CompletableFuture.supplyAsync( () -> {
            led.blink(500,1000,PinState.LOW);
            return new JsonMessageHandler(new String[][] {{"status","successfull"}}).toString();
        });
    }
}
