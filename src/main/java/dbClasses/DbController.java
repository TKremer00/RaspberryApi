package dbClasses;

import com.mongodb.client.MongoCollection;
import dbConfig.DBConfig;
import handler.JsonMessageHandler;
import interfaces.RealTimeInterface;
import models.CPUTemperature;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.concurrent.CompletableFuture;

import static com.mongodb.client.model.Filters.eq;

public abstract class DbController implements RealTimeInterface {

    protected String table = "";
    protected DbObject dbObject = null;

    // Get collection of table
    private MongoCollection<Document> collection() {
        return new DBConfig().collection(table);
    }

    // Get all database records for table
    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> CPUTemperature.toJson(coll.find()));
    }

    // Add database entry
    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync(dbObject::getInstance)
                .thenApplyAsync(DbObject::toBson)
                .thenApplyAsync(Document::toString);
    }

    public CompletableFuture<String> getOne(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(Document::toJson)
                .exceptionally(exeption -> new JsonMessageHandler(new String[][] {{"Status", "Error"}, {"Error","No record found"}}).toString());
    }

    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(document -> DbObject.deleteOne(collection(),document))
                .exceptionally(exeption -> new JsonMessageHandler(new String[][] {{"Status", "Error"}, {"Error","No record found"}}).toString());
    }
}
