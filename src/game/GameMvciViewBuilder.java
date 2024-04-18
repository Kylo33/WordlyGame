package game;

import java.util.function.Consumer;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignB;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;
import org.kordamp.ikonli.materialdesign2.MaterialDesignK;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import atlantafx.base.controls.Message;
import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import domain.Guess;
import domain.LetterState;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.util.Builder;
import javafx.util.Duration;

public class GameMvciViewBuilder implements Builder<Region> {

    private final GameMvciModel model;
    private final Consumer<Character> addLetterAction;
    private final Runnable enterAction;
    private final Runnable backspaceAction;

    public GameMvciViewBuilder(GameMvciModel model, Consumer<Character> addLetterAction, Runnable enterAction, Runnable backspaceAction) {
        this.model = model;
        this.addLetterAction = addLetterAction;
        this.enterAction = enterAction;
        this.backspaceAction = backspaceAction;
    }

    @Override
    public Region build() {
        BorderPane borderPane = new BorderPane();

        Region backButton = createPostGameButtons();
        BorderPane.setAlignment(backButton, Pos.TOP_RIGHT);
        BorderPane.setMargin(backButton, new Insets(5));
        borderPane.setTop(backButton);
        
        Region board = createBoard();
        Region revealMessage = createAnswerMessage();
        revealMessage.minWidthProperty().bind(board.widthProperty());
        Region successMessage = createSuccessMessage();
        successMessage.minWidthProperty().bind(board.widthProperty());
        VBox centerBox = new VBox(10, encapsulateInHBox(revealMessage), encapsulateInHBox(successMessage), encapsulateInHBox(board));
        centerBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(centerBox);

        Region keyboardDisplay = createKeyboardDisplay();
        borderPane.setBottom(keyboardDisplay);
        BorderPane.setMargin(keyboardDisplay, new Insets(5));

        StackPane result = new StackPane(borderPane);

        addKeyboardEvents(result);
        addPopups(result);

        return result;
    }

    private Node encapsulateInHBox(Region region) {
        HBox result = new HBox(region);
        result.setAlignment(Pos.CENTER);
        result.visibleProperty().bind(region.visibleProperty());
        return result;
    }

    private Region createAnswerMessage() {
        Message message = new Message();
        message.setTitle("Answer");
        message.descriptionProperty().bind(model.correctWordProperty());
        message.setGraphic(new FontIcon(MaterialDesignL.LIGHTBULB_ON_OUTLINE));
        
        message.getStyleClass().addAll(Styles.ACCENT);
        message.visibleProperty().bind(Bindings.createBooleanBinding(() -> model.gameIsOver() && model.getCurrentGuessIndex() > 0 && !lastGuessWasCorrect(), model.gameOverProperty()));
        return message;
    }

    private Region createSuccessMessage() {
        Message message = new Message();
        message.setTitle("Correct!");
        message.setGraphic(new FontIcon(MaterialDesignP.PARTY_POPPER));
        
        message.getStyleClass().addAll(Styles.SUCCESS);
        message.visibleProperty().bind(Bindings.createBooleanBinding(() -> model.gameIsOver() && model.getCurrentGuessIndex() > 0 && lastGuessWasCorrect(), model.gameOverProperty()));
        return message;
    }

    private boolean lastGuessWasCorrect() {
        return model.getGuesses().get(model.getCurrentGuessIndex() - 1).getLetterStates().stream().allMatch(ls -> ls == LetterState.GREEN);
    }

    private Region createPostGameButtons() {
        Button backButton = new Button("", new FontIcon(MaterialDesignE.EXIT_TO_APP));
        backButton.getStyleClass().add(Styles.BUTTON_ICON);
        backButton.visibleProperty().bind(model.gameOverProperty());
        backButton.setFocusTraversable(false);

        backButton.setOnAction((event) -> model.setGameScreenVisible(false));

        Button statsButton = new Button("", new FontIcon(MaterialDesignC.CHART_BAR));
        statsButton.getStyleClass().add(Styles.BUTTON_ICON);
        BorderPane.setAlignment(statsButton, Pos.TOP_RIGHT);
        statsButton.visibleProperty().bind(model.gameOverProperty());
        statsButton.setFocusTraversable(false);

        HBox result = new HBox(5, backButton, statsButton);
        result.setAlignment(Pos.CENTER_RIGHT);
        return result;
    }

