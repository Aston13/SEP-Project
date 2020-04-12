package sep.coursework.command;

import sep.coursework.Model;
import sep.seeter.net.message.Message;

/**
 *
 * @author Aston Turner
 */
public class TopicCommand implements Command {

    private final Model model;
    private String topic;

    public TopicCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        if (topic == null) {
            isTopicValid("");
        } else {
            if (isTopicValid(topic)) {
                model.addComposeTopic(topic);
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
        return true;
    }

    @Override
    public String getCommandString() {
        return "Topic";
    }

}
