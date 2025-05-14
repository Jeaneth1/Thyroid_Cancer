package com.github.jeaneth1.thyroid_project.controller;

public class AppController {
    public void processUserData (
        String age, String gender, String smokingStatus,
        String iodineLevel, String obesityStatus,
        String radiationExposure, String familyHistory,
        String diabetesLevel
    ) {         
    System.out.println("--- Data Received by Controller ---"); // This should print
    System.out.println("Controller - Age: " + age);
    System.out.println("Controller - Gender: " + gender);
    System.out.println("Controller - Smoking: " + smokingStatus);
    System.out.println("Controller - Iodine Level: " + iodineLevel);
    System.out.println("Controller - Obesity: " + obesityStatus);
    System.out.println("Controller - Radiation Exposure: " + radiationExposure);
    System.out.println("Controller - Family History: " + familyHistory);
    System.out.println("Controller - Diabetes: " + diabetesLevel); // If you added this
    System.out.println("-----------------------------------");
    }
    
}
