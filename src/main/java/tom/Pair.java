package tom;

/**
 * Represents a basic task with a description and completion status.
 */
public class Pair {
    private String item;
    private boolean done;

    /**
     * Constructs a tom.Pair instance with the specified description and completion status.
     *
     * @param item The description of the task.
     * @param done The completion status of the task.
     */
    public Pair(String item, boolean done) {
        this.item = item;
        this.done = done;
    }

    /**
     * Marks the task as done or not done.
     *
     * @param done True to mark the task as done, false to mark it as not done.
     */
    public void mark(boolean done) {
        this.done = done;
    }

    /**
     * Checks if the task is done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.done;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getItem() {
        return this.item;
    }

    /**
     * Returns a file-friendly string representation of the {@code Pair} task.
     *
     * @return A formatted string suitable for file storage.
     */
    public String toFileFormat() {
        return "Type | Done | Description | Time (If applicable)";
    }
}
