package dataOperations.featureExtractors;

import classifiedObjects.Article;
import dataOperations.Feature;
import utils.ExtractionMethod;

import java.util.List;
import java.util.Map;

public class KeyWordFeatureExtractor implements IFeatureExtractor<Article> {
    private Map<ExtractionMethod, Map<String, List<String>>> keyWords;

    public KeyWordFeatureExtractor() {
    }

    @Override
    public void setVariant(int i) {

    }

    @Override
    public List<Feature> extract(Article object) {

    }

    private Double findMaxDistance(String word, List<String> dataSet) {

    }

}
