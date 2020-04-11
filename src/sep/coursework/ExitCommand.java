package sep.coursework;

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
        //model.send(new Bye());
        control.shutdown();
        //exit gracefully..
    }
}
