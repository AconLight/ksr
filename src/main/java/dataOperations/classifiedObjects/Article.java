package dataOperations.classifiedObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Article extends ClassifiedObject {
    public String text;
    public String title;
    private ArrayList<String> textWords;

    public Article(HashMap<String, String> data, String label) {
        super(label);
        text = data.get("BODY");
        title = data.get("TITLE");
        textWords = (ArrayList<String>) Arrays.asList(text.split("\\s+"));
    }

    public String getText() {
        return text;
    }
}
