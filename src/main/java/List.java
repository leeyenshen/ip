import java.util.LinkedList;

/**
 * Manages a list of tasks, allowing for task addition, marking as done/undone, and displaying tasks.
 */
public class List{
    private LinkedList<Pair> list;

    /**
     * Constructs a List instance with a specified LinkedList of tasks.
     *
     * @param list A LinkedList containing tasks to initialize the list.
     */
    public List(LinkedList<Pair> list) {
        this.list = list;
    }

    /**
     * Constructs an empty List instance.
     */
    public List() {
        this.list = new LinkedList<>();
    }

    /**
     * Marks a task at the specified index as done.
     *
     * @param x The index of the task to mark as done (1-based index).
     */
    public void mark(int x) {
        if (x > this.list.size() || x <= 0) {
            System.out.println(Event.line);
            System.out.println("That item does not exist");
            System.out.println(Event.line);
            return;
        }
        Pair item = this.list.get(x - 1);
        if (item.isDone()) {
            System.out.println(Event.line);
            System.out.println("You have done it JERRYYYYYY!!!!");
            System.out.println(Event.line);
            return;
        }
        item.mark(true);
        System.out.println(Event.line);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + item);
        System.out.println(Event.line);
    }

    /**
     * Marks a task at the specified index as not done.
     *
     * @param x The index of the task to mark as not done (1-based index).
     */
    public void unmark(int x) {
        if (x >= this.list.size() || x < 0) {
            System.out.println("That item does not exist");
            return;
        }
        Pair item = this.list.get(x - 1);
        if (!item.isDone()) {
            System.out.println(Event.line);
            System.out.println("You have NOT done it JERRYYYYYY!!!!");
            System.out.println(Event.line);
            return;
        }
        item.mark(false);
        System.out.println(Event.line);
        System.out.println("OK, I've marked this task as not done:");
        System.out.println(" " + item);
        System.out.println(Event.line);
    }

    /**
     * Adds a new task to the list.
     *
     * @param item The task to be added.
     */
    public void add(Pair item){
        System.out.println("Got it. I've added this task: ");
        System.out.println(" " + item.toString());
        this.list.add(item);
        System.out.println("Now you have " + this.list.size() + " tasks in the list");
    }

    /**
     * Displays all tasks in the list with their status and details.
     */
    public void display() {
        System.out.println(Event.line);
        for (int i = 0; i < list.size(); i++) {
            Pair temp = list.get(i);
            System.out.print((i + 1) + ".");
            System.out.println(temp.toString());
        }
        System.out.println(Event.line);
    }

    /**
     * Retrieves the internal LinkedList of tasks.
     *
     * @return The LinkedList containing all tasks in this list.
     */
    public LinkedList<Pair> getList() {
        return this.list;
    }
}
