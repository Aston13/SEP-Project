package sep.coursework;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.message.Publish;

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
            model.changeState();
        } catch (IOException ex) {
            Logger.getLogger(SendCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
