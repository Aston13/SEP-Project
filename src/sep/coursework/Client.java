package sep.coursework;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.net.channel.ClientChannel;
import sep.seeter.net.message.Bye;
import sep.seeter.net.message.Publish;

// Must remain the main class for running the client (via main()).
public class Client {

    private final String user;
    private final String host;
    private final int port;
    private final boolean printSplash;
    private Parser parser;
    
    private ClientChannel channel;
    private Invoker invoker;
    private Receiver receiver;

    

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
        this.printSplash = true;
        channel = new ClientChannel(host, port);
        
        receiver = new SeetsReceiver(channel);
        parser = new Parser(receiver);
        
        
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
                
                Command c = parser.getCommand("exit");
                if (c != null) {
                    invoker = new Invoker(c);
                    invoker.invoke();
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

            if (channel.isOpen()) {

                // If the channel is open, send Bye and close
                channel.send(new Bye());
                channel.close();
            }
        }
    }

// Main loop: print user options, read user input and process
    public void loop(BufferedReader reader) throws IOException,
            ClassNotFoundException {

       // The app is in one of two states: "Main" or "Drafting"
        String activeState = "Main";  // Initial state
        
        // Holds the current draft data when in the "Drafting" state
        String draftTopic = null;
        List<String> draftLines = new LinkedList<>();
        
        
        String userInput;
        Command c;

        while (true) {
            
            switch (activeState){
                case ("Main"):
                    System.out.print( "\n[Main] Enter command: "
                    + "fetch [mytopic], "
                    + "compose [mytopic], "
                    + "exit"
                    + "\n> ");
                    
                case ("Drafting"):
                    //System.out.print(helper.
                        //formatDraftingMenuPrompt(null, draftLines));   
            }

            // Gets the users next input line.
            userInput = reader.readLine();
            c = parser.getCommand(userInput);
            
            // Checks if the user entered a valid command and invokes it if true.
            if (parser.isCommandValid(userInput)){ // Pass in state here?
                c = parser.getCommand(userInput);
                invoker = new Invoker(c);
                invoker.invoke();
                
                //System.out.println(receiver.getUserMessage(););
            } else {
                System.out.println("Command not recognised.");
            }   
            
        }
    }

    

    
}
