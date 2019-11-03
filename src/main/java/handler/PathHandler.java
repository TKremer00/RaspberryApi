package handler;

import dbClasses.DbController;
import interfaces.LedInterface;
import interfaces.RealTimeInterface;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.delete;

class PathHandler {

    private static void sensorControllerPaths(RealTimeInterface realTimeInterface) {
        path("/realtime", () -> get(ctx -> ctx.result(realTimeInterface.realTimeData())));
    }

    static void dbControllerPaths(DbController dbController) {
        get(ctx -> ctx.result(dbController.getAll()));
        post(ctx -> ctx.result(dbController.post()));
        sensorControllerPaths(dbController);

        path(":id", () -> {
            get(ctx -> ctx.result(dbController.getOne(ctx.pathParam("id"))));
            delete(ctx -> ctx.result(dbController.delete(ctx.pathParam("id"))));
        });
    }

    static void LedPaths(LedInterface ledInterface) {
        get(ctx -> ctx.result(ledInterface.toggle()));
        sensorControllerPaths(ledInterface);
        path("/blink", () -> get(ctx -> ctx.result(ledInterface.blink())));
        path("/pulse", () -> get(ctx -> ctx.result(ledInterface.pulse())));
    }
}
