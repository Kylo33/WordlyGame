package game;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignK;

import atlantafx.base.theme.Styles;
import domain.Guess;
import domain.LetterState;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class GameMvciViewBuilder implements Builder<Region> {

    private final GameMvciModel model;
    private static final String[][] KEYBOARD_CHARS = new String[][]{
        new String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
        new String[]{"A", "S", "D", "F", "G", "H", "J", "K", "L"},
        new String[]{".BACKSPACE", "Z", "X", "C", "V", "B", "N", "M", ".ENTER"},
    }; 

    public GameMvciViewBuilder(GameMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();

        result.setCenter(createBoard());
        result.setBottom(createKeyboard());

        return result;
    }

    private Region createBoard() {
        GridPane result = new GridPane();
        result.setHgap(5);
        result.setVgap(5);

        fillBoard(result);
        model.getGuesses().addListener((ListChangeListener<? super Guess>) change -> {
            while (change.next()) {
                result.getChildren().clear();
                fillBoard(result);
            }
        });

        result.setAlignment(Pos.CENTER);
        return result;
    }

    private void fillBoard(GridPane result) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                Label box = new Label(String.valueOf(model.getGuesses().get(j).getCharacters().get(i)));
                box.getStyleClass().add(Styles.TITLE_1);

                String color = String.valueOf(model.getGuesses().get(j).getLetterStates().get(i));

                box.setStyle("-fx-background-color:" + color + "; -fx-text-fill: white;");
                box.setPrefSize(50, 50);
                box.setAlignment(Pos.CENTER);
                result.add(box, i, j);
            }
        }
    }

    private Node createKeyboard() {
        GridPane result = new GridPane();
        result.setVgap(3);

        for (int i = 0; i < KEYBOARD_CHARS.length; i++) {
            HBox rowBox = new HBox(3);
            rowBox.setAlignment(Pos.CENTER);
            for (String s: KEYBOARD_CHARS[i]) {
                Button b;
                if (s.startsWith(".ENTER")) {
                    b = new Button("", new FontIcon(MaterialDesignK.KEYBOARD_RETURN));
                    b.getStyleClass().addAll(Styles.BUTTON_ICON);
                } else if (s.startsWith(".BACKSPACE")) {
                    b = new Button("", new FontIcon(MaterialDesignB.BACKSPACE));
                    b.getStyleClass().addAll(Styles.BUTTON_ICON);
                } else {
                    b = new Button(s);
                }
                b.setAlignment(Pos.CENTER);
                rowBox.getChildren().add(b);
            }
            result.add(rowBox, 0, i);
        }

        result.setAlignment(Pos.CENTER);
        return result;
    }
}
