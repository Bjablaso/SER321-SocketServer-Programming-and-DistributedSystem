package enitityfolder;

public class MessageCounter {
    private int totalMessage;


    public MessageCounter() {
        this.totalMessage = 0;

    }

    public void incrementTotalMessages() {
        totalMessage++;
    }


    public int getTotalMessages() {
        return totalMessage;
    }
}

