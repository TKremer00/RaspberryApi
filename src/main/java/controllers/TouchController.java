package controllers;

import com.mongodb.client.MongoCollection;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import dbClasses.DbController;
import dbClasses.*;
import dbConfig.DBConfig;
import handler.JsonMessageHandler;
import models.Touch;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.TouchSensor;
import java.util.concurrent.CompletableFuture;

import static com.mongodb.client.model.Filters.eq;

public class TouchController extends DbController {

    private static String tableName = "touch";

    public TouchController() {
        super.table = "touch";
    }

    @Override
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(() -> new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Boolean.toString(TouchSensor.getState())}}).toString());
    }

    @Override
    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync( () -> {
            try {
                MongoCollection<Document> coll = collection();
                return Touch.toJson(coll.find()).get();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public CompletableFuture<String> post() {
        return null;
    }

    @Override
    public CompletableFuture<String> getOne(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MongoCollection<Document> coll = collection();
                return Touch.toJson(coll.find( eq("_id", new ObjectId(id)) )).get();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync(() -> {
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

    public static void startListening() {
        TouchSensor.getTouchSensor().addListener(
            (GpioPinListenerDigital) event -> {
                // Log if high
                if(event.getState().isHigh()) {
                    Touch touch = new Touch();
                    Document doc = touch.toBson();
                    MongoCollection<Document> coll = new DBConfig().collection(tableName);
                    coll.insertOne(doc);
                }
            }
        );
    }
}
