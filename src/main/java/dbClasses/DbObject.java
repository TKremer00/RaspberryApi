package dbClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import handler.JsonMessageHandler;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbObject {

    private static ObjectMapper mapper = new ObjectMapper();
    static String succesJson = new JsonMessageHandler(new String[][] {{"status", "succesfull"}}).toString();

    // Object to document
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

    // FindIterable<Documents> to Json string
    public static String toJson(FindIterable<Document> documents) {
        return StreamSupport.stream(documents.spliterator(), false).map(Document::toJson)
                        .collect(Collectors.joining(", ", "[", "]"));
    }

    //
    public static String insertOne(MongoCollection<Document> collection, Document doc) {
        collection.insertOne(doc);
        return succesJson;
    }

    static String deleteOne (MongoCollection<Document> collection, Document doc) {
        collection.findOneAndDelete(doc);
        return succesJson;
    }
}
