package Entity;

import Control.Leader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Node {
    private String id;
    private Map<Integer, List<Integer>> historyCache;
    private Map<Integer, Integer> finalSum;
    private boolean isfualty = false ;

    public Node(String id) {
        this.id = id;
        this.historyCache = new HashMap<>();
        this.finalSum = new HashMap<>();
    }

    public void dataHistory(Integer term, List<Integer> data) {
        historyCache.put(term, data);
    }

    public void finalSum(Integer term, Integer sum) {
        finalSum.put(term, sum);
    }

    public List<Integer> findDataHistory(Integer term) {
        return historyCache.get(term);
    }

    public String getId() {
        return id;
    }

    public void setIsfualty(boolean isfualty) {
        this.isfualty = isfualty;
    }

    public boolean isIsfualty() {
        return isfualty;
    }
}