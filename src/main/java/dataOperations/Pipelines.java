package dataOperations;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipelines {
    public static StanfordCoreNLP preprocessingPipeline = preprocessingPipeline();
    public static StanfordCoreNLP sentimentPipeline = sentimentPipeline();

    private static StanfordCoreNLP preprocessingPipeline() {
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        return new StanfordCoreNLP(props);
    }

    private static StanfordCoreNLP sentimentPipeline() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        return new StanfordCoreNLP(props);
    }
}
