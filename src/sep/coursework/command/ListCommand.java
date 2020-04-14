package sep.coursework.command;

import java.io.IOException;
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
        try {
            model.send(new TopicsReq());
        } catch (IOException ex) {
            System.out.println(rb.getString("request_unsuccessful"));
        }
        try {
            TopicsReply t = (TopicsReply) model.receive();
            System.out.println(rb.getString("topics") + t.topics.toString());
            
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(rb.getString("request_unsuccessful"));
        } 
    }
    
    @Override
    public boolean undo() {
        return false;
    }
}
