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
    }

    private void led(String prefix) {
        app.get   (prefix + "/",         ctx -> ctx.result(lc.get())          );
        app.get   (prefix + "/realtime", ctx -> ctx.result(lc.realTimeData()) );
        app.get   (prefix + "/blink",    ctx -> ctx.result(lc.blink())        );
    }

    private void cpuTemperature(String prefix) {
        app.get   (prefix + "/",          ctx -> ctx.result(CPUtc.getAll())                        );
        app.get   (prefix + "/realtime",  ctx -> ctx.result(CPUtc.realTimeData())                  );
        app.post  (prefix + "/",          ctx -> ctx.result(CPUtc.post())                          );
        app.get   (prefix + "/:id",       ctx -> ctx.result(CPUtc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",       ctx -> ctx.result(CPUtc.delete(ctx.pathParam("id"))) );
    }

    private void touch(String prefix) {
        app.get   (prefix + "/",         ctx -> ctx.result(touchController.getAll())                        );
        app.get   (prefix + "/realtime", ctx -> ctx.result(touchController.realTimeData())                  );
        app.get   (prefix + "/:id",      ctx -> ctx.result(touchController.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",      ctx -> ctx.result(touchController.delete(ctx.pathParam("id"))) );
    }

    private void temperature(String prefix) {
        app.get   (prefix + "/",         ctx -> ctx.result(tc.getAll())                        );
        app.get   (prefix + "/realtime", ctx -> ctx.result(tc.realTimeData())                  );
        app.post  (prefix + "/",         ctx -> ctx.result(tc.post())                          );
        app.get   (prefix + "/:id",      ctx -> ctx.result(tc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",      ctx -> ctx.result(tc.delete(ctx.pathParam("id"))) );
    }
}
