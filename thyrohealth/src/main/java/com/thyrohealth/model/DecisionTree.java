package com.thyrohealth.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DecisionTree {
    // simple binary tree node
    private abstract class TreeNode {}
    private class DecisionNode extends TreeNode {
        int featureIndex;
        double threshold;
        TreeNode left, right;
        DecisionNode(int f, double t, TreeNode l, TreeNode r) {
            featureIndex = f;
            threshold    = t;
            left         = l;
            right        = r;
        }
    }
    private class LeafNode extends TreeNode {
        int label;
        LeafNode(int label) { this.label = label; }
    }

    private final int maxFeatures;
    private final Random rand;
    private TreeNode root;

    public DecisionTree(int maxFeatures, int totalFeatures) {
        this.maxFeatures = maxFeatures;
        this.rand        = new Random();
    }

    public void train(List<Node> data) {
        this.root = buildTree(data);
    }

    public int predict(double[] feats) {
        TreeNode node = root;
        while (!(node instanceof LeafNode)) {
            DecisionNode dn = (DecisionNode) node;
            if (feats[dn.featureIndex] <= dn.threshold) {
                node = dn.left;
            } else {
                node = dn.right;
            }
        }
        return ((LeafNode) node).label;
    }

    /* --- private helpers below --- */

    private TreeNode buildTree(List<Node> data) {
        if (data.isEmpty() || isPure(data)) {
            return new LeafNode(majorityLabel(data));
        }
        int totalF = data.get(0).getNumFeatures();
        // select m features at random
        int[] features = rand.ints(0, totalF)
                             .distinct()
                             .limit(maxFeatures)
                             .toArray();

        Split best = findBestSplit(data, features);
        if (best == null || best.gain <= 0) {
            return new LeafNode(majorityLabel(data));
        }
        TreeNode left  = buildTree(best.left);
        TreeNode right = buildTree(best.right);
        return new DecisionNode(best.feature, best.threshold, left, right);
    }

    private boolean isPure(List<Node> data) {
        int first = data.get(0).getLabel();
        return data.stream().allMatch(n -> n.getLabel() == first);
    }

    private int majorityLabel(List<Node> data) {
        int[] counts = new int[2];
        data.forEach(n -> counts[n.getLabel()]++);
        return counts[1] > counts[0] ? 1 : 0;
    }

    private double gini(List<Node> data) {
        if (data.isEmpty()) return 0;
        int[] counts = new int[2];
        data.forEach(n -> counts[n.getLabel()]++);
        double imp = 1;
        for (int c : counts) {
            double p = (double) c / data.size();
            imp -= p*p;
        }
        return imp;
    }

    private Split findBestSplit(List<Node> data, int[] feats) {
        double baseImp = gini(data);
        Split best     = null;
        for (int f : feats) {
            // gather candidate thresholds
            Set<Double> uniq = new HashSet<>();
            data.forEach(n -> uniq.add(n.getFeature(f)));
            for (double t : uniq) {
                List<Node> left  = new ArrayList<>();
                List<Node> right = new ArrayList<>();
                for (Node n : data) {
                    if (n.getFeature(f) <= t) left.add(n);
                    else right.add(n);
                }
                if (left.isEmpty() || right.isEmpty()) continue;
                double imp = (left.size()*gini(left) + right.size()*gini(right))
                             / data.size();
                double gain = baseImp - imp;
                if (gain > 0 && (best==null || gain>best.gain)) {
                    best = new Split(f, t, left, right, gain);
                }
            }
        }
        return best;
    }

    private static class Split {
        int feature;
        double threshold;
        List<Node> left, right;
        double gain;
        Split(int f,double t,List<Node> l,List<Node> r,double g){
            feature=f;threshold=t;left=l;right=r;gain=g;
        }
    }
}
