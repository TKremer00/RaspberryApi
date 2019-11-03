package handler;

import controllers.LedController;
import controllers.CPUTemperatureController;
import controllers.TouchController;
import io.javalin.Javalin;

public class RouteHandler {

    private final String BASE_PREFIX = "api/v1/";
    private LedController lc = new LedController();
    private CPUTemperatureController Ctc = new CPUTemperatureController();
    private TouchController tc = new TouchController();
    private Javalin app;

    private RouteHandler(Javalin app) {
        this.app = app;
    }

    public static void Routes(Javalin app) {
        RouteHandler routeHandler = new RouteHandler(app);
        routeHandler.Routes();
    }

    private void Routes() {
        led(BASE_PREFIX + "led");
        cpuTemperature(BASE_PREFIX + "cpu_temperature");
        touch(BASE_PREFIX + "touch");

        app.get("/", ctx -> ctx.html(PcbHandler.getHtml()));
    }

    private void led(String prefix) {
        PcbHandler.AddPcb(prefix);
        app.get   (prefix + "/",         ctx -> ctx.result(lc.get())          );
        app.get   (prefix + "/realtime", ctx -> ctx.result(lc.realTimeData()) );
        app.get   (prefix + "/blink",    ctx -> ctx.result(lc.blink())        );
        app.get   (prefix + "/pulse",    ctx -> ctx.result(lc.pulse())        );
    }

    private void cpuTemperature(String prefix) {
        PcbHandler.AddPcb(prefix);
        app.get   (prefix + "/",          ctx -> ctx.result(Ctc.getAll())                        );
        app.get   (prefix + "/realtime",  ctx -> ctx.result(Ctc.realTimeData())                  );
        app.post  (prefix + "/",          ctx -> ctx.result(Ctc.post())                          );
        app.get   (prefix + "/:id",       ctx -> ctx.result(Ctc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",       ctx -> ctx.result(Ctc.delete(ctx.pathParam("id"))) );
    }

    private void touch(String prefix) {
        PcbHandler.AddPcb(prefix);
        app.get   (prefix + "/",         ctx -> ctx.result(tc.getAll())                        );
        app.get   (prefix + "/realtime", ctx -> ctx.result(tc.realTimeData())                  );
        app.get   (prefix + "/:id",      ctx -> ctx.result(tc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",      ctx -> ctx.result(tc.delete(ctx.pathParam("id"))) );
    }


}
