package com.thyrohealth.model;

public class Node {
    private final double[] features;
    private final int      label;

    public Node(double[] features, int label) {
        this.features = features.clone();
        this.label    = label;
    }

    /** Return the feature at idx, or NaN if idx is invalid. */
    public double getFeature(int idx) {
        if (idx < 0 || idx >= features.length) {
            return Double.NaN;
        }
        return features[idx];
    }

    public int getLabel() {
        return label;
    }

    public int getNumFeatures() {
        return features.length;
    }

    /** Returns a defensive copy of the feature vector. */
    public double[] getFeatures() {
        return features.clone();
    }
}
