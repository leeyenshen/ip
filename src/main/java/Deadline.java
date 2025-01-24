/**
 * Represents a task with a deadline.
 * A Deadline includes a description of the task and a date/time by which it must be completed.
 */
public class Deadline extends Pair{
    protected String by;

    /**
     * Constructs a Deadline instance with the specified details.
     *
     * @param item The description of the deadline task.
     * @param done Whether the task has been completed.
     * @param by The deadline for the task.
     */

    public Deadline(String item, boolean done, String by){
        super(item, done);
        this.by = by;
    }

    /**
     * Returns a string representation of the Deadline, including its status and deadline date/time.
     *
     * @return A string representing the Deadline.
     */
    @Override
    public String toString() {
        String temp = "[D]";
        if (this.isDone()) {
            temp += "[X] ";
        } else {
            temp += "[ ] ";
        }
        temp += this.getItem() + " (by: " + this.by + ")";
        return temp;
    }
}
