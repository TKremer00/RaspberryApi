package handler;

import controllers.CPUTemperatureController;
import controllers.LedController;
import controllers.TouchController;
import static io.javalin.apibuilder.ApiBuilder.path;

public class RouteHandler {

    // Register main paths
    public static void Routes() {
        path( "touch", () -> PathHandler.dbControllerPaths(new TouchController()));
        path( "cpu_temperature", () -> PathHandler.dbControllerPaths(new CPUTemperatureController()));
        path( "led", () -> PathHandler.LedPaths(new LedController()));
    }
}
