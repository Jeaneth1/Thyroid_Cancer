package com.github.jeaneth1.thyroid_project.controller;

import com.github.jeaneth1.thyroid_project.view.MainFrame;
import com.github.jeaneth1.thyroid_project.model.RiskAssessmentModel; // Ensure this import is correct
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AppController {
    private MainFrame mainFrame;
    private RiskAssessmentModel riskModel;

    public AppController() {
        System.out.println("AppController created.");
        this.riskModel = new RiskAssessmentModel(); // Instantiate the model
    }

    public void setMainFrame(MainFrame frame) {
        this.mainFrame = frame;
        System.out.println("AppController linked to MainFrame.");
    }

    /** Very simple CSV reader — prints each cell of each line.*/
    public void loadCsvSimple(String filePath) {
    File csvFile = new File(filePath);
    try (Scanner scanner = new Scanner(csvFile)) {
        while (scanner.hasNextLine()) {
            String[] cells = scanner.nextLine().split(",");
            for (String cell : cells) {
                System.out.print(cell.trim() + " | ");
            }
            System.out.println();
        }
    } catch (FileNotFoundException e) {
        System.err.println("CSV file not found at: " + filePath);
    }
    }

    public void processUserData(
            String ageStr, String gender, String smokingStatus,
            String iodineLevel, String obesityStatus,
            String radiationExposure, String familyHistory,
            String diabetesStatus) {

        System.out.println("--- Data Received by Controller ---");
        System.out.println("Controller - Age: " + ageStr);
        System.out.println("Controller - Gender: " + gender);
        System.out.println("Controller - Smoking: " + smokingStatus);
        System.out.println("Controller - Iodine Level: " + iodineLevel);
        System.out.println("Controller - Obesity: " + obesityStatus);
        System.out.println("Controller - Radiation Exposure: " + radiationExposure);
        System.out.println("Controller - Family History: " + familyHistory);
        System.out.println("Controller - Diabetes: " + diabetesStatus);
        System.out.println("-----------------------------------");

        List<String> hardErrors = new ArrayList<>();
        List<String> warningsOrMissing = new ArrayList<>();
        int age = -1; // Will hold the parsed age if valid

        // 1. Validate Age
        if (ageStr == null || ageStr.trim().isEmpty()) {
            hardErrors.add("Age cannot be empty.");
        } else {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 0 || age > 120) {
                    hardErrors.add("Age must be between 0 and 120. You entered: " + ageStr);
                }
            } catch (NumberFormatException e) {
                hardErrors.add("Validation Error: Age must be a valid number. You entered: " + ageStr);
            }
        }

        // 2. Check Gender
        if (gender == null || gender.equals("-- Select --")) {
            warningsOrMissing.add("Gender wasn't selected.");
        }
        // 3. Check Smoking Status
        if (smokingStatus == null || smokingStatus.equals("Unselected")) {
            warningsOrMissing.add("Smoking Status wasn't selected.");
        }
        // 4. Check Iodine Level
        if (iodineLevel == null || iodineLevel.equals("-- Select --")) {
            warningsOrMissing.add("Iodine Level wasn't selected.");
        }
        // 5. Check Obesity Status
        if (obesityStatus == null || obesityStatus.equals("Unselected")) {
            warningsOrMissing.add("Obesity status wasn't selected.");
        }
        // 6. Check Radiation Exposure
        if (radiationExposure == null || radiationExposure.equals("Unselected")) {
            warningsOrMissing.add("Radiation Exposure status wasn't selected.");
        }
        // 7. Check Family History
        if (familyHistory == null || familyHistory.equals("Unselected")) {
            warningsOrMissing.add("Family History status wasn't selected.");
        }
        // 8. Check Diabetes Status
        if (diabetesStatus == null || diabetesStatus.equals("Unselected")) {
            warningsOrMissing.add("Diabetes status wasn't selected.");
        }

        // --- Decision Logic ---
        if (mainFrame != null) {
            if (!hardErrors.isEmpty()) {
                mainFrame.displayErrors(hardErrors);
                mainFrame.displayAssessmentResult("[Assessment not performed due to errors]"); // Update result display
                System.err.println("Controller: Hard validation errors found. User must correct them.");
            } else if (!warningsOrMissing.isEmpty()) {
                mainFrame.clearErrors(); // Clear previous hard errors if any
                StringBuilder warningDialogMessage = new StringBuilder("You have not provided information for the following:\n");
                for (String currentWarning : warningsOrMissing) {
                    String simplifiedWarning = currentWarning.replace(" wasn't selected.", "").replace(" status", "");
                    warningDialogMessage.append("- ").append(simplifiedWarning).append("\n");
                }
                warningDialogMessage.append("\nAre you sure you want to proceed with the assessment missing these inputs?");
                int userChoice = JOptionPane.showConfirmDialog(mainFrame, warningDialogMessage.toString(),
                        "Confirm Missing Information", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (userChoice == JOptionPane.YES_OPTION) {
                    System.out.println("Controller: User chose to proceed despite missing info.");
                    if (riskModel != null) {
                        String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                                obesityStatus, radiationExposure, familyHistory, diabetesStatus);
                        System.out.println("Controller: Model assessment result (for console): " + assessmentResult);
                        mainFrame.displayAssessmentResult(assessmentResult); // <<--- UPDATE THE GUI ---
                    } else {
                        System.err.println("Controller: RiskModel is not initialized!");
                        mainFrame.displayAssessmentResult("[Error: Model not available]");
                    }
                } else { // User clicked No or closed the dialog
                    System.out.println("Controller: User chose not to proceed. They should update the form.");
                    mainFrame.displayErrors(warningsOrMissing); // Show the warnings in the error area
                    mainFrame.displayAssessmentResult("[Assessment cancelled by user]"); // Update result display
                }
            } else { // No hard errors and no warnings
                mainFrame.clearErrors();
                System.out.println("Controller: All input data is complete and seems valid.");
                if (riskModel != null) {
                    String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesStatus);
                    System.out.println("Controller: Model assessment result (for console): " + assessmentResult);
                    mainFrame.displayAssessmentResult(assessmentResult); // <<--- UPDATE THE GUI ---
                } else {
                    System.err.println("Controller: RiskModel is not initialized!");
                    mainFrame.displayAssessmentResult("[Error: Model not available]");
                }
            }
        } else { // Fallback if mainFrame is null
            System.err.println("AppController: MainFrame reference not set. Cannot display UI feedback.");
            if (!hardErrors.isEmpty()) {
                System.err.println("Controller: Hard validation errors found (no UI):");
                for (String error : hardErrors) { System.err.println("- " + error); }
            } else if (!warningsOrMissing.isEmpty()) {
                System.out.println("Controller: Warnings (MainFrame not available):");
                for (String warningItem : warningsOrMissing) { System.out.println("- " + warningItem); }
                System.out.println("Controller (no UI): Assuming proceeding with warnings for console output (if no hard errors).");
                 if (riskModel != null && hardErrors.isEmpty()) { // Only proceed if no hard errors
                     String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesStatus);
                    System.out.println("Controller (no UI): Model assessment result: " + assessmentResult);
                } else if (riskModel == null) {
                     System.err.println("Controller (no UI): RiskModel is not initialized!");
                }
            } else { // No hard errors, no warnings, no UI
                System.out.println("Controller (no UI): All input data is complete and seems valid.");
                 if (riskModel != null) {
                     String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesStatus);
                    System.out.println("Controller (no UI): Model assessment result: " + assessmentResult);
                } else {
                     System.err.println("Controller (no UI): RiskModel is not initialized!");
                }
            }
        }
    }
}
