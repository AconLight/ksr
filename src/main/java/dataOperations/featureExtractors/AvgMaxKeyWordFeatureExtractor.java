package dataOperations.featureExtractors;

import classifiedObjects.Article;
import dataOperations.Feature;
import runner.RunnerConfig;
import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static config.Config.placesLabels;

public class AvgMaxKeyWordFeatureExtractor implements IFeatureExtractor<Article> {
    private Map<ExtractionMethod, Map<String, List<String>>> keyWords;

    private IWordSimilarity similarityMethod;
    private ExtractionMethod extractionMethod;

    @Override
    public IWordSimilarity similarityMethod(int i) {
        return RunnerConfig.wordSimilarities[i %  RunnerConfig.wordSimilarities.length];
    }

    @Override
    public ExtractionMethod extractionMethod(int i) {
        int j = i/ RunnerConfig.wordSimilarities.length;
        return ExtractionMethod.values()[j];
    }

    @Override
    public void setVariant(int i) {
        similarityMethod = similarityMethod(i);
        extractionMethod = extractionMethod(i);
    }

    @Override
    public List<Feature> extract(Article object) {
        List<Feature> features = new ArrayList<>();
        for (String label : placesLabels) {
            double value = avgMaxDistances(maxDistances(object.getTextWords(), keyWords.get(extractionMethod).get(label)));
            features.add(new Feature(value, "Avg max keywords {" + extractionMethod + similarityMethod + ", " + label + "}"));

        }
        return features;
    }

    private Double avgMaxDistances(List<Double> maxDistances) {
        return maxDistances.stream().mapToDouble(Double::doubleValue).sum() / maxDistances.size();
    }

    private List<Double> maxDistances(List<String> words, List<String> keyWords) {
        List<Double> maxSimilarities = new ArrayList<>();
        for (String word : words) {
            double maxSimilarity = 0;
            for (String keyword : keyWords) {
                double similarity = this.similarityMethod.measure(word, keyword);
                if (similarity == 1) {
                    maxSimilarities.add(similarity);
                    continue;
                }
                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                }
            }
            maxSimilarities.add(maxSimilarity);
        }
        return maxSimilarities;
    }


    @Override
    public void setData(ExtractorData data) {
        this.keyWords = data.keyWords;
    }


}
