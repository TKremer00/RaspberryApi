package handler;

import controllers.LedController;
import controllers.TemperatureController;
import io.javalin.Javalin;

public class RouteHandler {

    private final String BASE_PREFIX = "api/v1/";
    private TemperatureController tc = new TemperatureController();
    private LedController lc = new LedController();
    private Javalin app;

    private RouteHandler(Javalin app) {
        this.app = app;
    }

    public static void Routes(Javalin app) {
        RouteHandler routeHandler = new RouteHandler(app);
        routeHandler.Routes();
    }

    private void Routes() {
        // Base
        app.get("/", ctx -> ctx.result("Api version 1"));


        temperature(BASE_PREFIX + "temperature");
        led(BASE_PREFIX + "led");
    }

    private void temperature(String prefix) {
        app.get   (prefix + "/",         ctx -> tc.getAll(ctx) );
        app.post  (prefix + "/",         ctx -> tc.post  (ctx) );
        app.get   (prefix + "/:id",      ctx -> tc.getOne(ctx) );
        app.delete(prefix + "/:id",      ctx -> tc.delete(ctx) );
    }

    private void led(String prefix) {
        app.post  (prefix + "/",         ctx -> lc.get  (ctx) );
    }
}
