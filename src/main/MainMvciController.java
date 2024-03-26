package main;

import javafx.scene.layout.Region;

public class MainMvciController {
    private final MainMvciModel model;
    private final MainMvciViewBuilder viewBuilder;
    private final MainMvciInteractor interactor;

    public MainMvciController() {
        this.model = new MainMvciModel();
        this.viewBuilder = new MainMvciViewBuilder(model);
        this.interactor = new MainMvciInteractor(model);
    }

    public Region getView() {
        return this.viewBuilder.build();
    }
}
