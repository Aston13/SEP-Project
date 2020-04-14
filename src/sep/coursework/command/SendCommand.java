package sep.coursework.command;

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
            if (model.send(model.getPublish())) {
                model.resetDraftData();
                model.changeState();
            }
        }
    }
    
    @Override
    public boolean undo() {
        return false;
    }
}
