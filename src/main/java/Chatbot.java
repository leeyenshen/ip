import java.util.Queue;
import java.util.LinkedList;

public class Chatbot {
    private final Queue<Event> events;

    public Chatbot(Tom tom) {
        this.events = new LinkedList<>();
        this.events.add(tom.getInitialEvents());
    }

    public void run() {
        Event event = this.events.poll();
        while (event != null) {
            Event newEvents = event.simulate();
            this.events.add(newEvents);
            event = this.events.poll();
        }
    }
}
