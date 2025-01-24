public class Exit extends Event{
    public Event simulate() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(super.line);
        return null;
    }
}
