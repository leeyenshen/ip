import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A Deadline includes a description of the task and a date/time by which it must be completed.
 */
public class Deadline extends Pair{
    private LocalDateTime timeby;
    private LocalDate dateby;

    public Deadline(String item, boolean done, LocalDate dateby){
        super(item, done);
        this.dateby = dateby;
    }
    /**
     * Constructs a Deadline instance with the specified details.
     *
     * @param item The description of the deadline task.
     * @param done Whether the task has been completed.
     * @param timeby The deadline for the task.
     */

    public Deadline(String item, boolean done, LocalDateTime timeby){
        super(item, done);
        this.timeby = timeby;
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
        if (timeby == null) {
            temp += this.getItem() + " (by: " + this.dateby.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
        } else {
            temp += this.getItem() + " (by: " +
                        this.timeby.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a")) + ")";
        }
        return temp;
    }

    @Override
    public String toFileFormat() {
        if (timeby == null) {
            return "D | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.dateby;
        } else {
            return "D | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.timeby;
        }
    }
}
