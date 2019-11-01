package controllers;

import com.mongodb.client.MongoCollection;
import dbClasses.*;
import handler.JsonMessageHandler;
import models.CPUTemperature;
import models.Temperature;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.DS1820Sensor;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import static com.mongodb.client.model.Filters.eq;

public class TemperatureController extends DbController {

    public TemperatureController() {
        super.table = "temperature";
    }

    @Override
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(() -> new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Double.toString(DS1820Sensor.getTemperature())}}).toString());
    }

    @Override
    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MongoCollection<Document> coll = collection();
                return Temperature.toJson(coll.find()).get();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync( () -> {
            Temperature temperature = new Temperature();
            temperature.setTemperature(DS1820Sensor.getTemperature());

            Document doc = temperature.toBson();

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
