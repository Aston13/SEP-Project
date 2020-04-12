package sep.coursework;

import sep.coursework.command.*;
import sep.coursework.state.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/* In charge of intialising commands in a List and returning Command objects */
public final class Controller {

    private final Model model;
    private final View view;
    private String [] arguments = null;
    private String argument = null;
    private String commandWord = null;
    private Stack commandHistory = new Stack <>();
    
    public Controller(Model newModel, View newView) {
        addCommandWords();
        
        model = newModel;
        view = newView;
        view.setController(this);
    }
    
    public View getView() {
        return view;
    }
    
    public Model getModel() {
        return model;
    }
    
    public void shutdown() {
        view.close();
    }
    
    public void addCommandWords() {
        List<String> mainCommands = new ArrayList<>();
        List<String> draftCommands = new ArrayList<>();
        
        mainCommands.add("undo");
        mainCommands.add("exit");
        mainCommands.add("fetch");
        mainCommands.add("compose");
        mainCommands.add("list");
        
        draftCommands.add("topic");
        draftCommands.add("undo");
        draftCommands.add("exit");
        draftCommands.add("send");
        draftCommands.add("body");
        draftCommands.add("discard");
        
        State.MAIN.addCommands(mainCommands);
        State.DRAFTING.addCommands(draftCommands);
    }
    
    public void extractInput (String userInput) {
        
            // Read a line of user input
            if (userInput != null) {
                
                /* Trim leading/trailing white space, and split words according
                 * to spaces
                 */
                List<String> split = Arrays.stream(userInput.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
                commandWord = split.remove(0);
                arguments = split.toArray(new String[split.size()]);
            }
            
            if (arguments == null || arguments.length < 1) {
                argument = null;
            } else { 
                argument = arguments[0];
            }
    }
    
    public boolean isCommandValid(String userInput) {
        State state = getModel().getState();
        extractInput(userInput);
        if ((commandWord == null) || (commandWord.trim().isEmpty())){
            return false;
        }
        List stateCommandList = state.getCommands();
        
        for (Object cmd : stateCommandList) {
            if (cmd.toString().startsWith(commandWord)) {
                commandWord = cmd.toString();
                return true;
            } 
        }
        
        return false;
    }
        
    public boolean invokeCommand(String userInput) {
        Command command = null;
        
        if(isCommandValid(userInput)) {
            switch (commandWord) {
                case ("exit"):
                    command = new ExitCommand(model, this);
                    break;
                case ("fetch"):
                    command = new FetchCommand(model, argument);
                    break;
                case ("compose"):
                    command = new ComposeCommand(model, argument);
                    break;
                case ("body"):
                    command = new BodyCommand(model, arguments);
                    break;
                case ("send"):
                    command = new SendCommand(model);
                    break;
                case ("discard"):
                    command = new DiscardCommand(model);
                    break;
                case ("list"):
                    command = new ListCommand(model);
                    break;
                case ("topic"):
                    command = new TopicCommand(model, argument);
                    break;
                case("undo"):
                    if(commandHistory.isEmpty()) {
                        System.out.println("Nothing to undo.");
                        return true;
                    }
                    
                    command = (Command) commandHistory.peek();
                    if (command.undo()) {
                        return true;
                    } else {
                        System.out.println("Can't undo the previous " + 
                                command.getCommandString() + " command.");
                    }
                    
                    return true;
            }
            
            if (command != null) {
                command.execute();
                commandHistory.add(command);
                return true;
            }  
        } 
        return false;
    }
    
}
