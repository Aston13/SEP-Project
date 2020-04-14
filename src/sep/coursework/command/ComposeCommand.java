package sep.coursework.command;

import sep.coursework.Model;

/* Concrete Compose Command.
 * This class encapsulates the Compose command's specific data and methods.
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
            model.isTopicValid("");
        } else {
            if (model.isTopicValid(topic)) {
                model.setDraftTopic(topic);
                model.changeState();
            }
        }
    }

    @Override
    public boolean undo() {
        new DiscardCommand(model).execute();
        return true;
    }
}
