package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import dbClasses.DbObject;
import org.bson.Document;
import java.util.ArrayList;

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

    // @override
    public static ArrayList<Object> toList(FindIterable<Document> documents) throws JsonProcessingException {
        return DbObject.toList(documents, CPUTemperature.class);
    }
}
