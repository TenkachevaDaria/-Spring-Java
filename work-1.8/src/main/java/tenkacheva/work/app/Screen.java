package tenkacheva.work.app;

public interface Screen {
    /**
     * Processes a command and returns output based on input
     * @param command   a command to be processed
     * @return          output based on input
     */
    String input(String command);

    /**
     * @return Returns based view of a screen
     */
    String toString();
}
