package dataOperations;

public class Preprocessing {
    public static String cleanse(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^\\S ]+g", " "); // white chars to spaces
        text = text.replaceAll("[^a-z., ]", ""); // remove non a-z . , and spaces
        text = text.replaceAll("  +", " "); // multiple spaces to single
        text = text.replaceAll("( (a-z) )", ""); // single characters surrounded by spaces

        return text;
    }

}
