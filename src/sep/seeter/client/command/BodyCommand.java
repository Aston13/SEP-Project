package sep.seeter.client.command;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import static sep.seeter.client.Client.rb;
import sep.seeter.client.Model;

/**
 * Concrete Body Command.
 * This class encapsulates the Body command's specific data and methods.
 *
 * @author Aston Turner
 */
public class BodyCommand implements Command {
    
    private final Model model;
    private final String line;
            
    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     * @param arguments entered arguments to add to the current draft.
     */
    public BodyCommand(Model newModel, String []arguments) {
        model = newModel;
        line = Arrays.stream(arguments).collect(Collectors.joining());
    }
    
    /**
     * Adds a line to the current draft, if valid.
     */
    @Override
    public void execute() {
        if (isBodyValid()) {
            model.addDraftLine(line);
        }
    }
    
    /**
     * Undo of the previous <code>BodyCommand</code> <code>execute()</code>.
     * 
     * @see #execute()
     * @return True if a line was removed from the current draft.
     */
    @Override
    public boolean undo() {
        return (model.removeDraftLine());
    }
    
    /**
     * Checks if a line is valid based on a rule set.
     * 
     * @return True if the entered line is valid.
     */
    public boolean isBodyValid() {
        if (line.isEmpty() || line.length() > 48) {
            System.out.println(rb.getString("body_invalid_length"));
            return false;
        }
        if (line.contains(System.getProperty("line.separator"))) {
            System.out.println(MessageFormat.format(rb.getString
                ("body_invalid_line"), line));
            return false;
        }
        if (!line.matches("^[a-zA-Z0-9]*$")) {
            System.out.println(MessageFormat.format(rb.getString
                ("body_invalid_char"), line));
            return false;
        }
        return true;
    }
}
