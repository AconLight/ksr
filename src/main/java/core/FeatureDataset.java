package core;

import dataOperations.FeatureVector;

import java.util.ArrayList;
import java.util.List;

// wg mnie to powinno wyglądać tak:
//        - znajdujemy w learning secie max i min wartość cechy C
//        - przeskalowujemy każdy wektor obecnie istniejący i w przyszłości dodawany/sprawdzany w taki sposób, że:
//
//        najpierw zapisujemy x_max_abs = max(abs(x))
//
//        a potem każdy nowy x = x / x_max_abs


public class FeatureDataset {
    private List<List<Double>> vectors = new ArrayList<>();
    List<Double> maxAbsPerFeature = new ArrayList<>();

    FeatureDataset(List<FeatureVector> featureVectors) {
        for (FeatureVector fv : featureVectors) {
            vectors.add(fv.toList());
        }
        setRescalingParam();
    }

    private void setRescalingParam() {
        int featureCount = vectors.get(0).size();

        for (int i = 0; i < featureCount; i++) {
            double maxAbs = Math.abs(vectors.get(0).get(i));

            for (List<Double> v : vectors) {
                if (maxAbs < Math.abs(v.get(i))) maxAbs = v.get(i);
            }
            maxAbsPerFeature.add(maxAbs);
        }
    }
}
