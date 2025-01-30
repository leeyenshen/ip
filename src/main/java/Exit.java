/**
 * Represents the exit event that terminates the program.
 */
public class Exit extends Event{
    private List list;
    private ChatbotDataHandler chatbotDataHandler;
    public Exit(List list, ChatbotDataHandler chatbotDataHandler) {
        this.list = list;
        this.chatbotDataHandler = chatbotDataHandler;
    }
    /**
     * Simulates the exit event by printing a farewell message.
     *
     * @return Always returns null to indicate the end of events.
     */
    public Event simulate() {
        this.chatbotDataHandler.saveTasks(this.list);
        new Ui().exit();
        return null;
    }
}
