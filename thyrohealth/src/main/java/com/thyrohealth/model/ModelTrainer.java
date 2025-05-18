package com.thyrohealth.model;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class ModelTrainer {

    /**
     * 1) normalize numeric attrs
     * 2) shuffle & split 80/20
     * 3) 5‑fold CV tune numTrees & maxDepth
     * 4) train final RF
     * 5) print test accuracy
     */
    public RandomForest train(Instances data) throws Exception {
        // Make sure class is last attribute
        data.setClassIndex(data.numAttributes() - 1);

        // 1) normalize all features to [0,1]
        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normed = Filter.useFilter(data, normalize);

        // 2) randomize and split 80/20
        normed.randomize(new Random(42));
        int trainSize = (int)(normed.numInstances() * 0.8);
        Instances train = new Instances(normed, 0, trainSize);
        Instances test  = new Instances(normed, trainSize, normed.numInstances() - trainSize);

        train.setClassIndex(train.numAttributes() - 1);
        test .setClassIndex(test .numAttributes() - 1);

        // 3) CV‑parameter search on the training set
        CVParameterSelection cv = new CVParameterSelection();
        cv.setClassifier(new RandomForest());
        cv.setNumFolds(5);
        // search numTrees I from 50 to 300 step 50
        cv.addCVParameter("I 50 300 50");
        // search maxDepth D from 5 to 20 step 5
        cv.addCVParameter("depth 5 20 5");
        cv.buildClassifier(train);

        // 4) train final RF with the best found options
        String[] bestOpts = cv.getBestClassifierOptions();
        RandomForest rf = new RandomForest();
        rf.setOptions(bestOpts);
        rf.buildClassifier(train);

        // 5) evaluate on hold‑out test set
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(rf, test);
        System.out.printf(
            "Tuned RF [%s] → Test Accuracy: %.2f%%%n",
            String.join(" ", bestOpts),
            eval.pctCorrect()
        );

        return rf;
    }
}
