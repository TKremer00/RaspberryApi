package dbClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DbObject {

    private ObjectMapper mapper = new ObjectMapper();

    // Object to document
    public Document toBson() {
        Document doc = new Document();
        try {
            doc = Document.parse(mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
}
