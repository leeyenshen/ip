public class Greeting extends Event{
    public Event simulate() {
        System.out.println("Hello! I'm Tom \nWhat can I do for you?");
        System.out.println(super.line);
        return new Listen();
    }
}
