package sep.coursework.command;

import sep.coursework.Model;

/**
 *
 * @author Aston Turner
 */
public class DiscardCommand implements Command {

    private final Model model;

    public DiscardCommand(Model newModel) {
        model = newModel;
    }
    
    @Override
    public void execute() {
        model.resetDraftData();
        model.changeState();
    }
    
    @Override
    public boolean undo() {
        return false;
    }

}
