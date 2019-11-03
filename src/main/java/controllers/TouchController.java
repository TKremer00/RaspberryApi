package controllers;

import com.mongodb.client.MongoCollection;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import dbClasses.DbController;
import dbClasses.DbObject;
import dbConfig.DBConfig;
import handler.JsonMessageHandler;
import models.Touch;
import org.bson.Document;
import sensor.TouchSensor;
import java.util.concurrent.CompletableFuture;

public class TouchController extends DbController {

    private static String tableName = "touch";

    public TouchController() {
        super.table = "touch";
    }

    @Override
    // Get state of touch sensor
    public CompletableFuture<String> realTimeData() {
        return CompletableFuture.supplyAsync(JsonMessageHandler::new)
                .thenApplyAsync(jsonMessageHandler -> jsonMessageHandler.SensorMessage(Boolean.toString(TouchSensor.getState())));
    }

    @Override
    // Prepare model to add to database
    // id and timestamp will set.
    protected DbObject prepareModel() {
        return new Touch();
    }

    // Listen for state change, then add database entry
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
