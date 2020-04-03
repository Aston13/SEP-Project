package sep.coursework;

/**
 *
 * @author Aston Turner
 */
public class ComposeCommand implements Command {
    
    private DraftReceiver draftReceiver;
    private String topic;

    public ComposeCommand(DraftReceiver newDraftReceiver, String newTopic) {
        draftReceiver = newDraftReceiver;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        if (topic == null) {
            System.out.println("Enter a topic to compose");
        } else {
            draftReceiver.setDraftTopic(topic);
            System.out.println(draftReceiver.getDraftingOutput());
        } 
    }
}