    private void addPopups(StackPane result) {
        model.getNotificationSystem().addMessageReciever(message -> {
            Notification notification = new Notification(message, new FontIcon(MaterialDesignA.ALERT_CIRCLE));
            notification.getStyleClass().addAll(Styles.ELEVATED_1, Styles.WARNING);
            notification.setPrefHeight(Region.USE_PREF_SIZE);
            notification.setMaxHeight(Region.USE_PREF_SIZE);

            StackPane.setAlignment(notification, Pos.TOP_CENTER);
            StackPane.setMargin(notification, new Insets(10));

            result.getChildren().add(notification);
            SequentialTransition seqTransition = new SequentialTransition (
                Animations.fadeIn(notification, Duration.millis(100)),
                new PauseTransition(Duration.millis(1000)), // wait a second
                Animations.fadeOut(notification, Duration.millis(100))
            );
            seqTransition.setOnFinished(f -> result.getChildren().remove(notification));

            seqTransition.playFromStart();
        });
    }

    private void addKeyboardEvents(Parent parent) {
        parent.visibleProperty().addListener((observable, wasVisible, isVisible) -> {if (isVisible) parent.requestFocus();});
        parent.addEventFilter(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode().compareTo(KeyCode.ENTER) == 0) {
                key.consume();
                enterAction.run();
            } else if (key.getCode().compareTo(KeyCode.BACK_SPACE) == 0) {
                key.consume();
                backspaceAction.run();
            } else {
                key.consume();
                char c = key.getCode().getChar().charAt(0);
                if (Character.isLetter(c)) {
                    addLetterAction.accept(c);
                }
            }
        });
    }

    private Region createBoard() {
        GridPane result = new GridPane();
        result.setHgap(5);
        result.setVgap(5);

        model.getGuesses().addListener((ListChangeListener<? super Guess>) change -> {
            while (change.next()) {
                fillBoard(result);
            }
        });

        result.setAlignment(Pos.CENTER);
        return result;
    }

    private void fillBoard(GridPane result) {
        result.getChildren().clear();

        for (int i = 0; i < model.getGuesses().size(); i++) {
            for (int j = 0; j < 5; j++) {
                Label box = new Label(String.valueOf(model.getGuesses().get(i).getCharacters().get(j)));
                box.getStyleClass().add(Styles.TITLE_1);

                String color = String.valueOf(model.getGuesses().get(i).getLetterStates().get(j));

                box.setStyle("-fx-background-color:" + color + "; -fx-text-fill: white;");
                box.setPrefSize(50, 50);
                box.setAlignment(Pos.CENTER);
                result.add(box, j, i);
            }
        }
    }

    private Region createKeyboardDisplay() {
        GridPane result = new GridPane();
        result.setVgap(3);

        for (int i = 0; i < GameMvciModel.KEYBOARD_CHARS.length; i++) {
            HBox rowBox = new HBox(3);
            rowBox.setAlignment(Pos.CENTER);
            for (char c: GameMvciModel.KEYBOARD_CHARS[i]) {
                Button b = new Button(String.valueOf(c));
                b.setAlignment(Pos.CENTER);
                b.setOnAction((event) -> {
                    addLetterAction.accept(c);
                });
                rowBox.getChildren().add(b);
                
                ObjectProperty<LetterState> state = model.getKeyboardStateMap().get(c);
                b.setStyle("-fx-background-color:" + state.get() + ";-fx-font-weight: bold;");
                state.addListener((observable, oldLetterState, newLetterState) -> {
                    StringBuilder styleString = new StringBuilder("-fx-background-color:");
                    styleString.append(newLetterState.toString());
                    styleString.append("; -fx-text-fill: ");
                    styleString.append(newLetterState == LetterState.WHITE ? "black" : "white");
                    styleString.append("; -fx-font-weight: bold;");
                    b.setStyle(styleString.toString());
                });

                b.setFocusTraversable(false);
            }

            // Add ENTER and DELETE buttons to last row
            if (i == GameMvciModel.KEYBOARD_CHARS.length - 1) {
                Button backspace = new Button("", new FontIcon(MaterialDesignB.BACKSPACE));
                backspace.setOnAction(event -> backspaceAction.run());
                Button enter = new Button("", new FontIcon(MaterialDesignK.KEYBOARD_RETURN));
                enter.setOnAction(event -> enterAction.run());
                rowBox.getChildren().add(0, backspace);
                rowBox.getChildren().add(enter);
                enter.setFocusTraversable(false);
                backspace.setFocusTraversable(false);
                enter.setStyle("-fx-background-color:" + LetterState.WHITE + ";-fx-font-weight: bold;");
                backspace.setStyle("-fx-background-color:" + LetterState.WHITE + ";-fx-font-weight: bold;");
            }
            result.add(rowBox, 0, i);
        }

        result.setAlignment(Pos.CENTER);
        return result;
    }
}
