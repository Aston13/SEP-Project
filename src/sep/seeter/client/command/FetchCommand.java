package sep.seeter.client.command;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sep.seeter.client.Client.rb;
import sep.seeter.client.Model;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 * Concrete Fetch Command.
 * This class encapsulates the Fetch command's specific data and methods.
 *
 * @author Aston Turner
 */
public class FetchCommand implements Command {
    
    private final Model model;
    private final String topic;
    
    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     * @param newTopic the entered topic name to be fetched.
     */
    public FetchCommand(Model newModel, String newTopic) {
        model = newModel;
        topic = newTopic;
    }

    /**
     * Fetches and outputs a topic from the server, if possible.
     */
    @Override
    public void execute() {
        SeetsReply rep = null;
        
        if (model.isTopicValid(topic)) {
            if (model.send(new SeetsReq(topic))) {
                try {
                    rep = (SeetsReply) model.receive();
                    if (rep != null) {
                        System.out.print(formatFetched(topic, 
                                rep.users, rep.lines));
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FetchCommand.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Formats a string of the fetched topic data to be outputted.
     * 
     * @param topic the topic name.
     * @param users the user whom added the line to the topic.
     * @param fetched the list of fetched lines.
     * @return formatted <code>String</code>.
     */
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
  
    /**
     * @return False as this command cannot be undone.
     */
    @Override
    public boolean undo() {
        return false;
    }
}
