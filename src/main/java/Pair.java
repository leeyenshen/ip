public class Pair {
    private String item;
    private boolean done;

    public Pair(String item, boolean done) {
        this.item = item;
        this.done = done;
    }

    public void mark(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return this.done;
    }

    public String getItem() {
        return this.item;
    }
}
