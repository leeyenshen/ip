public class Meeting extends Pair{
    protected String from;
    protected String to;

    public Meeting(String item, boolean done, String from, String to){
        super(item, done);
        this.from = from;
        this.to = to;
    }

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
