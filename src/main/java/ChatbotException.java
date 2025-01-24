/**
 * Represents exceptions specific to the chatbot's operation.
 */
public class ChatbotException extends Exception {
    /**
     * Constructs a ChatbotException with the specified error message.
     *
     * @param message The detailed error message.
     */
    public ChatbotException(String message) {
        super(message);
    }
}
