package sep.coursework;

import java.util.ArrayList;
import java.util.List;

/* In charge of intialising commands in a List and returning Command objects */
public class CommandWords {

    private final List<String> commands = new ArrayList<String>();

    public CommandWords() {
        
        commands.add("exit");
        commands.add("fetch");
        commands.add("compose");
        commands.add("send");
        commands.add("body");


//        commands.put("list", new ListCommand());
//        commands.put("discard", new DiscardCommand());
//        commands.put("undo", new UndoCommand());
//        commands.put("topic, new TopicCommand());
    }
        
    public Command getCommand(String cmd, String arguments, Receiver receiver) {
        Command command = null;
        
        // Does exit start with ex?
        switch (cmd) {
            case (("exit")):
                command = new ExitCommand();
                break;
            case ("fetch"):
                command = new FetchCommand(receiver, arguments);
                break;
            case ("compose"):
                command = new ComposeCommand(receiver, arguments);
                
        }
            return command;
    }
    
    public boolean isCommandValid(String cmd) {
        return (commands.contains(cmd));
    }

}
