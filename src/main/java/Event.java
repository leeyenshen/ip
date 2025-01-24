/**
 * Represents an abstract event.
 * Events are meant to be extended to provide specific implementations of simulate behavior.
 */
public abstract class Event{
    /**
     * A separator line for output formatting.
     */
    public static String line = "========================================";

    /**
     * Simulates the behavior of the event and returns the next event.
     *
     * @return The next Event to be processed.
     */
    public abstract Event simulate();
}
