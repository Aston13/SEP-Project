package sep.coursework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* In charge of intialising commands in a List and returning Command objects */
public class CommandWords {

    private final List<String> commands = new ArrayList<String>();
    private ServerReceiver serverReceiver;
    private DraftReceiver draftReceiver;
    private String [] arguments = null;
    private String argument = null;
    private String commandWord = null;
    
    public CommandWords(ServerReceiver serverReceiver, DraftReceiver draftReceiver) {
        
        this.serverReceiver = serverReceiver;
        this.draftReceiver = draftReceiver;
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
        
    public void invokeCommand() {
        Command command = null;
        
        switch (commandWord) {
            case ("exit"):
                command = new ExitCommand();
                break;
            case ("fetch"):
                command = new FetchCommand(serverReceiver, argument);
                break;
            case ("compose"):
                command = new ComposeCommand(draftReceiver, argument);
                break;
            case ("body"):
                command = new BodyCommand(draftReceiver, arguments);
                break;
            case ("send"):
                command = new SendCommand(serverReceiver, draftReceiver);
                break;
                
        }
            command.execute();
    }
    
    public boolean isCommandValid(String userInput) {
        extractInput(userInput);
        return (commands.contains(commandWord));
    }
    
    public void extractInput (String userInput) {
            // Read a line of user input
            if (userInput != null) {
                
                // Trim leading/trailing white space, and split words according to spaces
                List<String> split = Arrays.stream(userInput.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
                commandWord = split.remove(0);
                arguments = split.toArray(new String[split.size()]);
            }
            
            if (arguments.length < 1) {
                argument = null;
            } else { 
                argument = arguments[0];
            }
    }

}
