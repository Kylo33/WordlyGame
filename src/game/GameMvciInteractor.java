package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import domain.Guess;

public class GameMvciInteractor {
    private final GameMvciModel model;

    public GameMvciInteractor(GameMvciModel model) {
        this.model = model;
        loadDictionary();
        loadSolutions();
        loadGuesses();
    }

    private void loadGuesses() {
        for (int i = 0; i < 6; i++) {
            model.getGuesses().add(new Guess());
        }
    }

    private void loadDictionary() {
        var dict = model.getDictionary();
        try(Scanner scanner = new Scanner(new File("resources/words/dictionary.txt"))) {
            while (scanner.hasNextLine()) {
                dict.add(scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Dictionary was not found.");
        }
    }

    private void loadSolutions() {
        var solutions = model.getSolutions();
        try(Scanner scanner = new Scanner(new File("resources/words/solutions.txt"))) {
            while (scanner.hasNextLine()) {
                solutions.add(scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Solutions.txt was not found.");
        }
    }
}
