package controllers;

import dbClasses.DbController;
import dbClasses.DbObject;
import handler.JsonMessageHandler;
import models.CPUTemperature;
import sensor.CpuSensor;
import java.util.concurrent.CompletableFuture;

public class CPUTemperatureController extends DbController {

    public CPUTemperatureController() {
        super.table = "cpu_temperature";
    }

    @Override
    // Get current temperature
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(JsonMessageHandler::new)
                .thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage(Double.toString(CpuSensor.getCPUtemperature())));
    }

    @Override
    // Prepare model to add to database
    // id and timestamp will set.
    protected DbObject prepareModel() {
        CPUTemperature cpuTemperature = new CPUTemperature();
        cpuTemperature.setTemperature(CpuSensor.getCPUtemperature());
        return cpuTemperature;
    }
}
