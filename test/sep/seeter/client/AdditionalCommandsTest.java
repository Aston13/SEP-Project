package sep.seeter.client;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sep.seeter.server.Server;

/**
 * This test class tests the additionally added commands: Discard, List and 
 * Topic.
 * 
 * @see #printOutputLines() to display System.output of test.
 * @author Aston Turner
 */
public class AdditionalCommandsTest extends TestSuite {
    
    private Thread myThread;

    @Test
    public void discardDraft() throws IOException {
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            provideInput("compose mytopic\nbody one\ndiscard\nexit");
            Client.main(super.getClientArgs());
        }
        boolean expected = (getOutLine(14).startsWith("[Main]"));
        
        assertEquals(expected, true);
    }
    
    @Test
    public void listTopics() throws IOException {
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            provideInput("compose mytop\nbody aa\nsend\nlist\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals("Topics: [mytop]", getOutLine(18));
    }
    
    @Test
    public void composeAddTopic() throws IOException {
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            provideInput("compose one\nbody 1\nsend\n compose one\nbody 2\n"
                    + "topic two\nsend\nfetch two\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals("       Aston  2", getOutLine(33));
    }
    
    @Test
    public void composeAddBodyToExistingTopic() throws IOException {
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            
            // Run the same input twice to check for body duplication bugs.
            provideInput("compose one\nbody 1\nbody 2\nsend"
                    + "\ncompose one\nbody 3\nsend\n"
                    + "\ncompose two\nbody two\ntopic one\nsend" 
                    + "compose one\nbody 1\nbody 2\nsend"
                    + "\ncompose one\nbody 3\nsend\n"
                    + "\ncompose two\nbody two\ntopic one\nsend"
                    + "\nfetch one\nexit");
            Client.main(super.getClientArgs());
        }
        
        // Output of topic one should be: 1\2\3\two\1\2\3\two.
        // Output of topic two should be: two\two.
        assertEquals("       Aston  1", getOutLine(94));
        assertEquals("       Aston  2", getOutLine(95));
        assertEquals("       Aston  3", getOutLine(96));
        assertEquals("       Aston  two", getOutLine(97));
        assertEquals("       Aston  1", getOutLine(98));
        assertEquals("       Aston  2", getOutLine(99));
        assertEquals("       Aston  3", getOutLine(100));
        assertEquals("       Aston  two", getOutLine(101));
    }
}