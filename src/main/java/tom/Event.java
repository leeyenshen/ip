package tom;

/**
 * Represents an abstract event.
 * Subclasses should implement the `simulate` method to define
 * specific event behaviors.
 */
public abstract class Event{
    private List list;
    private ChatbotDataHandler chatbotDataHandler;
    /**
     * A separator line for output formatting.
     */
    public static String LINE = "========================================";

    /**
     * Simulates the event.
     *
     * @return Always returns null, indicating the end of the event sequence.
     */
    public abstract Event simulate();
}
