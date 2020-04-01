package sep.coursework;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

public class FetchCommand implements Command {

    private final ClientChannel channel;
    private SeetsReply reply;
    private String [] arguments;
    private String topic;
    private String formattedString;
    
    public FetchCommand(ClientChannel channel) {
        this.channel = channel;
    }

    @Override
    public void execute() {
        try {
            channel.send(new SeetsReq(topic));
        } catch (IOException ex) {
            Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            reply = (SeetsReply) channel.receive();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FetchCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        formatFetched();
    }
    
    @Override
    public void setArgs(String[] arguments) {
        this.arguments = arguments;
        topic = arguments[0];
    }
    
    private void formatFetched() {
        StringBuilder b = new StringBuilder("Fetched: #");
        b.append(topic);
        Iterator<String> it = reply.lines.iterator();
        for (String user : reply.users) {
            b.append("\n");
            b.append(String.format("%12s", user));
            b.append("  ");
            b.append(it.next());
        };
        b.append("\n");
        
        formattedString = b.toString();
    }
    
    public String getMessage(){
        return formattedString;
    }

    @Override
    public boolean requiresArgs() {
        return true;
    }
}
