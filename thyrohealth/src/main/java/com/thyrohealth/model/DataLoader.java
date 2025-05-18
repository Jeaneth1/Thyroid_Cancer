package com.thyrohealth.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<Node> load(String csvPath) throws IOException {
        List<Node> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            // skip both header rows
            br.readLine(); // column names
            br.readLine(); // data types

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(",");
                if (t.length < 10) continue;

                double[] feats = new double[8];
                feats[0] = Integer.parseInt(t[1].trim());                   // Age
                feats[1] = t[2].equalsIgnoreCase("Male")   ? 1.0 : 0.0;     // Gender
                feats[2] = t[3].equalsIgnoreCase("Low")    ? 0.0           // Iodine
                         : t[3].equalsIgnoreCase("Normal") ? 1.0 : 2.0;
                // Boolean features: Yes/No
                feats[3] = t[4].equalsIgnoreCase("Yes")    ? 1.0 : 0.0;     // Smoking
                feats[4] = t[5].equalsIgnoreCase("Yes")    ? 1.0 : 0.0;     // Obesity
                feats[5] = t[6].equalsIgnoreCase("Yes")    ? 1.0 : 0.0;     // RadiationExposure
                feats[6] = t[7].equalsIgnoreCase("Yes")    ? 1.0 : 0.0;     // FamilyHistory
                feats[7] = t[8].equalsIgnoreCase("Yes")    ? 1.0 : 0.0;     // Diabetes

                // —— FIXED LABEL PARSING ——  
                // Diagnosis is also Yes/No
                int label = t[9].trim().equalsIgnoreCase("Yes") ? 1 : 0;

                data.add(new Node(feats, label));
            }
        }
        return data;
    }
}
