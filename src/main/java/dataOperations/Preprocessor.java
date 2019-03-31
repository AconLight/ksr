package dataOperations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import data.DataTables;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Preprocessor {
    private StanfordCoreNLP pipeline;

    public Preprocessor() {
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public String cleanse(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^\\S ]+g", " "); // white chars to spaces
        text = text.replaceAll("[^a-z., ]", ""); // remove non a-z . , and spaces
        text = text.replaceAll("  +", " "); // multiple spaces to single
        text = text.replaceAll("( (a-z) )", ""); // single characters surrounded by spaces

        return text;
    }

    public List<String> applyStopList(List<String> words) {
        List<String> stopList = DataTables.getStopList();
        List<String> prunedWords = new ArrayList<>();

        for (String word : words) {
            if (!stopList.contains(word.toLowerCase())) {
                prunedWords.add(word);
            }
        }
        return prunedWords;
    }

    public List<String> lemme(String text) {
        List<String> lemmas = new LinkedList<>();

        Annotation document = new Annotation(text);

        this.pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
            }
        }
        return lemmas;
    }

    public String[] stem(String[] textWords) throws Exception {
        throw new Exception("not implemented");
    }
}
