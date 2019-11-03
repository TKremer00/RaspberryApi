package controllers;

import dbClasses.DbController;
import handler.JsonMessageHandler;
import sensor.CpuSensor;

import java.util.concurrent.CompletableFuture;

public class CPUTemperatureController extends DbController {

    public CPUTemperatureController() {
        super.table = "cpu_temperature";
    }

    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(JsonMessageHandler::new)
                .thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage(Double.toString(CpuSensor.getCPUtemperature())));
    }
}
