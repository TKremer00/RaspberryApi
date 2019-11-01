package handler;

import java.util.ArrayList;

class PcbHandler {

    private static final ArrayList<String> routes = new ArrayList<>();


    static void AddPcb(String pcb) {
        routes.add(pcb);
    }

    static ArrayList<String> getAllRoutes() {
        return routes;
    }

}
