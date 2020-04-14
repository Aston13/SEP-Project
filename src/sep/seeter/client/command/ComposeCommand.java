package sep.seeter.client.command;

import sep.seeter.client.Model;

/**
 * Concrete Compose Command.
 * This class encapsulates the Compose command's specific data and methods.
 *
 * @author Aston Turner
 */
public class ComposeCommand implements Command {
    
    private final Model model;
    private final String topic;

    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     * @param arguments entered topic name.
     */
    public ComposeCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }
    
    /**
     * Starts drafting the topic, if possible.
     */
    @Override
    public void execute() {
        if (topic == null) {
            model.isTopicValid("");
        } else {
            if (model.isTopicValid(topic)) {
                model.setDraftTopic(topic);
                model.changeState();
            }
        }
    }

    /**
     * Undo of the previous <code>ComposeCommand</code> <code>execute()</code>.
     * 
     * @return True if the command was undone.
     */
    @Override
    public boolean undo() {
        new DiscardCommand(model).execute();
        return true;
    }
}
