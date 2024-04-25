package org.renn.main;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;

import atlantafx.base.theme.Styles;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class MainMvciViewBuilder implements Builder<Region> {
    private final MainMvciModel model;

    public MainMvciViewBuilder(MainMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        StackPane result = new StackPane();
        result.getChildren().addAll(createHomeScreen(), getGameScreen());
        return result;
    }

    public Region getGameScreen() {
        Region result = model.getGameMvciController().getView();
        result.visibleProperty().bind(model.gameScreenVisibleProperty());
        return result;
    }

    private Region createHomeScreen() {
        BorderPane result = new BorderPane();
        result.visibleProperty().bind(model.gameScreenVisibleProperty().not());
        
        Button classicButton = new Button("", new FontIcon(MaterialDesignC.CONTROLLER_CLASSIC));
        classicButton.getStyleClass().addAll(Styles.BUTTON_ICON);
        Button unlimitedButton = new Button("", new FontIcon(MaterialDesignI.INFINITY));
        unlimitedButton.getStyleClass().addAll(Styles.BUTTON_ICON);

        classicButton.setOnAction((event) -> {
            model.setGameMode(GameMode.CLASSIC);
            model.setGameScreenVisible(true);
        });

        unlimitedButton.setOnAction((event) -> {
            model.setGameMode(GameMode.UNLIMITED);
            model.setGameScreenVisible(true);
        });

        classicButton.setDefaultButton(true);

        Label title = new Label("WordlyGame");
        title.getStyleClass().addAll(Styles.TITLE_1);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setVgap(5);
        buttonGrid.setHgap(5);
        buttonGrid.add(new Label("Play Classic"), 0, 0);
        buttonGrid.add(new Label("Play Unlimited"), 0, 1);
        buttonGrid.add(classicButton, 1, 0);
        buttonGrid.add(unlimitedButton, 1, 1);
        buttonGrid.setAlignment(Pos.CENTER);

        VBox resultBox = new VBox(10, title, buttonGrid);
        resultBox.setAlignment(Pos.CENTER);

        result.setCenter(resultBox);
        return result;
    }
}