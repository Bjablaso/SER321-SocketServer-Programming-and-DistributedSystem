package example.grpcclient;

import java.time.LocalDateTime;

public class WeightHistory {
    private double weight;
    private LocalDateTime currentDateTime;

    public WeightHistory(double weight) {
        this.weight = weight;
        this.currentDateTime = LocalDateTime.now();
    }

    public double getWeight() {
        return weight;
    }
    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }
}
