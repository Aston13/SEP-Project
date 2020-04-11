package sep.coursework;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.mvc.AbstractView;

/**
 *
 * @author Aston Turner
 */
public class View {
    private String userInput;
    private Controller theController;
    private boolean running;
    private final BufferedReader reader = new BufferedReader
        (new InputStreamReader(System.in));
    
    public View() {
        theController = null;
    }
    
    public void init() {
        if (theController == null) {
            throw new IllegalStateException("[MVC error] "
                    + "This View has not been passed to a Controller.");
        }
        running = true;
    }
    
    public void setController(Controller control) {
        if (control == null) {
            throw new IllegalArgumentException(
                    "[MVC error] Registering a null Controller.");
        }
        if (theController != null) {
            Logger.getLogger(AbstractView.class.getName()).log(Level.SEVERE,
                    "[MVC error] Controller already set.  Shutting down.");
            //control.shutdown();
        }
        theController = control;
    }
    
    @SuppressFBWarnings(
            value = "DM_DEFAULT_ENCODING",
            justification = "When reading console, ignore default encoding warning")

    // Main loop: print user options, read user input and process
    public void run() throws IOException {
        
        if(getModel().validParameters()) {
            System.out.println("\nHello " + getModel().getUser() + "!\n"
                    + "Note:  Commands can be abbreviated to any prefix, "
                    + "e.g., fe [mytopic].\n");
        } else {
            System.err.println("User/host has not been set.");
            close();
        }
        
        while(running) {
            System.out.print(getModel().getStateHeader());
            
            userInput = reader.readLine();
            if (!theController.invokeCommand(userInput)) {
                System.out.println("Could not parse command/args.");
            }
        }
        
        return;
    }
    
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("No reader to close.");
        }
        running = false;
    }
    
    public Controller getController() {
        if (theController == null) {
            throw new IllegalStateException("[MVC error] "
                    + "This View has not been bound to a Controller.");
        }
        return theController;
    }
    
    public Model getModel() {
        return getController().getModel();
    }
}
