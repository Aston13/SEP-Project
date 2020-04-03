package sep.coursework;

import java.util.LinkedList;
import java.util.List;
import sep.seeter.net.message.Publish;

/* This Class is a receiver to implement drafting methods for 
 * command operations.
 *
 * @author Aston Turner
 */
public class DraftReceiver {
    
    private String draftTopic;
    private final List<String> draftLines;
    private Publish publish;
    
    public DraftReceiver() {
        draftTopic = null;
        draftLines = new LinkedList<>();
    }
    
    public String getDraftingOutput() {
        return "\nDrafting: " + getFormattedDraft()
            + "\n[Drafting] Enter command: "
            + "body [mytext], "
            + "send, "
            + "exit"
            + "\n> ";
    }
    
    public String getFormattedDraft() {
        StringBuilder b = new StringBuilder("#");
        b.append(draftTopic);
        int i = 1;
        
            for (String x : draftLines) {
                b.append("\n");
                b.append(String.format("%12d", i++));
                b.append("  ");
                b.append(x);
            };
            
        return b.toString();
    }
    
    public void addDraftLine(String line) {
        draftLines.add(line);
    }
    
    public void setDraftTopic(String newTopic) {
        draftTopic = newTopic;
    }
    
    public Publish getPublish (String user) {
        publish = new Publish(user, draftTopic, draftLines);
        return publish;
    }
}
