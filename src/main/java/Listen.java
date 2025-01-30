import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Listens for user commands and processes them to perform actions on the task list.
 * Commands include adding, deleting, marking tasks, and exiting the application.
 */
public class Listen extends Event{
    private static final Scanner scanner = new Scanner(System.in);
    private List list;

    /**
     * Constructs a Listen instance with the specified task list.
     *
     * @param list The task list to manage and update.
     */
    public Listen(List list) {
        this.list = list;
    }


    /**
     * Simulates the event by listening for user input and processing commands.
     *
     * @return A new Event based on the user command, or the current Listen instance if no valid command is entered.
     */
    @Override
    public Event simulate() {
        if (!scanner.hasNextLine()) {
            return this;
        }
        String command = scanner.nextLine();
        if (command.isEmpty() || isInteger(command)) {
            return this;
        }
        String bye = command.toLowerCase();
        if (bye.equals("bye")) {
            return new Exit(this.list);
        }
        try {
            System.out.println(Event.LINE);
            processCommand(command);
            System.out.println(Event.LINE);
        } catch (ChatbotException e) {
            System.out.println("OOPS!!! " + e.getMessage());
            System.out.println(Event.LINE);
        }
        return this;
    }

    /**
     * Processes the user's command and performs the corresponding action.
     * Throws a {@code ChatbotException} with specific error messages for invalid commands.
     *
     * @param command The user's command input.
     * @throws ChatbotException If the command is invalid or incomplete.
     */
    private void processCommand(String command) throws ChatbotException {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();

        switch (action) {
        case "todo":
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ChatbotException("JERRYYYYY!!!! The description of a todo cannot be empty. Please provide a task description.");
            }
            this.list.add(new Todo(parts[1].trim(), false));
            break;

        case "deadline":
            if (!command.contains("/by")) {
                throw new ChatbotException("A deadline must include '/by' followed by the due date. Example: deadline Finish report /by tomorrow.");
            }
            String[] deadlineParts = parts[1].split("/by", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
                throw new ChatbotException("The description or due date of a deadline cannot be empty.");
            }
            String inputDate = deadlineParts[1].trim();
            try {
                if (inputDate.contains(" ")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime date = LocalDateTime.parse(inputDate, formatter);
                    this.list.add(new Deadline(deadlineParts[0].trim(), false, date));
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(inputDate, formatter);
                    this.list.add(new Deadline(deadlineParts[0].trim(), false, date));
                }
            }
            catch (DateTimeParseException e) {
                System.err.println("Invalid date format: " + inputDate);
                System.out.println("Use yyyy-mm-dd or yyyy-mm-dd HHmm");
            }
            break;

        case "event":
            if (!command.contains("/from") || !command.contains("/to")) {
                throw new ChatbotException("An event must include '/from' and '/to' with valid time periods. Example: event Team meeting /from 2pm /to 4pm.");
            }
            String[] eventParts = parts[1].split("/from", 2);
            String[] timeParts = eventParts[1].split("/to", 2);
            if (eventParts.length < 2 || timeParts.length < 2 ||
                    eventParts[0].trim().isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                throw new ChatbotException("The description or time periods of an event cannot be empty.");
            }
            String from = timeParts[0].trim();
            String to = timeParts[1].trim();
            try {
                if (from.contains(" ")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime fromDate = LocalDateTime.parse(from, formatter);
                    LocalDateTime toDate = LocalDateTime.parse(to, formatter);
                    this.list.add(new Meeting(eventParts[0].trim(), false, fromDate, toDate));
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fromDate = LocalDate.parse(from, formatter);
                    LocalDate toDate = LocalDate.parse(to, formatter);
                    this.list.add(new Meeting(eventParts[0].trim(), false, fromDate, toDate));
                }
            }
            catch (DateTimeParseException e) {
                System.err.println("Invalid date format: " + from + to);
                System.out.println("Use yyyy-mm-dd or yyyy-mm-dd HHmm for both");
            }
            break;

        case "mark":
            try {
                this.list.mark(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                System.out.println("Input a proper number");
            }
            break;

        case "unmark":
            try {
                this.list.unmark(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                System.out.println("Input a proper number");
            }
            break;

        case "list":
            this.list.display();
            break;

        case "delete":
            try {
                this.list.delete(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                System.out.println("Input a proper number");
            }
            break;

        default:
            throw new ChatbotException("I'm sorry, but I don't know what the command '" + command + "' means. Please try again.");
        }
    }

    /**
     * Validates the index for the "mark" and "unmark" commands.
     * Throws a {@code ChatbotException} if the index is invalid.
     *
     * @param parts  The split command parts containing the action and index.
     * @param action The action being validated (e.g., "mark" or "unmark").
     * @throws ChatbotException If the index is missing or not a valid number.
     */
    private void validateIndex(String[] parts, String action) throws ChatbotException {
        if (parts.length < 2 || !isInteger(parts[1])) {
            throw new ChatbotException("The " + action + " command requires a valid task number. Example: " + action + " 2.");
        }
    }

    /**
     * Checks if the given input is an integer.
     *
     * @param input The input string to check.
     * @return True if the input is an integer, false otherwise.
     */
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
