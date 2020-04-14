package sep.coursework;

import sep.coursework.state.State;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Message;
import sep.seeter.net.message.Publish;
import static sep.coursework.Client.rb;

/* This Class acts as a receiver within the Command Pattern to implement all 
 * operations on commands including server interaction and drafting methods.
 * This class represents the Model in the MVC architecture.
 *
 * @author Aston Turner
 */
public class Model {
    
    private final ClientChannel channel;
    private final String user;
    private final String host;
    private String draftTopic;
    private final HashSet<String> draftTopics;
    private final Stack<String> topicHistory;
    private final List<String> draftLines;
    private Publish publish;
    private State state;
    
    public Model(String user, String host, int port) {
        channel = new ClientChannel(host, port);
        
        this.user = user;
        this.host = host;
        draftTopic = null;
        draftTopics = new HashSet<>();
        draftLines = new LinkedList<>();
        topicHistory = new Stack<>();
        state = State.MAIN;
    }

    /*
     * Server interaction related methods.
     */
    public boolean send(Message msg) {
        try {
            channel.send(msg);
            System.out.println(rb.getString("request_send_successful"));
            return true;
        } catch (IOException ex) {
            System.out.println(rb.getString("request_send_unsuccessful"));
            return false;
        }
    }

    public Message receive() throws ClassNotFoundException {
        Message m = null;
        try {
            m = channel.receive();
            System.out.println(rb.getString("request_receive_successful"));
        } catch (IOException ex) {
            System.out.println(rb.getString("request_receive_unsuccessful"));
        }
        return m;
    }
    
    public boolean isServerOpen() {
        return channel.isOpen();
    }
    
    public String getUser() {
        return user;
    }

    /*
     * IO related methods.
     */
    public boolean validParameters() {
        if (user.isEmpty()) {
            try {
                Message.isValidUserId(user);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); //printout
                return false;
            }
        } else if (host.isEmpty()) {
            System.out.println(rb.getString("empty_host"));
            return false;
        }
        return true;
    }
    
    public boolean isDraftedLinesValid() {
        for(String line: draftLines) {
            if (line.isEmpty() || line.length() > 48) {
                System.out.println(rb.getString("body_invalid_length"));
                return false;
            }
            if (line.contains(System.getProperty("line.separator"))) {
                System.out.println(MessageFormat.format(rb.getString
                    ("body_invalid_line"), line));
                return false;
            }
        }
        return(true);
    }
    
    public boolean isTopicValid(String topicName) {
        if (topicName.isEmpty() || topicName.length() > 8) {
            System.out.println(rb.getString("topic_invalid_length"));
            return false;
        }
        if (!topicName.matches("^[a-zA-Z0-9]*$")) {
            System.out.println(MessageFormat.format(rb.getString
                ("topic_invalid_char"), topicName));
            return false;
        }
        return true;
    }
    
    public String getDraftingOutput() {
        return MessageFormat.format(rb.getString("drafting_state_header"),
                getFormattedDraft());
    }
    
    public String getFormattedDraft() {
        StringBuilder b = new StringBuilder("#");
        if (draftTopics.size() > 1) {
            Iterator<String> itr = draftTopics.iterator();
            while(itr.hasNext()){
                b.append(itr.next());
                if (itr.hasNext()) {b.append(", #");}
            }
        } else {
            b.append(draftTopic);
        }
            
        int i = 1;
        for (String x : draftLines) {
            b.append("\n");
            b.append(String.format("%12d", i++));
            b.append("  ");
            b.append(x);
        }
        return b.toString();
    }
    
    public void resetDraftData() {
        draftLines.clear();
        draftTopics.clear();
        topicHistory.clear();
    }
    
    public void addDraftLine(String line) {
        draftLines.add(line);
    }
    
    public boolean removeDraftLine() {
        if (!draftLines.isEmpty()) {
            draftLines.remove(draftLines.size()-1);
            return true;
        } else {
            System.out.println(rb.getString("empty_draft_lines"));
            return false;
        }
    }
    
    public void setDraftTopic(String newTopic) {
        draftTopic = newTopic;
        topicHistory.add(newTopic);
        draftTopics.add(newTopic);
    }
    
    public void addComposeTopic(String additionalTopic) {
        topicHistory.add(additionalTopic);
        draftTopics.add(additionalTopic);
    }
    
    public boolean removeAdditionalTopic() {
        if (draftTopics.size() > 1) {
            draftTopics.remove(topicHistory.pop());
            return true;
        } else {
            return false;
        }
    }
    
    public Publish getPublish () {
        if (draftTopics.size() > 1) {
            publish = new Publish(user, draftTopics, draftLines);
        } else {
            publish = new Publish(user, draftTopic, draftLines);
        }
       
        return publish;
    }
    
    /* 
     * State related methods
     */
    public void changeState() {
        if (state == State.MAIN) {
            state = State.DRAFTING;
        } else {
            state = State.MAIN;
        }
    }
    
    public State getState() {
        return state;
    }
   
    public String getStateHeader() {
        switch (state) {
            case MAIN:
                return rb.getString("main_state_header");
            case DRAFTING:
                return getDraftingOutput();
        }
        return rb.getString("state_not_set");
    }
}
