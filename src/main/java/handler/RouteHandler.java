package handler;

import controllers.LedController;
import controllers.TemperatureController;
import controllers.TouchController;
import io.javalin.Javalin;

public class RouteHandler {

    private final String BASE_PREFIX = "api/v1/";
    private LedController lc = new LedController();
    private TemperatureController tc = new TemperatureController();
    private TouchController touchController = new TouchController();
    private Javalin app;

    private RouteHandler(Javalin app) {
        this.app = app;
    }

    public static void Routes(Javalin app) {
        RouteHandler routeHandler = new RouteHandler(app);
        routeHandler.Routes();
    }

    private void Routes() {
        // Default rout
        app.get("/", ctx -> ctx.result("Api version 1"));

        led(BASE_PREFIX + "led");
        temperature(BASE_PREFIX + "temperature");
        touch(BASE_PREFIX + "touch");
    }

    private void led(String prefix) {
        app.get  (prefix + "/",         ctx -> lc.get  (ctx) );
    }

    private void temperature(String prefix) {
        app.get   (prefix + "/",         ctx -> tc.getAll(ctx) );
        app.post  (prefix + "/",         ctx -> tc.post  (ctx) );
        app.get   (prefix + "/:id",      ctx -> tc.getOne(ctx) );
        app.delete(prefix + "/:id",      ctx -> tc.delete(ctx) );
    }

    private void touch(String prefix) {
        app.get   (prefix + "/",         ctx -> touchController.getAll(ctx) );
        app.get   (prefix + "/:id",      ctx -> touchController.getOne(ctx) );
        app.delete(prefix + "/:id",      ctx -> touchController.delete(ctx) );
    }
}
