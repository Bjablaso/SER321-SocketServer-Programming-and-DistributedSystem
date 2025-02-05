package travelaround.aroundtheworld;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

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

    @FXML
    public void initialize() {
        if (labelCount1 != null) labelCount1.setText("0");
        if (labelCount2 != null) labelCount2.setText("0");
        if (totalHint != null) totalHint.setText("3");
    }

    public void updateLabels(int count1, int count2) {
        if (labelCount1 != null) labelCount1.setText(String.valueOf(count1));
        if (labelCount2 != null) labelCount2.setText(String.valueOf(count2));
    }

    public void updateCount1(int count1) {
        if (labelCount1 != null) {
            System.out.println("Updating labelCount1");
            labelCount1.setText(String.valueOf(count1));
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
            imageDisplay.getEngine().load(url);
        } else {
            System.out.println("Image display is not initialized.");
        }
    }
}
