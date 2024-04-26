/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.main;

import javafx.scene.layout.Region;

public class MainMvciController {
    private final MainMvciModel model;
    private final MainMvciViewBuilder viewBuilder;

    public MainMvciController() {
        this.model = new MainMvciModel();
        this.viewBuilder = new MainMvciViewBuilder(model);
    }

    public Region getView() {
        return this.viewBuilder.build();
    }
}
