package dataOperations.preprocessing;

public class Preprocessing {
    static String cleanse(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^\\S ]+g", " ");

//        text = text.replaceAll("/[^a-z0-9+]/g", "");

        return text;
    }
}
