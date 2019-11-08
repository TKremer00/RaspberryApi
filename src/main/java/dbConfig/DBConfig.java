package dbConfig;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

public class DBConfig {

    private final String DB_CONN = "mongodb+srv://RaspBerry:PK5A90CvHCkRxP8n@cluster0-84gki.mongodb.net/admin?retryWrites=true&w=majority";

    private final ConnectionString CONN_STRING = new ConnectionString(DB_CONN);
    private final MongoClientSettings SETTINGS = MongoClientSettings.builder()
            .applyConnectionString(CONN_STRING)
            .retryWrites(true)
            .build();

    private final MongoClient MONGO_CLIENT = MongoClients.create(SETTINGS);
    private final MongoDatabase DB = MONGO_CLIENT.getDatabase("raspberry_pi_project");

    //Get collection of table
    public MongoCollection<Document> collection(String tableName) {
        return DB.getCollection(tableName);
    }
}
