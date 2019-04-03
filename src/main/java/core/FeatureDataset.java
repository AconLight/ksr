package core;

import dataOperations.FeatureVector;

import java.util.ArrayList;
import java.util.List;

class FeatureDataset {
    private int featureAmount;

    public List<FeatureVector> getFeatureVectors() {
        return featureVectors;
    }

    private List<FeatureVector> featureVectors;
    private List<Double> featureMaxAbs;


    FeatureDataset(List<FeatureVector> featureVectors) {
        featureAmount = featureVectors.get(0).getFeatures().size();
        featureMaxAbs = featureMaxAbs(featureVectors);

        for (FeatureVector fv : featureVectors) {
            fv.updateValues(featureMaxAbs);
        }

        this.featureVectors = featureVectors;
    }

    public void addFeatureVector(FeatureVector fv) {
        fv.updateValues(featureMaxAbs);
        featureVectors.add(fv);
    }

    private List<Double> featureMaxAbs(List<FeatureVector> featureVectors) {
        List<Double> featureMaxAbs = new ArrayList<>();
        for (int i = 0; i < featureAmount; i++) {
            double maxAbs = 0;
            for (FeatureVector fv : featureVectors) {
                double tmpMaxAbs = Math.abs(fv.getFeatures().get(i).getValue());
                if (maxAbs < tmpMaxAbs) maxAbs = tmpMaxAbs;
            }
            featureMaxAbs.add(maxAbs);
        }
        return featureMaxAbs;
    }
}
