package sep.coursework;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Message;

/**
 *
 * @author Aston Turner
 */

/* This Class is a reciever to implement specific commands for 
 * sending and recieving seets.
 */
public class SeetsReceiver implements Receiver {
    
    private ClientChannel channel;
    
    public SeetsReceiver(ClientChannel newChannel) {
        this.channel = newChannel;
    }

    @Override
    public void send(Message msg) {
        try {
            channel.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(SeetsReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Message receive() {
        Message reply = null;
        
        try {
            reply = channel.receive();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SeetsReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return reply;
    }
}
