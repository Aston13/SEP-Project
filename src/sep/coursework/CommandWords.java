package sep.coursework;

import java.util.HashMap;

/* In charge of intialising commands in the map and getting commands */

public class CommandWords {
    private HashMap<String, Command> commands = new HashMap<>();
    
    public CommandWords() {
        
        ExitCommand exit = new ExitCommand();
        Command
        
        exit.register("exit", execute);
        
        commands.put("exit", new ExitCommand());
        commands.put("send", new SendCommand());   

//         Second argument to command
//        commands.put("fetch", new FetchCommand());
//        commands.put("compose", new ComposeCommand());
//        commands.put("body", new BodyCommand());
        
        
//         Additional Functionality
//        commands.put("discard", new DiscardCommand());
//        commands.put("list", new ListCommand());
    }
    
    
}
