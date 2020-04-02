package sep.coursework;

import java.util.List;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 *
 * @author Aston Turner
 */
public class ComposeCommand implements Command {
    
    Receiver theReceiver;
    String topic;

    public ComposeCommand(Receiver newReceiver, String newTopic) {
        theReceiver = newReceiver;
        topic = newTopic;
    }
    
    @Override
    public void execute() {
        
        if (topic != null) {
            System.out.println("Drafting: #" + topic);
            // Change to drafting state and print drafting menu.
            formatDraftingMenuPrompt(topic, draftLines));
            
            
        } else {
            System.out.println("Topic Required to Compose");
            //theReceiver.setUserMessage("Topic Required");
        }
    }
    
    public String getDraftTopic() {
        return topic;
    }
    
    private String formatDraftingMenuPrompt(String topic,
      List<String> lines) {
        return "\nDrafting: " + formatDrafting(topic, lines)
            + "\n[Drafting] Enter command: "
            + "body [mytext], "
            + "send, "
            + "exit"
            + "\n> ";
  }
    
    private String formatDrafting(String topic, List<String> lines) {
        StringBuilder b = new StringBuilder("#");
        b.append(topic);
        int i = 1;
        for (String x : lines) {
          b.append("\n");
          b.append(String.format("%12d", i++));
          b.append("  ");
          b.append(x);
        };
        return b.toString();
  }

}
