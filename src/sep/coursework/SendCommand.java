package sep.coursework;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
        try {
            model.send(model.getPublish());
            model.resetDraftLines();
            model.changeState();
        } catch (IOException ex) {
            Logger.getLogger(SendCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void undo() {
        System.out.println("Can't undo the previous send command.");
    }
}
