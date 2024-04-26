/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.game;

import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class StatsManager {
    private final ObservableList<Integer> guessFrequencyList;
    private final Preferences preferences = Preferences.userNodeForPackage(getClass());

    public StatsManager() {
        this.guessFrequencyList = FXCollections.observableArrayList();

        for (int i = 1; i <= 6; i++)
            guessFrequencyList.add(preferences.getInt(String.valueOf(i), 0));
        guessFrequencyList.add(preferences.getInt("7", 0)); // anything over 6 guesses

        addListListener();
    }

    private void addListListener() {
        guessFrequencyList.addListener((ListChangeListener<? super Integer>) change -> {
            while (change.next())
                for (int i = 0, c = guessFrequencyList.size(); i < c; i++)
                    preferences.putInt(String.valueOf(i + 1), guessFrequencyList.get(i));
        });
    }

    public void addStat(int guessCountIndex) {
        guessFrequencyList.set(guessCountIndex, guessFrequencyList.get(guessCountIndex) + 1);
    }

    public int getWinPercent() {
        int totalCount = getGamesPlayed();
        int successCount = totalCount - guessFrequencyList.get(6);
        return successCount * 100 / totalCount;
    }

    public int getGamesPlayed() {
        return guessFrequencyList.stream().mapToInt(i -> i).sum();
    }

    public ObservableList<Integer> getGuessFrequencyList() {
        return guessFrequencyList;
    }
}
