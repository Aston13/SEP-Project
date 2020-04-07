package sep.coursework;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        model.addDraftLine(line);
    }

}
