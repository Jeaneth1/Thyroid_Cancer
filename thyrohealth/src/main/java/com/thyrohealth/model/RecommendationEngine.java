package com.thyrohealth.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommendationEngine {
    /**
     * @param input    a map from field names ("age","gender", etc.) to their values
     * @param malignant true if the model predicted malignant (high risk)
     * @return a list of lines to display under "Recommendations"
     */
    public static List<String> generateRecommendations(Map<String,Object> input,
                                                       boolean malignant) {
        List<String> recs = new ArrayList<>();
        // 1) Overall message
        if (malignant) {
            recs.add(" High Risk detected: please consult your healthcare provider.");
        } else {
            recs.add(" Low Risk: keep up healthy habits!");
        }
        recs.add(""); // blank line

        // 2) Modifiable factors
        // Iodine
        String iod = (String) input.get("iodine");
        if ("Low".equalsIgnoreCase(iod)) {
            recs.add("- Iodine level is LOW. Eat iodine‑rich foods like fish, dairy or use iodized salt.");
        } else if ("High".equalsIgnoreCase(iod)) {
            recs.add("- Iodine level is HIGH. Avoid excess supplements; aim for normal range.");
        }

        // Smoking
        if (Boolean.TRUE.equals(input.get("smoking"))) {
            recs.add("- Smoking increases risk. Consider quitting and seeking support.");
        }

        // Obesity
        if (Boolean.TRUE.equals(input.get("obesity"))) {
            recs.add("- Obesity is a known risk factor. Adopt balanced diet and regular exercise.");
        }

        // Radiation Exposure
        if (Boolean.TRUE.equals(input.get("radiationExposure"))) {
            recs.add("- Prior radiation exposure noted. Minimize unnecessary X‑rays/CT scans if possible.");
        }

        // Family History
        if (Boolean.TRUE.equals(input.get("familyHistory"))) {
            recs.add("- Positive family history. Schedule regular thyroid screenings.");
        }

        // Diabetes
        if (Boolean.TRUE.equals(input.get("diabetes"))) {
            recs.add("- Diabetes may affect thyroid health. Manage blood sugar levels closely.");
        }

        // 3) Final tip
        if (recs.size() == 2) { // only the overall + blank, no factors added
            if (malignant) {
                recs.add("- Maintain follow‑up appointments to monitor changes.");
            } else {
                recs.add("- Continue regular check‑ups to ensure ongoing low risk.");
            }
        }

        return recs;
    }
}
