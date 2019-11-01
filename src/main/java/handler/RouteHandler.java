package handler;

import controllers.LedController;
import controllers.CPUTemperatureController;
import controllers.TemperatureController;
import controllers.TouchController;
import io.javalin.Javalin;

public class RouteHandler {

    private final String BASE_PREFIX = "api/v1/";
    private LedController lc = new LedController();
    private CPUTemperatureController CPUtc = new CPUTemperatureController();
    private TouchController touchController = new TouchController();
    private TemperatureController tc = new TemperatureController();
    private Javalin app;

    private RouteHandler(Javalin app) {
        this.app = app;
    }

    public static void Routes(Javalin app) {
        RouteHandler routeHandler = new RouteHandler(app);
        routeHandler.Routes();
    }

    private void Routes() {
        // Default route
        app.get("/", ctx -> ctx.result("Api version 1"));

        led(BASE_PREFIX + "led");
        cpuTemperature(BASE_PREFIX + "cpu_temperature");
        touch(BASE_PREFIX + "touch");
        //temperature(BASE_PREFIX + "temperature");
    }

    private void led(String prefix) {
        app.get   (prefix + "/",          ctx -> ctx.result(lc.get().get()));
        app.get   (prefix + "/realtime", ctx -> lc.realTimeData(ctx)  );
        app.get   (prefix + "/blink",    ctx -> lc.blink(ctx)         );
    }

    private void cpuTemperature(String prefix) {
        app.get   (prefix + "/",          ctx -> CPUtc.getAll(ctx)       );
        app.get   (prefix + "/realtime",  ctx -> CPUtc.realTimeData(ctx) );
        app.post  (prefix + "/",          ctx -> CPUtc.post  (ctx)       );
        app.get   (prefix + "/:id",       ctx -> CPUtc.getOne(ctx)       );
        app.delete(prefix + "/:id",       ctx -> CPUtc.delete(ctx)       );
    }

    private void touch(String prefix) {
        app.get   (prefix + "/",         ctx -> touchController.getAll(ctx)       );
        app.get   (prefix + "/realtime", ctx -> touchController.realTimeData(ctx) );
        app.get   (prefix + "/:id",      ctx -> touchController.getOne(ctx)       );
        app.delete(prefix + "/:id",      ctx -> touchController.delete(ctx)       );
    }

    private void temperature(String prefix) {
        app.get   (prefix + "/",         ctx -> tc.getAll(ctx)       );
        app.get   (prefix + "/realtime", ctx -> tc.realTimeData(ctx) );
        app.post  (prefix + "/",         ctx -> tc.post  (ctx)       );
        app.get   (prefix + "/:id",      ctx -> tc.getOne(ctx)       );
        app.delete(prefix + "/:id",      ctx -> tc.delete(ctx)       );
    }
}
