package com.github.jeaneth1.thyroid_project.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException; // Import for specific file not found exception
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiskAssessmentModel {

    private List<String[]> dataset; // To store the loaded CSV data
    private String[] headers;       // Optional: to store the header row

    public RiskAssessmentModel() {
        System.out.println("RiskAssessmentModel created.");
        this.dataset = new ArrayList<>(); // Initialize the list
        loadCsvData("data/demo.csv");     // Load the CSV data when the model is created
                                          // Ensure demo.csv is in a 'data' folder at the project root
    }

    /**
     * Loads data from a specified CSV file path into the 'dataset' list.
     * @param filePath The path to the CSV file (e.g., "data/demo.csv")
     */
    private void loadCsvData(String filePath) {
        System.out.println("RiskAssessmentModel: Attempting to load CSV data from: '" + filePath + "'");
        String line = "";
        String csvSplitBy = ","; // Assuming your CSV is comma-separated

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read and process the header line
            String headerLine = br.readLine();
            if (headerLine != null && !headerLine.trim().isEmpty()) {
                System.out.println("Raw Header Line Read: [" + headerLine.trim() + "]");
                this.headers = headerLine.trim().split(csvSplitBy);
                // Trim each header
                for (int i = 0; i < this.headers.length; i++) {
                    this.headers[i] = this.headers[i].trim();
                }
                System.out.println("Processed Headers: " + Arrays.toString(this.headers));
            } else if (headerLine != null && headerLine.trim().isEmpty()) {
                System.err.println("RiskAssessmentModel: Warning - The first line of the CSV is empty. Attempting to read next line as header or assuming no header if file continues.");
            } else { // headerLine is null
                System.err.println("RiskAssessmentModel: Error - CSV file is empty or header line could not be read.");
                return; // Stop processing if the file is empty or only header is missing
            }

            // Read the rest of the data lines
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip any other blank lines in the data
                    continue;
                }
                String[] rowData = line.split(csvSplitBy);
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = rowData[i].trim();
                }
                dataset.add(rowData);
                rowCount++;
            }
            System.out.println("RiskAssessmentModel: Successfully loaded " + rowCount + " data rows (actual data, excluding processed header).");

            // Optional: Print a few rows to verify
            if (!dataset.isEmpty()) {
                System.out.println("Verifying first few loaded data rows:");
                for (int i = 0; i < Math.min(5, dataset.size()); i++) { // Print up to first 5 data rows
                    System.out.println("Data Row " + i + ": " + Arrays.toString(dataset.get(i)));
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("RiskAssessmentModel: ERROR - CSV File NOT FOUND at '" + filePath + "'. Please check the path and filename. Ensure it's in a 'data' folder at the root of your project.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("RiskAssessmentModel: IOException while loading CSV data from '" + filePath + "'.");
            e.printStackTrace();
        }
    }

    // Getter for the dataset (optional, but can be useful for other methods)
    public List<String[]> getDataset() {
        return dataset;
    }

    // Getter for headers (optional)
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Placeholder method to perform risk assessment.
     * It receives data from the controller.
     * Eventually, it will use the Random Forest model and the loaded dataset.
     */
    public String assessRisk(
            int age, String gender, String smokingStatus,
            String iodineLevel, String obesityStatus,
            String radiationExposure, String familyHistory,
            String diabetesStatus
    ) {
        System.out.println("--- RiskAssessmentModel: Received data for assessment ---");
        System.out.println("Model - Age: " + age);
        System.out.println("Model - Gender: " + gender);
        System.out.println("Model - Smoking: " + smokingStatus);
        System.out.println("Model - Iodine Level: " + iodineLevel);
        System.out.println("Model - Obesity: " + obesityStatus);
        System.out.println("Model - Radiation Exposure: " + radiationExposure);
        System.out.println("Model - Family History: " + familyHistory);
        System.out.println("Model - Diabetes: " + diabetesStatus);
        System.out.println("---------------------------------------------------------");

        // TODO:
        // 1. Preprocess the user's input data (age, gender, smokingStatus, etc.)
        //    to match the numerical format expected by your Random Forest model.
        //    This includes handling "Unselected", "-- Select --", "Don't Know" by converting
        //    them into a consistent representation (e.g., a specific number, or using an imputation technique).
        //
        // 2. Use the loaded `this.dataset` if it's needed for reference group matching or other parts
        //    of your Random Forest logic (as described in your PDF).
        //
        // 3. Load your pre-trained Random Forest model (if not already loaded in the constructor).
        // 4. Feed the preprocessed user input into the model to get a prediction.
        // 5. Determine the classification (Benign/Malignant) based on the model's output.
        // 6. (Optional) Generate a risk score from the model's probability output.
        // 7. (Optional) Generate recommendations based on the input and prediction.

        // For now, return a simple placeholder result based on some dummy logic
        String result;
        if (age > 60 || (smokingStatus != null && smokingStatus.equals("Yes")) || (familyHistory != null && familyHistory.equals("Yes"))) {
            result = "High Risk (Placeholder)";
        } else {
            result = "Low Risk (Placeholder)";
        }
        System.out.println("Model - Assessment Result: " + result);
        return result;
    }
}
