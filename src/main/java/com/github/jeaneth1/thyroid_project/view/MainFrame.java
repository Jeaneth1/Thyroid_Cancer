package com.github.jeaneth1.thyroid_project.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import com.github.jeaneth1.thyroid_project.controller.AppController;


public class MainFrame extends JFrame {
    //The setup code goes inside a special method called constructor. 
    private InputPanel inputPanel; //Decalre an instance variable 
    private AppController appController;

    public MainFrame(AppController controller){
        this.appController = controller;
        //Setup Code 
        //Setting up our Title
        setTitle("ThyroHealth - Thyroid Cancer Risk Assessment");
        //We have to set the size of the window width and heights in pixels
        setSize(800, 600);
        //Close Operation 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Allow the window to appear in the middle of the screen
        setLocationRelativeTo(null);


        //Creating an instance of our InputPanel AND add input panel
        inputPanel = new InputPanel(this.appController);
        add(inputPanel, BorderLayout.CENTER);
    }
}