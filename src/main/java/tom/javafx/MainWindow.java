package tom.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tom.ChatbotException;
import tom.Tom;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Tom tom;

    private Image jerryImage = new Image(this.getClass().getResourceAsStream("/images/Jerry.png"));
    private Image tomImage = new Image(this.getClass().getResourceAsStream("/images/Tom.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Tom instance */
    public void setTom(Tom tom) {
        this.tom = tom;
        addInitialBotMessage();
    }

    private void addInitialBotMessage() {
        if (tom != null) {
            try {
                dialogContainer.getChildren().add(DialogBox.getTomDialog(tom.getResponse("list"), tomImage));
            } catch (ChatbotException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Tom's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (input.isEmpty()) {
            userInput.clear();
            return;
        }

        String response = null;
        try {
            response = tom.getResponse(input);
        } catch (ChatbotException e) {
            response = e.getMessage();
        }

        // Display user input and bot response
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, jerryImage),
                DialogBox.getTomDialog(response, tomImage)
        );

        userInput.clear();

        // Exit if the user types 'bye'
        if (input.equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }
}
