package tom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and processes chatbot commands.
 */
public class Parser {
    private Event event;

    public Parser(Event event) {
        this.event = event;
    }

    public Event parse(String command, List list, ChatbotDataHandler chatbotDataHandler) {
        if (command.isEmpty() || isInteger(command)) {
            return this.event;
        }
        String bye = command.toLowerCase();
        if (bye.equals("bye")) {
            return new Exit(list, chatbotDataHandler);
        }
        Ui ui = new Ui();
        try {
            ui.formatOutput(processCommand(command, list));

        } catch (ChatbotException e) {
            ui.showCommandError(e);
        }
        return this.event;
    }


    /**
     * Processes the user's command and performs the corresponding action.
     * Throws a {@code ChatbotException} with specific error messages for invalid commands.
     *
     * @param command The user's command input.
     * @param list The task list to be modified.
     * @return A message confirming the action taken.
     * @throws ChatbotException If the command is invalid or incomplete.
     */
    public String processCommand(String command, List list) throws ChatbotException {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();

        switch (action) {
        case "todo":
            return processTodo(parts, list);
        case "deadline":
            return processDeadline(parts, command, list);
        case "event":
            return processEvent(parts, command, list);
        case "mark":
            return processMark(parts, list);
        case "unmark":
            return processUnmark(parts, list);
        case "list":
            return list.display();
        case "delete":
            return processDelete(parts, list);
        case "find":
            return processFind(parts, list);
        default:
            throw new ChatbotException("I'm sorry, but I don't know what the command '" + command.trim() + "' means. Please try again.");
        }
    }

    /**
     * Processes a todo command by adding a new todo task to the list.
     *
     * @param parts The split command containing task details.
     * @param list The task list.
     * @return The response message confirming the task addition.
     * @throws ChatbotException If the description is missing.
     */
    private String processTodo(String[] parts, List list) throws ChatbotException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ChatbotException("The description of a todo cannot be empty. Please provide a task description.");
        }
        return list.add(new Todo(parts[1].trim(), false));
    }

    /**
     * Processes a deadline command by adding a new deadline task to the list.
     *
     * @param parts The split command containing task details.
     * @param command The original user command.
     * @param list The task list.
     * @return The response message confirming the task addition.
     * @throws ChatbotException If the format is incorrect.
     */
    private String processDeadline(String[] parts, String command, List list) throws ChatbotException {
        if (!command.contains("/by")) {
            throw new ChatbotException("A deadline must include '/by' followed by the due date. Example: deadline Finish report /by tomorrow.");
        }

        String[] deadlineParts = parts[1].split("/by", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new ChatbotException("The description or due date of a deadline cannot be empty.");
        }

        return parseDeadline(deadlineParts, list);
    }

    /**
     * Parses the deadline date and creates a deadline task.
     *
     * @param deadlineParts The description and date parts.
     * @param list The task list.
     * @return The response message confirming the task addition.
     * @throws ChatbotException If the date format is invalid.
     */
    private String parseDeadline(String[] deadlineParts, List list) throws ChatbotException {
        String inputDate = deadlineParts[1].trim();
        try {
            if (inputDate.contains(" ")) {
                LocalDateTime date = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                return list.add(new Deadline(deadlineParts[0].trim(), false, date));
            } else {
                LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return list.add(new Deadline(deadlineParts[0].trim(), false, date));
            }
        } catch (DateTimeParseException e) {
            throw new ChatbotException("Invalid date format: " + inputDate + "\n Use yyyy-mm-dd or yyyy-mm-dd HHmm");
        }
    }

    /**
     * Processes an event command by adding a new event task to the list.
     *
     * @param parts The split command containing task details.
     * @param command The original user command.
     * @param list The task list.
     * @return The response message confirming the task addition.
     * @throws ChatbotException If the format is incorrect.
     */
    private String processEvent(String[] parts, String command, List list) throws ChatbotException {
        if (!command.contains("/from") || !command.contains("/to")) {
            throw new ChatbotException("An event must include '/from' and '/to' with valid time periods.");
        }

        String[] eventParts = parts[1].split("/from", 2);
        String[] timeParts = eventParts[1].split("/to", 2);
        if (eventParts.length < 2 || timeParts.length < 2 || eventParts[0].trim().isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new ChatbotException("The description or time periods of an event cannot be empty.");
        }

        return parseEvent(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim(), list);
    }

    /**
     * Parses the event date and time, then creates an event task.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     * @param list The task list.
     * @return The response message confirming the event addition.
     * @throws ChatbotException If the date format is invalid.
     */
    private String parseEvent(String description, String from, String to, List list) throws ChatbotException {
        try {
            if (from.contains(" ")) {
                LocalDateTime fromDate = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                LocalDateTime toDate = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                return list.add(new Meeting(description, false, fromDate, toDate));
            } else {
                LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return list.add(new Meeting(description, false, fromDate, toDate));
            }
        } catch (DateTimeParseException e) {
            throw new ChatbotException("Invalid date format: " + from + " to " + to + "\nUse yyyy-mm-dd or yyyy-mm-dd HHmm for both");
        }
    }

    /**
     * Processes a mark command by marking a task as completed.
     *
     * @param parts The split command containing the task index.
     * @param list The task list.
     * @return The response message confirming the task was marked as done.
     * @throws ChatbotException If the index is invalid.
     */
    private String processMark(String[] parts, List list) throws ChatbotException {
        try {
            return list.mark(Integer.parseInt(parts[1]));
        } catch (Exception e) {
            throw new ChatbotException("Input a proper number");
        }
    }

    /**
     * Processes an unmark command by marking a task as not completed.
     *
     * @param parts The split command containing the task index.
     * @param list The task list.
     * @return The response message confirming the task was unmarked.
     * @throws ChatbotException If the index is invalid.
     */
    private String processUnmark(String[] parts, List list) throws ChatbotException {
        try {
            return list.unmark(Integer.parseInt(parts[1]));
        } catch (Exception e) {
            throw new ChatbotException("Input a proper number");
        }
    }

    /**
     * Processes a delete command by removing a task from the list.
     *
     * @param parts The split command containing the task index.
     * @param list The task list.
     * @return The response message confirming the task deletion.
     * @throws ChatbotException If the index is invalid.
     */
    private String processDelete(String[] parts, List list) throws ChatbotException {
        try {
            return list.delete(Integer.parseInt(parts[1]));
        } catch (Exception e) {
            throw new ChatbotException("Input a proper number");
        }
    }

    /**
     * Processes a find command by searching for tasks containing a specified keyword.
     *
     * @param parts The split command containing the search keyword.
     * @param list The task list.
     * @return The response message containing matching tasks.
     * @throws ChatbotException If no keyword is provided.
     */
    private String processFind(String[] parts, List list) throws ChatbotException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ChatbotException("Please provide a keyword to search for.");
        }
        return list.find(parts[1].trim());
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

