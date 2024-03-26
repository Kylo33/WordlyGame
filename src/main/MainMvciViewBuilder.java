package main;

import javafx.scene.layout.Region;
import javafx.util.Builder;

public class MainMvciViewBuilder implements Builder<Region> {
    private final MainMvciModel model;

    public MainMvciViewBuilder(MainMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        return new Region();
    }
}