package sep.coursework;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sep.seeter.server.Server;

/**
 *
 * @author Aston Turner
 */
public class AdditionalCommandsTests extends TestSuite {
    
    private Thread myThread;
    
    @Test
    public void discardDraft() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
         
        provideInput("compose mytopic\nbody one\ndiscard\nexit");
        
        Client.main(super.getClientArgs());
        server.close();
        boolean expected = (getOutLine(14).startsWith("[Main]"));
        
        assertEquals(expected, true);
    }
    
    @Test
    public void listTopics() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
         
        provideInput("compose mytop\nbody aa\nsend\nlist\nexit");
        
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals("> Topics: [mytop]", getOutLine(16));
    }
    
    @Test
    public void composeAddTopic() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
         
        provideInput("compose one\nbody 1\nsend\n compose one\nbody 2\n"
                + "topic two\nsend\nfetch two\nexit");
        
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals("       Aston  2", getOutLine(31));
    }
 
}
