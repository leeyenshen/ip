package tom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meeting with a specific time frame.
 * A tom.Meeting includes a description, start time, and end time.
 */
public class Meeting extends Pair{
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;

    public Meeting(String item, boolean done, LocalDate from, LocalDate to){
        super(item, done);
        this.dateFrom = from;
        this.dateTo = to;
    }

    /**
     * Constructs a tom.Meeting instance with the specified details.
     *
     * @param item The description of the meeting.
     * @param done Whether the meeting task is completed.
     * @param from The start time of the meeting.
     * @param to The end time of the meeting.
     */
    public Meeting(String item, boolean done, LocalDateTime from, LocalDateTime to){
        super(item, done);
        this.timeFrom = from;
        this.timeTo = to;
    }

    /**
     * Returns a string representation of the tom.Meeting, including its status and time frame.
     *
     * @return A string representing the tom.Meeting.
     */
    @Override
    public String toString() {
        String temp = "[E]";
        if (this.isDone()) {
            temp += "[X] ";
        } else {
            temp += "[ ] ";
        }
        if (timeFrom == null) {
            temp += this.getItem() + " (by: " +
                        this.dateFrom.format(DateTimeFormatter.ofPattern("MMM d yyyy")) +
                            " to: " + this.dateTo.format(DateTimeFormatter.ofPattern("MMM d yyyy")) +")";
        } else {
            temp += this.getItem() + " (by: " +
                    this.timeFrom.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a")) +
                    " to: " + this.timeTo.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mm a")) +")";
        }
        return temp;
    }

    @Override
    public String toFileFormat() {
        if (timeFrom == null) {
            return "T | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.dateFrom + " | " + this.dateTo;
        } else {
            return "T | " + (this.isDone() ? "1" : "0") + " | " + this.getItem()
                    + " | " + this.timeFrom + " | " + this.timeTo;
        }
    }
}
