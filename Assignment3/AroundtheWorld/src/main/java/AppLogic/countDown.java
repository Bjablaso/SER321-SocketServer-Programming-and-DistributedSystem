package AppLogic;

import enitityfolder.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import travelaround.aroundtheworld.GameWindowController;
import travelaround.aroundtheworld.ViewSwitcher;


public class countDown {
    private static countDown instance;
    static int initcount =  25;
    static int counter1;
    static int counter2;
    static int counterValue = 1;
    static int resetValue = 9;
    private static GameWindowController gameWindowController;
    static Timeline timeline;
    private String currentCount = " ";

    private int numberx;
    private int numbery;

    public countDown(){
        gameWindowController = ViewSwitcher.getGameWindowController();

        if (gameWindowController == null) {
            System.out.println("Error: GameWindowController is null. Ensure you switch to GAMEPLAYVIEW first.");
            return;
        }


        Integer integer = initcount;
       String number = integer.toString();
       numberx = (int) Integer.parseInt(String.valueOf(number.charAt(0)));
       numbery = (int) Integer.parseInt(String.valueOf(number.charAt(1)));
      counter1 = numberx;
      counter2 = numbery;
        System.out.println("incount down method - number being added to label is " + numberx);

        gameWindowController.updateLabels(counter1, counter2);


    }

    public static countDown getInstance() {
        if (instance == null) {
            instance = new countDown();
        }
        return instance;
    }

    public  void startCountdown() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            updateCountdown();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private   void updateCountdown() {
        if (counter2 > 0) {
            counter2 -= counterValue;
          currentCount = String.valueOf(counter2) + String.valueOf(counterValue);

        } else if (counter1 > 0) {
            counter1--;
            counter2 = resetValue;
            currentCount = String.valueOf(counter2) + String.valueOf(counterValue);
        } else {
            timeline.stop();
        }

        // Ensure UI updates are done on the JavaFX thread
        Platform.runLater(() -> {
            gameWindowController.updateCount1(counter1);
            gameWindowController.updateCount2(counter2);
        });
    }

    public  void StopCountdown() {
        timeline.stop();
    }

    public void resetCountdown(){
        counter1 = numberx;
        counter2 = numbery;
        gameWindowController.updateLabels(counter1, counter2);
    }


    public String getCurrentCount() {
        return currentCount;
    }
}
