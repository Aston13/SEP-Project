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
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

// Must remain the main class for running the client (via main()).
public class Client {

    private final String user;
    private final String host;
    private final int port;
    private final boolean printSplash;
    private CommandWords command;
    private ClientChannel channel;

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
        this.channel = new ClientChannel(host, port);
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
                command.get("exit");
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

//        // The app is in one of two states: "Main" or "Drafting"
//        String state = "Main";  // Initial state
        // Holds the current draft data when in the "Drafting" state
        String draftTopic = null;
        List<String> draftLines = new LinkedList<>();

        // The loop
        while (true) {

            // Print user options
            if (state.equals("Main")) {
                System.out.print(helper.formatMainMenuPrompt());
            } else {  // state = "Drafting"
                System.out.print(helper.
                        formatDraftingMenuPrompt(draftTopic, draftLines));
            }

            // Read a line of user input
            String raw = reader.readLine();
            if (raw == null) {
                throw new IOException("Input stream closed while reading.");
            }
            // Trim leading/trailing white space, and split words according to spaces
            List<String> split = Arrays.stream(raw.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
            String cmd = split.remove(0);  // First word is the command keyword
            String[] rawArgs = split.toArray(new String[split.size()]);
            // Remainder, if any, are arguments

            // Process user input
            if (cmd.startsWith("exit")) {
                // exit command applies in either state
                command.get("exit");
            } 




// "Main" state commands
            else if (state.equals("Main")) {
                if ("compose".startsWith(cmd)) {
                    // Switch to "Drafting" state and start a new "draft"
                    state = "Drafting";
                    draftTopic = rawArgs[0];
                } else if ("fetch".startsWith(cmd)) {
                    // Fetch seets from server
                    channel.send(new SeetsReq(rawArgs[0]));
                    SeetsReply rep = (SeetsReply) channel.receive();
                    System.out.print(
                            helper.formatFetched(rawArgs[0], rep.users, rep.lines));
                } else {
                    System.out.println("Could not parse command/args.");
                }
            } 





// "Drafting" state commands
            else if (state.equals("Drafting")) {
                if ("body".startsWith(cmd)) {
                    // Add a seet body line
                    String line = Arrays.stream(rawArgs).
                            collect(Collectors.joining());
                    draftLines.add(line);
                } else if ("send".startsWith(cmd)) {
                    // Send drafted seets to the server, and go back to "Main" state
                    channel.send(new Publish(user, draftTopic, draftLines));
                    state = "Main";
                    draftTopic = null;
                } else {
                    System.out.println("Could not parse command/args.");
                }
            } else {
                System.out.println("Could not parse command/args.");
            }
            
            
            
        }
    }
}
