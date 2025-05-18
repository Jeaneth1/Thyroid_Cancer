
    package com.thyrohealth.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RandomForest {
    private final List<DecisionTree> trees = Collections.synchronizedList(new ArrayList<>());
    private final int numTrees, maxFeatures;
    private final Random rand = new Random();

    public RandomForest(int numTrees, int maxFeatures) {
        this.numTrees    = numTrees;
        this.maxFeatures = maxFeatures;
    }

    public void train(List<Node> data) {
        int totalF = data.get(0).getNumFeatures();
        ExecutorService exec = Executors.newFixedThreadPool(
            Math.min(Runtime.getRuntime().availableProcessors(), numTrees)
        );
        CountDownLatch latch = new CountDownLatch(numTrees);

        for (int i = 0; i < numTrees; i++) {
            exec.submit(() -> {
                try {
                    // bootstrap sample
                    List<Node> sample = new ArrayList<>(data);
                    Collections.shuffle(sample, rand);

                    DecisionTree tree = new DecisionTree(maxFeatures, totalF);
                    tree.train(sample);
                    trees.add(tree);
                } finally {
                    latch.countDown();
                }
            });
        }

        exec.shutdown();
        try {
            latch.await(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int predict(double[] feats) {
        int[] votes = new int[2];
        for (DecisionTree t : trees) {
            int p = t.predict(feats);
            votes[p]++;
        }
        return votes[1] > votes[0] ? 1 : 0;
    }

    public double evaluate(List<Node> test) {
        int correct = 0;
        for (Node n : test) {
            // ← NOW using getFeatures()
            int pred = predict(n.getFeatures());
            if (pred == n.getLabel()) correct++;
        }
        return 100.0 * correct / test.size();
    }
}
