public class Tom {
    public static void main(String[] args) {
        String logo = "  _______   ____    __  __ \n"
                + " |__   __| /  _ \\  |  \\/  |\n"
                + "    | |    | | | | | |\\/| |\n"
                + "    | |    | |_| | | |  | |\n"
                + "    |_|    \\____/  |_|  |_|\n";
        String line = "========================================";
        //System.out.println(logo);
        System.out.println(line);
        Tom tom = new Tom();
        Chatbot chatbot = new Chatbot(tom);
        chatbot.run();
    }

    public Event getInitialEvents() {
        // Define the initial events for the chatbot
        return new Greeting();
    }
}
