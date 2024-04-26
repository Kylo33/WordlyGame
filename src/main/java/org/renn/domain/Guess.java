/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.domain;

import java.util.HashMap;
import java.util.Map;
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
            letterStates.add(LetterState.WHITE);
        }
    }

    public void check(String correctWord) {

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            frequencyMap.put(Character.toLowerCase(correctWord.charAt(i)), 
                frequencyMap.getOrDefault(Character.toLowerCase(correctWord.charAt(i)), 0) + 1);
        }

        for (int i = 0; i < 5; i++) {
            char c = Character.toLowerCase(characters.get(i));
            if (correctWord.charAt(i) == c) {
                letterStates.set(i, LetterState.GREEN);
                if (frequencyMap.containsKey(c)) {
                    frequencyMap.put(c, frequencyMap.get(c) - 1);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            char c = Character.toLowerCase(characters.get(i));
            if (letterStates.get(i) != LetterState.GREEN && frequencyMap.getOrDefault(c, 0) > 0) {
                letterStates.set(i, LetterState.YELLOW);
                frequencyMap.put(c, frequencyMap.get(c)-1);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (letterStates.get(i) == LetterState.WHITE) {
                letterStates.set(i, LetterState.GRAY);
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