package tom;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.io.FileWriter;

/**
 * Handles the loading and saving of chatbot task data from a file.
 */
public class ChatbotDataHandler {
    private String FILE_PATH;


    public ChatbotDataHandler(String filePath) {
        this.FILE_PATH = filePath;
    }
    public List getTasks() throws ChatbotException, IOException {
        File dataFile = new File(this.FILE_PATH);
        if (!dataFile.exists()) {
            if (dataFile.getParentFile() != null && dataFile.getParentFile().mkdirs()) {
                System.out.println("Data directory created: " + dataFile.getParentFile().getAbsolutePath());
            }
            if (dataFile.createNewFile()) {
                System.out.println("tom.Chatbot data file created: " + dataFile.getAbsolutePath());
            } else {
                System.out.println("Failed to create chatbot data file.");
            }
        } else {
            System.out.println("Initialising data from memory");
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
        Ui ui = new Ui();
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
                ui.listEmpty();
            } else {
                ui.listNotEmpty(new List(tasks));
            }
        } catch (IOException e) {
            ui.showLoadingError();
        }
        return new List(tasks);
    }

    /**
     * Parses a task string and creates the appropriate Task object.
     * Format:
     * - tom.Todo: "T | <done> | <description>"
     * - tom.Deadline: "D | <done> | <description> | <due_date>"
     * - tom.Event: "E | <done> | <description> | <event_from> | <event_to>"
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
        case "T": // tom.Todo Task
            return new Todo(description, isDone);

        case "D": // tom.Deadline Task
            if (parts.length < 4) {
                System.out.println("Invalid task format: " + description);
                return null;
            }
            String inputDate = parts[3].trim();
            if (inputDate.contains(":")) {
                LocalDateTime date = LocalDateTime.parse(inputDate);
                return new Deadline(description, isDone, date);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(inputDate, formatter);
                return new Deadline(description, isDone, date);
            }

        case "E": // tom.Event Task
            if (parts.length < 4) {
                System.out.println("Invalid task format: " + description);
                return null;
            }
            String from = parts[3].trim();
            String to = parts[4].trim();
            if (from.contains(":")) {
                LocalDateTime fromDate = LocalDateTime.parse(from);
                LocalDateTime toDate = LocalDateTime.parse(to);
                return new Meeting(description, isDone, fromDate, toDate);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);
                return new Meeting(description, isDone, fromDate, toDate);
            }

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
    public void saveTasks(List tasks) {
        File dataFile = new File(this.FILE_PATH);
        try {
            if (!dataFile.exists()) {
                if (dataFile.getParentFile() != null && dataFile.getParentFile().mkdirs()) {
                    System.out.println("Data directory created: " + dataFile.getParentFile().getAbsolutePath());
                }
                if (dataFile.createNewFile()) {
                    System.out.println("tom.Chatbot data file created: " + dataFile.getAbsolutePath());
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
