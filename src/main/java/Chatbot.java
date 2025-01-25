import java.util.Queue;
import java.util.LinkedList;

/**
 * Represents a chatbot that processes and simulates events in sequence.
 */
public class Chatbot {
    private final Queue<Event> events;

    /**
     * Constructs a Chatbot instance with an initial set of events from the given entity.
     *
     * @param tom The source of initial events for the chatbot.
     */
    public Chatbot(Tom tom) {
        this.events = new LinkedList<>();
        this.events.add(tom.getInitialEvents());
    }

    /**
     * Starts the chatbot, processing and simulating events in the queue
     * until there are no more events to process. Each event may add
     * subsequent events to the queue.
     */
    public void run() {
        Event event = this.events.poll();
        while (event != null) {
            Event newEvents = event.simulate();
            this.events.add(newEvents);
            event = this.events.poll();
        }
    }
}
