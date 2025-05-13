package com.github.jeaneth1.thyroid_project.view;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    //The setup code goes inside a special method called constructor. 
    public MainFrame(){
        //Setup Code 
        //Setting up our Title
        setTitle("ThyroHealth - Thyroid Cancer Risk Assessment");
        //We have to set the size of the window width and heights in pixels
        setSize(600, 400);
        //Close Operation 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Allow the window to appear in the middle of the screen
        setLocationRelativeTo(null);
    }

}