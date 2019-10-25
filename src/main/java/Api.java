import handler.RouteHandler;
import io.javalin.Javalin;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Api {

    public static void main(String[] args) {
        try(final DatagramSocket socket = new DatagramSocket()){
            // Get the ip where the api is running
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            System.out.println("\n\n========== \nStarting API \nAT : "+  socket.getLocalAddress().getHostAddress() +":7000  \n==========\n\n");
        } catch (Exception e) {
            System.out.println("\n\n========== \nStarting API \n==========\n\n");
        }

        Javalin app = Javalin.create().start(7000);
        RouteHandler.Routes(app);
    }
}
