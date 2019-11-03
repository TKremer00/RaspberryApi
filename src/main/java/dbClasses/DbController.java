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
    private final DBConfig DB_CONFIG = new DBConfig();

    protected abstract DbObject prepareModel();
    public abstract CompletableFuture<String> realTimeData();
    
    // Get collection of table
    private MongoCollection<Document> collection() {
        return DB_CONFIG.collection(table);
    }

    // Get all database records for table
    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> CPUTemperature.toJson(coll.find()));
    }

    // Add database entry
    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync(this::prepareModel)
                .thenApplyAsync(DbObject::toBson)
                .thenApplyAsync(document -> {collection().insertOne(document); return JsonMessageHandler.successMessage();});
    }

    // Get entry by id
    public CompletableFuture<String> getOne(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(Document::toJson)
                .exceptionally(exeption -> JsonMessageHandler.errorMessage("No record found"));
    }

    // Delete entry by id
    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> coll.find( eq("_id", new ObjectId(id))).first())
                .thenApplyAsync(document -> {collection().findOneAndDelete(document);return JsonMessageHandler.successMessage();})
                .exceptionally(exeption -> JsonMessageHandler.errorMessage("No record found"));
    }
}
