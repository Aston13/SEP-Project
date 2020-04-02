package sep.coursework;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    
    private CommandWords commandWords;
    private Receiver receiver;

    
    /* Responsible for taking user input from the Client and
     * formatting it into a a command and argument (if any).
     *
     * If the extracted user entered command is valid, then a Command 
     * object is returned to the Client.
     */
    public Parser(Receiver receiver) {
        commandWords = new CommandWords();
        this.receiver = receiver;
    }
    
    public boolean isCommandValid(String userInput) {
            String command = null;
            
            // Read a line of user input
            if (userInput != null) {
                
                // Trim leading/trailing white space, and split words according to spaces
                List<String> split = Arrays.stream(userInput.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
                
                command = split.remove(0);
            }
            
        return commandWords.isCommandValid(command); // mainCommandWords/draftCommandWords?
    }
    
    public Command getCommand (String userInput) {
            String [] arguments = null;
            String command = null;
            String argument;
            
            // Read a line of user input
            if (userInput != null) {
                
                // Trim leading/trailing white space, and split words according to spaces
                List<String> split = Arrays.stream(userInput.trim().split("\\ "))
                    .map(x -> x.trim()).collect(Collectors.toList());
                
                command = split.remove(0);
                arguments = split.toArray(new String[split.size()]);
            }
            
            if (arguments.length < 1) {
                argument = null;
            } else { 
                argument = arguments[0];
            }

            Command cmd = commandWords.getCommand(command, argument, receiver);
            
            // Returns null if no valid command found.
            return cmd;
    }
   
}
