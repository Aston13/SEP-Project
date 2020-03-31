package sep.coursework;

import java.util.HashMap;

/* In charge of intialising commands in the map and getting commands */
public class CommandWords {

    private final HashMap<String, Command> mainCommands = new HashMap<>();
    private final HashMap<String, Command> draftCommands = new HashMap<>();

    public CommandWords() {

        state = "main"; // State is always initialised as main.
        
        // Initialise main state commands.
        mainCommands.put("exit", new ExitCommand());
        mainCommands.put("fetch", new FetchCommand());
        mainCommands.put("list", new ListCommand());
        mainCommands.put("compose", new ComposeCommand());
        
        // Initialise drafting state commands.
        draftCommands.put("exit", new ExitCommand());
        draftCommands.put("send", new SendCommand());
        draftCommands.put("discard", new DiscardCommand());
        draftCommands.put("body", new BodyCommand());
        
        //         Additional Functionality
        //        commands.put("undo", new UndoCommand());
    }
    
    public boolean getState() {
        
    }
    

    
    public Command get(String cmd) {
        return commands.get(cmd);
    }

}
