package dataOperations.featureExtractors;

import classifiedObjects.Article;
import dataOperations.Feature;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;

import dataOperations.Pipelines;
import org.ejml.simple.SimpleMatrix;
import utils.ExtractionMethod;
import utils.Utils;
import word.similarity.IWordSimilarity;


public class SentimentExtractor implements IFeatureExtractor<Article> {

    private int variant = 0;
    @Override
    public void setVariant(int i) {
        variant = i;
        // tutaj ustawiamy ewentualne wagi do konkretnego wariantu
    }

    @Override
    public List<Feature> extract(Article article) {
        List<Feature> features = new ArrayList<>();
        List<Double> sentiments = textSentiment(article.getText());

        features.add(new Feature(averageSentiment(sentiments), "average sentence sentiment"));
        features.add(new Feature(minSentiment(sentiments), "min sentence sentiment"));
        features.add(new Feature(maxSentiment(sentiments), "max sentence sentiment"));
        return features;
    }

    @Override
    public void setData(ExtractorData data) {
        // do nothing, no use
    }

    @Override
    public IWordSimilarity similarityMethod(int i) {
        return null;
    }

    @Override
    public ExtractionMethod extractionMethod(int i) {
        return null;
    }

    private Double averageSentiment(List<Double> sentiments) {
        double sum = 0;
        for (Double s : sentiments) {
            sum += s;
        }
        return sum / sentiments.size();
    }

    private Double minSentiment(List<Double> sentiments) {
        return sentiments.stream().min(Double::compareTo).get();
    }

    private Double maxSentiment(List<Double> sentiments) {
        return sentiments.stream().max(Double::compareTo).get();
    }

    private List<Double> textSentiment(String text) {
        List<Double> sentiments = new ArrayList<>();
        Annotation document = new Annotation(text);

        Pipelines.sentimentPipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
            sentiments.add(Utils.sentimentMatrixToDouble(sm));
        }
        return sentiments;
    }
}
