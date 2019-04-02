package runner;

import dataOperations.featureExtractors.IFeatureExtractor;
import dataOperations.featureExtractors.SentimentExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureExtractorsConfig {
    public List<IFeatureExtractor> extractors;
    public List<List<Integer>> variantsList;

    public FeatureExtractorsConfig() {
        extractors = new ArrayList<>();
        variantsList = new ArrayList<>();
    }

    public static FeatureExtractorsConfig fec1() {
        FeatureExtractorsConfig fec = new FeatureExtractorsConfig();
        fec.extractors.add(new SentimentExtractor());
        fec.extractors.add(new SentimentExtractor()); // doubling for testing purposes
        fec.variantsList.add(Arrays.asList(0, 0));
        fec.variantsList.add(Arrays.asList(1, 2));
        fec.variantsList.add(Arrays.asList(0, 4));
        return fec;
    }

    public static FeatureExtractorsConfig fec2() {
        FeatureExtractorsConfig fec = new FeatureExtractorsConfig();
        fec.extractors.add(new SentimentExtractor());
        fec.extractors.add(new SentimentExtractor()); // doubling for testing purposes
        fec.extractors.add(new SentimentExtractor()); // same
        fec.variantsList.add(Arrays.asList(0, 0, 0));
        fec.variantsList.add(Arrays.asList(2, 4, 4));
        return fec;
    }

}
