package runner;

// it provides basic configurations for running the program
// it should enable us to automatically create SPRAWOZDANIE

import classifiedObjects.Article;
import config.Config;
import core.Knn;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.MainFeatureExtractor;
import dataOperations.textStatistics.KeyWordExtractor;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;
import word.similarity.NGram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

import static config.Config.*;

public class Runner {
    private List<Configurable> operations;
    private RunnerConfig runnerConfig;
    private List<List<Article>> articlesList;
    private List<List<Article>> articlesListTest;
    private List<List<Article>> articlesListTrain;
    private List<List<List<FeatureVector>>> featuresLists;
    private List<List<Knn>> knnList;
    private List<Result> results;
    private ArrayList<String> dataSets;
    private KeyWordsData keyWordsData = new KeyWordsData();
    Path save;

    public Runner() {




    }

    public void findBest() {
        RunnerConfig.set();
        RunnerConfig.dataSetsRange = 2;
        differentDataSize("places");
        RunnerConfig.set();
        RunnerConfig.dataSetsRange = 2;
        differentDataDiv("places");
        perform();
    }

    public void compare(String etykieta) {
        System.out.println("k");
        RunnerConfig.set();
        differentK(etykieta);
        perform();
        System.out.println("keywords");
        RunnerConfig.set();
        differentKeywords(etykieta);
        perform();
        System.out.println("wordsim");
        RunnerConfig.set();
        differentWordSims(etykieta);
        perform();
        System.out.println("metrics");
        RunnerConfig.set();
        differentMetrics(etykieta);
        perform();
        System.out.println("datasize");
        RunnerConfig.set();
        differentDataSize(etykieta);
        perform();
        System.out.println("datadiv");
        RunnerConfig.set();
        differentDataDiv(etykieta);
        perform();
    }

