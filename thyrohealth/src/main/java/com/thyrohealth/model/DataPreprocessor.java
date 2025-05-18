
    package com.thyrohealth.model;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.StringToNominal;

public class DataPreprocessor {

    private Instances header;

    /**
     * Loads the CSV, converts Gender & Iodine (string) → nominal,
     * converts the last (Diagnosis) numeric → nominal, and sets class index.
     */
    public Instances loadAndPreprocess(String csvPath) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(csvPath));
        Instances data = loader.getDataSet();

        File f = new File("data/thyroid_cancer_risk_dataset.csv");
System.out.println("Loading from: " + f.getAbsolutePath());
loader.setSource(f);


        // 1) Convert Gender (2) & Iodine (3) from string → nominal
        StringToNominal str2nom = new StringToNominal();
        str2nom.setAttributeRange("2,3");
        str2nom.setInputFormat(data);
        Instances nominalData = Filter.useFilter(data, str2nom);

        // 2) Convert the class (last) numeric → nominal
        NumericToNominal num2nom = new NumericToNominal();
        num2nom.setAttributeIndices("last");
        num2nom.setInputFormat(nominalData);
        Instances finalData = Filter.useFilter(nominalData, num2nom);

        finalData.setClassIndex(finalData.numAttributes() - 1);
        header = new Instances(finalData, 0);  // keep empty header for making new instances
        return finalData;
    }

    public Instances getHeader() {
        return header;
    }
}
