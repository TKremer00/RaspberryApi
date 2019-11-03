package models;

import com.mongodb.BasicDBObject;
import dbClasses.DbObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import sensor.CpuSensor;
import java.util.Date;

public class CPUTemperature extends DbObject {

    private Document _id;
    private BasicDBObject timeStamp;
    private double temperature;

    // GET AND SET
    public Document get_id() {
        return _id;
    }

    public BasicDBObject getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(BasicDBObject timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public Document toBson() {
        Document doc = new Document();
        doc.put("_id" , new ObjectId());
        doc.put("timeStamp" , new Date());
        doc.put("temperature", this.getTemperature());
        System.out.println("\n\n\n\n\nTo bson on : " + this.getClass().getName() + "\nValues : " + doc.toString() + "\n\n\n\n\n");
        return doc;
    }

    @Override
    public DbObject getInstance() {
        CPUTemperature cpuTemperature = new CPUTemperature();
        cpuTemperature.setTemperature(CpuSensor.getCPUtemperature());
        return cpuTemperature;
    }
}
