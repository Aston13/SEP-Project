package sep.coursework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private ByteArrayInputStream inputData;
    private String fullOutput = "";
    private boolean printOutput = false;
    
    public String[] getClientArgs() {
        return new String[] {"Aston", "localhost", "8888"};
    }
    
    public void provideInput(String data) {
        inputData = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputData);
    }
    
    public String getOutLine(int line) {
       String []dataLines = outData.toString().split("\\r?\\n");
       return dataLines[line];
    }
    
    public void printOutputLines() {
       String []dataLines = outData.toString().split("\\r?\\n");

       for (String s: dataLines) {
           fullOutput += s + "\n";
       }
       
       printOutput = true;
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
    public void setUpIOStreams() throws UnsupportedEncodingException, IOException {
        System.setOut(new PrintStream(outData));
        System.setErr(new PrintStream(errData));
        System.setIn(new ByteArrayInputStream(("exit").getBytes("UTF-8")));
    }
    
    @After
    public void restoreIOStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        if(printOutput){System.out.println(fullOutput);}
    }
}
