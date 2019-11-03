package handler;

import dbClasses.DbController;
import static io.javalin.apibuilder.ApiBuilder.*;

class PathHandler {

    static void MakePaths(DbController dbController) {
        get(ctx -> ctx.result(dbController.getAll()));

        path("/realtime", () -> get(ctx -> ctx.result(dbController.realTimeData())));

        path(":id", () -> {
            get(ctx -> ctx.result(dbController.getOne(ctx.pathParam("id"))));
            delete(ctx -> ctx.result(dbController.delete(ctx.pathParam("id"))));
        });
    }

}
