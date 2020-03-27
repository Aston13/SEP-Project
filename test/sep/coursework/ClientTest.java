package sep.coursework;

/*
 * Aston Turner created this.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import sep.coursework.Client;

/**
 *
 * @author Aston Turner <16052488 @ herts.ac.uk>
 */
public class ClientTest {

    private final InputStream tmpIn;
    private final PrintStream tmpOut;
    private String output = "1";
    
    public ClientTest() {
        tmpIn = System.in;
        tmpOut = System.out;
    }
    
    @After
    public void tearDown() {
        System.setIn(tmpIn);
        System.setOut(tmpOut);
    }
    
    @Test
    public void exitTest() throws UnsupportedEncodingException, IOException {
        
        String input = "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
        System.setIn(in); // Sets System.in to the supplied stream
        
        Client client = new Client();
        client.set("foo", "bar", 8888);
        client.run();
    }
    
    @Test
    public void helloTest() throws UnsupportedEncodingException, IOException { 
        
        String name = "username";
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out, true, "UTF-8"));
        
        String input = "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF-8"));
        System.setIn(in); // Sets System.in to the supplied stream
        
        Client client = new Client();
        client.set(name, "bars", 8888);
        client.run();
        output = out.toString("UTF-8");
        
        Assert.assertEquals(name, output);
        //System.out.println(output);
    }
}

