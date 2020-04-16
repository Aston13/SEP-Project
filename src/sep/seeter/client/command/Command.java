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
    void execute();

    /**
     * This encapsulates functionality for undo, if supported.
     * 
     * @return True if undo functionality is supported.
     */
    boolean undo();
    
}
