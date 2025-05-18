package com.thyrohealth.model;

import java.util.List;

public class PredictionResult {
    private boolean malicious;
    private List<String> recommendations;

    public PredictionResult(boolean malicious, List<String> recs) {
        this.malicious      = malicious;
        this.recommendations = recs;
    }

    public boolean isMalignant() {
        return malicious;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }
}
