package models;

import com.mongodb.BasicDBObject;
import dbClasses.DbObject;
import org.bson.Document;
import sensor.CpuSensor;

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
    public DbObject getInstance() {
        System.out.println("\n\n\n\nGet Instance of CpuTemperature\n\n\n\n");
        CPUTemperature cpuTemperature = new CPUTemperature();
        cpuTemperature.setTemperature(CpuSensor.getCPUtemperature());
        return cpuTemperature;
    }
}
