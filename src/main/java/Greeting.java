/**
 * Represents the greeting event that welcomes the user and starts the program.
 */
public class Greeting extends Event{
    private List list;
    private ChatbotDataHandler chatbotDataHandler;

    public Greeting(List list, ChatbotDataHandler chatbotDataHandler) {
        this.list = list;
        this.chatbotDataHandler = chatbotDataHandler;
    }

    /**
     * Simulates the greeting event by printing a welcome message and initializing the task list.
     *
     * @return A new Listen event to handle user commands.
     */
    public Event simulate() {
        new Ui().greeting();
        return new Listen(this.list ,this.chatbotDataHandler);
    }
}
