package sep.coursework.command;

/* Every concrete Command must implement this interface and methods.
 *
 * @author Aston Turner
 */
public interface Command {
    
    public void execute();
    public boolean undo();
    
}
