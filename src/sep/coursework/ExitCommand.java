package sep.coursework;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Thanks for using Seeter!");
        //theReceiver.setUserMessage("Thanks for using Seeter!");
        //serverReceiver.send(new Bye);
        System.exit(0);
    }
}
