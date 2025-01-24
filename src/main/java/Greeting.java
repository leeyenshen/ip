import java.util.LinkedList;

public class Greeting extends Event{
    public Event simulate() {
        System.out.println("Hello! I'm Tom \nWhat can I do for you?");
        System.out.println(Event.line);
        return new Listen(new List());
    }
}
