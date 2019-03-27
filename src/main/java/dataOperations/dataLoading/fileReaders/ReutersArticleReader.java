package dataOperations.dataLoading.fileReaders;

import classifiedObjects.Article;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReutersArticleReader implements IFileReader<Article> {

    @Override
    public List<Article> read(File file) {
        if (!file.getName().endsWith(".sgm")) {
            return new ArrayList<>();
        }
        return extractFile(file, marks);
    }

    private String[] marks = {"PLACES", "TITLE", "BODY"};
    private String[] acceptedPlaces = {"west-germany", "usa", "france", "uk", "canada", "japan"};
    private String[] marksToRemove = {"D"};
    private String[] stringsToRemove = {"REUTER", "&#3;"};

    private String opener(String mark) {
        return "<" + mark + ">";
    }

    private String closer(String mark) {
        return "</" + mark + ">";
    }

    private String removeMark(String text, String mark) {
        return text.replaceAll(opener(mark), "").replaceAll(closer(mark), " ");
    }

    private String removeString(String text, String toRemove) {
        return text.replaceAll(toRemove, "");
    }

    private List<Article> extractFile(File sgmFile, String[] marks) {
        try {
            ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(sgmFile));
            String line;
            while ((line = reader.readLine()) != null) {
                HashMap<String, String> data = new HashMap<>();
                boolean flaga = false;
//                System.out.println("enter the for");
                for (String mark : marks) {
                    String temp = line;
                    String b = "asd";
                    while (!temp.contains(opener(mark))) {
                        b = reader.readLine();
                        if (b == null) {
//                            System.out.println(temp);
//                            System.out.println("breaked");
                            break;
                        }
                        temp += b + " ";
                    }
//                    System.out.println("entering the if");
                    if (temp.contains(opener(mark))) {
                        temp = temp.split(opener(mark))[1];
                        while (!temp.contains(closer(mark))) {
                            temp += reader.readLine() + " ";
                            for (String toRemove : stringsToRemove) {
                                temp = removeString(temp, toRemove);
                            }
                        }
                        if (temp.split(closer(mark)).length > 1)
                            line = temp.split(closer(mark))[1];
                        else
                            line = "";
                        if (temp.split(closer(mark)).length > 0) {
                            temp = temp.split(closer(mark))[0];
                            for (String markToRemove : marksToRemove) {
                                temp = removeMark(temp, markToRemove);
                            }
                        }
//                        System.out.println("adding mark");
                        data.put(mark, temp);
                    } else {
                        flaga = true;
                    }
                }
                if (!flaga) {
                    dataList.add(data);
//                    System.out.println(data.toString());
//                    System.out.println("escaping the for");
                }
            }
            reader.close();
            return dataListToToArticle(dataList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Article> dataListToToArticle(List<HashMap<String, String>> dataList) {
        List<Article> articles = new ArrayList<>();
        for (HashMap<String, String> articleData : dataList) {
            String label = articleData.get("PLACES").trim();
            if (placeIsAccepted(label)) {
                articles.add(new Article(articleData, label));
            }
        }
        return articles;
    }

    private boolean placeIsAccepted(String checkedPlace) {
        for (String place : acceptedPlaces) {
            if (Objects.equals(place, checkedPlace)) {
                return true;
            }
        }
        return false;
    }
}


 