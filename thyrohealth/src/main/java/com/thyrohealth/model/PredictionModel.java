package com.thyrohealth.model;

import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.DenseInstance;
import java.util.Map;

public class PredictionModel {

    private RandomForest classifier;
    private Instances header;

    public PredictionModel(RandomForest classifier, Instances header) {
        this.classifier = classifier;
        this.header     = header;
    }

    /**
     * Builds a single-instance from the user map and returns true if 'Diagnosis' == 1.
     */
    public boolean predict(Map<String,Object> inputData) throws Exception {
        Instance inst = new DenseInstance(header.numAttributes());
        inst.setDataset(header);

        inst.setValue(header.attribute("Age"),               (int)   inputData.get("Age"));
        inst.setValue(header.attribute("Gender"),            (String)inputData.get("Gender"));
        inst.setValue(header.attribute("Iodine"),            (String)inputData.get("Iodine"));
        inst.setValue(header.attribute("Smoking"),           (Boolean)inputData.get("Smoking")  ? 1.0 : 0.0);
        inst.setValue(header.attribute("Obesity"),           (Boolean)inputData.get("Obesity")  ? 1.0 : 0.0);
        inst.setValue(header.attribute("Radiation Exposure"),(Boolean)inputData.get("Radiation Exposure") ? 1.0 : 0.0);
        inst.setValue(header.attribute("Family History"),    (Boolean)inputData.get("Family History")    ? 1.0 : 0.0);
        inst.setValue(header.attribute("Diabetes"),          (Boolean)inputData.get("Diabetes")         ? 1.0 : 0.0);

        inst.setMissing(header.classIndex());  // we want to predict this

        double clsIdx = classifier.classifyInstance(inst);
        String  clsVal = header.classAttribute().value((int)clsIdx);
        return "1".equals(clsVal);
    }
}

