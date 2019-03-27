package classifiedObjects;

import java.util.HashMap;

public class Article extends ClassifiedObject {
    private String text;
    private String title;
    private String[] textWords;

    public Article(HashMap<String, String> data, String label) {
        this.label = label;
        text = data.get("BODY");
        title = data.get("TITLE");
        textWords = text.split("\\s+");
    }

    @Override
    public String toString() {
        return "Label:\t" + label + "\n title:\t" + title + "\n text:\t" + text;
    }

    public String getText() {
        return text;
    }
}
