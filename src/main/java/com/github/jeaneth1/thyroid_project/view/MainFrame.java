package com.github.jeaneth1.thyroid_project.view;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.JScrollPane;// Displaying error message
import java.awt.BorderLayout; //to make the text area scrollable
import java.awt.Color;//for setting text color (red for those erros)
import java.util.List; // so we can have a list of errors
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;


import com.github.jeaneth1.thyroid_project.controller.AppController;


public class MainFrame extends JFrame {
    //The setup code goes inside a special method called constructor. 
    private InputPanel inputPanel; //Decalre an instance variable 
    private AppController appController;
    private JTextArea errorTextArea;
    private JLabel resultLabel; // Label to display the assessment result



    public MainFrame(AppController controller){

        //Understanding how MVC works: Input Panel (View) needs to talk to AppController (Controller): When the user clicks the Assess Risk button in your Input Panel
        //the input panel needs to tell AppController to process the data. Thus why InputPanels construcot takes an AppController object and stores it 
        //Thus the MainFrame which create InputPanel also takes AppController in it constructor so it can pass it to InputPanel
        //APPCONTROLLER NEES TO TALK TO MAINFRAME      
        //Current set up InputPanel has it own reference to App Controller but it seems we need it this. for mainframe
        // Our mainframe doesn't have any of it own direct reasons to call methods on to the appControlller. Thus this.appController=controller not needd in mainFrame 
        //Setup Code 
        this.appController= controller;

        //Create Input Pane;, now pass the app controller 
        if (this.appController == null){
            System.err.println("MainFrame Constructor: Warning AppController recently been made");
        }

        //Creating an instance of our InputPanel AND add input panel
        inputPanel = new InputPanel(this.appController);
        add(inputPanel, BorderLayout.CENTER);

        //Setting up our Title
        setTitle("ThyroHealth - Thyroid Cancer Risk Assessment");
        //We have to set the size of the window width and heights in pixels
        setSize(800, 600);
        //Close Operation 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Allow the window to appear in the middle of the screen
        setLocationRelativeTo(null);

        //Create a panel for results and errors
        JPanel bottomPanel= new JPanel(new BorderLayout());

        //Result Display Area
        resultLabel = new JLabel("Assessment Result: [Pending]", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(resultLabel, BorderLayout.NORTH);


        
        //Create and Configure the error display area
        errorTextArea= new JTextArea(4,40);
        errorTextArea.setEditable(false); //User forbidden from typing here
        errorTextArea.setLineWrap(true); //Wrap Lines
        errorTextArea.setWrapStyleWord(true);
        errorTextArea.setForeground(Color.RED);
        JScrollPane errorScrollPane= new JScrollPane(errorTextArea); // the power of scrollbar
        bottomPanel.add(errorScrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        
    }
    public void displayErrors(List<String> errors){
        errorTextArea.setText("");
        if(errors != null && !errors.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(String error: errors){
                sb.append(error).append("\n"); // add each error on to a new line so easier to read
            }
            errorTextArea.setText(sb.toString());
        }
        errorTextArea.revalidate();
        errorTextArea.repaint();
    }

    public void clearErrors(){
        errorTextArea.setText("");
        errorTextArea.revalidate();
        errorTextArea.repaint();
    }
    public void displayAssessmentResult(String result){
         if (result != null && !result.isEmpty()) {
                resultLabel.setText("Assessment Result: " + result);

                String lowerCaseResult = result.toLowerCase();
                if (lowerCaseResult.contains("high risk")) {
                    resultLabel.setForeground(Color.RED);
                } else if (lowerCaseResult.contains("low risk")) {
                    resultLabel.setForeground(new Color(0, 128, 0)); // Dark Green
                } else if (result.toLowerCase().contains("pending") || result.toLowerCase().contains("not available") || result.toLowerCase().contains("error") || result.toLowerCase().contains("cancelled")) {
                    resultLabel.setForeground(Color.BLUE);
                } else {
                    resultLabel.setForeground(Color.BLACK);
                }
                } else {
                    resultLabel.setText("Assessment Result: [Not Available]");
                    resultLabel.setForeground(Color.GRAY);
                }
                // Crucial for UI update after changing properties
                resultLabel.revalidate();
                resultLabel.repaint();
                System.out.println("MainFrame: resultLabel text set to: " + resultLabel.getText() + " with color: " + resultLabel.getForeground());
    }
}