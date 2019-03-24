package dataOperations;

import java.util.HashMap;

public class ClassifiedObject {
    private String label;

    public String getLabel() {
        return label;
    }

    public String title;
    public String body;
    public String[] places;

    public ClassifiedObject(HashMap<String, String> data) {
        title = data.get("TITLE");
        body = data.get("BODY");
        places = data.get("PLACES").split(" ");
    }
}
