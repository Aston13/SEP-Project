package sep.coursework.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import static sep.coursework.Client.rb;
import sep.coursework.Model;
import sep.seeter.net.message.TopicsReply;
import sep.seeter.net.message.TopicsReq;

/* Concrete Exit Command.
 * This class encapsulates the Exit command's specific data and methods.
 *
 * @author Aston Turner
 */
public class ListCommand implements Command{

    private final Model model;
    
    public ListCommand(Model newModel) {
        model = newModel;
    }
    
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
    
    @Override
    public boolean undo() {
        return false;
    }
}
