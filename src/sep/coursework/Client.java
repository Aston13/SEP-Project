package sep.coursework;

import java.io.IOException;

// Must remain the main class for running the client (via main()).
public class Client {
    
    /* Must accept the same arguments as current. 
     * User name, host name and port number.
     */
    public static void main(String[] args) throws IOException {
        new Client(args[0], args[1], Integer.parseInt(args[2]));
    }
    
    public Client(String user, String host, int port) throws IOException {
        Model model = new Model(user, host, port);
        View view = new View();
        new Controller(model, view);
        view.init();
        view.run();
    }
}
