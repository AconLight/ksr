package runner;

import classifiedObjects.Article;
import config.Config;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.preprocessing.FullPreprocessor;
import dataOperations.preprocessing.IPreprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExtractingDataSets extends Configurable {

    private float divide = 0.9f; // stosunek train do all

    private List<List<Article>> articlesList;

    private List<List<Article>> articlesListTrain;

    private List<List<Article>> articlesListTest;

    public ExtractingDataSets(int range, List<List<Article>> articlesList, List<List<Article>> articlesListTest, List<List<Article>> articlesListTrain) {
        super(range);
        this.articlesList = articlesList;
        this.articlesListTest = articlesListTest;
        this.articlesListTrain = articlesListTrain;
    }

    @Override
    public void perform(int i) {
        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        ReutersArticleReader reader = new ReutersArticleReader(preprocessor);
        List<Article> temp;
        Collections.shuffle(temp = loader.loadObjects(Config.allSetPath, reader), new Random(i));
        articlesList.add(temp);
        int lastId = temp.size() - 1;
        int trainI = (int)(temp.size() * divide);
        articlesListTest.add(temp.subList(trainI, lastId));
        articlesListTrain.add(temp.subList(0, trainI-1));

        System.out.println("dataSet " + articlesListTrain.get(0).size() + ", " + articlesListTest.get(0).size());
    }
}
