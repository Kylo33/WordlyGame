package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class StatsManager {
    private final File statsFile;
    private final ObservableList<Integer> guessFrequencyList;

    public StatsManager(File statsFile) {
        this.statsFile = statsFile;
        this.guessFrequencyList = FXCollections.observableArrayList();
        
        try (Scanner scanner = new Scanner(statsFile)) {
            int i = 0;
            while (scanner.hasNextLine() && i < 7) {
                guessFrequencyList.add(Integer.valueOf(scanner.nextLine()));
                i++;
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
        for (int i = guessFrequencyList.size(); i < 7; i++) {
            guessFrequencyList.add(0);
        }

        addListListener();
    }

    private void addListListener() {
        guessFrequencyList.addListener((ListChangeListener<? super Integer>) change -> {
            while (change.next()) {
                try (PrintWriter writer = new PrintWriter(statsFile)) {
                    guessFrequencyList.forEach(i -> writer.println(i));
                } catch (FileNotFoundException exception) {
                    throw new RuntimeException(exception);
                }
            }
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
        return guessFrequencyList.stream().mapToInt(i -> i.intValue()).sum();
    }

    public ObservableList<Integer> getGuessFrequencyList() {
        return guessFrequencyList;
    }

    public static void main(String[] args) {
        StatsManager sm = new StatsManager(new File("resources/stats/statistics.txt"));
        System.out.println(sm.getWinPercent());
        System.out.println(sm.getGamesPlayed());
        System.out.println(sm.getGuessFrequencyList());
    }
}
