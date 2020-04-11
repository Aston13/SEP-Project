package sep.coursework;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.seeter.net.message.Bye;

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
            try {
                model.send(new Bye());
            } catch (IOException ex) {
                Logger.getLogger(ExitCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            

        control.shutdown();
        //exit gracefully..
    }
    
    @Override
    public void undo() {
        System.out.println("Can't undo the previous exit command.");
    }
}
