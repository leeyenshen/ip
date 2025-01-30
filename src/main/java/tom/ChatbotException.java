package tom;

/**
 * Represents exceptions specific to the chatbot's operation.
 */
public class ChatbotException extends Exception {
    /**
     * Constructs a tom.ChatbotException with the specified error message.
     *
     * @param message The detailed error message.
     */
    public ChatbotException(String message) {
        super(message);
    }
}
