package sep.coursework.command;

import sep.coursework.Model;
import sep.seeter.net.message.Message;

/**
 *
 * @author Aston Turner
 */
public class ComposeCommand implements Command {
    
    private final Model model;
    private String topic;

    public ComposeCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        
        if (topic == null) {
            isTopicValid("");
        } else {
            if (isTopicValid(topic)) {
                model.setDraftTopic(topic);
                model.changeState();
            }
        }
    }
    
    public boolean isTopicValid(String topicName) {
        try {
            Message.isValidTopic(topicName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean undo() {
        new DiscardCommand(model).execute();
        //System.out.println("Compose command undone.");
        return true;
    }
    
    @Override
    public String getCommandString() {
        return "Compose";
    }
}
