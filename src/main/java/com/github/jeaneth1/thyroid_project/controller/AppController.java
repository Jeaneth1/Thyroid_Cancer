package com.github.jeaneth1.thyroid_project.controller;
import java.util.ArrayList;
import java.util.List;
import com.github.jeaneth1.thyroid_project.view.MainFrame;
import javax.swing.JOptionPane; 


public class AppController {
    private MainFrame mainFrame;
    public AppController(){
        System.out.println("AppController created: ");
    }

    //Method for MainApp to set the reference 
    public void setMainFrame(com.github.jeaneth1.thyroid_project.view.MainFrame frame){
        this.mainFrame= frame;
        System.out.println("AppController linked to MainFrame");
    }

    public void processUserData (
        String ageStr, String gender, String smokingStatus,
        String iodineLevel, String obesityStatus,
        String radiationExposure, String familyHistory,
        String diabetesLevel
    ) { 
            
    System.out.println("--- Data Received by Controller ---"); // This should print
    System.out.println("Controller - Age: " + ageStr);
    System.out.println("Controller - Gender: " + gender);
    System.out.println("Controller - Smoking: " + smokingStatus);
    System.out.println("Controller - Iodine Level: " + iodineLevel);
    System.out.println("Controller - Obesity: " + obesityStatus);
    System.out.println("Controller - Radiation Exposure: " + radiationExposure);
    System.out.println("Controller - Family History: " + familyHistory);
    System.out.println("Controller - Diabetes: " + diabetesLevel); // If you added this
    System.out.println("-----------------------------------");

    //Start Validation
    boolean isValid = true; // Flag to track overall 
    List<String> hardErrors = new ArrayList<>();
    List<String> warningsOrMissing = new ArrayList<>();


    // Validation Age 
    //Default age is going to be an invalid age 
    int age= -1;
    if (ageStr ==null || ageStr.trim().isEmpty()){
        hardErrors.add("Age cannot be empty.");
        isValid= false;
    } else{
        try{
            age = Integer.parseInt(ageStr); 
            if (age <0 || age >120){
                hardErrors.add(" Age must be between 0 to 120");
                isValid=false;
            }
        } catch (NumberFormatException e){
            hardErrors.add("Validatin Error: Age must be a valid number. You entered " + ageStr);
            isValid= false;
        }
    }

    //Validation Gender
    if (gender == null || gender.equals("-- Select --")) {
    warningsOrMissing.add("Gender was not selected.");
   }

   //Validation Smoking  
   if (smokingStatus == null || smokingStatus.equals("Unselected")) {
            warningsOrMissing.add("Smoking Status wasn't selected.");
        }
    // 4. Validate Iodine Level
        if (iodineLevel == null || iodineLevel.equals("-- Select --")) {
            warningsOrMissing.add("Iodine Level wasn't selected.");
        }

        // 5. Validate Obesity Status
        if (obesityStatus == null || obesityStatus.equals("Unselected")) {
            warningsOrMissing.add("Obesity wasn't selected.");
        }

        // 6. Validate Radiation Exposure
        if (radiationExposure == null || radiationExposure.equals("Unselected")) {
            warningsOrMissing.add("Radiation Exposure wasn't selected.");
        }

        // 7. Validate Family History
        if (familyHistory == null || familyHistory.equals("Unselected")) {
            warningsOrMissing.add("Family History wasn't selected.");
        }

        // 8. Validate Diabetes Status
        if (diabetesLevel == null || diabetesLevel.equals("Unselected")) {
            warningsOrMissing.add("Diabetes Status wasn't selected.");
        }
        // --- End Validation ---

        // Our Decision Logic 
        if (mainFrame != null) {
            if (!hardErrors.isEmpty()){
                //IF THERE ARE HARD ERRORS, IT WILL DISPLAY them and stop and user must fix these
                mainFrame.displayErrors(hardErrors);
                System.err.println("Controller: Hard validation errors found. USER must correct age");
            } else if (!warningsOrMissing.isEmpty()){
                //No hard error, but some items are missing or unselected. Ask the user
                mainFrame.clearErrors(); // clear any previous hard errors
               
                
                //Message to the user
                StringBuilder warningMessage= new StringBuilder("You nave not provided information for the following: \n");
                for (String warning: warningsOrMissing){
                    warningMessage.append("- ").append(warning.replace(" was not selected", " ")).append("\n");
                }
                warningMessage.append("\nAre you sure you want to proceed with the assessment missing theses inputs");

                //Panel logic giving option to proceed or not
                int userChoice= JOptionPane.showConfirmDialog(mainFrame, warningMessage.toString(),"Confirm Missing Information", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                //JOptionPanel Logic behidn the yes or no
                if (userChoice == JOptionPane.YES_OPTION){
                    System.out.println("Controller: User chose to proceed despite missing info ");
                    //TODO: Pass data to the Model (8 attributes)
                    //Keep account of how the model will treat unselectedd or select appropriately
                    System.out.println("Controller: Data (including unselected/default values) would be passed to the Model ");
                } else {
                    System.out.println("Controller: User chose not to proceed. They should update the form ");
                    mainFrame.displayErrors(warningsOrMissing);
                }
            }else {
                //No hard errors and no warnings/missing items. PROCEED WITH NO ISSUES
                mainFrame.clearErrors();
                System.out.println("Controller: All input data is complete and seems valid. ");
                //TODO: Pass data to the Model 8 attributes

            }
            // TODO: If valid, pass to the Model for risk assessment
            // For example: riskModel.calculateRisk(age, gender, smokingStatus, ...);
            // Note: For the model, you'll likely convert "Yes"/"No"/"Don't Know" to boolean or numerical representations.
        } else { // This is the block if mainFrame is null
            System.err.println("AppController: MainFrame reference not set. Cannot display UI feedback. ");
            //if app controller can't access the mainframe reference we will fallback to the console
            if (!hardErrors.isEmpty()) {
                for (String error : hardErrors) {
                    System.err.println("- " + error); // Added a dash for clarity
                }
            } else if (!warningsOrMissing.isEmpty()) {
                System.out.println("Controller: Warnings (MainFrame not available):"); // Clarified message
                for (String error : warningsOrMissing) { // The loop variable is 'error'
                    System.out.println("- Warning: " + error); // Corrected to use 'error'
                }
            }
        }
    }
    
}
