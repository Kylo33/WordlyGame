/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.game;

import java.util.*;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.renn.domain.Guess;
import org.renn.domain.LetterState;
import org.renn.main.GameMode;

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

    private final BooleanProperty gameOver = new SimpleBooleanProperty();
    private final BooleanProperty gameScreenVisible = new SimpleBooleanProperty();

    private final StatsManager statsManager = new StatsManager();

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

    public boolean gameIsOver() {
        return gameOver.get();
    }

    public BooleanProperty gameOverProperty() {
        return gameOver;
    }

    public void setGameOver(boolean v) {
        gameOver.set(v);
    }

    public boolean getGameScreenVisible() {
        return gameScreenVisible.get();
    }

    public BooleanProperty gameScreenVisibleProperty() {
        return gameScreenVisible;
    }

    public void setGameScreenVisible(boolean v) {
        gameScreenVisible.set(v);
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
