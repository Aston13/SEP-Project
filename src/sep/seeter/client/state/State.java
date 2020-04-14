package sep.seeter.client.state;

import java.util.HashMap;

/**
 * This class provides a state machine for commands when the program is 
 * running.
 * 
 * @author Aston Turner
 */
public enum State implements StateCommands {
      
    /**
     * Contains the main state methods and data.
     */
    MAIN {
        private HashMap mainCommands;
        
        @Override
        public void addCommands(HashMap commands) {
            mainCommands = commands;
        }
        
        @Override
        public HashMap getCommands() {
            return mainCommands;
        }
    }, 
    
    /**
     * Contains the drafting state methods and data.
     */
    DRAFTING {
        private HashMap draftCommands;
        
        @Override
        public void addCommands(HashMap commands) {
            draftCommands = commands;
        }
        
        @Override
        public HashMap getCommands() {
            return draftCommands;
        }
    };
}
