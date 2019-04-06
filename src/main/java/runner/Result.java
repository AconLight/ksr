package runner;

public class Result {
    public String dataSets;
    public String preprocessing;
    public String keyWords;
    public String extractorsWithVariants;
    public String wordSimilarity;
    public String knn;
    public String originalLabel;
    public String metric;
    public String k;

    public String toString() {
        return "dataSet\n" + dataSets + "\n" +
                "preprocessing\n" + preprocessing + "\n" +
                "keyWords\n" + keyWords + "\n" +
                "extractorsWithVariants\n" + extractorsWithVariants + "\n" +
                "wordSimilarity\n" + wordSimilarity + "\n" +
                "knn\n" + knn + "\n" +
                "k\n" + k + "\n" +
                "originalLabel\n" + originalLabel + "\n" +
                "metric\n" + metric + "\n";

    }
}

