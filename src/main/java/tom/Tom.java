package tom;

/**
 * Represents the main entry point for the chatbot application.
 * This class initializes the chatbot with its initial events and starts it.
 */
public class Tom {
    private List list;
    private ChatbotDataHandler chatbotDataHandler;
    private Ui ui;

    public Tom(String filePath){
        this.ui = new Ui();
        ui.startUp();
        this.chatbotDataHandler = new ChatbotDataHandler(filePath);
        try {
            this.list = chatbotDataHandler.getTasks();
        } catch (Exception e){
            ui.showLoadingError();
            this.list = new List();
        }
    }

    /**
     * The main method that launches the chatbot application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Tom tom = new Tom("data/Tom.txt");
        new Chatbot(tom).run();
    }

    /**
     * Retrieves the initial event to be processed by the chatbot.
     *
     * @return The first event for the chatbot, which is a {@code Greeting} event.
     */
    public Event getInitialEvents() {
        return new Greeting(this.list, this.chatbotDataHandler);
    }

    public String getResponse(String input) throws ChatbotException {
        if (input.equalsIgnoreCase("bye")){
            this.chatbotDataHandler.saveTasks(this.list);
        }
        Parser parser = new Parser(new Listen(this.list, this.chatbotDataHandler));
        return parser.processCommand(input, this.list);
    }
}
