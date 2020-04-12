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
        model.changeState();
    }
    
    @Override
    public boolean undo() {
        return false;
        //System.out.println("Can't undo the previous discard command.");
    }
    
    @Override
    public String getCommandString() {
        return "Discard";
    }

}
