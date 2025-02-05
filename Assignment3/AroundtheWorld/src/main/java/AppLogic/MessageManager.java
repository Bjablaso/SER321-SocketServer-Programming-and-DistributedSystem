package AppLogic;

import enitityfolder.Message;
import enitityfolder.MessageCounter;
import org.json.JSONObject;

import java.util.HashMap;

public class MessageManager {
    private boolean isFirstMessage = true;
    MessageCounter messageCounter;
    private HashMap<Integer, Node> messageMap;
    long timestamp;
    private Node head, tail;
    private int  messageID;

    public MessageManager() {
        this.messageCounter = new MessageCounter();
        this.messageMap = new HashMap<>();
        this.head = this.tail = null;
        this.messageID = 0;
        if (messageCounter.getTotalMessages() > 0) {
            isFirstMessage = false; // Only the first message triggers this
        }

    }

    public void put( JSONObject message) {
        messageCounter.incrementTotalMessages();
         messageID =  messageCounter.getTotalMessages();
        timestamp = System.currentTimeMillis();

        Message newMeggae = new Message(messageID, timestamp, message);

        Node newNode = new Node(newMeggae);

        if(head == null){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        messageMap.put(messageID, newNode);
    }


    public Message getID(int id) {
        return messageMap.containsKey(id) ? messageMap.get(id).message : null;
    }

    public Message getNextMessage(int id) {
        Node node = messageMap.get(id);
        return (node != null && node.next != null) ? node.next.message : null;
    }

    public Message getPrevMessage(int id) {
        Node node = messageMap.get(id);
        return (node != null && node.next != null) ? node.prev.message : null;
    }

    public int getTotalMessages() {
        return messageCounter.getTotalMessages();
    }

    public boolean isFirstMessage() {
        return isFirstMessage;
    }

    public int getMessageID() {
        return messageID;
    }

    private class Node{
        Message message;
        Node next;
        Node prev;
        private Node(Message message){
            this.message = message;
            this.next = null;
            this.prev = null;
        }




    }

}
