package sep.coursework;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import static sep.coursework.Client.rb;

/* In charge of running the main program loop for taking user input and 
 * outputting (most) UI output using the initialisated Readers. 
 * IO data is passed to the Controller for processing (if required).
 * This class represents the View in the MVC architecture.
 *
 * @author Aston Turner
 */
public class View {
    private String userInput;
    private Controller theController;
    private boolean running;
    private BufferedReader reader =reader = new BufferedReader(new InputStreamReader(System.in));
    
    public View() {
        theController = null;
    }
    
    public void init() {
        running = true;
    }
    
    public void setController(Controller control) {
        theController = control;
    }
    
    @SuppressFBWarnings(
            value = "DM_DEFAULT_ENCODING",
            justification = "When reading console, ignore default encoding warning")

    // Main loop: print user options, read user input and process
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
    
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        running = false;
    }
    
    public Controller getController() {
        return theController;
    }
    
    public Model getModel() {
        return getController().getModel();
    }
}
