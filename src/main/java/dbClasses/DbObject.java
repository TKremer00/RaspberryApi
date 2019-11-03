package dbClasses;

import com.fasterxml.jackson.core.type.TypeReference;
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

public abstract class DbObject {

    private static ObjectMapper mapper = new ObjectMapper();
    static String succesJson = new JsonMessageHandler(new String[][] {{"status", "succesfull"}}).toString();

    public abstract DbObject getInstance();

    // Object to document
    public Document toBson() {

        try {
            System.out.println(mapper.writeValueAsString(this));
            Document doc = Document.parse(mapper.writeValueAsString(this));
//            if(!doc.containsKey("_id")){
//                doc.put("_id" , new ObjectId());
//            }
//            if(!doc.containsKey("timeStamp")) {
//                doc.put("timeStamp" , new Date());
//            }
            System.out.println("\n\n\n\n\nTo bson on : " + this.getClass().getName() + "\nValues : " + doc.toString() + "\n\n\n\n\n");
            return doc;
        }catch (Exception e) {
            e.printStackTrace();
        }
        Document doc = new Document();
        doc.put("_id" , new ObjectId());
        doc.put("timeStamp" , new Date());
        System.out.println("\n\n\n\n\nTo bson on : " + this.getClass().getName() + " Error \nValues : " + doc.toString() + "\n\n\n\n\n");
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
