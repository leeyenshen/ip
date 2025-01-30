package tom;

/**
 * Listens for user commands and processes them to perform actions on the task list.
 * Commands include adding, deleting, marking tasks, and exiting the application.
 */
public class Listen extends Event{
    private List list;
    private ChatbotDataHandler chatbotDataHandler;
    /**
     * Constructs a tom.Listen instance with the specified task list.
     *
     * @param list The task list to manage and update.
     */
    public Listen(List list, ChatbotDataHandler chatbotDataHandler) {
        this.list = list;
        this.chatbotDataHandler = chatbotDataHandler;
    }

    /**
     * Simulates the event by listening for user input and processing commands.
     *
     * @return A new tom.Event based on the user command, or the current tom.Listen instance if no valid command is entered.
     */
    @Override
    public Event simulate() {
        Ui ui = new Ui();
        if (!ui.hasNextLine()) {
            return this;
        }
        String command = ui.nextLine();
        Parser parser = new Parser(this);
        return parser.parse(command, this.list, this.chatbotDataHandler);
    }
}
