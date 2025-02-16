package Entity;

/**
 * Class: Player 
 * Description: Class that represents a Player, I only used it in my Client 
 * to sort the LeaderBoard list
 * You can change this class, decide to use it or not to use it, up to you.
 */

public class Player {

    private final int wins;
    private final String name;
    private int point;

    // constructor, getters, setters
    public Player(String name, int wins) {
        this.wins = wins;
        this.name = name;
        this.point = 0;
    }

    public int getWins() {
        return wins;
    }



    public void addScore(int score) {
        this.point += score;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}