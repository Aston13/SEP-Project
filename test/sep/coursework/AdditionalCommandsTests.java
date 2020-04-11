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
        
        Client.main(new String[] {"foo", "bar", "8888"});
        server.close();
        boolean expected = (getOutLine(14).startsWith("[Main]"));
        
        assertEquals(expected, true);
    }
    
//    @Test
//    public void listTopics() throws IOException {
//        Server server = new Server(8888);
//        myThread = new Thread(() -> server.run());
//        myThread.start();
//         
//        String expected = "Topics:";
//        provideInput("compose mytopic\nbody one\nsend\nlist\nexit");
//        
//        Client.main(new String[] {"foo", "bar", "8888"});
//        server.close();
//        
//        assertEquals(expected, getOutLine(15));
//    }
 
}
