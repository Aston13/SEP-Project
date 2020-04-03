package sep.coursework;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Aston Turner
 */
public class BodyCommand implements Command {
    
    private DraftReceiver draftReceiver;
    private String line;
            
    public BodyCommand(DraftReceiver draftReceiver, String []arguments) {
        this.draftReceiver = draftReceiver;
        line = Arrays.stream(arguments).
              collect(Collectors.joining());
    }
    
    @Override
    public void execute() {
        draftReceiver.addDraftLine(line);
    }

}
