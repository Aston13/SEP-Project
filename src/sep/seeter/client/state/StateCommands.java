package sep.seeter.client.state;

import java.util.HashMap;

/**
 * This class is implemented by <code>enum</code> <code>State</code> to provide
 * a state machine for commands.
 * 
 * @author Aston Turner
 */
public interface StateCommands {

    /**
     * Used to add commands to a <code>State</code>.
     * 
     * @param h contains <code>Command</code> implementing objects.
     */
    public void addCommands(HashMap h);

    /**
     * Used to retrieve commands from a <code>State</code>.
     * 
     * @return A <code>HashMap</code> containing <code>Command</code> 
     *         implementing objects.
     */
    public HashMap getCommands();
}
