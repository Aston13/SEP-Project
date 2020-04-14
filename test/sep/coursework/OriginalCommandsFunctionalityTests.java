package sep.coursework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.*;
import org.junit.Test;
import sep.seeter.server.Server;

/**
 *
 * @author Aston Turner
 */
public class OriginalCommandsFunctionalityTests extends TestSuite {

    private Thread myThread;
    
    @Test
    public void emptyInputCommand() throws IOException {
        String expectedOutput = "> Could not parse command/args.";
        
        provideInput("  \rexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void invalidCommand() throws IOException {
        String expectedOutput = "> Could not parse command/args.";
        
        provideInput("ahg3254aglj\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void fetchStartsWith() throws UnsupportedEncodingException, IOException {
        String expectedOutput = "> Topic name should be non-empty and not "
            + "longer than 8 characters.";
        
        provideInput("fe\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void fetchEmpty() throws UnsupportedEncodingException, IOException {
        String expectedOutput = "> Topic name should be non-empty and not "
            + "longer than 8 characters.";
        provideInput("fetch\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void fetchTopic() throws IOException, ClassNotFoundException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "> Send request processed.";
         
        provideInput("fetch mytopic\nexit");
        
        Client.main(super.getClientArgs());
        
        server.close();
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void sendChangesState() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
         
        provideInput("compose mytopic\nbody one\nsend\nexit");
        
        Client.main(super.getClientArgs());
        server.close();
        boolean expected = (getOutLine(15).startsWith("[Main]"));
        
        assertEquals(expected, true);
    }
    
//    @Test
//    public void sendEmptyBody() throws IOException {
//        Server server = new Server(8888);
//        myThread = new Thread(() -> server.run());
//        myThread.start();
//         
//        provideInput("compose topic\nsend\nexit");
//        
//        Client.main(super.getClientArgs());
//        server.close();
//        
//        assertEquals("> Cannot publish a draft with an empty body.", getOutLine(9));
//    }
    
    @Test
    public void composeEmpty() throws IOException {
        String expectedOutput = "> Topic name should be non-empty and not longer than 8 characters.";
        
        provideInput("compose\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void composeTopic() throws IOException {
        String expectedOutput = "Drafting: #mytop";
        
        provideInput("compose mytop\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }
    
    @Test
    public void bodyAddLine() throws IOException {
        String expectedOutput = "           1  firstline"; // trim?

        provideInput("compose mytop\nbody first line\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }
    
    @Test
    public void fetchPublishedTopicBody() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "Receive request processed.";

        provideInput("compose fetTop\nbody test\nsend\nfetch fetTop\nexit");
        Client.main(super.getClientArgs());
        server.close();
        
        printOutputLines();
        
        assertEquals(expectedOutput, (getOutLine(17)));
    }
    
}
