package controllers;

import java.util.concurrent.CompletableFuture;
import dbClasses.*;
import handler.JsonMessageHandler;
import models.CPUTemperature;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.CpuSensor;

import static com.mongodb.client.model.Filters.eq;

public class CPUTemperatureController extends DbController {

    public CPUTemperatureController() {
        super.table = "cpu_temperature";
    }

    @Override
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(() -> new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Double.toString(CpuSensor.getCPUtemperature())}}).toString());
    }

    @Override
    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> CPUTemperature.toJson2(coll.find()));
    }

    @Override
    // TODO : Test this methode
    public CompletableFuture<String> post() {

        return CompletableFuture.supplyAsync(CPUTemperature::getInstance)
                .thenApplyAsync(DbObject::toBson)
                .thenApplyAsync(document -> super.insertOne(collection(), document));
    }

    @Override
    // TODO : Test this methode
    public CompletableFuture<String> getOne(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(Document::toJson);
    }

    @Override
    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(document -> super.deleteOne(collection(),document));
    }
}
