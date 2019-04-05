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
    private int articlesId;
    private List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;
    private KeyWordsData keyWordsData;

    public List<List<FeatureVector>> featuresList;

    public ExtractingFeature(KeyWordsData keyWordsData, IFeatureExtractor featureExtractor, List<List<Article>> articlesList, int articlesId, List<List<FeatureVector>> featuresList) {
        this.articlesId = articlesId;
        this.featureExtractor = featureExtractor;
        this.articlesList = articlesList;
        this.featuresList = featuresList;
        this.keyWordsList = keyWordsData.keyWordsList;
        this.keyWordsData = keyWordsData;
    }

    @Override
    public void perform(int i) {
        featureExtractor.setVariant(i);
        System.out.println("performing feature extraction variant: " + i + " articlesId: " + articlesId);

        ExtractorData data = new ExtractorData();
        data.keyWords = keyWordsList.get(articlesId);
        featureExtractor.setData(data);

        featuresList.add(new ArrayList<>());
        int id = 0;
        if (featuresList.get(featuresList.size()-1).isEmpty()) {
            for (Article a : articlesList.get(articlesId)) {
                FeatureVector fv;
                featuresList.get(featuresList.size()-1).add(fv = new FeatureVector(a.getLabel()));
                fv.origin = keyWordsData.origin.get(articlesId);
                System.out.println(fv.origin);
                if (featureExtractor.extractionMethod(i) != null)
                    fv.keyWordsMethod = featureExtractor.extractionMethod(i).name();
                if (featureExtractor.similarityMethod(i) != null)
                    fv.similarity = featureExtractor.similarityMethod(i).getClass().getName();
            }
        }
        for (Article a : articlesList.get(articlesId)) {
            featuresList.get(featuresList.size()-1).get(id).addFeatures(featureExtractor.extract(a));
            id ++;
        }
        System.out.println("performed feature extraction variant: " + i);

    }
}
