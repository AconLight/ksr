package dataOperations.featureExtractors;

import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

import java.util.List;
import java.util.Map;

public class ExtractorData {
    public Map<ExtractionMethod, Map<String, List<String>>> keyWords;

    public ExtractorData(Map<ExtractionMethod, Map<String, List<String>>> keyWords, IWordSimilarity similarity, ExtractionMethod extractionMethod) {
        this.keyWords = keyWords;
    }

    public ExtractorData() {

    }
}
