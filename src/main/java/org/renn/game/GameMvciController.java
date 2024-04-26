/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;
import org.renn.main.GameMode;

public class GameMvciController {

    private final GameMvciViewBuilder viewBuilder;

    public GameMvciController(ObjectProperty<GameMode> gameMode, BooleanProperty gameScreenVisibleProperty) {
        GameMvciModel model = new GameMvciModel();
        GameMvciInteractor interactor = new GameMvciInteractor(model);
        this.viewBuilder = new GameMvciViewBuilder(model, interactor::addLetter, interactor::submitGuess, interactor::removeLetter);
        model.gameModeProperty().bindBidirectional(gameMode);
        model.gameScreenVisibleProperty().bindBidirectional(gameScreenVisibleProperty);
    }
    
    public Region getView() {
        return viewBuilder.build();
    }
}
