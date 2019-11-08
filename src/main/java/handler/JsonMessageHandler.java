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

    // Default success message style
    public static String successMessage() {
        JsonMessageHandler jmh = new JsonMessageHandler();
        try {
            return jmh.toJson(jmh.arrayToMap(new String[][] {{"status", "succesfull"}}));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Default error message style
    public static String errorMessage(String error) {
        JsonMessageHandler jmh = new JsonMessageHandler();
        try {
            return jmh.toJson(jmh.arrayToMap(new String[][] {{"Status", "Error"}, {"Error",error}}));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Default sensor message style
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

    // Transform array to map
    private Map<String,String> arrayToMap(String[][] array) {
        return Stream.of(array).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    // Transform map<String,String> to a json string
    private String toJson(Map<String,String> message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(message);
    }

    @Override
    // Change default toString to the json string
    public String toString() {
        try {
            return toJson(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
