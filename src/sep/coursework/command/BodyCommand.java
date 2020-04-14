package sep.coursework.command;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import static sep.coursework.Client.rb;
import sep.coursework.Model;

/* Concrete Body Command.
 * This class encapsulates the Body command's specific data and methods.
 *
 * @author Aston Turner
 */
public class BodyCommand implements Command {
    
    private final Model model;
    private final String line;
            
    public BodyCommand(Model newModel, String []arguments) {
        model = newModel;
        line = Arrays.stream(arguments).collect(Collectors.joining());
    }
    
    @Override
    public void execute() {
        if (isBodyValid()) {
            model.addDraftLine(line);
        }
    }
    
    @Override
    public boolean undo() {
        return (model.removeDraftLine());
    }
    
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
        
        return true;
    }
}
