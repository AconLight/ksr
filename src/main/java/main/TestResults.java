package main;

import utils.ExtractionMethod;

public class TestResults {
    // TODO add confusion matrix

    TestResults(int ngram, int initSizePerClass, int k, int numberOfKeywords, ExtractionMethod extractionMethod, double precision) {
        this.ngram = ngram;
        this.initSizePerClass = initSizePerClass;
        this.k = k;
        this.numberOfKeywords = numberOfKeywords;
        this.extractionMethod = extractionMethod;
        this.precision = precision;
    }

    private int ngram;
    private int initSizePerClass;
    private int k;
    private int numberOfKeywords;
    private double precision;
    private ExtractionMethod extractionMethod;

    @Override
    public String toString() {
        return "{" +
                "ngram:" + ngram +
                ", initSizePerClass:" + initSizePerClass +
                ", k:" + k +
                ", numberOfKeywords:" + numberOfKeywords +
                ", extractionMethod:" + extractionMethod +
                ", precision:" + precision +
                '}';
    }
}
