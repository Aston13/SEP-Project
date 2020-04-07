package sep.coursework;

import java.io.IOException;

// Must remain the main class for running the client (via main()).
public class Client {

    private static String user;
    private static String host;
    private static int port;
    
    private final Model theModel;
    private final View theView;
    private final Controller theController;

    /* Must accept the same arguments as current. 
     * User name, host name and port number.
     */
    public static void main(String[] args) throws IOException {
        user = args[0];
        host = args[1];
        port = Integer.parseInt(args[2]);
        Client client = new Client();
    }
    
    public Client() throws IOException {
        theModel = new Model(user, host, port);
        theView = new View();
        theController = new Controller(theModel, theView);
        
        theView.init();
        theView.run();
    }
}
