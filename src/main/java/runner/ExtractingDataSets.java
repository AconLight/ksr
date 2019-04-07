package runner;

import classifiedObjects.Article;
import config.Config;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.IFileReader;
import dataOperations.dataLoading.fileReaders.MailReader;
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

    ArrayList<String> dataSets;

    public ExtractingDataSets(ArrayList<String> dataSets, List<List<Article>> articlesList, List<List<Article>> articlesListTest, List<List<Article>> articlesListTrain) {
        super(RunnerConfig.dataSetsRange);
        this.articlesList = articlesList;
        this.articlesListTest = articlesListTest;
        this.articlesListTrain = articlesListTrain;
        this.dataSets = dataSets;
    }

    @Override
    public void perform(int i) {
        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        IFileReader reader;
        List<Article> temp;
        if (RunnerConfig.isMail) {
            reader = new MailReader(preprocessor);
            temp = loader.loadObjects(Config.mailSets, reader);
            temp.addAll(loader.loadObjects(Config.spamSets, reader));
            Collections.shuffle(temp, new Random(i));
        } else {
            reader = new ReutersArticleReader(preprocessor);
            Collections.shuffle(temp = loader.loadObjects(Config.allSetPath, reader), new Random(i));
        }




        articlesList.add(temp);
        int lastId = temp.size() - 1;
        int trainI = (int)(temp.size() * divide);
        articlesListTest.add(temp.subList(trainI, lastId));
        articlesListTrain.add(temp.subList(0, trainI-1));

        for (Article article: articlesListTrain.get(articlesListTrain.size()-1)) {
            article.origin = "train dataSet " + i + " - train: " + articlesListTrain.get(0).size() + ", test: " + articlesListTest.get(0).size();
        }

        for (Article article: articlesListTest.get(articlesListTest.size()-1)) {
            article.origin = "test dataSet " + i + " - train: " + articlesListTrain.get(0).size() + ", test: " + articlesListTest.get(0).size();
        }
        dataSets.add("dataSet " + i + " - train: " + articlesListTrain.get(0).size() + ", test: " + articlesListTest.get(0).size());
    }
}
