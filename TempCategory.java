import java.io.Serializable;
import java.util.ArrayList;

public class TempCategory implements Serializable {

    private String name;
    private ArrayList<String> keys;

    public TempCategory(String name) {
        this.name = name;
        keys = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void addKey(String key) {
        keys.add(key);
    }

    public String[] getKeys() {
        return keys.toArray(new String[keys.size()]);
    }
}
