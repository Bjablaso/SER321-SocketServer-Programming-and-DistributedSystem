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

    public Customer(String name, double initialWeight) {
        this.name = name;
        this.currentWeight = initialWeight;
        this.currentTime = LocalDateTime.now();
        this.weightHistory = new ArrayList<>();
        // Add the initial weight to history
        this.weightHistory.add(new WeightHistory(initialWeight));
    }

    // Default constructor if needed
    public Customer(String name) {
        this(name, 0.0);
    }



    public Customer updateCurrentWeight(double newCurrentWeight) {

        this.currentWeight = newCurrentWeight; // keep current weight updated
        this.currentTime = LocalDateTime.now();
        addCurrentWeight(this.currentWeight);// track weight changes

        return this;
    }

    public Customer addCurrentWeight(double weight) {
        this.weightHistory.add(new WeightHistory(weight));
        return this;
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
        if(weightHistory.isEmpty()){
            return null;
        }
        return weightHistory;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
