package runner;

import classifiedObjects.Article;
import dataOperations.Feature;
import dataOperations.featureExtractors.IFeatureExtractor;

import java.util.*;

public class ExtractingFeatures extends Configurable {

    private List<List<List<Integer>>> featureCombinations;

    private List<List<ExtractingFeature>> extractingFeaturesList;


    private List<Integer> getI;
    private List<List<Integer>> getIVariant;

    private int currentI;

    public ExtractingFeatures(List<List<Article>> articlesList, int articlesId, List<List<Feature>> featuresList) {
        currentI = 0;
        range = 1;
        featureCombinations = new ArrayList<>();
        extractingFeaturesList = new ArrayList<>();
        List<ExtractingFeature> temp;
        List<List<Integer>> temp2;
        getI = new ArrayList<>();
        getIVariant = new ArrayList<>();
        for (int i = 0; i < RunnerConfig.featureExtractorsConfig.length; i++) {
            featureCombinations.add(temp2 = new ArrayList<>());
            extractingFeaturesList.add(temp = new ArrayList<>());
            for (IFeatureExtractor featureExtractor : RunnerConfig.featureExtractorsConfig[i].extractors) {
                temp.add(new ExtractingFeature(featureExtractor, articlesList, articlesId, featuresList));
            }
            for (List<Integer> variant : RunnerConfig.featureExtractorsConfig[i].variantsList) {
                temp2.add(variant);
                getI.add(i);
                getIVariant.add(variant);
            }
        }
        range = getI.size();
    }

    private void perform(int extractorListId, List<Integer> variant) {
        int i = 0;
        for (ExtractingFeature extractingFeatures: extractingFeaturesList.get(extractorListId)) {
            extractingFeatures.perform(variant.get(i));
            i++;
        }
    }

    @Override
    public void perform(int i) {
        System.out.println("performing " + i);
        perform(getI.get(i), getIVariant.get(i));
        System.out.println("performed " + i);
    }
}
