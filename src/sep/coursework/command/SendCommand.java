package sep.coursework.command;

import java.io.IOException;
import static sep.coursework.Client.rb;
import sep.coursework.Model;

/* Concrete Send Command.
 * This class encapsulates the Send command's specific data and methods.
 *
 * @author Aston Turner
 */
public class SendCommand implements Command {

    private final Model model;
    
    public SendCommand(Model newModel) {
       model = newModel;
    }
    
    @Override
    public void execute() {
        if (model.isDraftedLinesValid()) {
            try {
                model.send(model.getPublish());
                model.resetDraftData();
                model.changeState();
            } catch (IOException ex) {
                System.out.println(rb.getString("request_unsuccessful"));
            }
        }
    }
    
    @Override
    public boolean undo() {
        return false;
    }
}
