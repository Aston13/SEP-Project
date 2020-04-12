package sep.coursework;

/* Every Command must implement this interface */ 
public interface Command {
    
    public void execute();
    public boolean undo();
    public String getCommandString();
    
}
