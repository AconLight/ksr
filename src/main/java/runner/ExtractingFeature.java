package runner;

import classifiedObjects.Article;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.ExtractorData;
import dataOperations.featureExtractors.IFeatureExtractor;
import edu.stanford.nlp.coref.statistical.FeatureExtractor;
import utils.ExtractionMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractingFeature extends Configurable {

    private List<IFeatureExtractor> featureExtractors;
    private List<List<Article>> articlesList;
    private int articlesId;
    private List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;
    private KeyWordsData keyWordsData;
    private int exextractorListId;

    public List<List<FeatureVector>> featuresList;

    public ExtractingFeature(int extractorListId, KeyWordsData keyWordsData, List<IFeatureExtractor> featureExtractors, List<List<Article>> articlesList, int articlesId, List<List<FeatureVector>> featuresList) {
        this.articlesId = articlesId;
        this.featureExtractors = featureExtractors;
        this.articlesList = articlesList;
        this.featuresList = featuresList;
        this.keyWordsList = keyWordsData.keyWordsList;
        this.keyWordsData = keyWordsData;
        this.exextractorListId = extractorListId;
    }

    @Override
    public void perform(int i) {
        featuresList.add(new ArrayList<>());
        for (IFeatureExtractor featureExtractor: featureExtractors) {
            featureExtractor.setVariant(i);
            //System.out.println("performing feature extraction variant: " + i + " articlesId: " + articlesId);

            ExtractorData data = new ExtractorData();
            data.keyWords = keyWordsList.get(articlesId);
            featureExtractor.setData(data);


            int id = 0;
            if (featuresList.get(featuresList.size() - 1).isEmpty()) {
                for (Article a : articlesList.get(articlesId)) {
                    FeatureVector fv;
                    featuresList.get(featuresList.size() - 1).add(fv = new FeatureVector(a.getLabel()));
                    fv.origin = keyWordsData.origin.get(articlesId) + "elo";
                    //System.out.println(fv.origin);
                    if (featureExtractor.extractionMethod(i) != null)
                        fv.keyWordsMethod = featureExtractor.extractionMethod(i).name();
                    if (featureExtractor.similarityMethod(i) != null)
                        fv.similarity = featureExtractor.similarityMethod(i).getClass().getName() + featureExtractor.similarityMethod(i).getParams();
                }
            }
            for (Article a : articlesList.get(articlesId)) {
                featuresList.get(featuresList.size() - 1).get(id).addFeatures(featureExtractor.extract(a));
                featuresList.get(featuresList.size() - 1).get(id).origin += "elo";
                id++;
            }
           // System.out.println("id1: " + (featuresList.size() - 1));
            //System.out.println("id2: " + (id - 1));
            //System.out.println(featuresList.get(featuresList.size() - 1).get(id - 1).getFeatureNamesToString());
            System.out.println("performed feature extraction variant: " + i);
        }

    }
}
