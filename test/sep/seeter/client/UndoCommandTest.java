package sep.seeter.client;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * This test class tests the functionality of the additional undo command on 
 * appropriate commands.
 * 
 * @author Aston Turner
 */
public class UndoCommandTest extends TestSuite {
    
    @Test
    public void undoTopic() throws IOException {
        provideInput("compose a\nbody a\ntopic b\ntopic c\nundo\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals("Drafting: #a, #b", (getOutLine(23)));
    }
    
    @Test
    public void undoCompose() throws IOException {
        provideInput("compose a\ndiscard\nexit");
        Client.main(super.getClientArgs());
        boolean output = getOutLine(10).startsWith("[Main]");
        
        assertEquals(output, true);
    }
    
    @Test
    public void undoBody() throws IOException {
        provideInput("compose a\nbody new\nundo\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals("> The previous body command was undone.", (getOutLine(13)));
    }
    
    @Test
    public void undoNothing() throws IOException {
        provideInput("undo\nexit");
        Client.main(super.getClientArgs());
        
        assertEquals("> Nothing to undo.", (getOutLine(6)));
    }
}
