package sep.coursework;

import sep.seeter.net.message.Publish;

/**
 *
 * @author Aston Turner
 */
public class SendCommand implements Command {

    Receiver theReceiver;
    Publish completedDraft;
    
    public SendCommand(Receiver newReceiver, Publish completedDraft) {
       theReceiver = newReceiver;
       this.completedDraft = completedDraft;
    }
    
    @Override
    public void execute() {
        theReceiver.send(completedDraft);
        // Change state back to main.
        // Clear draftTopic in DraftCommand.
    }

}
