package tom;

import java.util.Scanner;

/**
 * Handles user interactions by providing input and output functionalities.
 */
public class Ui {
    private static final Scanner scanner = new Scanner(System.in);
    public static String LINE = "========================================";

    public void formatOutput(String output) {
        System.out.println(Event.LINE);
        System.out.println(output);
        System.out.println(Event.LINE);
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public void listEmpty() {
        System.out.println("JERRYYYYYY!!!! Your list is empty");
        System.out.println(Event.LINE);
    }

    public void listNotEmpty(List list) {
        System.out.println("JERRYYYYYY!!!! Your list is not empty");
        System.out.println(list.display());
        System.out.println(Event.LINE);
    }

    public void showCommandError(ChatbotException e){
        System.out.println("OOPS!!! " + e.getMessage());
        System.out.println(Event.LINE);
    }

    public void showLoadingError() {
        System.out.println("Error while accessing chatbot data file");
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

    public void greeting() {
        System.out.println("Hello! I'm Tom\nWhat can I do for you?");
        System.out.println(Event.LINE);
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.print(Event.LINE);
    }
}
