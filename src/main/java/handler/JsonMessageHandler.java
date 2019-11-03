package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonMessageHandler {

    private Map<String,String> message;

    public JsonMessageHandler() {
    }

    public JsonMessageHandler(String[][] message) {
        this.message = arrayToMap(message);
    }

    public String SensorMessage(String data) {
        String[][] message =  new String[][] {{"Status" ,"successfull"},{"realTimeData", data}};
        Map<String,String> messageMap = arrayToMap(message);
        try {
            return toJson(messageMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Map<String,String> arrayToMap(String[][] array) {
        return Stream.of(array).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    private String toJson(Map<String,String> message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(message);
    }

    @Override
    public String toString() {
        try {
            return toJson(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
