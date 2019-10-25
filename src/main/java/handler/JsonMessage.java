package handler;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonMessage {

    
    private Map<String,String> message;

    public JsonMessage(String[][] message) {
        this.message = Stream.of(message).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[{");
        for (Map.Entry<String, String> entry : message.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\" : \"").append(entry.getValue()).append("\"");
        }
        sb.append("}]");

        return sb.toString();
    }
}
