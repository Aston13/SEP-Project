package sep.coursework.command;

/* Every Command must implement this interface */ 
public interface Command {
    
    public void execute();
    public boolean undo();
    public String getCommandString();
    
}
