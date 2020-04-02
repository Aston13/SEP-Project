package sep.coursework;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Aston Turner
 */
public class BodyCommand implements Command {
    
    String draftTopic;
    List <String> draftLines;
    String line;
    String [] arguments;
            
    public BodyCommand(String newDraftTopic, String [] arguments) {
        draftTopic = newDraftTopic;
        draftLines = new LinkedList<>();
    }
    
    @Override
    public void execute() {
        line = Arrays.stream(arguments).collect(Collectors.joining());
        
        draftLines.add(line);
    }

}
