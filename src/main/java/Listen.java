import java.util.LinkedList;
import java.util.Scanner;
public class Listen extends Event{
    private static final Scanner scanner = new Scanner(System.in);
    private List list;

    public Listen(List list) {
        this.list = list;
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
            System.out.println(Event.line);
            return new Exit();
        } else if (temp.equals("list")) {
            this.list.display();
            return new Listen(this.list);
        } else if (temp.startsWith("mark")) {
            String[] parts = temp.split("\\s+");
            if (parts.length == 2 && isInteger(parts[1])) {
               this.list.mark(Integer.parseInt(parts[1]));
            }
            return new Listen(this.list);
        } else if (temp.startsWith("unmark")) {
            String[] parts = temp.split("\\s+");
            if (parts.length == 2 && isInteger(parts[1])) {
                this.list.unmark(Integer.parseInt(parts[1]));
            }
            return new Listen(this.list);
        } else {
            System.out.println(Event.line);
            this.list.add(new Pair(command, false));
            System.out.println(Event.line);
            return new Listen(this.list);
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
