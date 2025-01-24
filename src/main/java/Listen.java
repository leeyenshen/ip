import java.util.Scanner;
/**
 * Listens for user commands and processes them to perform actions on the task list.
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
    public Event simulate() {
        if (!scanner.hasNextLine()) {
            return this;
        }
        String command = scanner.nextLine();
        if (command.isEmpty() || isInteger(command)) {
            return this;
        }
        String temp = command.toLowerCase();
        if (temp.equals("bye")) {
            System.out.println(Event.line);
            return new Exit();
        } else if (temp.equals("list")) {
            this.list.display();
            return new Listen(this.list);
        } else {
            String[] parts = temp.split(" ");
            try {
                switch (parts[0].toLowerCase()) {
                    case "mark":
                        if (parts.length == 2 && isInteger(parts[1])) {
                            this.list.mark(Integer.parseInt(parts[1]));
                        }
                        return new Listen(this.list);
                    case "unmark":
                        if (parts.length == 2 && isInteger(parts[1])) {
                            this.list.unmark(Integer.parseInt(parts[1]));
                        }
                        return new Listen(this.list);
                    case "todo":
                        System.out.println(Event.line);
                        this.list.add(new Todo(command.substring(5), false));
                        System.out.println(Event.line);
                        return new Listen(this.list);
                    case "deadline":
                        String[] temp2 = command.split("/by");
                        System.out.println(Event.line);
                        this.list.add(new Deadline(temp2[0].substring(9).trim(), false, temp2[1].trim()));
                        System.out.println(Event.line);
                        return new Listen(this.list);
                    case "event":
                        String[] temp3 = command.split("/from");
                        String[] temp4 = temp3[1].split("/to");
                        System.out.println(Event.line);
                        this.list.add(new Meeting(temp3[0].substring(6).trim(),
                                false, temp4[0].trim(), temp4[1].trim()));
                        System.out.println(Event.line);
                        return new Listen(this.list);
                    default:
                        return new Listen(this.list);
                }
            } catch (Exception e){
                System.out.println("Ensure proper formatting");
                System.out.println(Event.line);
                return new Listen(this.list);
            }
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
