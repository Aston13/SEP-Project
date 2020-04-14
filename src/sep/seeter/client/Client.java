package sep.seeter.client;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** 
 * This class contains the main method for running the program, as well as
 * the initialisation of the MVC classes and locale. 
 *
 * @author Aston Turner
 */
public class Client {
    
    /* Initialisation of i18n variables */
    private static final String RESOURCE_PATH = 
            "sep/seeter/client/resourcebundles/MessageBundle";
    private final Locale locale = new Locale("en", "GB");
    public static ResourceBundle rb;
    

    /**
     * Main method to be called to start the program.
     *
     * @param args Arguments required to initialise <code>Client</code>.
     * @throws IOException IO errors.
     */
    public static void main(String[] args) throws IOException {
        new Client(args[0], args[1], Integer.parseInt(args[2]));
    }
    
    /**
     * Class constructor.
     * 
     * @param user the user's name.
     * @param host the host name.
     * @param port the port number.
     * @throws IOException IO errors.
     */
    public Client(String user, String host, int port) throws IOException {
        rb =  ResourceBundle.getBundle(RESOURCE_PATH, locale);
        Model model = new Model(user, host, port);
        View view = new View();
        new Controller(model, view);
        view.init();
        view.run();
    }
}
