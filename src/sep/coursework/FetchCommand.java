package sep.coursework;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                model.send(new SeetsReq(topic));
                SeetsReply rep = (SeetsReply) model.receive();
                System.out.print(formatFetched(topic, rep.users, rep.lines));
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
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
  
    @Override
    public boolean undo() {
        return false;
        //System.out.println("Can't undo the previous fetch command.");
    }
    
    @Override
    public String getCommandString() {
        return "Fetch";
    }

}
