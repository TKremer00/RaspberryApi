package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import dbClasses.DbObject;
import org.bson.Document;

import java.util.ArrayList;

public class Touch extends DbObject {

    private Document _id;
    private BasicDBObject timeStamp;

    private  final static Pin PIN = RaspiPin.GPIO_02;

    public Document get_id() {
        return _id;
    }

    public void set_id(Document _id) {
        this._id = _id;
    }

    public BasicDBObject getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(BasicDBObject timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static Pin getPin() {
        return PIN;
    }

}
