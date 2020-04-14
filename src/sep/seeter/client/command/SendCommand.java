package sep.seeter.client.command;

import sep.seeter.client.Model;

/**
 * Concrete Send Command.
 * This class encapsulates the Send command's specific data and methods.
 *
 * @author Aston Turner
 */
public class SendCommand implements Command {

    private final Model model;
    
    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     */
    public SendCommand(Model newModel) {
       model = newModel;
    }
    
    /**
     * Tries to send the current draft to the server.
     */
    @Override
    public void execute() {
        if (model.isDraftedLinesValid()) {
            if (model.send(model.getPublish())) {
                model.resetDraftData();
                model.changeState();
            }
        }
    }
    
    /**
     * @return False as this command cannot be undone.
     */
    @Override
    public boolean undo() {
        return false;
    }
}
