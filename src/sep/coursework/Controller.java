package sep.coursework;

import sep.coursework.command.*;
import sep.coursework.state.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import static sep.coursework.Client.rb;

/* In charge of intialising commands in a List and returning Command objects */
public final class Controller {

    private final Model model;
    private final View view;
    private String [] arguments = null;
    private String argument = null;
    private String commandWord = null;
    private Stack commandHistory = new Stack <Command>();
    private HashMap commandData = new HashMap<Command, String>();
    private HashMap<String, Runnable> mainCommands = new HashMap<>();
    private HashMap<String, Runnable> draftCommands = new HashMap<>();
    private Command userC; // User's entered Command.
    
    /* Initilisation of i18n Strings as set by Locale */
    private String undo = rb.getString("command_undo");
    private String exit = rb.getString("command_exit");
    private String fetch = rb.getString("command_fetch");
    private String compose = rb.getString("command_compose");
    private String list = rb.getString("command_list");
    private String topic = rb.getString("command_topic");
    private String send = rb.getString("command_send");
    private String body = rb.getString("command_body");
    private String discard = rb.getString("command_discard");
    
    public Controller(Model newModel, View newView) {
        addCommands();
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
    
    public void addCommands() {
        mainCommands.put(exit, () -> userC = new ExitCommand (model, this));
        mainCommands.put(fetch, () -> userC = new FetchCommand(model, argument));
        mainCommands.put(compose, () -> userC = new ComposeCommand(model, argument));
        mainCommands.put(list, () -> userC = new ListCommand(model));
        
        draftCommands.put(topic, () -> userC = new TopicCommand(model, argument));
        draftCommands.put(exit, () -> userC = new ExitCommand(model, this));
        draftCommands.put(send, () -> userC = new SendCommand(model));
        draftCommands.put(body, () -> userC = new BodyCommand(model, arguments));
        draftCommands.put(discard, () -> userC = new DiscardCommand(model));
        
        State.MAIN.addCommands(mainCommands);
        State.DRAFTING.addCommands(draftCommands);
    }
    
    public void extractInput (String userInput) {
            if (userInput != null) { // Read a line of user input
                
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
    
    public boolean isExecuteCommandValid(String userInput) {
        State state = getModel().getState();
        HashMap stateCommandMap = state.getCommands();
        userC = null;
        extractInput(userInput);
        
        if ((commandWord == null) || (commandWord.trim().isEmpty())){
            return false;
        } 

        stateCommandMap.forEach((k, v) -> {
            if (k.toString().startsWith(commandWord)) {((Runnable) v).run();}});
        
        return (userC != null);
    }
        
    public boolean invokeCommand(String userInput) {
        if (isExecuteCommandValid(userInput)) {
            userC.execute();
            commandData.put(userC, commandWord);
            commandHistory.add(userC);
            return true;
            
        } else if (commandWord.equals(undo)) {
                    if(commandHistory.isEmpty()) {
                        System.out.println("Nothing to undo.");
                        return true;
                    }
                    
                    Command cmd = (Command) commandHistory.pop();
                    if (cmd.undo()) {
                        System.out.println("The previous " +  
                                commandData.get(cmd) +
                                " command was undone.");
                        return true;
                    } else {
                        System.out.println("Can't undo the previous " + 
                                commandData.get(cmd) + " command.");
                        return true;
                    }
            } 
            return false;
        }  
}
