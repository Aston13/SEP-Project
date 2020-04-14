package sep.seeter.client.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import static sep.seeter.client.Client.rb;
import sep.seeter.client.Model;
import sep.seeter.net.message.TopicsReply;
import sep.seeter.net.message.TopicsReq;

/**
 * Concrete Exit Command.
 * This class encapsulates the Exit command's specific data and methods.
 *
 * @author Aston Turner
 */
public class ListCommand implements Command{

    private final Model model;
    
    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     */
    public ListCommand(Model newModel) {
        model = newModel;
    }
    
    /**
     * Lists all published topics (if any).
     */
    @Override
    public void execute() {
        TopicsReply rep = null;
        
        if (model.send(new TopicsReq())) {
            try {
                rep = (TopicsReply) model.receive();
                if (rep != null) {
                    System.out.println(rb.getString("topics")
                            + rep.topics.toString());
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * @return False as this command cannot be undone.
     */
    @Override
    public boolean undo() {
        return false;
    }
}
