package sep.seeter.client.command;

/**
 * Every concrete Command must implement this interface and methods.
 *
 * @author Aston Turner
 */
public interface Command {
    
    /**
     * This is the main method for executing a <code>Command</code>
     */
    public void execute();

    /**
     * This encapsulates functionality for undo, if supported.
     * 
     * @return True if undo functionality is supported.
     */
    public boolean undo();
    
}
