package enitityfolder;

import AppLogic.countDown;
import travelaround.aroundtheworld.ViewSwitcher;
import travelaround.aroundtheworld.WinScreenController;

public class Player {
    private static Player instance;
    private int id;
    private int point;
    private int timeComplete;


    private Player() {
        this.id = 0;  // Default ID
        this.point = 0;  // Default Points
        this.timeComplete = 0;  // Default Time Completion

    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public int getPoint() {
        return point;
    }

    public int getTimeComplete() {
        return timeComplete;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setTimeComplete(int timeComplete) {
        this.timeComplete = timeComplete;
    }

    public void calculateScore() {

        if (timeComplete > 15) {
            point += PointScale.MAXPOINT.getValue();



        } else if (timeComplete > 0 && timeComplete <= 15) {
            point += PointScale.MINPOINT.getValue();

        } else {
            point += PointScale.NOPOINT.getValue();

        }
    }

    public void restScore(){
        point = 0;
    }


}
