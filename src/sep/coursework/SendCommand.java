package sep.coursework;

import sep.seeter.net.message.Publish;

/**
 *
 * @author Aston Turner
 */
public class SendCommand implements Command {

    private ServerReceiver serverReceiver;
    private DraftReceiver draftReceiver;
    private Publish publishDraft;
    private String user;

    
    public SendCommand(ServerReceiver serverReceiver,
            DraftReceiver draftReceiver) {
       this.serverReceiver = serverReceiver;
       this.draftReceiver = draftReceiver;
       user = serverReceiver.getUser();
       
    }
    
    @Override
    public void execute() {
        publishDraft = draftReceiver.getPublish(user);
        serverReceiver.send(publishDraft);
    }

}
