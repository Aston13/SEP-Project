package sep.coursework;

/* Called by client */
public class Invoker {
    Command theCommand;
    
    public Invoker (Command newCommand) {
        theCommand = newCommand;
    }
    
    /* When invoke() is called, the execute method is called 
     * on the Command object passed by the Client.
    */
    public void invoke() {
        theCommand.execute();
    }
}
