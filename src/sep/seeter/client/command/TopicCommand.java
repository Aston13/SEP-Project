package sep.seeter.client.command;

import sep.seeter.client.Model;

/**
 * Concrete Topic Command.
 * This class encapsulates the Topic command's specific data and methods.
 *
 * @author Aston Turner
 */
public class TopicCommand implements Command {

    private final Model model;
    private final String topic;

    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     * @param newTopic the topic name to add as a secondary topic.
     */
    public TopicCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    /**
     * Tries to add a secondary topic to the current topic draft.
     */
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
    
    /**
     * Removes the previously added topic.
     * 
     * @return True if the previous <code>TopicCommand</code> 
     *         <code>execute()</code> was undone.
     */
    @Override
    public boolean undo() {
        return model.removeAdditionalTopic();
    }
}
