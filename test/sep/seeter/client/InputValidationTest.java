package sep.seeter.client;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sep.seeter.server.Server;

/**
 *
 * @author Aston Turner
 */
public class InputValidationTest extends TestSuite {
    
    private Thread myThread;
    
    /*
     * Body validation tests.
     */
    @Test
    public void emptyBody() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Seet body should be non-empty and"
                    + " not longer than 48 characters.";
            provideInput("compose top\nbody \nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(9)));
    }
    
    @Test
    public void minBodyLength1() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "           1  1";
            provideInput("compose top\nbody 1\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }
    
    @Test
    public void midBodyLength24() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "           1  iiiiiiiiiiiiiiiiiiiiiiii";
            provideInput("compose top\nbody iiiiiiiiiiiiiiiiiiiiiiii\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }
    
    @Test
    public void maxBodyLength48() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "           1  iiiiiiiiiiiiiiiiiiiiiiii"
                    + "iiiiiiiiiiiiiiiiiiiiiiii";
            provideInput("compose top\nbody"
                    + " iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }
    
    @Test
    public void outerMaxBodyLength49() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Seet body should be non-empty and not "
                    + "longer than 48 characters.";
            provideInput("compose top\nbody"
                    + " iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(9)));
    }
    
    @Test
    public void validAlphaBodyChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "           1  aAzZ";
            provideInput("compose top\nbody aAzZ\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }
    
    @Test
    public void validNumericBodyChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "           1  09";
            provideInput("compose top\nbody 09\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(11)));
    }

    @Test
    public void invalidSymbolBodyChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Body line should be alphanumeric, not: ^]*$";
            provideInput("compose top\nbody ^]*$\nexit");
            Client.main(super.getClientArgs());
        }
        printOutputLines();
        
        assertEquals(expectedOutput, (getOutLine(9)));
    }  
    
    /*
     * Topic validation tests.
     */
    @Test
    public void emptyTopic() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Topic name should be non-empty and"
                    + " not longer than 8 characters.";
            provideInput("compose\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void minTopicLength1() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "Drafting: #1";
            provideInput("compose 1\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }
    
    @Test
    public void midTopicLength4() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "Drafting: #1234";
            provideInput("compose 1234\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }
    
    @Test
    public void maxTopicLength8() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "Drafting: #12345678";
            provideInput("compose 12345678\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }
    
    @Test
    public void outerMaxTopicLength9() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Topic name should be non-empty and"
                    + " not longer than 8 characters.";
            provideInput("compose 123456789\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }
    
    @Test
    public void validAlphaTopicChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "Drafting: #aAzZ";
            provideInput("compose aAzZ\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }
    
    @Test
    public void validNumericTopicChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "Drafting: #09";
            provideInput("compose 09\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(7)));
    }

    @Test
    public void invalidSymbolTopicChar() throws IOException {
        String expectedOutput;
        try (Server server = new Server(8888)) {
            myThread = new Thread(() -> server.run());
            myThread.start();
            expectedOutput = "> Topic should be alphanumeric, not: ^]*$";
            provideInput("compose ^]*$\nexit");
            Client.main(super.getClientArgs());
        }
        
        assertEquals(expectedOutput, (getOutLine(6)));
    }   
}
