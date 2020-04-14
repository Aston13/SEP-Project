package sep.coursework.command;

import sep.coursework.Controller;
import sep.coursework.Model;
import sep.seeter.net.message.Bye;

/* Concrete Exit Command.
 * This class encapsulates the Exit command's specific data and methods.
 *
 * @author Aston Turner
 */
public class ExitCommand implements Command {
    
    private final Model model;
    private final Controller control;
    
    public ExitCommand(Model newModel, Controller controller) {
        model = newModel;
        control = controller;
    }
    
    @Override
    public void execute() {
        System.out.println("Thanks for using Seeter!");
        if (model.isServerOpen()) {
            System.out.println("true");
            model.send(new Bye());
        }
            
        control.shutdown();
        //exit gracefully..
    }
    
    @Override
    public boolean undo() {
        return false;
    }
}
