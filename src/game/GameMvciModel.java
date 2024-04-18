package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.Guess;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.GameMode;

public class GameMvciModel {
    private final ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();
    private final ObservableList<Guess> guesses = FXCollections.observableArrayList(guess -> new Observable[]{guess.getCharacters(), guess.getLetterStates()});
    private final Set<String> dictionary = new HashSet<>();
    private final List<String> solutions = new ArrayList<>();

    public GameMode getGameMode() {
        return gameMode.get();
    }

    public ObjectProperty<GameMode> gameModeProperty() {
        return gameMode;
    }

    public void setGameMode(GameMode v) {
        gameMode.set(v);
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public ObservableList<Guess> getGuesses() {
        return guesses;
    }
}
