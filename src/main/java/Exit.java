/**
 * Represents the exit event that terminates the program.
 */
public class Exit extends Event{
    private List list;

    public Exit(List list) {
        this.list = list;
    }
    /**
     * Simulates the exit event by printing a farewell message.
     *
     * @return Always returns null to indicate the end of events.
     */
    public Event simulate() {
        ChatbotDataHandler.saveTasks(this.list);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.print(Event.LINE);
        return null;
    }
}
