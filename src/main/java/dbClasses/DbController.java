package dbClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import dbConfig.DBConfig;
import io.javalin.http.Context;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class DbController {

    protected String table = "";
    protected ObjectMapper mapper = new ObjectMapper();

    //Get collection of table
    protected MongoCollection<Document> collection() {
        return new DBConfig().collection(table);
    }

    public abstract CompletableFuture<String> realTimeData();
    public abstract CompletableFuture<String> getAll();
    public abstract CompletableFuture<String> post();
    public abstract CompletableFuture<String> getOne(String id);
    public abstract CompletableFuture<String> delete(String id);
}
