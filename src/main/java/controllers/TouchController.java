package controllers;

import dbClasses.DbController;
import io.javalin.http.Context;

//sudo wget http://bits.netbeans.org/netbeans/8.2/community/zip/netbeans-8.2-201610071157.zip

public class TouchController extends DbController {


    public TouchController() {
        super.table = "touch";
    }

    @Override
    public void getAll(Context ctx) {

    }

    @Override
    public void post(Context ctx) {
        throw new UnsupportedOperationException("Method not suported");
    }

    @Override
    public void getOne(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }
}
