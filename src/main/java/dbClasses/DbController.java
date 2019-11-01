package dbClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import dbConfig.DBConfig;
import io.javalin.http.Context;
import org.bson.Document;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class DbController {

    protected String table = "";
    protected ExecutorService executor  = Executors.newSingleThreadExecutor();
    protected ObjectMapper mapper = new ObjectMapper();

    //Get collection of table
    protected MongoCollection<Document> collection() {
        return new DBConfig().collection(table);
    }

    public abstract void realTimeData(Context ctx);
    public abstract void getAll(Context ctx);
    public abstract void post(Context ctx);
    public abstract void getOne(Context ctx);
    public abstract void delete(Context ctx);

    public abstract Future<String> realTimeData();
    public abstract Future<String> getAll();
    public abstract Future<String> post();
    public abstract Future<String> getOne(String id);
    public abstract Future<String> delete(String id);
}
