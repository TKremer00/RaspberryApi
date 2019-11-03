package dbClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import handler.JsonMessageHandler;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public static Future<String> toJson(FindIterable<Document> documents) {
        ExecutorService executors = Executors.newSingleThreadExecutor();
        Future<String> jsonString = executors.submit(
                () -> StreamSupport.stream(documents.spliterator(), false).map(Document::toJson)
                        .collect(Collectors.joining(", ", "[", "]")));
        executors.shutdown();
        return jsonString;
    }

    public static String toJson2(FindIterable<Document> documents) {
        return StreamSupport.stream(documents.spliterator(), false).map(Document::toJson)
                        .collect(Collectors.joining(", ", "[", "]"));
    }
}
