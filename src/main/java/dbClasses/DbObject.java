package dbClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import handler.JsonMessageHandler;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DbObject {

    private static ObjectMapper mapper = new ObjectMapper();
    public static String succesJson = new JsonMessageHandler(new String[][] {{"status", "succesfull"}}).toString();
    public static String errorJson = new JsonMessageHandler(new String[][] {{"status", "error"} }).toString();

    public Document toBson() {
        Document doc = new Document();
        try {
            doc = new Document(mapper.convertValue(this,Map.class));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        doc.put("_id" , new ObjectId());
        doc.put("timeStamp" , new Date());
        return doc;
    }

    public static String toJson(FindIterable<Document> documents) throws JsonProcessingException {
        return mapper.writeValueAsString(documents);
    }
}
