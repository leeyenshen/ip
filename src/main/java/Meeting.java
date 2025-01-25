/**
 * Represents a meeting with a specific time frame.
 * A Meeting includes a description, start time, and end time.
 */
public class Meeting extends Pair{
    private String from;
    private String to;

    /**
     * Constructs a Meeting instance with the specified details.
     *
     * @param item The description of the meeting.
     * @param done Whether the meeting task is completed.
     * @param from The start time of the meeting.
     * @param to The end time of the meeting.
     */
    public Meeting(String item, boolean done, String from, String to){
        super(item, done);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Meeting, including its status and time frame.
     *
     * @return A string representing the Meeting.
     */
    @Override
    public String toString() {
        String temp = "[E]";
        if (this.isDone()) {
            temp += "[X] ";
        } else {
            temp += "[ ] ";
        }
        temp += this.getItem() + " (from: " + this.from + " to: " + this.to + ")";
        return temp;
    }
}
