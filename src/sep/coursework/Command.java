package sep.coursework;

public interface Command {
    public void execute(); // Could be boolean.
    //public void saveCommandHistory();
    public boolean requiresArgs();
    public void setArgs(String [] args);
}
