package runner;

import classifiedObjects.Article;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.ExtractorData;
import dataOperations.featureExtractors.IFeatureExtractor;
import utils.ExtractionMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractingFeature extends Configurable {

    private IFeatureExtractor featureExtractor;
    private List<List<Article>> articlesList;
    private int articleId, articlesId;
    private List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public List<FeatureVector> featuresList;

    public ExtractingFeature(List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList, IFeatureExtractor featureExtractor, List<List<Article>> articlesList, int articleId, int articlesId, List<FeatureVector> featuresList) {
        this.articleId = articleId;
        this.articlesId = articlesId;
        this.featureExtractor = featureExtractor;
        this.articlesList = articlesList;
        this.featuresList = featuresList;
        this.keyWordsList = keyWordsList;
    }

    @Override
    public void perform(int i) {
        featureExtractor.setVariant(i);
        System.out.println("performing feature extraction variant: " + i);

        ExtractorData data = new ExtractorData();
        data.keyWords = keyWordsList.get(articlesId);
        featureExtractor.setData(data);

        int id = 0;
        if (featuresList.isEmpty()) {
            for (Article a : articlesList.get(articleId)) {
                featuresList.add(new FeatureVector(a.getLabel()));
            }
        }
        for (Article a : articlesList.get(articleId)) {
            featuresList.get(id).addFeatures(featureExtractor.extract(a));
            id ++;
        }
        System.out.println("performed feature extraction variant: " + i);

    }
}
