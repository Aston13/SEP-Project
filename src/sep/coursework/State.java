package sep.coursework;

import java.util.List;

/**
 *
 * @author Aston Turner
 */
public enum State implements StateCommands {
      
    MAIN {
        private List mainCommands;
        
        @Override
        public void addCommands(List commands) {
            mainCommands = commands;
        }
        
        @Override
        public List getCommands() {
            return mainCommands;
        }
    }, 
    
    DRAFTING {
        private List draftCommands;
        
        @Override
        public void addCommands(List commands) {
            draftCommands = commands;
        }
        
        @Override
        public List getCommands() {
            return draftCommands;
        }
    };
}
