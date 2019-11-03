package handler;

import controllers.CPUTemperatureController;
import controllers.LedController;
import controllers.TouchController;
import static io.javalin.apibuilder.ApiBuilder.path;

public class RouteHandler {

    public static void Routes2() {
        path( "touch", () -> PathHandler.dbControllerPaths(new TouchController()));
        path( "cpu_temperature", () -> PathHandler.dbControllerPaths(new CPUTemperatureController()));
        path( "led", () -> PathHandler.LedPaths(new LedController()));
    }
}
