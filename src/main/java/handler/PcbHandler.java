package handler;

import java.util.ArrayList;

class PcbHandler {

    private static final ArrayList<String> routes = new ArrayList<>();


    static void AddPcb(String pcb) {
        routes.add(pcb);
    }

    static String getHtml(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head><meta name=\"author\" content=\"TKremer00\"><meta name=\"github\" content=\"https://github.com/TKremer00/\"></head>");
        sb.append("<body>");
        for (String value: routes) {
            sb.append("<a href=\"/").append(value).append("\">").append(value).append("</a><br>");
        }
        sb.append("</body></html>");
        return sb.toString();
    }

}
