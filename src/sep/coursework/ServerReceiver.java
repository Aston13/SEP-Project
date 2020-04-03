package sep.coursework;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Message;
import sep.seeter.net.message.SeetsReply;

/* This Class is a receiver to implement server interaction methods for 
 * command operations.
 *
 * @author Aston Turner
 */
public class ServerReceiver {
    
    private ClientChannel channel;
    private String user;
    
    public ServerReceiver(String user, String host, int port) {
        channel = new ClientChannel(host, port);
        this.user = user;
    }

    public void send(Message msg) {
        try {
            channel.send(msg);
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Message receive() {
        Message reply = null;
        
        try {
            reply = channel.receive();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return reply;
    }
    
    public String getUser() {
        return user;
    }

}
