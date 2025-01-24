public class Deadline extends Pair{
    protected String by;

    public Deadline(String item, boolean done, String by){
        super(item, done);
        this.by = by;
    }

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
