package controllers;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import handler.JsonMessageHandler;
import interfaces.SensorController;
import models.Led;
import java.util.concurrent.CompletableFuture;

public class LedController implements SensorController {

    private final static GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(Led.getPin(), "led", PinState.HIGH);

    public CompletableFuture<String> get() {
        return CompletableFuture.supplyAsync( () -> {
            led.toggle();
            return new JsonMessageHandler();
        }).thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage(led.isLow() ? "high" : "low"));
    }

    @Override
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(JsonMessageHandler::new)
                .thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage((led.isLow() ? "high" : "low")));
    }

    public CompletableFuture<String> blink() {
        return CompletableFuture.supplyAsync( () -> {
            led.blink(500,1500,PinState.LOW);
            return new JsonMessageHandler(new String[][] {{"status","successfull"}}).toString();
        });
    }

    public CompletableFuture<String> pulse() {
        return CompletableFuture.supplyAsync( () -> {
            led.pulse(1500,PinState.LOW);
            return new JsonMessageHandler(new String[][] {{"status","successfull"}}).toString();
        });
    }
}
