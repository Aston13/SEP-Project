package sep.coursework;

import java.util.Iterator;
import java.util.List;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
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
        if (topic != null) {
          model.send(new SeetsReq(topic));
          SeetsReply rep = (SeetsReply) model.receive();
          System.out.print(formatFetched(topic, rep.users, rep.lines));
        } else {
            System.out.println("The fetch command requires a topic.");
        }
    }
    
  public String formatFetched(String topic, List<String> users,
      List<String> fetched) {
        StringBuilder b = new StringBuilder("Fetched: #");
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

}
