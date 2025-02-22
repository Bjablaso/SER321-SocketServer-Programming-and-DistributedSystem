package Control;

import Entity.Node;

import java.util.List;
import java.util.Map;

public class Leader implements Runnable{
    private String leaderId;                     // Unique identifier for the leader
    private List<Node> activeNodes;              // List of active nodes
    private Map<Node, int[]> assignedTasks;      // Tracks which node gets which data
    private Map<Node, Integer> pendingResults;   // Stores partial sums from nodes
    private Map<Node, Boolean> consensusResults; // Stores consensus verification results
    private long heartbeatInterval = 5000;  // Check every 5 seconds
    private long timeoutThreshold = 10000; // Mark as lost if no response in 10 sec
    private boolean faultToleranceEnabled;       // Whether fault handling is enabled

    // Constructor
    public Leader(String leaderId, List<Node> activeNodes,  boolean faultToleranceEnabled) {
        this.leaderId = leaderId;
        this.activeNodes = activeNodes;
        this.assignedTasks = new java.util.HashMap<>();
        this.pendingResults = new java.util.HashMap<>();
        this.consensusResults = new java.util.HashMap<>();
        this.faultToleranceEnabled = faultToleranceEnabled;
    }
    @Override
    public void run() {

    }
}
