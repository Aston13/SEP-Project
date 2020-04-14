package sep.coursework.command;

import sep.coursework.Model;

/* Concrete Topic Command.
 * This class encapsulates the Topic command's specific data and methods.
 *
 * @author Aston Turner
 */
public class TopicCommand implements Command {

    private final Model model;
    private final String topic;

    public TopicCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        if (topic == null) {
            model.isTopicValid("");
        } else {
            if (model.isTopicValid(topic)) {
                model.addComposeTopic(topic);
            }
        }
    }
    
    @Override
    public boolean undo() {
        return model.removeAdditionalTopic();
    }
}
