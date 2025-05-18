  
 package com.thyrohealth.controller;

import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import com.thyrohealth.model.DataLoader;
import com.thyrohealth.model.Node;
import com.thyrohealth.model.PredictionResult;
import com.thyrohealth.model.RandomForest;
import com.thyrohealth.model.RecommendationEngine;
import com.thyrohealth.model.UserInput;

public class AppController {
    private static final String CSV_PATH = "data/Demo.csv";

    private RandomForest      model;
    private boolean           trained = false;

    public AppController() {
        try {
            // 1) Load & split
            List<Node> all   = DataLoader.load(CSV_PATH);
            Collections.shuffle(all);
            int split        = (int)(all.size() * 0.8);
            List<Node> train = all.subList(0, split);
            List<Node> test  = all.subList(split, all.size());

            // 2) Train
            model   = new RandomForest(100, train.get(0).getNumFeatures());
            model.train(train);

            // 3) Evaluate
            double acc = model.evaluate(test);
            System.out.printf("Test Accuracy: %.2f%%%n", acc);

            trained = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Startup Error:\n" + ex.getMessage(),
                "Initialization Failed",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public PredictionResult predict(UserInput in) {
        if (!trained) {
            return new PredictionResult(
                false,
                List.of("Model not trained—please restart.")
            );
        }
        // 4) Get your user features as a double[]
        double[] feats = in.toArray();
        boolean mal   = model.predict(feats) == 1;
        List<String> recs = RecommendationEngine.generateRecommendations(
            in.toMap(), mal
        );
        return new PredictionResult(mal, recs);
    }
}
