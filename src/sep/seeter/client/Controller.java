package sep.seeter.client;

import sep.seeter.client.command.ComposeCommand;
import sep.seeter.client.command.FetchCommand;
import sep.seeter.client.command.ListCommand;
import sep.seeter.client.command.DiscardCommand;
import sep.seeter.client.command.TopicCommand;
import sep.seeter.client.command.ExitCommand;
import sep.seeter.client.command.Command;
import sep.seeter.client.command.BodyCommand;
import sep.seeter.client.command.SendCommand;
import sep.seeter.client.state.State;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import static sep.seeter.client.Client.rb;

/**
 * In charge of initialising commands in a Map and returning Command objects. 
 * This class represents the Controller in the MVC architecture.
 *
 * @author Aston Turner
 */
public final class Controller {

    private final Model model;
    private final View view;
    private String [] arguments = null;
    private String argument = null;
    private String commandWord = null;
    private final Stack commandHistory = new Stack <>();
    private final HashMap commandData = new HashMap<>();
    private final HashMap<String, Runnable> mainCommands = new HashMap<>();
    private final HashMap<String, Runnable> draftCommands = new HashMap<>();
    private Command userC; // User's entered Command.
    
    /* Initialisation of i18n Strings as set by Locale */
    private final String undo = rb.getString("command_undo");
    private final String exit = rb.getString("command_exit");
    private final String fetch = rb.getString("command_fetch");
    private final String compose = rb.getString("command_compose");
    private final String list = rb.getString("command_list");
    private final String topic = rb.getString("command_topic");
    private final String send = rb.getString("command_send");
    private final String body = rb.getString("command_body");
    private final String discard = rb.getString("command_discard");
    
    /**
     * Class constructor.
     * 
     * @param newModel the bound <code>Model</code>.
     * @param newView the bound <code>View</code>.
     */
    public Controller(Model newModel, View newView) {
        addCommands();
        model = newModel;
        view = newView;
        view.setController(this);
    }
    
    public Model getModel() {
        return model;
    }
    
    /**
     * Executes the close method in <code>View</code> to shutdown the Client.
     */
    public void shutdown() {
        view.close();
    }
    
    /**
     * Initialisation of <code>Command</code> objects within a lambda 
     * expression and stored within a <code>HashMap</code>. These 
     * <code>Runnable Expressions</code> will be called by the 
     * <code>invokeCommand</code> when required.
     * 
     * Additional <code>Command</code>'s can be added here and <code>put</code> 
     * within the relevant state <code>Map</code>.
     */
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
    
    /**
     * Processes the user's input, and extracts the first word as a command 
     * and additional words as possible arguments for further use.
     * 
     * @param userInput the user's entered input.
     */
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
                argument = "";
            } else { 
                argument = arguments[0];
            }
    }
    
    /**
     * Checks if the user's input contains a valid command to execute.
     * 
     * @param userInput the user's entered input.
     * @return True if the input contained a valid command word.
     */
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
        
    /**
     * Invokes a command, if a command is valid and existing.
     * 
     * @param userInput the user's entered input.
     * @return True if a command was invoked.
     */
    public boolean invokeCommand(String userInput) {
        if (isExecuteCommandValid(userInput)) {
            userC.execute();
            commandData.put(userC, commandWord);
            commandHistory.add(userC);
            return true;
            
        } else if (commandWord.equals(undo)) {
                    if(commandHistory.isEmpty()) {
                        System.out.println(rb.getString("undo_empty"));
                        return true;
                    }
                    
                    Command cmd = (Command) commandHistory.pop();
                    if (cmd.undo()) {
                        System.out.println(MessageFormat.format(rb.getString
                        ("undo_successful"), commandData.get(cmd)));
                        return true;
                    } else {
                        System.out.println(MessageFormat.format(rb.getString
                        ("undo_unsuccessful"), commandData.get(cmd)));
                        return true;
                    }
        } 
        return false;
    }  
}