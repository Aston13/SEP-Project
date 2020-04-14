package sep.seeter.client.command;

import sep.seeter.client.Model;

/**
 * Concrete Discard Command.
 * This class encapsulates the Discard command's specific data and methods.
 *
 * @author Aston Turner
 */
public class DiscardCommand implements Command {

    private final Model model;

    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     */
    public DiscardCommand(Model newModel) {
        model = newModel;
    }
    
    /**
     * Clears the current draft data, and changes state from Drafting to Main.
     */
    @Override
    public void execute() {
        model.resetDraftData();
        model.changeState();
    }
    
    /**
     * @return False as this command cannot be undone.
     */
    @Override
    public boolean undo() {
        return false;
    }

}
