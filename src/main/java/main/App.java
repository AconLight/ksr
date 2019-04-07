package main;

import classifiedObjects.Article;
import config.Config;
import core.Knn;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.*;
import dataOperations.preprocessing.IPreprocessor;
import dataOperations.textStatistics.KeyWordExtractor;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.preprocessing.FullPreprocessor;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;
import word.similarity.NGram;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class App {
    public static void main(String[] args) {

        int myK;
        IMetric myMetric = new ChebyshevMetric();
        ExtractionMethod myExtractionMethod = ExtractionMethod.OVERALL_WORD_OCCURRENCES;
        IWordSimilarity myWordSim = new NGram(2);
        String myTrain = "";
        String myTest = "";
        String[] labels = {"west-germany", "usa", "france", "uk", "canada", "japan"};
        String tag = "PLACES";
        IFeatureExtractor f1;
        IFeatureExtractor f2;
        int[] variant = {0, 0};


        File file = Config.runnerConfig.toFile();
        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(new FileReader(file));

            String line;

            line = reader2.readLine();

            myK = Integer.parseInt(line, 10);

            line = reader2.readLine();

            switch(line) {
                case "chebyshev": {
                    myMetric = new ChebyshevMetric();
                    break;
                }
                case "manhattan": {
                    myMetric = new ManhattanMetric();
                    break;
                }
                case "euclidean": {
                    myMetric = new EuclideanMetric();
                    break;
                }
            }

            line = reader2.readLine();

            switch(line) {
                case "tfidf": {
                    myExtractionMethod = ExtractionMethod.TFIDF;
                    break;
                }
                case "overall_word_count": {
                    myExtractionMethod = ExtractionMethod.OVERALL_WORD_COUNT;
                    break;
                }
                case "overall_word_occurences": {
                    myExtractionMethod = ExtractionMethod.OVERALL_WORD_OCCURRENCES;
                    break;
                }
            }

            line = reader2.readLine();

            switch(line) {
                case "ngram 2": {
                    myWordSim = new NGram(2);
                    break;
                }
                case "ngram 1": {
                    myWordSim = new NGram(1);
                    break;
                }
                case "generalized ngram": {
                    myWordSim = new GeneralizedNGramWithLimits(1, 3);
                    break;
                }
            }

            line = reader2.readLine();

            myTrain = line;


            line = reader2.readLine();

            myTest = line;

            line = reader2.readLine();

            labels = line.split(" ");

            line = reader2.readLine();

            tag = line;

















        int ngram = 1;
        int initSizePerClass = 35;
        int k = 3;
        int numberOfKeywords = 5;
        Config.tag = tag;
        Config.placesLabels = Arrays.asList(labels);

        ExtractionMethod extractionMethod = myExtractionMethod;
        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        ReutersArticleReader reader = new ReutersArticleReader(preprocessor);

        List<Article> trainSetArticles = loader.loadObjects(Paths.get(myTrain), reader);

        Map<ExtractionMethod, Map<String, List<String>>> keyWords = KeyWordExtractor.extractTopN(trainSetArticles, numberOfKeywords);


        List<IFeatureExtractor<Article>> subExtractors = new ArrayList<>();
            AvgMaxKeyWordFeatureExtractor avg =  new AvgMaxKeyWordFeatureExtractor();
            avg.extractionMethod = myExtractionMethod;
            avg.similarityMethod = myWordSim;
            float [] avgN = {1f, 0.6f, 0.2f};
            avg.N = avgN;
            FrequencyKeyWordExtractor freq =  new FrequencyKeyWordExtractor();
            freq.extractionMethod = myExtractionMethod;
            freq.similarityMethod = myWordSim;
            freq.N = 7;
        IFeatureExtractor<Article> subExtractor = avg;
        subExtractor.setData(new ExtractorData(keyWords, myWordSim, extractionMethod));
            IFeatureExtractor<Article> subExtractor2 = avg;
            subExtractor2.setData(new ExtractorData(keyWords, myWordSim, extractionMethod));
        subExtractors.add(subExtractor2);
            subExtractors.add(subExtractor);

        MainFeatureExtractor<Article> extractor = new MainFeatureExtractor<>(subExtractors);
        List<Article> initiatingArticles = loader.loadNObjects(Paths.get(myTest), reader, initSizePerClass, Arrays.asList(labels));
        List<Article> testArticles = loader.loadObjects(Paths.get(myTest), reader);

        Knn knn = new Knn(myK, extractor.extractAll(initiatingArticles), myMetric);

        Map<String, Integer> confusionMatrix = new HashMap<>();

        int sum = 0;
        for (FeatureVector fv : extractor.extractAll(testArticles)) {
            String result = knn.evaluate(fv);
            String name = fv.getLabel() + " " + result;
            if (!confusionMatrix.containsKey(name)) {
                confusionMatrix.put(name, 0);
            }
            confusionMatrix.put(name, confusionMatrix.get(name) + 1);


            if (result.equals(fv.getLabel())) sum++;
        }

        System.out.println(confusionMatrix);

        double precision = (double) sum / testArticles.size();
        System.out.println("Precision: " + precision);

        StatWriter.write(new TestResults(initSizePerClass, k, numberOfKeywords, extractionMethod, confusionMatrix.toString()));




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
