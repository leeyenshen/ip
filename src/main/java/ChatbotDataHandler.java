import javax.sound.midi.SysexMessage;
import java.io.*;
import java.util.LinkedList;

public class ChatbotDataHandler {

    private static final String FILE_PATH = "data" + File.separator + "Tom.txt";

    public static List getTasks() {
        File dataFile = new File(FILE_PATH);
        try {
            if (!dataFile.exists()) {
                if (dataFile.getParentFile() != null && dataFile.getParentFile().mkdirs()) {
                    System.out.println("Data directory created: " + dataFile.getParentFile().getAbsolutePath());
                }
                if (dataFile.createNewFile()) {
                    System.out.println("Chatbot data file created: " + dataFile.getAbsolutePath());
                } else {
                    System.out.println("Failed to create chatbot data file.");
                }
            } else {
                System.out.println("Initialising data from memory");
            }
        } catch (IOException e) {
            System.err.println("Error while accessing chatbot data file: " + e.getMessage());
            e.printStackTrace();
        }

        return readTasksFromFile(dataFile);
    }

    /**
     * Reads tasks from the specified file and returns them as a list.
     *
     * @param file The file containing task data.
     * @return A list of tasks
     */
    private static List readTasksFromFile(File file) {
        LinkedList<Pair> tasks = new LinkedList<>();

        if (!file.exists()) {
            System.out.println("No existing task file found. A new file will be created.");
            return new List(tasks);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pair task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            if (tasks.isEmpty()) {
                System.out.println("JERRYYYYYY!!!! Your list is empty");
                System.out.println(Event.LINE);
            } else {
                System.out.println("JERRYYYYYY!!!! Your list is not empty");
                List Result = new List(tasks);
                Result.display();
                System.out.println(Event.LINE);
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks from file: " + e.getMessage());
            e.printStackTrace();
        }

        return new List(tasks);
    }

    /**
     * Parses a task string and creates the appropriate Task object.
     * Format:
     * - Todo: "T | <done> | <description>"
     * - Deadline: "D | <done> | <description> | <due_date>"
     * - Event: "E | <done> | <description> | <event_from> | <event_to>"
     *
     * @param line A line from the file representing a task.
     * @return A Task object or null if parsing fails.
     */
    private static Pair parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Invalid task format: " + line);
            return null;
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T": // Todo Task
            return new Todo(description, isDone);

        case "D": // Deadline Task
            if (parts.length < 4) {
                System.out.println("Invalid task format: " + description);
                return null;
            }
            return new Deadline(description, isDone, parts[3]);

        case "E": // Event Task
            if (parts.length < 4) {
                System.out.println("Invalid task format: " + description);
                return null;
            }
            return new Meeting(description, isDone, parts[3], parts[4]);

        default:
            System.out.println("Unknown task type: " + line);
            return null;
        }
    }

    /**
     * Saves the current task list back to the file.
     *
     * @param tasks The list of tasks to save.
     */
    public static void saveTasks(List tasks) {
        File dataFile = new File(FILE_PATH);
        try {
            if (!dataFile.exists()) {
                if (dataFile.getParentFile() != null && dataFile.getParentFile().mkdirs()) {
                    System.out.println("Data directory created: " + dataFile.getParentFile().getAbsolutePath());
                }
                if (dataFile.createNewFile()) {
                    System.out.println("Chatbot data file created: " + dataFile.getAbsolutePath());
                } else {
                    System.out.println("Failed to create chatbot data file.");
                }
            } else {
                System.out.println("Saving data to memory");
            }
        } catch (IOException e) {
            System.err.println("Error while accessing chatbot data file: " + e.getMessage());
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            LinkedList<Pair> list = tasks.getList();
            for (Pair pair : list) {
                writer.write(pair.toFileFormat());
                writer.newLine();
            }
            System.out.println("Tasks successfully saved to: " + FILE_PATH);
            System.out.println(Event.LINE);
        } catch (IOException e) {
            System.err.println("Error writing tasks to file: " + e.getMessage());
            System.out.println(Event.LINE);
            e.printStackTrace();
        }
    }
}
