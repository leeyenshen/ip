package tom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A tom.Deadline includes a description of the task and a date/time by which it must be completed.
 */
public class Deadline extends Pair{
    private LocalDateTime timeBy;
    private LocalDate dateBy;

    public Deadline(String item, boolean done, LocalDate dateBy) {
        super(item, done);
        assert dateBy != null : "Deadline date cannot be null";
        this.dateBy = dateBy;
    }

    /**
     * Constructs a tom.Deadline instance with the specified details.
     *
     * @param item The description of the deadline task.
     * @param done Whether the task has been completed.
     * @param timeBy The deadline for the task.
     */
    public Deadline(String item, boolean done, LocalDateTime timeBy) {
        super(item, done);
        assert timeBy != null : "Deadline time cannot be null";
        this.timeBy = timeBy;
    }

    /**
     * Returns a string representation of the tom.Deadline, including its status and deadline date/time.
     *
     * @return A string representing the tom.Deadline.
     */
    @Override
    public String toString() {
        String status = this.isDone() ? "[X] " : "[ ] ";
        String timeString = (timeBy != null)
                ? timeBy.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"))
                : dateBy.format(DateTimeFormatter.ofPattern("MMM d yyyy"));

        return "[D]" + status + this.getItem() + " (by: " + timeString + ")";
    }


    @Override
    public String toFileFormat() {
        if (timeBy == null) {
            return "D | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.dateBy;
        } else {
            return "D | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.timeBy;
        }
    }
}
