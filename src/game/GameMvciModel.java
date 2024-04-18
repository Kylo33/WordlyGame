package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.Guess;
import domain.LetterState;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.GameMode;

public class GameMvciModel {
    public static final char[][] KEYBOARD_CHARS = new char[][]{
        new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
        new char[]{'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'},
        new char[]{'Z', 'X', 'C', 'V', 'B', 'N', 'M'},
    }; 
    private final Map<Character, ObjectProperty<LetterState>> keyboardStateMap = new HashMap<>();

    private final ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<>();
    private final ObservableList<Guess> guesses = FXCollections.observableArrayList(guess -> new Observable[]{guess.getCharacters(), guess.getLetterStates()});
    private final IntegerProperty currentGuessIndex = new SimpleIntegerProperty();

    private final Set<String> dictionary = new HashSet<>();
    private final List<String> solutions = new ArrayList<>();

    private final StringProperty correctWord = new SimpleStringProperty();

    private final NotificationSystem notificationSystem = new NotificationSystem();

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

    public IntegerProperty currentGuessIndexProperty() {
        return currentGuessIndex;
    }

    public int getCurrentGuessIndex() {
        return currentGuessIndex.get();
    }

    public void setCurrentGuessIndex(int i) {
        currentGuessIndex.set(i);
    }

    public StringProperty correctWordProperty() {
        return correctWord;
    }

    public String getCorrectWord() {
        return correctWord.get();
    }

    public void setCorrectWord(String word) {
        correctWord.set(word);
    }

    public Map<Character, ObjectProperty<LetterState>> getKeyboardStateMap() {
        return keyboardStateMap;
    }

    public NotificationSystem getNotificationSystem() {
        return notificationSystem;
    }
}
