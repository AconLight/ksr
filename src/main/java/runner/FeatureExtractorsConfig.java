package runner;

import dataOperations.featureExtractors.AvgMaxKeyWordFeatureExtractor;
import dataOperations.featureExtractors.FrequencyKeyWordExtractor;
import dataOperations.featureExtractors.IFeatureExtractor;
import dataOperations.featureExtractors.SentimentExtractor;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureExtractorsConfig {
    public List<IFeatureExtractor> extractors;
    public List<List<Integer>> variantsList;

    public FeatureExtractorsConfig() {
        extractors = new ArrayList<>();
        variantsList = new ArrayList<>();
    }

    public static FeatureExtractorsConfig fec1() {
        FeatureExtractorsConfig fec = new FeatureExtractorsConfig();
        fec.extractors.add(new AvgMaxKeyWordFeatureExtractor());
        fec.extractors.add(new FrequencyKeyWordExtractor());
        fec.variantsList.addAll(generateAllComb2(
                RunnerConfig.avgWeights.length*RunnerConfig.wordSimilarities.length*3,
                RunnerConfig.freqN.length*RunnerConfig.wordSimilarities.length*3
        ));
        return fec;
    }

    private static ArrayList<ArrayList<Integer>> generateAllComb2(int size1, int size2) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(i);
                temp.add(j);
                result.add(temp);
            }
        }

        return result;
    }

    private static ArrayList<ArrayList<Integer>> generateAllComb3(int size1, int size2, int size3) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                for (int k = 0; k < size3; k++) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    temp.add(k);
                    result.add(temp);
                }
            }
        }

        return result;
    }

    public static FeatureExtractorsConfig fec3() {
        FeatureExtractorsConfig fec = new FeatureExtractorsConfig();
        IFeatureExtractor avg = new AvgMaxKeyWordFeatureExtractor();
        avg.setM(ExtractionMethod.OVERALL_WORD_OCCURRENCES); avg.setW(new GeneralizedNGramWithLimits(2, 3));
        IFeatureExtractor fq = new FrequencyKeyWordExtractor();
        fq.setM(ExtractionMethod.OVERALL_WORD_OCCURRENCES); fq.setW(new GeneralizedNGramWithLimits(2, 3));
        fec.extractors.add(avg);
        fec.extractors.add(fq);
//        fec.variantsList.add(Arrays.asList(0, 0));
        fec.variantsList.add(Arrays.asList(0, 0));
//        fec.variantsList.add(Arrays.asList(12, 12));
//        fec.variantsList.add(Arrays.asList(13, 13));
//        fec.variantsList.add(Arrays.asList(14, 14));
//        fec.variantsList.add(Arrays.asList(15, 15));
//        fec.variantsList.add(Arrays.asList(16, 16));
//        fec.variantsList.add(Arrays.asList(17, 17));

        return fec;
    }

    public static FeatureExtractorsConfig fec2() {
        FeatureExtractorsConfig fec = new FeatureExtractorsConfig();
        fec.extractors.add(new AvgMaxKeyWordFeatureExtractor());
        fec.extractors.add(new FrequencyKeyWordExtractor());
        fec.variantsList.add(Arrays.asList(16+0, 21));
        fec.variantsList.add(Arrays.asList(16+1, 21));
        fec.variantsList.add(Arrays.asList(16+2, 21));
        fec.variantsList.add(Arrays.asList(16+3, 21));
        fec.variantsList.add(Arrays.asList(16+4, 21));
        fec.variantsList.add(Arrays.asList(16+5, 21));
        fec.variantsList.add(Arrays.asList(16+6, 21));
        fec.variantsList.add(Arrays.asList(16+7, 21));
        return fec;
    }

}
