package controllers;

import com.mongodb.client.MongoCollection;
import dbClasses.DbController;
import handler.JsonMessageHandler;
import io.javalin.http.Context;
import models.CPUTemperature;
import models.Temperature;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.CpuSensor;
import sensor.DS1820Sensor;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class TemperatureController extends DbController {

    public TemperatureController() {
        super.table = "temperature";
    }


    @Override
    public void realTimeData(Context ctx) {
        ctx.result(new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Double.toString(DS1820Sensor.getTemperature())}}).toString());
        ctx.status(200);
    }

    @Override
    public void getAll(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            ArrayList<Object> temperatures = Temperature.toList(coll.find());
            ctx.json(temperatures);
            ctx.status(200);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(CPUTemperature.errorJson);
            ctx.status(400);
        }
    }

    @Override
    public void post(Context ctx) {
        Temperature temperature = new Temperature();
        temperature.setTemperature(DS1820Sensor.getTemperature());

        Document doc = temperature.toBson();

        MongoCollection<Document> coll = collection();
        coll.insertOne(doc);

        ctx.result(Temperature.succesJson);
        ctx.status(200);
    }

    @Override
    public void getOne(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            ArrayList<Object> temperatures = CPUTemperature.toList(coll.find( eq("_id", new ObjectId(ctx.pathParam("id"))) ));
            ctx.json(temperatures);
            ctx.status(200);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(Temperature.errorJson);
            ctx.status(400);
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            coll.findOneAndDelete( eq("_id", new ObjectId(ctx.pathParam("id"))) );
            ctx.result(Temperature.succesJson);
            ctx.status(200);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(Temperature.errorJson);
            ctx.status(400);
        }
    }
}
