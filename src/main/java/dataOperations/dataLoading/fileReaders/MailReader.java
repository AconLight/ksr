package dataOperations.dataLoading.fileReaders;

import classifiedObjects.Article;
import dataOperations.preprocessing.IPreprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static config.Config.placesLabels;

public class MailReader implements IFileReader<Article> {
    private IPreprocessor<String> preprocessor;

    public MailReader(IPreprocessor<String> preprocessor) {
        this.preprocessor = preprocessor;
    }

    @Override
    public List<Article> read(File file) {
        if (!file.getName().endsWith(".txt")) {
            return new ArrayList<>();
        }

        String label = file.getParent();
        label = label.substring(label.length()-4);
        return extractFile(file, label);
    }

    private List<Article> extractFile(File sgmFile, String label) {
        try {
            ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(sgmFile));
            String line;

            reader = new BufferedReader(new FileReader(sgmFile));

            HashMap<String, String> data = new HashMap<>();

            String text = "";

            while ((line = reader.readLine()) != null) {
                text += line + " ";
            }

            data.put("LABEL", label);
            data.put("BODY", text);
            dataList.add(data);
            reader.close();

            return dataListToToArticle(dataList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Article> dataListToToArticle(List<HashMap<String, String>> dataList) {
        List<Article> articles = new ArrayList<>();
        for (HashMap<String, String> articleData : dataList) {
            if (articleData.get("LABEL") == null) continue;
            //System.out.println(articleData.get("TOPICS"));
            String label = articleData.get("LABEL").trim();
            String[] myLabel = label.split(" ");
            if (myLabel.length > 0) {
                label = myLabel[0];
            }
            if (placeIsAccepted(label)) {
                articles.add(new Article(articleData, label, this.preprocessor));
            }
        }
        return articles;
    }

    private boolean placeIsAccepted(String checkedPlace) {
        for (String place : placesLabels) {
            if (true || Objects.equals(place, checkedPlace)) {
                return true;
            }
        }
        return false;
    }



}


