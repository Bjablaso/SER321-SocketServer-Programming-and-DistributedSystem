package enitityfolder;

import org.json.JSONObject;


public class Message {
    private final int id;
    private final long timestamp;
    private final JSONObject messageContent;

    public Message(int id, long timestamp, JSONObject messageContent) {
        this.id = id;
        this.timestamp = timestamp;
        this.messageContent = messageContent;
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public JSONObject getMessageContent() {
        return messageContent;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("timestamp", timestamp);
        obj.put("message", messageContent);
        return obj;
    }
}

