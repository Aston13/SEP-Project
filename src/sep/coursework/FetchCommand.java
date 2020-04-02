package sep.coursework;

import java.util.Iterator;
import java.util.List;
import sep.seeter.net.message.Message;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 *
 * @author Aston Turner
 */
public class FetchCommand implements Command {
    
    Receiver theReceiver;
    String topic;
    
    public FetchCommand(Receiver newReceiver, String newTopic) {
        theReceiver = newReceiver;
        this.topic = newTopic;
    }

    @Override
    public void execute() {
        if (topic != null) {
          theReceiver.send(new SeetsReq(topic));
          SeetsReply rep = (SeetsReply) theReceiver.receive();
          System.out.print(formatFetched(topic, rep.users, rep.lines));
        } else {
            System.out.println("Topic required");
            //theReceiver.setUserMessage("Topic Required");
        }
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
