/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.main;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import org.renn.game.GameMvciController;

public class MainMvciModel {
    private final BooleanProperty gameScreenVisible = new SimpleBooleanProperty(false);
    private final ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();
    private final GameMvciController gameMvciController = new GameMvciController(gameModeProperty(), gameScreenVisible);

    public BooleanProperty gameScreenVisibleProperty() {
        return gameScreenVisible;
    }

    public boolean getGameScreenVisible() {
        return gameScreenVisible.get();
    }

    public void setGameScreenVisible(boolean v) {
        gameScreenVisible.set(v);
    }

    public GameMvciController getGameMvciController() {
        return gameMvciController;
    }
    
    public GameMode getGameMode() {
        return gameMode.get();
    }

    public ObjectProperty<GameMode> gameModeProperty() {
        return gameMode;
    }

    public void setGameMode(GameMode g) {
        gameMode.set(g);
    }
}
