package tom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private Parser parser;
    private List list;
    private ChatbotDataHandler dataHandler;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        LinkedList<Pair> taskList = new LinkedList<>();
        taskList.add(new Todo("call home", false));
        taskList.add(new Deadline("call dad", false,
                    LocalDateTime.parse("2019-02-13 2359", formatter)));
        taskList.add(new Meeting("Meet dad", false,
                    LocalDateTime.parse("2019-02-13 2359", formatter),
                    LocalDateTime.parse("2019-02-14 2359", formatter)));

        this.list = new List(taskList);
        this.dataHandler = new ChatbotDataHandler("data/Tom.txt");
        this.parser = new Parser(new Listen(list, dataHandler));
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void parse_validTodoCommand_success() {
        int initialSize = list.getList().size();

        parser.parse("todo read book", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();

        assertEquals(initialSize + 1, list.getList().size());
        assertTrue(list.getList().getLast() instanceof Todo);
        assertEquals("read book", list.getList().getLast().getItem());
        assertTrue(consoleOutput.contains("Got it. I've added this task: [T][ ] read book"));
    }

    @Test
    public void parse_validDeadlineCommand_success() {
        int initialSize = list.getList().size();

        parser.parse("deadline submit report /by 2025-02-01", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();

        assertEquals(initialSize + 1, list.getList().size());
        assertTrue(list.getList().getLast() instanceof Deadline);
        assertEquals("submit report", list.getList().getLast().getItem());
        assertTrue(consoleOutput.contains("Got it. I've added this task: [D][ ] submit report (by: Feb 1 2025)"));
    }

    @Test
    public void parse_validEventCommand_success() {
        int initialSize = list.getList().size();

        parser.parse("event team meeting /from 2025-02-01 /to 2025-02-02", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();

        assertEquals(initialSize + 1, list.getList().size());
        assertTrue(list.getList().getLast() instanceof Meeting);
        assertEquals("team meeting", list.getList().getLast().getItem());
        assertTrue(consoleOutput.contains("Got it. I've added this task: [E][ ] team meeting (by: Feb 1 2025 to: Feb 2 2025)"));
    }

    @Test
    public void parse_deleteTask_success() {
        int initialSize = list.getList().size();

        parser.parse("delete 1", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();

        assertEquals(initialSize - 1, list.getList().size());
        assertTrue(consoleOutput.contains("Noted. I've removed this task"));
    }

    @Test
    public void parse_emptyCommand_doesNothing() {
        Event event = parser.parse("", list, dataHandler);
        assertNotNull(event); // Ensure the event remains the same
    }

    @Test
    public void parse_invalidCommand_printsErrorMessage() {
        parser.parse("invalidCommand", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("I'm sorry, but I don't know what the command 'invalidCommand' means. " +
                    "Please try again."));
    }

    @Test
    public void parse_deadlineWithoutBy_printsErrorMessage() {
        parser.parse("deadline Finish work", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("A deadline must include '/by' followed by the due date. " +
                    "Example: deadline Finish report /by tomorrow."));
    }

    @Test
    public void parse_markInvalidIndex_printsErrorMessage() {
        parser.parse("mark five", list, dataHandler);
        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("Input a proper number"));
    }
}
