package Entity;

import java.util.Map;

public class Node {

    private String id;
    private int sum;
    private int checksum = 0;
    private int runningState;
    private int[] data;
    private long delay;
    boolean fault = false;
    boolean dataConsensus = false;
//    boolean leaderConsensus = false;


    private Map<Node, Integer> otherResult; // checksum property

    public Node( String id, int[] data, long delay) {
        this.data = data;
        this.delay = delay;
        this.id = id;
        this.sum = 0;
        this.checksum = 0;
        this.runningState = 0;
    }


    public int getSum() {
        return sum;
    }

    public int[] getData() {
        return data;
    }

    public long getDelay() {
        return delay;
    }

    public boolean isFault() {
        return fault;
    }

    public boolean isDataConsensus() {
        return dataConsensus;
    }

    public String getId() {
        return id;
    }

}
