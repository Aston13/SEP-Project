package sep.coursework;

import java.util.HashMap;

/* In charge of intialising commands in the map and getting commands */
public class ValidCommands {

    //private final HashMap<String, Command> commands = new HashMap<>();

    public ValidCommands() {
        // State is always initialised as main.
        

        
//        commands.put("exit", new ExitCommand());
//        
//        // Initialise main state commands.
//        commands.put("fetch", new FetchCommand());
        
//        commands.put("compose", new ComposeCommand());
//        commands.put("list", new ListCommand());
        
        // Initialise drafting state commands.
//        commands.put("send", new SendCommand());
//        commands.put("discard", new DiscardCommand());
//        commands.put("body", new BodyCommand());
        
        //         Additional Functionality
        //        commands.put("undo", new UndoCommand());
        //        commands.put("topic, new TopicCommand());
    }
    
    public Command getCommand(String cmd, String arguments, Receiver receiver) {
        Command command = null;
        
        switch (cmd){
            case ("exit"):
                command = new ExitCommand();
                break;
            case ("fetch"):
                command = new FetchCommand(receiver, arguments);
                break;
                
        }
            return command;
    }

}
