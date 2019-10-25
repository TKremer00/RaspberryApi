package controllers;

import com.mongodb.client.MongoCollection;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import dbClasses.DbController;
import dbConfig.DBConfig;
import io.javalin.http.Context;
import models.Touch;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.TouchSensor;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class TouchController extends DbController {

    private static String tableName = "touch";

    public TouchController() {
        super.table = "touch";
    }

    @Override
    public void getAll(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            ArrayList<Object> touch = Touch.toList(coll.find());
            ctx.json(touch);
            ctx.status(201);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(Touch.errorJson);
            ctx.status(400);
        }
    }

    @Override
    public void post(Context ctx) {
        throw new UnsupportedOperationException("Method not suported");
    }

    @Override
    public void getOne(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            ArrayList<Object> touch = Touch.toList(coll.find( eq("_id", new ObjectId(ctx.pathParam("id"))) ));
            ctx.json(touch);
            ctx.status(201);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(Touch.errorJson);
            ctx.status(400);
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            coll.findOneAndDelete( eq("_id", new ObjectId(ctx.pathParam("id"))) );
            ctx.result(Touch.succesJson);
            ctx.status(201);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(Touch.errorJson);
            ctx.status(400);
        }
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
