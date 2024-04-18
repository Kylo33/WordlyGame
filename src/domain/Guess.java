package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Guess {
    private final BooleanProperty checked = new SimpleBooleanProperty(false);
    private final ObservableList<Character> characters = FXCollections.observableArrayList();
    private final ObservableList<LetterState> letterStates = FXCollections.observableArrayList();
    
    public Guess() {
        for (int i = 0; i < 5; i++) {
            characters.add(' ');
            letterStates.add(LetterState.GRAY);
        }
    }

    public void check(String correctWord) {

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            if (correctWord.contains(String.valueOf(characters.get(i)))) {
                map.put(characters.get(i), map.getOrDefault(i, 0) + 1);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (correctWord.charAt(i) == characters.get(i).charValue()) {
                letterStates.set(i, LetterState.GREEN);
                if (map.containsKey(characters.get(i))) {
                    map.put(characters.get(i), map.get(characters.get(i)) - 1);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (letterStates.get(i) != LetterState.GREEN && map.getOrDefault(characters.get(i), 0) > 0) {
                letterStates.set(i, LetterState.YELLOW);
                map.put(characters.get(i), map.get(characters.get(i))-1);
            }
        }

        checked.set(true);
    }

    public ObservableList<Character> getCharacters() {
        return characters;
    }

    public ObservableList<LetterState> getLetterStates() {
        return letterStates;
    }
}