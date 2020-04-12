package sep.coursework.command;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.coursework.Model;

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
        
        if (model.isBodyValid()) {
            try {
                model.send(model.getPublish());
                model.resetDraftData();
                model.changeState();
                System.out.println("Draft published.");
            } catch (IOException ex) {
                Logger.getLogger(SendCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Cannot send a topic with an empty body.");
        }
    }
    
    @Override
    public boolean undo() {
        return false;
        //System.out.println("Can't undo the previous send command.");
    }

    @Override
    public String getCommandString() {
        return "Send";
    }
    
    
}
