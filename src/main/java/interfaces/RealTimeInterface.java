package interfaces;

import java.util.concurrent.CompletableFuture;

public interface RealTimeInterface {

    CompletableFuture<String> realTimeData();

}
