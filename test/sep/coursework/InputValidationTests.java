package sep.coursework;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sep.seeter.server.Server;

/**
 *
 * @author Aston Turner
 */
public class InputValidationTests extends TestSuite {
    
    private Thread myThread;
    
    /*
     * Body input validation tests.
     */
    
    @Test
    public void emptyBody() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "       Aston  publishTest";

        provideInput("compose top\nbody\nexit");
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals(expectedOutput, (getOutLine(17)));
    }
    
    @Test
    public void mid24Body() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "       Aston  publishTest";

        provideInput("compose top\nbody iiiiiiiiiiiiiiiiiiiiiiii\nexit");
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals(expectedOutput, (getOutLine(17)));
    }
    
    @Test
    public void max48Body() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "       Aston  publishTest";

        provideInput("compose top\nbody"
                + " iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii\nexit");
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals(expectedOutput, (getOutLine(17)));
    }
    
    @Test
    public void overMax49Body() throws IOException {
        Server server = new Server(8888);
        myThread = new Thread(() -> server.run());
        myThread.start();
        
        String expectedOutput = "       Aston  publishTest";

        provideInput("compose top\nbody"
                + " iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii\nexit");
        Client.main(super.getClientArgs());
        server.close();
        
        assertEquals(expectedOutput, (getOutLine(17)));
    }
    
    /*
     * Topic input validation tests.
     */
    
    /*
     *  User validation tests.
     */
    
}
