public class Todo extends Pair{
    public Todo(String item, boolean done) {
        super(item, done);
    }

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
