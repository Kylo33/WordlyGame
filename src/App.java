import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.MainMvciController;

public class App extends Application{

    private final MainMvciController controller = new MainMvciController();

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(controller.getView()));
        stage.setTitle("WordlyGame");
        stage.show();
    }

    /** Main Method to Start the JavaFX Application */
    public static void main (String[] args) {
        launch(args);
    }
}
