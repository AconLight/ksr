package runner;

import classifiedObjects.Article;
import dataOperations.Feature;
import dataOperations.featureExtractors.IFeatureExtractor;

import java.util.List;

public class ExtractingFeature extends Configurable {

    private IFeatureExtractor featureExtractor;
    private List<List<Article>> articlesList;
    private int articleId;

    public List<List<Feature>> featuresList;

    public ExtractingFeature(IFeatureExtractor featureExtractor, List<List<Article>> articlesList, int articleId, List<List<Feature>> featuresList) {
        this.articleId = articleId;
        this.featureExtractor = featureExtractor;
        this.articlesList = articlesList;
        this.featuresList = featuresList;
    }

    @Override
    public void perform(int i) {
        featureExtractor.setVariant(i);
        System.out.println("performing feature extraction variant: " + i);
        for (Article article: articlesList.get(articleId)) {
            // featuresList.add(featureExtractor.extract(article));
        }
        System.out.println("performed feature extraction variant: " + i);

    }
}
