package controllers;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import com.mongodb.client.MongoCollection;
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
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//
//                MongoCollection<Document> coll = collection();
//                return CPUTemperature.toJson(coll.find()).get();
//            }catch (Exception e) {
//                System.out.println(e.getMessage());
//                return DbObject.errorJson;
//            }
//        });

        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> CPUTemperature.toJson2(coll.find()));
    }

    @Override
    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync( () -> {
            CPUTemperature CPUTemperature = new CPUTemperature();
            CPUTemperature.setTemperature(CpuSensor.getCPUtemperature());

            Document doc = CPUTemperature.toBson();

            MongoCollection<Document> coll = collection();
            coll.insertOne(doc);

            return DbObject.succesJson;
        });
    }

    @Override
    public CompletableFuture<String> getOne(String id) {
        return CompletableFuture.supplyAsync( () -> {
            try {
                MongoCollection<Document> coll = collection();
                return Objects.requireNonNull(coll.find(eq("_id", new ObjectId(id))).first()).toJson();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync( () -> {
            try {
                MongoCollection<Document> coll = collection();
                coll.findOneAndDelete( eq("_id", new ObjectId(id)) );
                return DbObject.succesJson;
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }
}
