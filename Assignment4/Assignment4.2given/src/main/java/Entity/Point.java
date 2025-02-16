package Entity;

public enum Point {
    LEVELEASY(25),
    LEVELMEDIUM(50),
    LEVELHARD (100);

    private final int points;

     Point(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
