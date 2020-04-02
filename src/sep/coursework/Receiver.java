package sep.coursework;

import sep.seeter.net.message.Message;

/**
 * Interface for interacting with the Seeter Server.
 * @author Aston Turner
 */
public interface Receiver {
    
    /* Methods to support Reciever's capabilities */
    public void send(Message msg);
    public Message receive();
    // public void setUserMessage();
    // public String getUserMessage();
}
