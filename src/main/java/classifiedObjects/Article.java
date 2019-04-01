package classifiedObjects;

import dataOperations.preprocessing.FullPreprocessor;
import dataOperations.preprocessing.IPreprocessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Article extends ClassifiedObject {
    private String text;
    private String title;
    private List<String> textWords;

    public Article(HashMap<String, String> data, String label, IPreprocessor<String> preprocessor) {
        this.label = label;
        text = preprocessor.process(data.get("BODY"));
        title = data.get("TITLE");
        textWords = Arrays.asList(text.split("\\s+"));
    }

    @Override
    public String toString() {
        return "Label:\t" + label + "\n title:\t" + title + "\n text:\t" + text;
    }

    public String getText() {
        return text;
    }

    public List<String> getTextWords() {
        return textWords;
    }
}
