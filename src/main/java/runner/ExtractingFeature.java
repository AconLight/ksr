package runner;

import classifiedObjects.Article;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.ExtractorData;
import dataOperations.featureExtractors.IFeatureExtractor;
import edu.stanford.nlp.coref.statistical.FeatureExtractor;
import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractingFeature {

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

    public void perform(int i, ExtractionMethod m, IWordSimilarity w) {
        featuresList.add(new ArrayList<>());
        System.out.println(RunnerConfig.extractionMethods.length);
        if (m != null) {
                myPerform(i, m);
        } else if (w != null) {
                myPerform(i, w);
        }
        else {
            myPerform(i);
        }
    }

    private void myPerform(int i, ExtractionMethod m) {
        System.out.println(m.name());
        for (IFeatureExtractor featureExtractor: featureExtractors) {
            featureExtractor.setVariant(i);
            featureExtractor.setM(m);
            //System.out.println("performing feature extraction variant: " + i + " articlesId: " + articlesId);

            ExtractorData data = new ExtractorData();
            data.keyWords = keyWordsList.get(articlesId);
            featureExtractor.setData(data);


            int id = 0;
            if (featuresList.get(featuresList.size() - 1).isEmpty()) {
                for (Article a : articlesList.get(articlesId)) {
                    FeatureVector fv;
                    featuresList.get(featuresList.size() - 1).add(fv = new FeatureVector(a.getLabel()));
                    fv.origin = keyWordsData.origin.get(articlesId);
                    //System.out.println(fv.origin);
                    fv.keyWordsMethod = m.name();
                    fv.similarity = featureExtractor.getW().getClass().getName() + featureExtractor.getW().getParams();
                }
            }
            for (Article a : articlesList.get(articlesId)) {
                featuresList.get(featuresList.size() - 1).get(id).addFeatures(featureExtractor.extract(a));
                id++;
            }
            // System.out.println("id1: " + (featuresList.size() - 1));
            //System.out.println("id2: " + (id - 1));
            //System.out.println(featuresList.get(featuresList.size() - 1).get(id - 1).getFeatureNamesToString());
            System.out.println("performed feature extraction variant: " + i);
        }
    }

    private void myPerform(int i, IWordSimilarity w) {
        for (IFeatureExtractor featureExtractor: featureExtractors) {
            featureExtractor.setVariant(i);
            featureExtractor.setW(w);
            //System.out.println("performing feature extraction variant: " + i + " articlesId: " + articlesId);

            ExtractorData data = new ExtractorData();
            data.keyWords = keyWordsList.get(articlesId);
            featureExtractor.setData(data);


            int id = 0;
            if (featuresList.get(featuresList.size() - 1).isEmpty()) {
                for (Article a : articlesList.get(articlesId)) {
                    FeatureVector fv;
                    featuresList.get(featuresList.size() - 1).add(fv = new FeatureVector(a.getLabel()));
                    fv.origin = keyWordsData.origin.get(articlesId);
                    //System.out.println(fv.origin);
                    fv.keyWordsMethod = featureExtractor.getM().name();
                    fv.similarity = w.getClass().getName() + w.getParams();
                }
            }
            for (Article a : articlesList.get(articlesId)) {
                featuresList.get(featuresList.size() - 1).get(id).addFeatures(featureExtractor.extract(a));
                id++;
            }
            // System.out.println("id1: " + (featuresList.size() - 1));
            //System.out.println("id2: " + (id - 1));
            //System.out.println(featuresList.get(featuresList.size() - 1).get(id - 1).getFeatureNamesToString());
            System.out.println("performed feature extraction variant: " + i);
        }
    }

    private void myPerform(int i) {
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
                    fv.origin = keyWordsData.origin.get(articlesId);
                    //System.out.println(fv.origin);
                    fv.keyWordsMethod = featureExtractor.getM().name();
                    fv.similarity = featureExtractor.getW().getClass().getName() + featureExtractor.getW().getParams();
                }
            }
            for (Article a : articlesList.get(articlesId)) {
                featuresList.get(featuresList.size() - 1).get(id).addFeatures(featureExtractor.extract(a));
                id++;
            }
            // System.out.println("id1: " + (featuresList.size() - 1));
            //System.out.println("id2: " + (id - 1));
            //System.out.println(featuresList.get(featuresList.size() - 1).get(id - 1).getFeatureNamesToString());
            System.out.println("performed feature extraction variant: " + i);
        }
    }
}
