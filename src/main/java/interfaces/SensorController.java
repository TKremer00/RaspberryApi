package interfaces;

import java.util.concurrent.CompletableFuture;

public interface SensorController {

    CompletableFuture<String> realTimeData();

}
