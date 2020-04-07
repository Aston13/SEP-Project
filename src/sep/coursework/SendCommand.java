package sep.coursework;

import sep.seeter.net.message.Publish;

/**
 *
 * @author Aston Turner
 */
public class SendCommand implements Command {

    private final Model model;
    private Publish publishDraft;
    
    public SendCommand(Model newModel) {
       model = newModel;
       
    }
    
    @Override
    public void execute() {
        model.send(publishDraft);
        model.changeState();
    }

}
