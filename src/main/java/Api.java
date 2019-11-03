import controllers.TouchController;
import handler.RouteHandler;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static io.javalin.apibuilder.ApiBuilder.path;


public class Api {

    public static void main(String[] args) {
        try(final DatagramSocket socket = new DatagramSocket()){
            // Get the ip where the api is running
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            System.out.println("\n\n========== \nStarting API \nAT : http://"+  socket.getLocalAddress().getHostAddress() +":7000  \n==========\n\n");
        } catch (Exception e) {
            System.out.println("\n\n========== \nStarting API \n==========\n\n");
        }

        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(7000);
        app.routes(() -> path("api/v1", RouteHandler::Routes));

        //Start listening for touches
        TouchController.startListening();
    }
}
