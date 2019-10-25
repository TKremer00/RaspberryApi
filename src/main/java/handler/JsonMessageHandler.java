package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonMessageHandler {

    private Map<String,String> message;

    public JsonMessageHandler(String[][] message) {
        this.message = Stream.of(message).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
