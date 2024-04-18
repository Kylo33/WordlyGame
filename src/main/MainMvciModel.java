package main;

import game.GameMvciController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

public class MainMvciModel {
    private final BooleanProperty gameScreenVisible = new SimpleBooleanProperty(false);
    private final ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>(GameMode.CLASSIC);
    private final GameMvciController gameMvciController = new GameMvciController(gameModeProperty());

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
