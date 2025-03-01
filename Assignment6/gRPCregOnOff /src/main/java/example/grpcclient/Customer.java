package example.grpcclient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private double height;
    private double currentWeight;
    private double currentBMI;
//    private List<BMIHistory> historyWeight;
    private List<WeightHistory> weightHistory;
    private LocalDateTime currentTime;

    public Customer(String name, double currentWeight) {
        this.name = name;
        this.currentWeight = currentWeight;
        double currentBMI = 0.0;
        double height = 0.0;

        this.weightHistory = new ArrayList<>();
        currentBMI = 0;
    }



    public Customer updateCurrentWeight(double newCurrentWeight) {
        this.currentWeight = newCurrentWeight; // keep current weight updated
        this.currentTime = LocalDateTime.now();

        weightChangeTracker(this.currentWeight);// track weight changes

        return this;
    }

    public void weightChangeTracker(double weight) {
        this.weightHistory.add(new WeightHistory(weight));
    }

    public double getCurrentBMI() {
        return currentBMI;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public List<WeightHistory> getWeightHistory() {
        return weightHistory;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
