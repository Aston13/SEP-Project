package sep.seeter.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import static sep.seeter.client.Client.rb;

/**
 * In charge of running the main program loop for taking user input and 
 * outputting (most) UI output using the initialised Readers. 
 * IO data is passed to the Controller for processing (if required).
 * This class represents the View in the MVC architecture.
 *
 * @author Aston Turner
 */
public class View {
    private String userInput;
    private Controller theController;
    private boolean running;
    private BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Class constructor.
     */
    public View() {
        theController = null;
    }
    
    /**
     * Sets the variable <code>running</code> to true, used by the 
     * <code>Client.run</code> method's while loop to indicate that the program 
     * is running.
     */
    public void init() {
        running = true;
    }
    
    /**
     * Sets the <code>Controller</code> as part of the MVC.
     * 
     * @param control
     */
    public void setController(Controller control) {
        theController = control;
    }
    
    /**
     * @throws IOException
     */
    @SuppressFBWarnings(
            value = "DM_DEFAULT_ENCODING",
            justification = "When reading console, ignore default encoding warning")

    // Main program loop.
    public void run() throws IOException {
        
        if(getModel().validParameters()) {
            System.out.println(MessageFormat.format(rb.getString
        ("welcome_message"), getModel().getUser()));
        } else {
            System.err.println(rb.getString("user_host_not_set"));
            close();
        }
        
        while(running) {
            
            System.out.print(getModel().getStateHeader());
            
            userInput = reader.readLine();
            
            if (!theController.invokeCommand(userInput)) {
                System.out.println(rb.getString("unrecognised_command"));
            }
        }
    }
    
    /**
     * Gracefully closes the program. <code>running</code> is set to false to 
     * break the <code>Client.run</code> method's loop.
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        running = false;
    }
    
    /**
     * @return The bound <code>Controller</code>.
     */
    public Controller getController() {
        return theController;
    }
    
    /**
     * @return The bound <code>Model</code>.
     */
    public Model getModel() {
        return getController().getModel();
    }
}
