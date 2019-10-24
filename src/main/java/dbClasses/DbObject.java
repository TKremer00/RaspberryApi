package dbClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DbObject {

    private static ObjectMapper mapper = new ObjectMapper();
    public static String succesJson = "[{\"status\" : \"successfull\"}]";
    public static String errorJson = "[{\"status\" : \"error\"}]";

    public Document toBson() {
        Document doc = new Document(mapper.convertValue(this,Map.class));
        doc.put("_id" , new ObjectId());
        doc.put("timeStamp" , new Date());
        return doc;
    }

    protected static ArrayList<Object> toList(FindIterable<Document> documents, Class currClass) throws JsonProcessingException {
        ArrayList<Object> objects = new ArrayList<>();
        for (Document document : documents) {
            objects.add(mapper.readValue(document.toJson(), currClass));
        }
        return objects;
    }
}
