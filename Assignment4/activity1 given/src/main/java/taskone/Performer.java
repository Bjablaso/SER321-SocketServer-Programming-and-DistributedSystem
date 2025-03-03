/**
  File: Performer.java
  Author: Student in Fall 2020B
  Description: Performer class in package taskone.
*/

package taskone;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

/**
 * Class: Performer 
 * Description: Threaded Performer for server tasks.
 */
class Performer {

    private StringList state;


    public Performer(StringList strings) {

        this.state = strings;
    }

    public JSONObject add(String str) throws InterruptedException {
        System.out.println("Start add"); 
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "add");
        sleep(6000); // to make this take a bit longer
        state.add(str);
        json.put("data", state.toString());
        System.out.println("end add");
        return json;
    }

    public  JSONObject error(String err) {
        JSONObject json = new JSONObject();
        json.put("type", "error");
        json.put("message", err);
        return json;
    }

    public JSONObject display(int index){
        JSONObject json = new JSONObject();
        if (index < 0 || index >= state.size()) {
            return error("Index out of bounds.");
        }
        try {
            List<String> str = state.getStrings();
            String result = str.get(index);
            json.put("type", "display");
            json.put("data", result);
        } catch (Exception e) {
            return error("Failed to display string: " + e.getMessage());
        }
        return  json;
    }

    public JSONObject count(){
        JSONObject json = new JSONObject();
        try {
            int numberOfStrings = state.size();
            String total = String.valueOf(numberOfStrings);
            json.put("type", "count");
            json.put("data", total);
        } catch (Exception e) {
            return error("Failed to count strings: " + e.getMessage());
        }
        return json;
    }


    public JSONObject quite(){
        JSONObject json = new JSONObject();
        json.put("type", "quite");
        json.put("Ok", true);
        json.put("data", "Server disconnecting..");

        return json;
    }



}
