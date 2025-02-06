package tom.javafx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import tom.Tom;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

/**
 * JavaFX Application entry point.
 */
public class Main extends Application {
    private Tom tom = new Tom("data/Tom.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTom(tom);
            stage.setTitle("Tom Chatbot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Main method to launch JavaFX */
    public static void main(String[] args) {
        launch(args);
    }
}
