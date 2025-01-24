import java.util.LinkedList;

/**
 * Represents the greeting event that welcomes the user and starts the program.
 */
public class Greeting extends Event{
    /**
     * Simulates the greeting event by printing a welcome message and initializing the task list.
     *
     * @return A new Listen event to handle user commands.
     */
    public Event simulate() {
        System.out.println("Hello! I'm Tom\nWhat can I do for you?");
        System.out.println(Event.line);
        return new Listen(new List());
    }
}
