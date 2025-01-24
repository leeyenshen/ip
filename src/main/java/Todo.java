/**
 * Represents a simple to-do task.
 */
public class Todo extends Pair{
    /**
     * Constructs a Todo instance with the specified description and completion status.
     *
     * @param item The description of the to-do task.
     * @param done Whether the task is completed.
     */
    public Todo(String item, boolean done) {
        super(item, done);
    }

    /**
     * Returns a string representation of the Todo, including its status.
     *
     * @return A string representing the Todo.
     */
    @Override
    public String toString() {
        String temp = "[T]";
        if (this.isDone()) {
            temp += "[X] ";
        } else {
            temp += "[ ] ";
        }
        temp += this.getItem();
        return temp;
    }
}
