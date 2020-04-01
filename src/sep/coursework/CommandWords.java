package sep.coursework;

import java.util.HashMap;
import sep.seeter.net.channel.ClientChannel;

/* In charge of intialising commands in the map and getting commands */
public class CommandWords {

    private final HashMap<String, Command> commands = new HashMap<>();
    private final ClientChannel channel;
    //private final HashMap<String, Command> draftCommands = new HashMap<>();

    public CommandWords(ClientChannel channel) {
        // State is always initialised as main.
        this.channel = channel;
        
        commands.put("exit", new ExitCommand());
        
        // Initialise main state commands.
        commands.put("fetch", new FetchCommand(channel));
        
        commands.put("compose", new ComposeCommand());
        commands.put("list", new ListCommand());
        
        // Initialise drafting state commands.
        commands.put("send", new SendCommand());
        commands.put("discard", new DiscardCommand());
        commands.put("body", new BodyCommand());
        
        //         Additional Functionality
        //        commands.put("undo", new UndoCommand());
        //        commands.put("topic, new TopicCommand());
    }
    

    

    
    public Command get(String cmd, String[] arguments) {
        Command c = commands.get(cmd);
        if (c.requiresArgs()) {
            c.setArgs(arguments);
        }
        return c;
    }

}
