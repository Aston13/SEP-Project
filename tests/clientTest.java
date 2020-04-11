import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.*;
import org.junit.Test;
import original.Client;

/**
 *
 * @author Aston Turner <16052488 @ herts.ac.uk>
 */
public class clientTest extends Suite {

    @Test
    public void exit() throws UnsupportedEncodingException, IOException {
        String input = "exit";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
        System.setIn(in); // Sets System.in to the supplied stream
        Client.main(new String[] {"foo", "bar", "8888"});
    }
    
    @Test
    public void welcomeUser() throws IOException  { 
        String expectedWelcomeMsg = "Hello foo!";
        Client.main(new String[] {"foo", "bar", "8888"});
        String actualWelcomeMsg = (getOutLine(1));
        
        assertEquals(expectedWelcomeMsg, actualWelcomeMsg);
    }
    
    @Test
    public void emptyUserHost() throws IOException {
        String expectedErrMsg = ("User/host has not been set.");
        Client.main(new String[] {"", "", "8888"});
        String actualErrMsg = (getErrLine(0));
        
        assertEquals(expectedErrMsg, actualErrMsg);
    }
}
