package models;

import com.mongodb.BasicDBObject;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import dbClasses.DbObject;
import org.bson.Document;

public class Touch extends DbObject {

    private Document _id;
    private BasicDBObject timeStamp;


    private  final static Pin PIN = RaspiPin.GPIO_02;

    public static Pin getPin() {
        return PIN;
    }
}
