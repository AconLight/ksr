package runner;

import classifiedObjects.Article;
import config.Config;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.preprocessing.FullPreprocessor;
import dataOperations.preprocessing.IPreprocessor;

import java.util.ArrayList;
import java.util.List;

public class ExtractingDataSets extends Configurable {

    private List<List<Article>> articlesList;

    public ExtractingDataSets(int range, List<List<Article>> articlesList) {
        super(range);
        this.articlesList = articlesList;
    }

    @Override
    public void perform(int i) {
        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        ReutersArticleReader reader = new ReutersArticleReader(preprocessor);
        articlesList.add(loader.loadObjects(Config.learningSetPath, reader));
    }
}
