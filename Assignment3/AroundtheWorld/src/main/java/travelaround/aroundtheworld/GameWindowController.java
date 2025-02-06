package travelaround.aroundtheworld;


import AppLogic.LoadData;
import AppLogic.countDown;
import enitityfolder.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import org.json.JSONObject;

import java.io.IOException;

public class GameWindowController {
    @FXML private Label labelCount1;
    @FXML private Label labelCount2;
    @FXML private Label answerKey1;
    @FXML private Label answerKey2;
    @FXML private Label answerKey3;
    @FXML private Label hint1;
    @FXML private Label hint2;
    @FXML private Label hint3;
    @FXML private WebView imageDisplay;
    @FXML private Label totalHint;

    private LoadData loadData;
    private Player player;
    private int remainingTime;
    private countDown countdown;



    @FXML
    public void initialize() {
        player = Player.getInstance();
        countdown = countDown.getInstance();
        loadData = LoadData.getInstance();


        if (labelCount1 != null) labelCount1.setText("0");
        if (labelCount2 != null) labelCount2.setText("0");
        if (totalHint != null) totalHint.setText("3");

        answerKey1.setOnMouseClicked(this::handleAnswerSelection);
        answerKey2.setOnMouseClicked(this::handleAnswerSelection);
        answerKey3.setOnMouseClicked(this::handleAnswerSelection);

        hint1.setVisible(false);
        hint2.setVisible(false);
        hint3.setVisible(false);

    }


    public void updateLabels(int count1, int count2) {
        if (labelCount1 != null) labelCount1.setText(String.valueOf(count1));
        if (labelCount2 != null) labelCount2.setText(String.valueOf(count2));



        if(labelCount1.getText().equals("0") && labelCount2.getText().equals("0")) {
            ViewSwitcher.popUpWindow();
        }
    }

    public void updateCount1(int count1) {
        if (labelCount1 != null) {
            System.out.println("Updating labelCount1");
            labelCount1.setText(String.valueOf(count1));
        }
    }

    @FXML
    public void showhit(MouseEvent event){

        if(totalHint.getText().equals("3")){
            hint1.setVisible(true);
            totalHint.setText("2");
        }else if (totalHint.getText().equals("2")){
            hint2.setVisible(true);
            totalHint.setText("1");
        }else if (totalHint.getText().equals("1")){
            hint3.setVisible(true);
            totalHint.setText("0");
        }
        ViewSwitcher.popUpWindow();

    }

    @FXML
    private void handleAnswerSelection(MouseEvent event) {
        Label selectedLabel = (Label) event.getSource();
        String selectedAnswer = selectedLabel.getText();


        if (loadData.checkAnswer(selectedAnswer)) {
            player.setTimeComplete(remainingTime);
            player.calculateScore();

            System.out.println("Correct Answer! Score: " + player.getPoint());
            countdown.StopCountdown();
           ViewSwitcher.popUpWindow();
           // Platform.runLater(this::popUpWindow);
        } else {
            System.out.println("Incorrect Answer! Try Again.");
        }

    }

    public void updateCount2(int count2) {
        if (labelCount2 != null) {
            labelCount2.setText(String.valueOf(count2));

        }
    }

    public void setHint1(String hintText) {
        if (hint1 != null) hint1.setText(hintText);
    }

    public void setHint2(String hintText) {
        if (hint2 != null) hint2.setText(hintText);
    }

    public void setHint3(String hintText) {
        if (hint3 != null) hint3.setText(hintText);
    }

    public void setAnswerKey1(String answer) {
        if (answerKey1 != null) answerKey1.setText(answer);
    }

    public void setAnswerKey2(String answer) {
        if (answerKey2 != null) answerKey2.setText(answer);
    }

    public void setAnswerKey3(String answer) {
        if (answerKey3 != null) answerKey3.setText(answer);
    }

    public void setImageDisplay(String url) {
        if (imageDisplay != null) {
            Platform.runLater(() -> imageDisplay.getEngine().load(url));
        } else {
            System.out.println("Image display is not initialized.");
        }
    }

    public void setLoadData(LoadData loader) {
        this.loadData = loader;
    }


}
