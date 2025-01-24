/**
 * Represents the main entry point for the chatbot application.
 * This class initializes the chatbot with its initial events and starts it.
 */
public class Tom {
    /**
     * The main method that launches the chatbot application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        String logo = "  _______   ____    __  __ \n"
                + " |__   __| /  _ \\  |  \\/  |\n"
                + "    | |    | | | | | |\\/| |\n"
                + "    | |    | |_| | | |  | |\n"
                + "    |_|    \\____/  |_|  |_|\n";
        String line = "========================================";
        //System.out.println(logo);
        System.out.println(line);
        Tom tom = new Tom();
        Chatbot chatbot = new Chatbot(tom);
        chatbot.run();
    }

    /**
     * Retrieves the initial events to be processed by the chatbot.
     *
     * @return The first event for the chatbot, which is a Greeting event.
     */

    public Event getInitialEvents() {
        // Define the initial events for the chatbot
        return new Greeting();
    }
}
