package tom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * Throws a {@code tom.ChatbotException} with specific error messages for invalid commands.
     *
     * @param command The user's command input.
     * @throws ChatbotException If the command is invalid or incomplete.
     */
    private String processCommand(String command, List list) throws ChatbotException {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();

        switch (action) {
        case "todo":
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ChatbotException("JERRYYYYY!!!! The description of a todo cannot be empty. " +
                        "Please provide a task description.");
            }
            return list.add(new Todo(parts[1].trim(), false));

        case "deadline":
            if (!command.contains("/by")) {
                throw new ChatbotException("A deadline must include '/by' followed by the due date. " +
                        "Example: deadline Finish report /by tomorrow.");
            }
            String[] deadlineParts = parts[1].split("/by", 2);
            if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()
                    || deadlineParts[1].trim().isEmpty()) {
                throw new ChatbotException("The description or due date of a deadline cannot be empty.");
            }
            String inputDate = deadlineParts[1].trim();
            try {
                if (inputDate.contains(" ")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime date = LocalDateTime.parse(inputDate, formatter);
                    return list.add(new Deadline(deadlineParts[0].trim(), false, date));
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(inputDate, formatter);
                    return list.add(new Deadline(deadlineParts[0].trim(), false, date));
                }
            } catch (DateTimeParseException e) {
                throw new ChatbotException("Invalid date format: " + inputDate +
                        "\n Use yyyy-mm-dd or yyyy-mm-dd HHmm");

            }

        case "event":
            if (!command.contains("/from") || !command.contains("/to")) {
                throw new ChatbotException("An event must include '/from' and '/to' with valid time periods. " +
                        "Example: event Team meeting /from 2pm /to 4pm.");
            }
            String[] eventParts = parts[1].split("/from", 2);
            String[] timeParts = eventParts[1].split("/to", 2);
            if (eventParts.length < 2 || timeParts.length < 2 ||
                    eventParts[0].trim().isEmpty() || timeParts[0].trim().isEmpty()
                    || timeParts[1].trim().isEmpty()) {
                throw new ChatbotException("The description or time periods of an event cannot be empty.");
            }
            String from = timeParts[0].trim();
            String to = timeParts[1].trim();
            try {
                if (from.contains(" ")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime fromDate = LocalDateTime.parse(from, formatter);
                    LocalDateTime toDate = LocalDateTime.parse(to, formatter);
                    return list.add(new Meeting(eventParts[0].trim(), false, fromDate, toDate));
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fromDate = LocalDate.parse(from, formatter);
                    LocalDate toDate = LocalDate.parse(to, formatter);
                    return list.add(new Meeting(eventParts[0].trim(), false, fromDate, toDate));
                }
            } catch (DateTimeParseException e) {
                throw new ChatbotException("Invalid date format: " + from + to +
                        "\nUse yyyy-mm-dd or yyyy-mm-dd HHmm for both");
            }

        case "mark":
            try {
                return list.mark(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                throw new ChatbotException("Input a proper number");
            }

        case "unmark":
            try {
                return list.unmark(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                throw new ChatbotException("Input a proper number");
            }

        case "list":
            return list.display();

        case "delete":
            try {
                return list.delete(Integer.parseInt(parts[1]));
            } catch (Exception e) {
                throw new ChatbotException("Input a proper number");
            }

        case "find":
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ChatbotException("Please provide a keyword to search for.");
            }
            String keyword = parts[1].trim();
            return list.find(keyword);


        default:
                throw new ChatbotException("I'm sorry, but I don't know what the command '" + command.trim() + "' means. Please try again.");
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
