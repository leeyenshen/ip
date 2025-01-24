import java.util.Scanner;
public class Listen extends Event{
    private static final Scanner scanner = new Scanner(System.in);

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input); // Attempt to parse the string as an integer
            return true;             // Parsing succeeded, it's an integer
        } catch (NumberFormatException e) {
            return false;            // Parsing failed, not an integer
        }
    }

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
            System.out.println(super.line);
            return new Exit();
        } else {
            System.out.println(super.line);
            System.out.println(command);
            System.out.println(super.line);
            return new Listen();
        }
    }
}
