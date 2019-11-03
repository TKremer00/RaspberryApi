package handler;

import controllers.LedController;
import controllers.CPUTemperatureController;
import controllers.TouchController;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RouteHandler {

    private final String BASE_PREFIX = "api/v1/";
    private Javalin app;

    private RouteHandler(Javalin app) {
        this.app = app;
    }

    public static void Routes(Javalin app) {
        RouteHandler routeHandler = new RouteHandler(app);
        routeHandler.Routes();
    }

    public static void Routes2() {
        path( "touch", () -> PathHandler.MakePaths(new TouchController()));
        path( "cpu_temperature", () -> PathHandler.MakePaths(new TouchController()));
        path( "led", RouteHandler::led);
    }

    private void Routes() {
        led(BASE_PREFIX + "led");
        cpuTemperature(BASE_PREFIX + "cpu_temperature");
        touch(BASE_PREFIX + "touch");

        app.get("/", ctx -> ctx.html(PcbHandler.getHtml()));
    }

    private void led(String prefix) {
        PcbHandler.AddPcb(prefix);
        LedController lc = new LedController();
        app.get   (prefix + "/",         ctx -> ctx.result(lc.get())          );
        app.get   (prefix + "/realtime", ctx -> ctx.result(lc.realTimeData()) );
        app.get   (prefix + "/blink",    ctx -> ctx.result(lc.blink())        );
        app.get   (prefix + "/pulse",    ctx -> ctx.result(lc.pulse())        );
    }

    private void cpuTemperature(String prefix) {
        PcbHandler.AddPcb(prefix);
        CPUTemperatureController ctc = new CPUTemperatureController();
        app.get   (prefix + "/",          ctx -> ctx.result(ctc.getAll())                        );
        app.get   (prefix + "/realtime",  ctx -> ctx.result(ctc.realTimeData())                  );
        app.post  (prefix + "/",          ctx -> ctx.result(ctc.post())                          );
        app.get   (prefix + "/:id",       ctx -> ctx.result(ctc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",       ctx -> ctx.result(ctc.delete(ctx.pathParam("id"))) );
    }

    private void touch(String prefix) {
        TouchController tc = new TouchController();
        PcbHandler.AddPcb(prefix);
        app.get   (prefix + "/",         ctx -> ctx.result(tc.getAll())                        );
        app.get   (prefix + "/realtime", ctx -> ctx.result(tc.realTimeData())                  );
        app.get   (prefix + "/:id",      ctx -> ctx.result(tc.getOne(ctx.pathParam("id"))) );
        app.delete(prefix + "/:id",      ctx -> ctx.result(tc.delete(ctx.pathParam("id"))) );

    }

    private static void led() {
        LedController lc = new LedController();
        get(ctx -> ctx.result(lc.get()));
        path("/realtime", () -> get(ctx -> ctx.result(lc.realTimeData())));
        path("/blink", () -> get(ctx -> ctx.result(lc.blink())));
        path("/pulse", () -> get(ctx -> ctx.result(lc.pulse())));
    }
}
