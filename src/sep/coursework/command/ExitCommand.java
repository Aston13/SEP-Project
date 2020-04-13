package sep.coursework.command;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sep.coursework.Controller;
import sep.coursework.Model;
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
            System.out.println("true");
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
    public boolean undo() {
        return false;
        //System.out.println("Can't undo the previous exit command.");
    }
}
