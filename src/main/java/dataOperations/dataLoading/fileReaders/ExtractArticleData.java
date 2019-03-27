package dataOperations.dataLoading.fileReaders;

import config.Config;
import dataOperations.classifiedObjects.Article;
import dataOperations.classifiedObjects.ClassifiedObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractArticleData {

    public static ArrayList<HashMap<String, String>> extract(Path dirPath, String[] marks) {
        ArrayList<HashMap<String, String>> tempDataList = null;
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
        File reutersDir = dirPath.toFile();
        File[] sgmFiles = reutersDir.listFiles(file -> file.getName().endsWith(".sgm"));
        if (sgmFiles != null && sgmFiles.length > 0) {
            for (File sgmFile : sgmFiles) {
                tempDataList = extractFile(sgmFile, marks);
                dataList.addAll(tempDataList);
            }
        } else {
            System.err.println("No .sgm files in " + reutersDir);
        }
        return dataList;
    }

    private static String[] marks = {"PLACES", "TITLE", "BODY"};
    private static String[] acceptedPlaces = {"west-germany", "usa", "france", "uk", "canada", "japan"};
    private static String[] marksToRemove = {"D"};
    private static String[] stringsToRemove = {"REUTER", "&#3;"};

    private static String opener(String mark) {
        return "<" + mark + ">";
    }

    private static String closer(String mark) {
        return "</" + mark + ">";
    }

    private static String removeMark(String text, String mark) {
        return text.replaceAll(opener(mark), "").replaceAll(closer(mark), " ");
    }

    private static String removeString(String text, String toRemove) {
        return text.replaceAll(toRemove, "");
    }

    private static ArrayList<HashMap<String, String>> extractFile(File sgmFile, String[] marks) {
        try {
            ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
            BufferedReader reader = new BufferedReader(new FileReader(sgmFile));
            String line = "";
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
                            for(String toRemove: stringsToRemove) {
                                temp = removeString(temp, toRemove);
                            }
                        }
                        if (temp.split(closer(mark)).length > 1)
                            line = temp.split(closer(mark))[1];
                        else
                            line = "";
                        if (temp.split(closer(mark)).length > 0) {
                            temp = temp.split(closer(mark))[0];
                            for (String markToRemove : ExtractArticleData.marksToRemove) {
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
            return dataList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean placeIsAccepted(String checkedPlace) {
        for (String place : ExtractArticleData.acceptedPlaces) {
            if (place.equals(checkedPlace)) {
                return true;
            }
        }
        return false;
    }

    static ArrayList<Article> extractToObjects(Path path) {
        ArrayList<Article> objects = new ArrayList<>();
        ArrayList<HashMap<String, String>> extractedData = ExtractArticleData.extract(path, ExtractArticleData.marks);
        for (HashMap<String, String> data : extractedData) {
            if (!placeIsAccepted(data.get("PLACES"))) {
                continue;
            }
            objects.add(new Article(data, data.get("PLACES")));
        }
        return objects;
    }

    public static void main(String[] args) {
        ArrayList<Article> objects = extractToObjects(Config.learningSetPath);
        for (Article obj : objects) {
            System.out.println("title: " + obj.title);
            System.out.println("body: " + obj.text);
            System.out.println("1st place: " + obj.getLabel());
            System.out.println();
        }
    }
}


 