package dbClasses;

import com.mongodb.client.MongoCollection;
import dbConfig.DBConfig;
import io.javalin.http.Context;
import org.bson.Document;

public abstract class DbController {

    protected String table = "";

    //Get collection of table
    protected MongoCollection<Document> collection() {
        return new DBConfig().collection(table);
    }

    public abstract void getAll(Context ctx);
    public abstract void post(Context ctx);
    public abstract void getOne(Context ctx);
    public abstract void delete(Context ctx);
}