    public void differentDataSize(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "datasize");
        int[] size = {
                100, 150
        };
        RunnerConfig.dataSize = size;
        RunnerConfig.dataSetsRange = 2;
        reloadOperations();
    }

    public void differentDataDiv(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "datadiv");
        float[] div = {
                0.4f, 0.6f
        };
        RunnerConfig.dataDiv = div;
        RunnerConfig.dataSetsRange = 2;
        reloadOperations();
    }

    public void differentKeywords(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "keywords");
        ExtractionMethod[] extractionMethods = {
                ExtractionMethod.TFIDF,
                ExtractionMethod.OVERALL_WORD_COUNT,
                ExtractionMethod.OVERALL_WORD_OCCURRENCES
        };
        RunnerConfig.extractionMethods = extractionMethods;
        reloadOperations();
    }

    public void differentWordSims(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "wordsimilarity");
        IWordSimilarity[] sims = {
//                new NGram(1),
//                new NGram(2),
                new NGram(3),
                new GeneralizedNGramWithLimits(1, 2),
//                new GeneralizedNGramWithLimits(1, 3),
//                new GeneralizedNGramWithLimits(2, 3)
        };
        RunnerConfig.wordSimilarities = sims;
        reloadOperations();
    }

    public void differentMetrics(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "knnmetrics");
        IMetric[] metrics = {
                new ChebyshevMetric(),
                new ManhattanMetric(),
//                new EuclideanMetric()
        };
        RunnerConfig.metrics = metrics;
        reloadOperations();
    }

    public void differentK(String etykieta) {
        save = Config.getRunnerresultsPath(etykieta, "k");
        int[] k = {3, 4};
        RunnerConfig.k = k;
        reloadOperations();
    }

    public void reloadOperations() {
        runnerConfig = new RunnerConfig();
        articlesList = new ArrayList<>();
        articlesListTest = new ArrayList<>();
        articlesListTrain = new ArrayList<>();
        featuresLists = new ArrayList<>();
        keyWordsData = new KeyWordsData();
        keyWordsData.keyWordsList = new ArrayList<>();
        keyWordsData.origin = new ArrayList<>();
        operations = new ArrayList<>();
        knnList = new ArrayList<>();
        results = new ArrayList<>();

        dataSets = new ArrayList<>();

        operations.clear();
        operations.add(new ExtractingDataSets(dataSets, articlesList, articlesListTest, articlesListTrain));
        operations.add(new ExtractingKeyWords(RunnerConfig.dataSetsRange, articlesListTrain, keyWordsData));
        operations.add(new ExtractingFeatures(articlesListTest, featuresLists, keyWordsData));
        operations.add(new KNNRunner(results, knnList, featuresLists, Arrays.asList(RunnerConfig.metrics)));
    }



    public void perform() {
        for (Configurable op: operations) {
            op.performAll();
        }
        List<String> resultsStr = new ArrayList<>();
        List<String> bestResultsStr = new ArrayList<>();
        //System.out.println("results:");
        for (Result r: results) {
            //System.out.println(r.dataSets);
        }

        HashMap<String, List<Result>> experiments = new HashMap<>();

        float bestAvgPrec = 0;
        float bestAvgRec = 0;
        float bestAvgPrecRec = 0;

        for (Result r: results) {
            String ds = "";
            if (RunnerConfig.mode == 0) ds = r.dataSets;

            if (experiments.get((ds + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants)) == null) {
                List<Result> newR;
                experiments.put((ds + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants), newR = new ArrayList<>());
                newR.add(r);
            } else {
                experiments.get((ds + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants)).add(r);
            }
        }

        //System.out.println("results by experiment:");

        for (Map.Entry<String, List<Result>> entry: experiments.entrySet()) {
            String rs = "";

            //System.out.println(entry.getKey());
            rs += "{\n";
            rs += "\"title\": \"" + entry.getKey() + "\",\n";
            float good = 0;
            float bad = 0;
            HashMap<String, Integer> confMatrix = new HashMap<>();
            HashMap<String, Integer> originalLabelsCount = new HashMap<>();
            HashMap<String, Integer> positives = new HashMap<>();
            HashMap<String, Integer> negatives = new HashMap<>();
            HashMap<String, Float> precision = new HashMap<>();
            HashMap<String, Float> recall = new HashMap<>();
            for (Result r: entry.getValue()) {
                if (r.knn == r.originalLabel) good++;
                else bad++;
                if (confMatrix.get((r.knn + " " + r.originalLabel)) == null) {
                    confMatrix.put((r.knn + " " + r.originalLabel), 1);
                } else {
                    confMatrix.put((r.knn + " " + r.originalLabel), confMatrix.get((r.knn + " " + r.originalLabel)) + 1);
                }

                if (originalLabelsCount.get((r.originalLabel)) == null) {
                    originalLabelsCount.put((r.originalLabel), 1);
                } else {
                    originalLabelsCount.put((r.originalLabel), originalLabelsCount.get((r.originalLabel)) + 1);
                }

                if (positives.get((r.originalLabel)) == null) {
                    if (r.originalLabel.equalsIgnoreCase(r.knn))
                        positives.put((r.originalLabel), 1);
                    else
                        positives.put((r.originalLabel), 0);
                } else if (r.originalLabel.equalsIgnoreCase(r.knn)) {
                    positives.put((r.originalLabel), positives.get((r.originalLabel))+1);
                }

                if (negatives.get((r.knn)) == null) {
                    if (!r.originalLabel.equalsIgnoreCase(r.knn))
                        negatives.put((r.knn), 1);
                    else
                        negatives.put((r.knn), 0);
                } else if (!r.originalLabel.equalsIgnoreCase(r.knn)) {
                    negatives.put((r.knn), negatives.get((r.knn))+1);
                }

                for (Map.Entry<String, Integer> posEntry: originalLabelsCount.entrySet()) {
                    if (negatives.get(posEntry.getKey()) == null)
                        negatives.put(posEntry.getKey(), 0);
                }

            }
            float avgPrec = 0;
            int sum = 0;
            for (Map.Entry<String, Integer> posEntry: positives.entrySet()) {
                if (posEntry.getValue() == null) {
                    precision.put(posEntry.getKey(), 0f);
                } else {
                    float neg = 0;
                    if (negatives.get(posEntry.getKey()) != null) {
                        neg = negatives.get(posEntry.getKey());
                    }
                    precision.put(posEntry.getKey(), (1f * posEntry.getValue() / (posEntry.getValue() + neg)));
                    if ((posEntry.getValue() + neg) != 0) {
                        avgPrec += originalLabelsCount.get(posEntry.getKey())*(1f * posEntry.getValue() / (posEntry.getValue() + neg));
                        sum += originalLabelsCount.get(posEntry.getKey());
                    }
                }
            }

            avgPrec /= sum;


            float avgRec = 0;
            int sumRec = 0;

            for (Map.Entry<String, Integer> posEntry: positives.entrySet()) {
                if (posEntry.getValue() == null) {
                    recall.put(posEntry.getKey(), 0f);
                } else {
                    recall.put(posEntry.getKey(), (1f * posEntry.getValue() / originalLabelsCount.get(posEntry.getKey())));
                    if ((originalLabelsCount.get(posEntry.getKey())) != 0) {
                        avgRec += originalLabelsCount.get(posEntry.getKey())*(1f * posEntry.getValue() / originalLabelsCount.get(posEntry.getKey()));
                        sumRec += originalLabelsCount.get(posEntry.getKey());
                    }
                }
            }
            //System.out.print(keyWordsData.keyWordsList);
            avgRec /= sumRec;

            boolean isBest = false;

            if (avgRec > bestAvgRec) {
                bestAvgRec = avgRec;
                isBest = true;
            }

            if (avgPrec > bestAvgPrec) {
                bestAvgPrec = avgPrec;
                isBest = true;
            }

            if (avgPrec + avgRec > bestAvgPrecRec) {
                bestAvgPrecRec = avgPrec + avgRec;
                isBest = true;
            }

            rs += "\"averagePrecision\": " + avgPrec + ",\n";
            rs += "\"averageRecall\": " + avgRec + ",\n";
            rs += "\"averagePrecisionRecall\": " + (avgRec + avgPrec) + ",\n";


            rs += "\"precision\": {\n";
            boolean flaga = false;
            for (Map.Entry<String, Float> pentry: precision.entrySet()) {
                if (flaga) {
                    rs += ",\n";
                }
                flaga = true;
                rs += "\"" + pentry.getKey() + "\": " + pentry.getValue();
            }

            rs += "\n},\n";

            rs += "\"recall\": {\n";
            flaga = false;
            for (Map.Entry<String, Float> pentry: recall.entrySet()) {
                if (flaga) {
                    rs += ",\n";
                }
                flaga = true;
                rs += "\"" + pentry.getKey() + "\": " + pentry.getValue();
            }

            rs += "\n},\n";

            rs += "\"confusion matrix\": [\n";


            flaga = false;
            for (Map.Entry<String, Integer> pentry: originalLabelsCount.entrySet()) {
                if (flaga) {
                    rs += ",\n";
                }
                flaga = true;
                rs += "{\"" + pentry.getKey() + "\": [\n";
                boolean flaga2 = false;
                for (Map.Entry<String, Integer> pentry2: originalLabelsCount.entrySet()) {
                    if (flaga2) {
                        rs += ",\n";
                    }
                    flaga2 = true;
                    rs += "{\"" + pentry2.getKey() + "\": " + confMatrix.get(pentry.getKey() + " " + pentry2.getKey()) + "}";
                }
                rs += "\n]}";

                //rs += "\"" + pentry.getKey() + "\": " + pentry.getValue();
            }

            rs += "\n]";

            rs += "\n}";
            resultsStr.add(rs);

            if (isBest) {
                bestResultsStr.add(rs);
            }



        }
        //System.out.println("duppppppaaaaaa");
        //System.out.println(save.resolve(0 +"").toString());
        deleteFolder(save.toFile());

        int asd = 0;
        for (String t: resultsStr) {
            try {
                //System.out.println();
                //System.out.println(t);
                PrintWriter out = new PrintWriter(new FileOutputStream(save.resolve(asd +"").toFile()));
                //System.out.println(save.resolve(asd +"").toString());
                out.print(t);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            asd ++;
        }

        deleteFolder(bestrunnerresultsPathDir.toFile());

        asd = 0;
        for (String t: bestResultsStr) {
            try {
                //System.out.println();
                //System.out.println(t);
                PrintWriter out = new PrintWriter(new FileOutputStream(bestrunnerresultsPath(asd +"").toFile()));
                out.print(t);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            asd ++;
        }

    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        //folder.delete();
    }



}
