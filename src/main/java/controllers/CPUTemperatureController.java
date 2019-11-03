package controllers;

import com.mongodb.BasicDBObject;
import dbClasses.DbController;
import dbClasses.DbObject;
import handler.JsonMessageHandler;
import models.CPUTemperature;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.CpuSensor;

import java.util.Date;
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
    // Prepare a model to add to database
    protected DbObject prepareModel() {
        CPUTemperature cpuTemperature = new CPUTemperature();
        Document id = new Document();
        ObjectId oId = new ObjectId();
        id.put("$oid", oId);

        cpuTemperature.set_id(id);

        BasicDBObject date = new BasicDBObject();
        date.put("$date",new Date());
        cpuTemperature.setTimeStamp(date);
        cpuTemperature.setTemperature(CpuSensor.getCPUtemperature());
        return cpuTemperature;
    }

}
