/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.renn.main.MainMvciController;

public class App extends Application{

    private final MainMvciController controller = new MainMvciController();

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        stage.setScene(new Scene(controller.getView(), 1280, 720));
        stage.setTitle("WordlyGame");
        stage.show();
    }

    /** Main Method to Start the JavaFX Application */
    public static void main (String[] args) {
        launch(args);
    }
}
