package travelaround.aroundtheworld;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class WinScreenController {

    @FXML
    private StackPane winScreen;

    @FXML
    public void initialize() {
        // Create a rectangle with rounded corners
        Rectangle clip = new Rectangle();
        clip.setWidth(winScreen.getPrefWidth());
        clip.setHeight(winScreen.getPrefHeight());
        clip.setArcWidth(40); // Adjust for roundness
        clip.setArcHeight(40);

        // Set the clipping mask
        winScreen.setClip(clip);
    }
}







