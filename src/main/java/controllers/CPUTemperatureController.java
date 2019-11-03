package controllers;

import dbClasses.DbController;
import dbClasses.DbObject;
import handler.JsonMessageHandler;
import models.CPUTemperature;
import org.bson.Document;
import sensor.CpuSensor;

import java.util.concurrent.CompletableFuture;

public class CPUTemperatureController extends DbController {

    public CPUTemperatureController() {
        super.table = "cpu_temperature";
        super.dbObject = new CPUTemperature();
    }

    @Override
    // Get current temperature
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(JsonMessageHandler::new)
                .thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage(Double.toString(CpuSensor.getCPUtemperature())));
    }


    @Override
    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync(CPUTemperature::new)
                .thenApplyAsync(cpuTemperature -> { cpuTemperature.setTemperature(CpuSensor.getCPUtemperature()); return cpuTemperature.toBson();})
                .thenApplyAsync(Document::toString);
    }
}
