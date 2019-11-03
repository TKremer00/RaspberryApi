package interfaces;

import java.util.concurrent.CompletableFuture;

public interface LedInterface extends RealTimeInterface {

    CompletableFuture<String> toggle();

    CompletableFuture<String> blink();

    CompletableFuture<String> pulse();
}
