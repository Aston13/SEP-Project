package sep.seeter.client.command;

import sep.seeter.client.Controller;
import sep.seeter.client.Model;
import sep.seeter.net.message.Bye;

/**
 * Concrete Exit Command.
 * This class encapsulates the Exit command's specific data and methods.
 *
 * @author Aston Turner
 */
public class ExitCommand implements Command {
    
    private final Model model;
    private final Controller control;
    
    /**
     * Class constructor.
     * 
     * @param newModel the <code>Model</code> of the MVC.
     * @param controller the <code>Controller</code> of the MVC.
     */
    public ExitCommand(Model newModel, Controller controller) {
        model = newModel;
        control = controller;
    }
    
    /**
     * Tells the <code>Controller</code> to gracefully shutdown the program.
     */
    @Override
    public void execute() {
        System.out.println("Thanks for using Seeter!");
        if (model.isServerOpen()) {
            System.out.println("true");
            model.send(new Bye());
        }
            
        control.shutdown();
    }
    
    /**
     * @return False as this command cannot be undone.
     */
    @Override
    public boolean undo() {
        return false;
    }
}
