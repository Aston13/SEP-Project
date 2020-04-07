package sep.coursework;

import sep.seeter.net.message.Bye;

public class ExitCommand implements Command {
    
    private final Model model;
    
    public ExitCommand(Model newModel) {
        model = newModel;
    }
    
    @Override
    public void execute() {
        System.out.println("Thanks for using Seeter!");
        model.send(new Bye());
        System.exit(0);
        //exit gracefully..
    }
}
