package sep.seeter.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.Before;

/**
 * This test suite class provides methods for setting up, 
 * tearing down and processing IO data for child test classes.
 * 
 * @see #printOutputLines() to display System.output of test.
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
    
    /**
     * The arguments set here will be used by child classes to run tests.
     * 
     * @return arguments to initialise a <code>Client</code>.
     */
    public String[] getClientArgs() {
        return new String[] {"Aston", "localhost", "8888"};
    }
    
    /**
     * This method supplies input to the tests JVM <code>System.in</code>.
     * 
     * @param data the input <code>String</code> to supply.
     */
    public void provideInput(String data) {
        inputData = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputData);
    }
    
    /**
     * This method gets a line from the tests JVM output.
     * 
     * @param line the line number of the CLI output to retrieve.
     * @return a line from the tests JVM <code>System.out</code>.
     */
    public String getOutLine(int line) {
       String []dataLines = outData.toString().split("\\r?\\n");
       return dataLines[line];
    }
    
    /**
     * This method can be used to print the tests <code>System.out</code> 
     * output to the console.
     */
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
    
    /**
     * Sets the IO streams to the tests JVM.
     */
    @Before
    public void setUpIOStreams() throws UnsupportedEncodingException, IOException {
        System.setOut(new PrintStream(outData));
        System.setErr(new PrintStream(errData));
        System.setIn(new ByteArrayInputStream(("exit").getBytes("UTF-8")));
    }
    
    /**
     * Reverts the IO streams to the original.
     */
    @After
    public void restoreIOStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        if(printOutput){System.out.println(fullOutput);}
    }
}
