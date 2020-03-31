package sep.coursework;

import java.util.HashMap;

public class CommandState {
    private static final HashMap<String, Command> draftCommands = new HashMap<>();
    private static final HashMap<String, Command> mainCommands = new HashMap<>();

    public CommandState () {
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
    }
    
    public enum State implements Command {
        DRAFTING_STATE {
            

            @Override
            public void execute() {
            }
        },

        MAIN_STATE {
            

            public boolean isCommandValid(String cmd) {
                if (mainCommands.containsKey(cmd)){return true;}
                return false;
            }

            @Override
            public void execute() {
            }
        };

    }
}
