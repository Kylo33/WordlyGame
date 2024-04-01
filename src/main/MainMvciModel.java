package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MainMvciModel {
    private final BooleanProperty boardScreenVisible = new SimpleBooleanProperty();
    private final BooleanProperty menuScreenVisible = new SimpleBooleanProperty();

    public BooleanProperty boardScreenVisibleProperty() {
        return boardScreenVisible;
    }
    public BooleanProperty menuScreenVisibleProperty() {
        return menuScreenVisible;
    }
}
