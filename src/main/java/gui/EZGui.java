package gui;

import config.Config;
import dataOperations.featureExtractors.AvgMaxKeyWordFeatureExtractor;
import dataOperations.featureExtractors.FrequencyKeyWordExtractor;
import dataOperations.textStatistics.KeyWordExtractor;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import runner.FeatureExtractorsConfig;
import runner.Runner;
import runner.RunnerConfig;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;
import word.similarity.NGram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EZGui {
    JFrame f;
    EZGui(){
        f=new JFrame("We love classification");
        JButton b=new JButton("Run classification");
        b.setBounds(50,50,200,20);
        JLabel blabel = new JLabel("");
        blabel.setBounds(260, 50, 200, 20);
        f.add(blabel);

        JLabel metriclabel = new JLabel("metric in knn");
        metriclabel.setBounds(50, 80, 200, 20);
        f.add(metriclabel);
        String metrics[]={"Manhattan","Chebyshev","Euclidean"};
        final JComboBox metricsBox=new JComboBox(metrics);
        metricsBox.setBounds(50, 100,200,20);
        f.add(metricsBox);

        JLabel keywordslabel = new JLabel("keywords extraction");
        keywordslabel.setBounds(50, 130, 200, 20);
        f.add(keywordslabel);
        String keywords[]={"TFIDF","OVERALL_WORD_OCCURENCES","OVERALL_WORD_COUNT"};
        final JComboBox keywordsBox=new JComboBox(keywords);
        keywordsBox.setBounds(50, 150,200,20);
        f.add(keywordsBox);

        JLabel divlabel = new JLabel("data division");
        divlabel.setBounds(50, 180, 200, 20);
        f.add(divlabel);
        String datadiv[]={"0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9"};
        final JComboBox datadivBox=new JComboBox(datadiv);
        datadivBox.setBounds(50, 200,200,20);
        f.add(datadivBox);

        JLabel sizelabel = new JLabel("data size");
        sizelabel.setBounds(50, 220, 200, 20);
        f.add(sizelabel);
        String datasize[]={"300", "400", "500", "600", "700", "800", "900"};
        final JComboBox datasizeBox=new JComboBox(datasize);
        datasizeBox.setBounds(50, 250,200,20);
        f.add(datasizeBox);

        JLabel kwordSimlabel = new JLabel("word similarity");
        kwordSimlabel.setBounds(50, 280, 200, 20);
        f.add(kwordSimlabel);
        String wordsSim[]={"ngram", "generalized ngram"};
        final JComboBox wordsSimBox=new JComboBox(wordsSim);
        wordsSimBox.setBounds(50, 300,200,20);
        f.add(wordsSimBox);
        JTextField wordsimparam1 = new JTextField("1");
        wordsimparam1.setBounds(310, 300,20,20);
        f.add(wordsimparam1);
        JTextField wordsimparam2 = new JTextField("3");
        wordsimparam2.setBounds(340, 300,20,20);
        f.add(wordsimparam2);

        JLabel klabel = new JLabel("k in knn");
        klabel.setBounds(50, 330, 200, 20);
        f.add(klabel);
        JTextField kField = new JTextField("1");
        kField.setBounds(50, 350,200,20);
        f.add(kField);

        JLabel llabel = new JLabel("labels");
        llabel.setBounds(50, 380, 200, 20);
        f.add(llabel);
        JTextField lField = new JTextField("usa japan west-germany uk france canada");
        lField.setBounds(50, 400,200,20);
        f.add(lField);

        JLabel tlabel = new JLabel("tag");
        tlabel.setBounds(50, 430, 200, 20);
        f.add(tlabel);
        JTextField tField = new JTextField("PLACES");
        tField.setBounds(50, 450,200,20);
        f.add(tField);

        JLabel slabel = new JLabel("source folder");
        slabel.setBounds(50, 480, 200, 20);
        f.add(slabel);
        JTextField sField = new JTextField("");
        sField.setBounds(50, 500,200,20);
        f.add(sField);

        JCheckBox avgcheckbox = new JCheckBox("AvgMaxKeyWord");
        avgcheckbox.setBounds(50, 550, 200, 20);
        f.add(avgcheckbox);
        JTextField avgpField = new JTextField("0.6 0.4 0.2");
        avgpField.setBounds(260, 550,200,20);
        f.add(avgpField);

        JCheckBox freqcheckbox = new JCheckBox("FreqKeyWords");
        freqcheckbox.setBounds(50, 600, 200, 20);
        f.add(freqcheckbox);
        JTextField freqpField = new JTextField("7");
        freqpField.setBounds(260, 600,200,20);
        f.add(freqpField);


        f.add(b);
        f.setLayout(null);
        f.setSize(600,700);
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RunnerConfig.set();
                String data = kField.getText();
                int[] k = {Integer.parseInt(data, 10)};
                RunnerConfig.k = k;
                data = "" + wordsSimBox.getItemAt(wordsSimBox.getSelectedIndex());
                String param1 = wordsimparam1.getText();
                String param2 = wordsimparam1.getText();
                IWordSimilarity wsim = null;
                switch (data) { case "ngram" : wsim = new NGram(Integer.parseInt(param1, 10)); break;
                    case "generalized ngram" : wsim = new GeneralizedNGramWithLimits(Integer.parseInt(param1, 10), Integer.parseInt(param2, 10)); break; }
                IWordSimilarity[] wsima = {wsim};
                    RunnerConfig.wordSimilarities = wsima;

                data = "" + datasizeBox.getItemAt(datasizeBox.getSelectedIndex());
                int[] dsize = {Integer.parseInt(data, 10)};
                RunnerConfig.dataSize = dsize;

                data = "" + datadivBox.getItemAt(datadivBox.getSelectedIndex());
                float[] ddiv = {Float.parseFloat(data)};
                RunnerConfig.dataDiv = ddiv;

                data = "" + keywordsBox.getItemAt(keywordsBox.getSelectedIndex());
                ExtractionMethod kwords = null;
                switch (data) { case "TFIDF" : kwords = ExtractionMethod.TFIDF; break;
                    case "OVERALL_WORD_COUNT" : kwords = ExtractionMethod.OVERALL_WORD_COUNT; break;
                    case "OVERALL_WORD_OCCURENCES" : kwords = ExtractionMethod.OVERALL_WORD_OCCURRENCES; break; }
                ExtractionMethod[] kmets = {kwords};
                RunnerConfig.extractionMethods = kmets;

                data = "" + metricsBox.getItemAt(metricsBox.getSelectedIndex());
                IMetric metric = null;
                switch (data) { case "Manhattan" : metric = new ManhattanMetric(); break;
                    case "Chebyshev" : metric = new ChebyshevMetric(); break;
                    case "Euclidean" : metric = new EuclideanMetric(); break; }
                IMetric[] mets = {metric};
                RunnerConfig.metrics = mets;


                int fn = Integer.parseInt(freqpField.getText(), 10);
                System.out.println(fn);
                String[] ans = avgpField.getText().split(" ");
                float[] an = new float[ans.length];
                int i = 0;
                for (String n: ans) {
                    an[i] = Float.parseFloat(n);
                    System.out.println(an[i]);
                    i++;
                }

                FeatureExtractorsConfig fec = new FeatureExtractorsConfig();

                if (avgcheckbox.isSelected()) {
                    AvgMaxKeyWordFeatureExtractor avg = new AvgMaxKeyWordFeatureExtractor();
                    avg.setM(RunnerConfig.extractionMethods[0]); avg.setW(RunnerConfig.wordSimilarities[0]);
                    fec.extractors.add(avg);
                    float[][] myan= {an};
                    RunnerConfig.avgWeights = myan;
                }

                if (freqcheckbox.isSelected()) {
                    FrequencyKeyWordExtractor freq = new FrequencyKeyWordExtractor();
                    freq.setM(RunnerConfig.extractionMethods[0]); freq.setW(RunnerConfig.wordSimilarities[0]);
                    fec.extractors.add(freq);
                    int [] ns = {fn};
                    RunnerConfig.freqN = ns;
                }
                if (fec.extractors.size() == 2)
                    fec.variantsList.add(Arrays.asList(0, 0));
                else if (fec.extractors.size() == 1)
                    fec.variantsList.add(Arrays.asList(0));

                FeatureExtractorsConfig[] fc = {fec};
                RunnerConfig.featureExtractorsConfig = fc;



                RunnerConfig.path = sField.getText();
                //System.out.println(tField.getText());
                //System.out.println(lField.getText());
                Config.tag = tField.getText();
                //Config.tag = "PLACES";

                Config.setLabels(lField.getText().split(" "));
                //Config.setLabels(Config.acceptedPlaces);

                if (Config.tag.equals("SPAM")) {
                    RunnerConfig.isMail = true;
                }

                if (!sField.getText().equals("")) {

                }

                Runner r = new Runner();

                Thread thread = new Thread(new Runnable() {
                    public void run()
                    {
                        blabel.setText("running...");
                        try {
                            r.run();
                            blabel.setText("done!");
                        } catch (Exception e) {
                            blabel.setText("crashed!");
                            System.out.println(e);
                        }

                    }
                });
                thread.start();
            }
        });
    }
    public static void main(String[] args) {
        new EZGui();
    }
}