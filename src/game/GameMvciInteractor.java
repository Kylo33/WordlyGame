package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Guess;
import domain.LetterState;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import main.GameMode;

public class GameMvciInteractor {
    private final GameMvciModel model;
    private static final Random random = new Random();

    public GameMvciInteractor(GameMvciModel model) {
        this.model = model;
        loadDictionary();
        loadSolutions();
        manageGameOver();
        initKeyboardStateMap();


        model.gameScreenVisibleProperty().addListener((observable, wasVisible, isVisible) -> {
            if (isVisible) {
                chooseWord();
                loadGuesses();
                resetKeyboardStateMap();
                model.setCurrentGuessIndex(0);
            }
        });
    }

    private void resetKeyboardStateMap() {
        Map<Character, ObjectProperty<LetterState>> map = model.getKeyboardStateMap();
        map.forEach((c, objectProperty) -> objectProperty.set(LetterState.WHITE));
    }

    private void manageGameOver() {
        model.gameOverProperty().bind(
            Bindings.createBooleanBinding(()
                                              -> model.getCurrentGuessIndex() > 5
                    || (model.getCurrentGuessIndex() > 0
                        && model.getGuesses()
                               .get(model.getCurrentGuessIndex() - 1)
                               .getLetterStates()
                               .stream()
                               .allMatch(l -> l == LetterState.GREEN)),
                model.currentGuessIndexProperty()));
    }

    private void initKeyboardStateMap() {
        Map<Character, ObjectProperty<LetterState>> map = model.getKeyboardStateMap();
        for (char[] row: GameMvciModel.KEYBOARD_CHARS) {
            for (char c: row) {
                map.put(c, new SimpleObjectProperty<>(LetterState.WHITE));
            }
        }
    }

    private void chooseWord() {
        int solutionCount = model.getSolutions().size();
        if (model.getGameMode() == GameMode.CLASSIC) {
            long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
            int daysWrapped = (int) (days % solutionCount);
            model.setCorrectWord(model.getSolutions().get(daysWrapped));
        } else if (model.getGameMode() == GameMode.UNLIMITED) {
            int randomIndex = random.nextInt(solutionCount);
            model.setCorrectWord(model.getSolutions().get(randomIndex));
        }
    }

    private void loadGuesses() {
        model.getGuesses().clear();
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

    public void addLetter(char letter) {
        if (model.gameIsOver()) return;
        Guess currentGuess = model.getGuesses().get(model.getCurrentGuessIndex());
        for (int i = 0; i < 5; i++) {
            if (currentGuess.getCharacters().get(i) == ' ') {
                currentGuess.getCharacters().set(i, letter);
                return;
            }
        }
        
    }

    public void submitGuess() {
        if (model.gameIsOver()) return;
        Guess currentGuess = model.getGuesses().get(model.getCurrentGuessIndex());
        String guessWord = charListToString(currentGuess.getCharacters());

        if (currentGuess.getCharacters().stream().anyMatch(c -> c == ' ')) {
            model.getNotificationSystem().sendMessage("Not enough letters");
            return;
        };
        
        if (!model.getDictionary().contains(guessWord.toLowerCase())) {
            model.getNotificationSystem().sendMessage("Not in word list");
            return;
        };

        currentGuess.check(model.getCorrectWord());
        updateKeyboard(currentGuess);
        
        model.setCurrentGuessIndex(model.getCurrentGuessIndex() + 1);

        if (model.gameIsOver()) {
            model.getStatsManager().addStat(model.getCurrentGuessIndex());
        }
    }

    private String charListToString(List<Character> characters) {
        StringBuilder builder = new StringBuilder();
        for(char c: characters) {
            builder.append(c);
        }
        return builder.toString();
    }

    private void updateKeyboard(Guess currentGuess) {
        Map<Character, ObjectProperty<LetterState>> map = model.getKeyboardStateMap();
        
        // add green and gray letters

        for (int i = 0; i < 5; i++) {
            char c = Character.toUpperCase(currentGuess.getCharacters().get(i));
            LetterState ls = currentGuess.getLetterStates().get(i);
            if (ls == LetterState.GREEN || ls == LetterState.GRAY)
                map.get(c).set(ls);
        }

        // for yellow letters, put them as yellow if they are not already green
        for (int i = 0; i < 5; i++) {
            char c = Character.toUpperCase(currentGuess.getCharacters().get(i));
            LetterState ls = currentGuess.getLetterStates().get(i);
            if (ls == LetterState.YELLOW && map.get(c).get() != LetterState.GREEN)
                map.get(c).set(ls);
        }
    }

    public void removeLetter() {
        if (model.gameIsOver()) return;
        Guess currentGuess = model.getGuesses().get(model.getCurrentGuessIndex());
        for (int i = 4; i >= 0; i--) {
            if (currentGuess.getCharacters().get(i) != ' ') {
                currentGuess.getCharacters().set(i, ' ');
                return;
            }
        }
    }
}
