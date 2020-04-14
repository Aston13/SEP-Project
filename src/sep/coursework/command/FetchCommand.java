package sep.coursework.command;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import static sep.coursework.Client.rb;
import sep.coursework.Model;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/* Concrete Fetch Command.
 * This class encapsulates the Fetch command's specific data and methods.
 *
 * @author Aston Turner
 */
public class FetchCommand implements Command {
    
    private final Model model;
    private final String topic;
    
    public FetchCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }

    @Override
    public void execute() {
        if (model.isTopicValid(topic)) {
            try {
                model.send(new SeetsReq(topic));
                try {
                    SeetsReply rep = (SeetsReply) model.receive();
                    System.out.print(formatFetched(topic, rep.users, rep.lines));
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(rb.getString("request_unsuccessful"));
                }
            } catch (IOException ex) {
                System.out.println(rb.getString("request_unsuccessful"));
            } 
        }
    }
    
  public String formatFetched(String topic, List<String> users,
      List<String> fetched) {
      
        StringBuilder b = new StringBuilder(rb.getString("fetched"));
        b.append(topic);
        Iterator<String> it = fetched.iterator();
        
        for (String user : users) {
            b.append("\n");
            b.append(String.format("%12s", user));
            b.append("  ");
            b.append(it.next());
        }
        
        b.append("\n");
        return b.toString();
  }
  
    @Override
    public boolean undo() {
        return false;
    }
}
