package Entity;

import Control.Leader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    private String id;
    private int sum;
    private int checksum = 0;
    private int runningState;
    private Map<Integer, List<Integer>> historyCache;
    private Map<Integer, Integer>  finalSum;

    private long delay = 0;
    boolean fault = false;
    boolean dataConsensus = false;
//    boolean leaderConsensus = false;


//    private Map<Node, Integer> otherResult; // checksum property

    public Node(String id) {
        this.id = id;
        this.sum = 0;
        this.checksum = 0;
        this.runningState = 0;
        this.historyCache = new HashMap<>();
        this.finalSum = new HashMap<>();
    }

    public void datahistory(Integer term, List<Integer> data) {
        historyCache.put(term, data);
    }

    public void finalSum(Integer term, Integer sum) {
        finalSum.put(term, sum);

    }

    public List<Integer> finddatahistory(Integer term) {
        return historyCache.get(term);
    }

    public String getId() {
        return id;
    }
}
