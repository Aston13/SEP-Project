package sep.seeter.client;

import sep.seeter.client.state.State;
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
import static sep.seeter.client.Client.rb;

/**
 * This Class acts as a receiver within the Command Pattern to implement all 
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
    
    /**
     * Class constructor.
     * 
     * @param user the user's name.
     * @param host the host name.
     * @param port the port number.
     */
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

    /**
     * Used to communicate with the server.
     * 
     * @param msg A <code>Message</code> to send to 
     *        the <code>ClientChannel</code>.
     * @return True if message was successfully sent.
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

    /**
     * Used to communicate with the server.
     * 
     * @return A <code>Message</code> from the <code>ClientChannel</code>.
     * @throws ClassNotFoundException for channel.receive();
     */
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

    /**
     * Checks if the parameterised user and host are valid.
     * 
     * @return True if <code>user</code> and <code>host</code> are valid.
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
    
    /**
     * Checks if each current drafted body line is valid based on a rule set.
     * 
     * @return True if all current body lines are valid.
     */
    public boolean isDraftedLinesValid() {
        if (draftLines.isEmpty()) {
            System.out.println(rb.getString("send_invalid_empty"));
            return false;
        }
        
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
        return true;
    }
    
    /**
     * Checks if the topic is valid to a alphanumeric rule set.
     * 
     * @param topicName the topic name to be checked.
     * @return True if the topic is valid.
     */
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
    
    /**
     * @return a formatted <code>String</code> representing the current draft.
     */
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
    
    /**
     * Clears the held data about the current drafted topic.
     */
    public void resetDraftData() {
        draftLines.clear();
        draftTopics.clear();
        topicHistory.clear();
    }
    
    public void addDraftLine(String line) {
        draftLines.add(line);
    }
    
    /**
     * Removes a body line from a drafted topic body.
     * 
     * @return True if the line was removed.
     */
    public boolean removeDraftLine() {
        if (!draftLines.isEmpty()) {
            draftLines.remove(draftLines.size()-1);
            return true;
        } else {
            System.out.println(rb.getString("empty_draft_lines"));
            return false;
        }
    }
    
    /**
     * Sets the current drafts topic data.
     * 
     * @param newTopic topic name.
     */
    public void setDraftTopic(String newTopic) {
        draftTopic = newTopic;
        topicHistory.add(newTopic);
        draftTopics.add(newTopic);
    }
    
    /**
     * Adds a secondary topic from the current draft.
     * 
     * @param additionalTopic Secondary topic name.
     */
    public void addComposeTopic(String additionalTopic) {
        topicHistory.add(additionalTopic);
        draftTopics.add(additionalTopic);
    }
    
    /**
     * Removes a secondary topic from the current draft.
     * 
     * @return True if topic was successfully removed.
     */
    public boolean removeAdditionalTopic() {
        if (draftTopics.size() > 1) {
            draftTopics.remove(topicHistory.pop());
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets a published topic, based upon <code>draftTopic</code>.
     *
     * @return <code>Publish</code> topic if existing,
     *         else Publish is returned as <code>null</code>;
     */
    public Publish getPublish () {
        if (draftTopics.size() >= 1) {
            publish = new Publish(user, draftTopics, draftLines);
        }
        return publish;
    }
    
    /* 
     * State related methods
     */

    /**
     * Called by an appropriate <code>Command</code> to change the active state.
     */
    public void changeState() {
        if (state == State.MAIN) {
            state = State.DRAFTING;
        } else {
            state = State.MAIN;
        }
    }
    
    /**
     * Gets the active <code>State</code>, either drafting or main.
     *
     * @return The active state.
     */
    public State getState() {
        return state;
    }
   
    /**
     * Gets the active <code>State</code>'s header output for the CLI.
     *
     * @return A localised String representing the current <code>State</code>.
     */
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
