package dataOperations.featureExtractors;

import classifiedObjects.Article;
import dataOperations.Feature;
import runner.RunnerConfig;
import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static config.Config.placesLabels;

public class FrequencyKeyWordExtractor implements IFeatureExtractor<Article> {
    private Map<ExtractionMethod, Map<String, List<String>>> keyWords;

    public IWordSimilarity similarityMethod;
    public ExtractionMethod extractionMethod;

    public void setN(int i) {
        N = RunnerConfig.freqN[i];
    }

    public int N;

    @Override
    public void setM(ExtractionMethod m) {
        this.extractionMethod = m;
    }

    @Override
    public void setW(IWordSimilarity w) {
        this.similarityMethod = w;
    }

    @Override
    public ExtractionMethod getM() {
        return extractionMethod;
    }

    @Override
    public IWordSimilarity getW() {
        return similarityMethod;
    }

    @Override
    public void setVariant(int i) {
        setN(i);
    }

    @Override
    public List<Feature> extract(Article object) {
        List<Feature> features = new ArrayList<>();
        for (String label : placesLabels) {
            if (keyWords.get(extractionMethod).get(label) == null) continue;
            double value = calcFreq(object.getTextWords(), keyWords.get(extractionMethod).get(label));
            features.add(new Feature(value, "Frequency keywords (N: " + N + ") { label: "  + label + "}"));

        }
        return features;
    }
    Random rand = new Random();
    private double calcFreq(List<String> words, List<String> keyWords) {
        double similaritySum = 0;
        for (int i = 0; i < words.size(); i++) {

            for (String keyWord: keyWords) {
                for (int j = 1; j < N; j++) {
                    if (i + j < words.size()) {
                        if (this.similarityMethod.measure(words.get(i+j), keyWord) == 1.0)
                            similaritySum += 1f*j/N/10f;
                    }
                    if (i - j >= 0) {
                        if (this.similarityMethod.measure(words.get(i-j), keyWord) == 1.0)
                            similaritySum += 1f*j/N/10f;
                    }
                }


            }
        }

        return similaritySum;
    }


    @Override
    public void setData(ExtractorData data) {
        this.keyWords = data.keyWords;
    }


}
