package sep.coursework;

import java.util.Arrays;
import java.util.stream.Collectors;
import sep.seeter.net.message.Message;

/**
 *
 * @author Aston Turner
 */
public class BodyCommand implements Command {
    
    private final Model model;
    private final String line;
            
    public BodyCommand(Model newModel, String []arguments) {
        model = newModel;
        line = Arrays.stream(arguments).
              collect(Collectors.joining());
    }
    
    @Override
    public void execute() {
        if (isBodyValid()) {
            model.addDraftLine(line);
        }
    }
    
    public boolean isBodyValid() {
        try {
            Message.isValidBody(line);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
