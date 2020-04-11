package sep.coursework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author Aston Turner
 */
class TestSuite {
    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errData = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    
    public String getOutLine(int line) {
       String []dataLines = outData.toString().split("\\r?\\n");
       return dataLines[line];
    }
    
    public String getErrLine(int line) {
       String []dataLines = errData.toString().split("\\r?\\n");
       return dataLines[line];
    }
    
    public ByteArrayOutputStream getOutData() {
        return outData;
    }
    
    public ByteArrayOutputStream getErrData() {
        return errData;
    }
    
    @Before
    public void setUpIOStreams() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(outData));
        System.setErr(new PrintStream(errData));
        System.setIn(new ByteArrayInputStream(("exit").getBytes("UTF-8")));
    }
    
    @After
    public void restoreIOStreams() {
        //System.setOut(null); // Avoids null pointer errors.
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
