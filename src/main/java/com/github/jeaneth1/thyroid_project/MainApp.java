package com.github.jeaneth1.thyroid_project;
import com.github.jeaneth1.thyroid_project.view.MainFrame;
import javax.swing.SwingUtilities;
public class MainApp {
    public static void main(String[] args) {
       //SwingUtilities.invokeLater to ensure UI Code runs on the Event Dispatch Thread
       SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
            //Create an instance of our main window class 
            MainFrame mainFrame = new MainFrame();
            //To make the frame visible we need to use this function
            mainFrame.setVisible(true);
        }
       });
    }
}
 