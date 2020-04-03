package sep.coursework;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Must remain the main class for running the client (via main()).
public class Client {

    private final String user;
    private final String host;
    private final int port;
    private final boolean printSplash;
    private CommandWords commandWords;
    private ServerReceiver serverReceiver;
    private DraftReceiver draftReceiver;

   
    /* Must accept the same arguments as current. 
     * User name, host name and port number.
     */
    public static void main(String[] args) throws IOException {
        String user = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);

        Client client = new Client(user, host, port);
        client.run();
    }

    public Client(String user, String host, int port) {
        this.user = user;
        this.host = host;
        this.port = port;
        printSplash = true;
        
        draftReceiver = new DraftReceiver();
        serverReceiver = new ServerReceiver(user, host, port);
        commandWords = new CommandWords(serverReceiver, draftReceiver);
    }

    // Run the client
    @SuppressFBWarnings(
            value = "DM_DEFAULT_ENCODING",
            justification = "When reading console, ignore default encoding warning")

    void run() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            if (user.isEmpty() || host.isEmpty()) {
                System.err.println("User/host has not been set.");
                
                if (commandWords.isCommandValid("exit")){
                    commandWords.invokeCommand();
                }
            }

            if (printSplash == true) {
                System.out.println("\nHello " + user + "!\n"
                        + "Note:  Commands can be abbreviated to any prefix, "
                        + "e.g., fe [mytopic].\n");
            }

            loop(reader);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            if (commandWords.isCommandValid("exit")){
                commandWords.invokeCommand();
            }
        }
    }

// Main loop: print user options, read user input and process
    public void loop(BufferedReader reader) throws IOException,
            ClassNotFoundException {

        String userInput;

        while(true) {
            
            userInput = reader.readLine();
            
            // Checks if the user entered a valid command and invokes it if true.
            if (commandWords.isCommandValid(userInput)){ // Pass in state here?
                commandWords.invokeCommand();
            } else {
                System.out.println("Command not recognised.");
            }   
            
        }
    }

    

    
}
