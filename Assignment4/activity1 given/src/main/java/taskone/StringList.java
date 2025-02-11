package taskone;

import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    /**
     * Check to see if the string exist in a current position in the list of string
     * if it does not it return -1
     * if it does not it  return a postion 0 and above
     * @param str
     */
    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public String toString() {
        return strings.toString();
    }

    public List<String> getStrings() {
        return strings;
    }
}