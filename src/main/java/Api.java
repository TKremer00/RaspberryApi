import controllers.TouchController;
import handler.RouteHandler;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.core.util.Header;
import java.net.DatagramSocket;
import java.net.InetAddress;


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

        app.before( ctx -> {
            if(ctx.method().equals("OPTIONS")) {
                ctx.header(Header.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            }
        });

        RouteHandler.Routes(app);

        //Start listening for touches
        TouchController.startListening();
    }
}
