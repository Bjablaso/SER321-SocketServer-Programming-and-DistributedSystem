package travelaround.aroundtheworld;

import AppLogic.LoadData;
import AppLogic.countDown;
import enitityfolder.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class WinScreenController {

    @FXML
    private Button NextButton;

    @FXML
    private Label leavelOf;

    @FXML
    private Label leveloutOf;

    @FXML
    private Button quiteButton;

    @FXML
    private Button restartButton;

    @FXML
    private Label score;
    @FXML
    private Label scoreValue;

    @FXML
    private StackPane winScreen;
    LoadData loader;
    countDown timer;
    Player player;

    @FXML
    public void initialize() {
        loader = LoadData.getInstance();
        timer = countDown.getInstance();
        player = Player.getInstance();

        Rectangle clip = new Rectangle();
        clip.setWidth(winScreen.getPrefWidth());
        clip.setHeight(winScreen.getPrefHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);


        winScreen.setClip(clip);
    }

    @FXML
    public void nextButtonClicked() {
        winScreen.setVisible(false);
        var counter = new countDown();
        counter.resetCountdown();
        counter.startCountdown();
       loader.nextQuestion();

    }

    public void setScoreVakue() {
        this.scoreValue.setText(String.valueOf(player.getPoint()));
    }
}







