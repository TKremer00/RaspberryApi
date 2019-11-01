package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import dbClasses.DbObject;
import org.bson.Document;

import java.util.ArrayList;

public class Temperature extends DbObject{

    private  final static Pin PIN = RaspiPin.GPIO_01;

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

    public static Pin getPin() {
        return PIN;
    }

}
