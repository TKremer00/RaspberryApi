package models;

import com.mongodb.BasicDBObject;
import dbClasses.DbObject;
import org.bson.Document;

public class CPUTemperature extends DbObject {

    private Document _id;
    private BasicDBObject timeStamp;
    private double temperature;

    // GET AND SET
    public void set_id(Document _id) {
        this._id = _id;
    }

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
}
