package controllers;

import java.util.ArrayList;
import com.mongodb.client.MongoCollection;
import dbClasses.DbController;
import io.javalin.http.Context;
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
    public void getAll(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            ArrayList<Object> temperatures = CPUTemperature.toList(coll.find());
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
        CPUTemperature CPUTemperature = new CPUTemperature();
        CPUTemperature.setTemperature(CpuSensor.getCPUtemperature());

        Document doc = CPUTemperature.toBson();

        MongoCollection<Document> coll = collection();
        coll.insertOne(doc);

        ctx.result(CPUTemperature.succesJson);
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
            ctx.result(CPUTemperature.errorJson);
            ctx.status(400);
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            MongoCollection<Document> coll = collection();
            coll.findOneAndDelete( eq("_id", new ObjectId(ctx.pathParam("id"))) );
            ctx.result(CPUTemperature.succesJson);
            ctx.status(200);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.result(CPUTemperature.errorJson);
            ctx.status(400);
        }
    }
}
