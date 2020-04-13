package sep.coursework.state;

import java.util.HashMap;

/**
 *
 * @author Aston Turner
 */
public enum State implements StateCommands {
      
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
