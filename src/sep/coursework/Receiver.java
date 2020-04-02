package sep.coursework;

import sep.seeter.net.message.Message;

/**
 *
 * @author Aston Turner
 */
public interface Receiver {
    
    /* Methods to support Reciever's capabilities */
    public void send(Message msg);
    public Message receive();
}
