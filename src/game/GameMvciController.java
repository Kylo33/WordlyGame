package game;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;
import main.GameMode;

public class GameMvciController {

    private final GameMvciViewBuilder viewBuilder;

    public GameMvciController(ObjectProperty<GameMode> gameMode) {
        GameMvciModel model = new GameMvciModel();
        GameMvciInteractor interactor = new GameMvciInteractor(model);
        this.viewBuilder = new GameMvciViewBuilder(model);
        model.gameModeProperty().bindBidirectional(gameMode);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("h");
            }
            Platform.runLater(() -> {
                model.getGuesses().get(0).getCharacters().setAll("tithe".chars()
                .mapToObj(e->(char)e).collect(Collectors.toList()));
                model.getGuesses().get(0).check("tithe");
            });
        }).start();
    }
    
    public Region getView() {
        return viewBuilder.build();
    }
}
