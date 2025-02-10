package tom;

import java.util.Scanner;

/**
 * Handles user interactions by providing input and output functionalities.
 */
public class Ui {
    private static final Scanner scanner = new Scanner(System.in);
    public static String LINE = "========================================";

    public String formatOutput(String output) {
        System.out.println(Event.LINE);
        System.out.println(output);
        System.out.println(Event.LINE);
        return output;
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String nextLine() {
        assert scanner != null : "Scanner should be initialized";
        return scanner.nextLine();
    }

    public String listEmpty() {
        System.out.println("JERRYYYYYY!!!! Your list is empty");
        System.out.println(Event.LINE);
        return "JERRYYYYYY!!!! Your list is empty";
    }

    public String listNotEmpty(List list) {
        System.out.println("JERRYYYYYY!!!! Your list is not empty");
        System.out.println(list.display());
        System.out.println(Event.LINE);
        return "JERRYYYYYY!!!! Your list is not empty";
    }

    public String showCommandError(ChatbotException e){
        System.out.println("OOPS!!! " + e.getMessage());
        System.out.println(Event.LINE);
        return "OOPS!!! " + e.getMessage();
    }

    public String showLoadingError() {
        System.out.println("Error while accessing chatbot data file");
        return "Error while accessing chatbot data file";
    }

    public void startUp() {
        String logo = "  _______   ____    __  __ \n"
                    + " |__   __| /  _ \\  |  \\/  |\n"
                    + "    | |    | | | | | |\\/| |\n"
                    + "    | |    | |_| | | |  | |\n"
                    + "    |_|    \\____/  |_|  |_|\n";
        System.out.println(logo);
        System.out.println(LINE);
    }

    public String greeting() {
        System.out.println("Hello! I'm Tom\nWhat can I do for you?");
        System.out.println(Event.LINE);
        return "Hello! I'm Tom\nWhat can I do for you?";
    }

    public String exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.print(Event.LINE);
        return "Bye. Hope to see you again soon!";
    }
}
