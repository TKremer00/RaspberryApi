package controllers;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.Future;

import com.mongodb.client.MongoCollection;
import dbClasses.DbController;
import dbClasses.DbObject;
import handler.JsonMessageHandler;
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
    public void realTimeData(Context ctx) {
        ctx.result(new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Double.toString(CpuSensor.getCPUtemperature())}}).toString());
        ctx.status(200);
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

    @Override
    public Future<String> realTimeData() {
        return super.executor.submit(() -> new JsonMessageHandler(new String[][] {{"status", "succesfull"}, {"realTimeData", Double.toString(CpuSensor.getCPUtemperature())}}).toString());

    }

    @Override
    public Future<String> getAll() {
        return super.executor.submit(() -> {
            try {
                StringWriter sw = new StringWriter();
                MongoCollection<Document> coll = collection();
                ArrayList<Object> temperatures = CPUTemperature.toList(coll.find());
                return super.mapper.writeValueAsString(temperatures);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public Future<String> post() {
        return super.executor.submit( () -> {
            CPUTemperature CPUTemperature = new CPUTemperature();
            CPUTemperature.setTemperature(CpuSensor.getCPUtemperature());

            Document doc = CPUTemperature.toBson();

            MongoCollection<Document> coll = collection();
            coll.insertOne(doc);

            return DbObject.succesJson;
        });
    }

    @Override
    public Future<String> getOne(String id) {
        return super.executor.submit( () -> {
            try {
                MongoCollection<Document> coll = collection();
                ArrayList<Object> temperatures = CPUTemperature.toList(coll.find( eq("_id", new ObjectId(id)) ));
                return super.mapper.writeValueAsString(temperatures);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return DbObject.errorJson;
            }
        });
    }

    @Override
    public Future<String> delete(String id) {
        return super.executor.submit( () -> {
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
