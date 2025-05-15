package com.github.jeaneth1.thyroid_project.controller;

import java.util.ArrayList;
import java.util.List;
import com.github.jeaneth1.thyroid_project.view.MainFrame;
import javax.swing.JOptionPane;
import com.github.jeaneth1.thyroid_project.model.*;

public class AppController {
    private MainFrame mainFrame;
    private RiskAssessmentModel riskModel; // Instance of RiskAssessmentModel

    public AppController() {
        System.out.println("AppController created."); // Corrected colon placement
        this.riskModel = new RiskAssessmentModel(); // Instantiate the model
    }

    // Method for MainApp to set the reference
    public void setMainFrame(MainFrame frame) { // Can use short name 'MainFrame' due to import
        this.mainFrame = frame;
        System.out.println("AppController linked to MainFrame.");
    }

    public void processUserData(
            String ageStr, String gender, String smokingStatus,
            String iodineLevel, String obesityStatus,
            String radiationExposure, String familyHistory,
            String diabetesLevel // Using diabetesLevel as per your variable
    ) {

        System.out.println("--- Data Received by Controller ---");
        System.out.println("Controller - Age: " + ageStr);
        System.out.println("Controller - Gender: " + gender);
        System.out.println("Controller - Smoking: " + smokingStatus);
        System.out.println("Controller - Iodine Level: " + iodineLevel);
        System.out.println("Controller - Obesity: " + obesityStatus);
        System.out.println("Controller - Radiation Exposure: " + radiationExposure);
        System.out.println("Controller - Family History: " + familyHistory);
        System.out.println("Controller - Diabetes: " + diabetesLevel);
        System.out.println("-----------------------------------");

        // Start Validation
        // boolean isValid = true; // This flag is okay, but hardErrors.isEmpty() is more direct for the final decision
        List<String> hardErrors = new ArrayList<>();
        List<String> warningsOrMissing = new ArrayList<>();

        // Validation Age
        int age = -1; // Default age
        if (ageStr == null || ageStr.trim().isEmpty()) {
            hardErrors.add("Age cannot be empty.");
            // isValid = false; // Not strictly needed if checking hardErrors.isEmpty()
        } else {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 0 || age > 120) {
                    hardErrors.add("Age must be between 0 and 120. You entered: " + ageStr);
                    // isValid = false;
                }
            } catch (NumberFormatException e) {
                hardErrors.add("Validation Error: Age must be a valid number. You entered: " + ageStr); // Corrected "Validatin"
                // isValid = false;
            }
        }

        // Validation Gender
        if (gender == null || gender.equals("-- Select --")) {
            warningsOrMissing.add("Gender wasn't selected.");
        }

        // Validation Smoking
        if (smokingStatus == null || smokingStatus.equals("Unselected")) {
            warningsOrMissing.add("Smoking Status wasn't selected.");
        }
        // 4. Validate Iodine Level
        if (iodineLevel == null || iodineLevel.equals("-- Select --")) {
            warningsOrMissing.add("Iodine Level wasn't selected.");
        }

        // 5. Validate Obesity Status
        if (obesityStatus == null || obesityStatus.equals("Unselected")) {
            warningsOrMissing.add("Obesity status wasn't selected.");
        }

        // 6. Validate Radiation Exposure
        if (radiationExposure == null || radiationExposure.equals("Unselected")) {
            warningsOrMissing.add("Radiation Exposure status wasn't selected.");
        }

        // 7. Validate Family History
        if (familyHistory == null || familyHistory.equals("Unselected")) {
            warningsOrMissing.add("Family History status wasn't selected.");
        }

        // 8. Validate Diabetes Status
        if (diabetesLevel == null || diabetesLevel.equals("Unselected")) {
            warningsOrMissing.add("Diabetes status wasn't selected.");
        }
        // --- End Validation ---

        // Our Decision Logic
        if (mainFrame != null) {
            if (!hardErrors.isEmpty()) {
                // IF THERE ARE HARD ERRORS, IT WILL DISPLAY them and stop and user must fix these
                mainFrame.displayErrors(hardErrors);
                System.err.println("Controller: Hard validation errors found. User must correct them."); // Corrected message
            } else if (!warningsOrMissing.isEmpty()) {
                // No hard error, but some items are missing or unselected. Ask the user
                mainFrame.clearErrors(); // clear any previous hard errors

                // Message to the user
                StringBuilder warningDialogMessage = new StringBuilder("You have not provided information for the following:\n"); // Corrected "nave"
                for (String currentWarning : warningsOrMissing) { // Loop variable is 'currentWarning'
                    // Simplify the message for the dialog
                    String simplifiedWarning = currentWarning.replace(" wasn't selected.", "").replace(" status", "");
                    warningDialogMessage.append("- ").append(simplifiedWarning).append("\n");
                }
                warningDialogMessage.append("\nAre you sure you want to proceed with the assessment missing these inputs?"); // Corrected "theses"

                // Panel logic giving option to proceed or not
                int userChoice = JOptionPane.showConfirmDialog(mainFrame, warningDialogMessage.toString(),
                        "Confirm Missing Information", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                // JOptionPane Logic behind the yes or no
                if (userChoice == JOptionPane.YES_OPTION) {
                    System.out.println("Controller: User chose to proceed despite missing info.");
                    if (riskModel != null) {
                        String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                                obesityStatus, radiationExposure, familyHistory, diabetesLevel);
                        System.out.println("Controller: Model assessment result: " + assessmentResult);
                        // TODO: Display this assessmentResult in the MainFrame
                    } else {
                        System.err.println("Controller: RiskModel is not initialized!");
                    }
                } else {
                    System.out.println("Controller: User chose not to proceed. They should update the form.");
                    mainFrame.displayErrors(warningsOrMissing); // Show the warnings in the UI error area
                }
            } else {
                // No hard errors and no warnings/missing items. PROCEED WITH NO ISSUES
                mainFrame.clearErrors();
                System.out.println("Controller: All input data is complete and seems valid.");
                if (riskModel != null) {
                    String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesLevel);
                    System.out.println("Controller: Model assessment result: " + assessmentResult);
                    // TODO: Display this assessmentResult in the MainFrame
                } else {
                    System.err.println("Controller: RiskModel is not initialized!");
                }
            }
        } else { // This is the block if mainFrame is null
            System.err.println("AppController: MainFrame reference not set. Cannot display UI feedback.");
            // if app controller can't access the mainframe reference we will fallback to the console
            if (!hardErrors.isEmpty()) {
                System.err.println("Controller: Hard validation errors found (no UI):"); // Added context
                for (String error : hardErrors) { // Loop variable is 'error'
                    System.err.println("- " + error); // Using 'error'
                }
            } else if (!warningsOrMissing.isEmpty()) {
                System.out.println("Controller: Warnings (MainFrame not available):");
                for (String warningItem : warningsOrMissing) { // Changed loop variable to 'warningItem' for clarity
                    System.out.println("- " + warningItem); // CRITICAL FIX: Use the loop variable 'warningItem' (was 'warning' before)
                }
                // If no UI, and user would have been prompted, decide if we proceed to model for console output
                System.out.println("Controller (no UI): Assuming proceeding with warnings for console output.");
                if (riskModel != null) {
                    String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesLevel);
                    System.out.println("Controller (no UI): Model assessment result: " + assessmentResult);
                } else {
                    System.err.println("Controller (no UI): RiskModel is not initialized!");
                }
            } else { // No hard errors, no warnings, no UI
                System.out.println("Controller (no UI): All input data is complete and seems valid.");
                if (riskModel != null) {
                    String assessmentResult = riskModel.assessRisk(age, gender, smokingStatus, iodineLevel,
                            obesityStatus, radiationExposure, familyHistory, diabetesLevel);
                    System.out.println("Controller (no UI): Model assessment result: " + assessmentResult);
                } else {
                    System.err.println("Controller (no UI): RiskModel is not initialized!");
                }
            }
        }
    }
}
