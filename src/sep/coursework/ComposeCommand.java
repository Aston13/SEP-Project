package sep.coursework;

/**
 *
 * @author Aston Turner
 */
public class ComposeCommand implements Command {
    
    private final Model model;
    private final String topic;

    public ComposeCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        if (topic == null) {
            System.out.println("Enter a topic to compose");
        } else {
            model.setDraftTopic(topic);
            System.out.println(model.getDraftingOutput());
            model.changeState();
        } 
    }
}
