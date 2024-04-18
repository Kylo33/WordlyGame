package game;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;
import main.GameMode;

public class GameMvciController {

    private final GameMvciViewBuilder viewBuilder;

    public GameMvciController(ObjectProperty<GameMode> gameMode) {
        GameMvciModel model = new GameMvciModel();
        GameMvciInteractor interactor = new GameMvciInteractor(model);
        this.viewBuilder = new GameMvciViewBuilder(model, interactor::addLetter, interactor::submitGuess, interactor::removeLetter);
        model.gameModeProperty().bindBidirectional(gameMode);
    }
    
    public Region getView() {
        return viewBuilder.build();
    }
}
