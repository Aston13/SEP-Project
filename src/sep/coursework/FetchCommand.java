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
    
    Receiver theReceiver;
    String topic;
    
    public FetchCommand(Receiver newReciever, String topic) {
        theReceiver = newReciever;
        this.topic = topic;
    }

    @Override
    public void execute() {
          theReceiver.send(new SeetsReq(topic));
          SeetsReply rep = (SeetsReply) theReceiver.receive();
          System.out.print(formatFetched(topic, rep.users, rep.lines));
    }
    
  String formatFetched(String topic, List<String> users,
      List<String> fetched) {
        StringBuilder b = new StringBuilder("Fetched: #");
        b.append(topic);
        Iterator<String> it = fetched.iterator();
        for (String user : users) {
          b.append("\n");
          b.append(String.format("%12s", user));
          b.append("  ");
          b.append(it.next());
        };
        b.append("\n");
        return b.toString();
  }

}
