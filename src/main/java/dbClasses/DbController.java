package dbClasses;

import com.mongodb.client.MongoCollection;
import dbConfig.DBConfig;
import handler.JsonMessageHandler;
import interfaces.SensorController;
import models.CPUTemperature;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.concurrent.CompletableFuture;

import static com.mongodb.client.model.Filters.eq;

public abstract class DbController implements SensorController {

    protected String table = "";

    //Get collection of table
    private MongoCollection<Document> collection() {
        return new DBConfig().collection(table);
    }

    public CompletableFuture<String> getAll() {
        return CompletableFuture.supplyAsync(this::collection)
                .thenApplyAsync(coll -> CPUTemperature.toJson(coll.find()));
    }

    public CompletableFuture<String> post() {
        return CompletableFuture.supplyAsync(CPUTemperature::getInstance)
                .thenApplyAsync(DbObject::toBson)
                .thenApplyAsync(document -> DbObject.insertOne(collection(), document));
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
