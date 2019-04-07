package main;

import metrics.IMetric;
import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

public class TestResults {
    // TODO add confusion matrix

    TestResults(int initSizePerClass, int k, int numberOfKeywords, ExtractionMethod extractionMethod, String matrix) {
        this.ngram = ngram;
        this.initSizePerClass = initSizePerClass;
        this.k = k;
        this.numberOfKeywords = numberOfKeywords;
        this.extractionMethod = extractionMethod;
        this.matrix = matrix;
    }

    private int ngram;
    private int initSizePerClass;
    private int k;
    private int numberOfKeywords;
    private String matrix;
    private ExtractionMethod extractionMethod;

    @Override
    public String toString() {
        return "{" +
                ", confusion matrix:" + matrix +
                '}';
    }
}
