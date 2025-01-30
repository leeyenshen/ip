package tom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ChatbotDataHandlerTest {
    private ChatbotDataHandler dataHandler;
    private File tempFile;
    private List taskList;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("testTasks", ".txt");
        dataHandler = new ChatbotDataHandler(tempFile.getAbsolutePath());
        taskList = new List(new LinkedList<>());
    }

    @Test
    public void saveTasks_emptyList_createsEmptyFile() throws IOException {
        dataHandler.saveTasks(new List());

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    public void saveTasks_withTasks_correctFormat() throws IOException {
        taskList.add(new Todo("Buy groceries", false));
        taskList.add(new Deadline("Submit report", false, LocalDate.parse("2025-02-01")));

        dataHandler.saveTasks(taskList);

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertEquals("T | 0 | Buy groceries", reader.readLine());
        assertEquals("D | 0 | Submit report | 2025-02-01", reader.readLine());
        reader.close();
    }

    @Test
    public void saveTasks_createsFileIfNotExist() {
        File newFile = new File("newTestFile.txt");
        newFile.delete();

        ChatbotDataHandler newHandler = new ChatbotDataHandler(newFile.getAbsolutePath());
        newHandler.saveTasks(new List());

        assertTrue(newFile.exists());
        newFile.delete();
    }

    @Test
    public void saveTasks_overwritesExistingData() throws IOException {
        taskList.add(new Todo("Old Task", false));
        dataHandler.saveTasks(taskList);

        taskList = new List(new LinkedList<>());
        taskList.add(new Todo("New Task", false));
        dataHandler.saveTasks(taskList);

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        assertEquals("T | 0 | New Task", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }
}
