package tom;

import java.util.LinkedList;

/**
 * Manages a list of tasks, allowing for task addition, marking as done/undone, and displaying tasks.
 */
public class List{
    private LinkedList<Pair> list;

    /**
     * Constructs a tom.List instance with a specified LinkedList of tasks.
     *
     * @param list A pre-existing LinkedList containing tasks to initialize the list.
     */
    public List(LinkedList<Pair> list) {
        this.list = list;
    }

    /**
     * Constructs an empty tom.List instance.
     */
    public List() {
        this.list = new LinkedList<>();
    }

    /**
     * Marks a task at the specified index as done.
     *
     * @param x The index of the task to mark as done (1-based index).
     */
    public String mark(int x) throws ChatbotException {
        if (x > this.list.size() || x <= 0) {
            throw new ChatbotException("This item does not exist JERRYYYYYY!!!!");
        }
        Pair item = this.list.get(x - 1);
        if (item.isDone()) {
            throw new ChatbotException("You have done it JERRYYYYYY!!!!");
        }
        item.mark(true);
        return "Nice! I've marked this task as done: " + item;
    }

    /**
     * Marks a task at the specified index as not done.
     *
     * @param x The index of the task to mark as not done (1-based index).
     */
    public String unmark(int x) throws ChatbotException{
        if (x >= this.list.size() || x <= 0) {
            throw new ChatbotException("This item does not exist JERRYYYYYY!!!!");
        }
        Pair item = this.list.get(x - 1);
        if (!item.isDone()) {
            throw new ChatbotException("You have NOT done it JERRYYYYYY!!!!");
        }
        item.mark(false);
        return "OK, I've marked this task as not done: " + item;
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param x The index of the task to delete (1-based index).
     * @throws ChatbotException If the index is invalid.
     */
    public String delete(int x) throws ChatbotException {
        if (x <= 0 || x > this.list.size()) {
            throw new ChatbotException("This item does not exist JERRYYYYYY!!!!");
        }
        Pair removedTask = this.list.remove(x - 1);
        return "Noted. I've removed this task: " + removedTask.toString() +
                "\nNow you have " + this.list.size() + " tasks in the list.";

    }

    /**
     * Adds a new task to the list.
     *
     * @param item The task to be added.
     */
    public String add(Pair item){
        this.list.add(item);
        return "Got it. I've added this task: " + item.toString()
                    + "\nNow you have " + this.list.size() + " tasks in the list";
    }

    /**
     * Displays all tasks in the list with their status and details.
     */
    public String display() {
        String output = "Your List\n";
        for (int i = 0; i < list.size(); i++) {
            Pair temp = list.get(i);
            output += ((i + 1) + "." + temp.toString() + "\n");
        }
        return output + "End of List";
    }

    /**
     * Retrieves the internal LinkedList of tasks.
     *
     * @return The LinkedList containing all tasks in this list.
     */
    public LinkedList<Pair> getList() {
        return this.list;
    }

    /**
     * Finds and returns a list of tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A formatted string containing matching tasks or a message if no matches are found.
     */
    public String find(String keyword) {
        StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
        boolean found = false;

        for (int i = 0; i < list.size(); i++) {
            Pair task = list.get(i);
            if (task.getItem().toLowerCase().contains(keyword.toLowerCase())) {
                result.append(i + 1).append(".").append(task).append("\n");
                found = true;
            }
        }

        return found ? result.toString() : "No matching tasks found.";
    }
}
