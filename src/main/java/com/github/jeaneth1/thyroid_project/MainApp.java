package com.github.jeaneth1.thyroid_project;
import com.github.jeaneth1.thyroid_project.view.MainFrame;
import javax.swing.SwingUtilities;
import com.github.jeaneth1.thyroid_project.controller.AppController;

public class MainApp {
    public static void main(String[] args) {
       //SwingUtilities.invokeLater to ensure UI Code runs on the Event Dispatch Thread
       SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run(){
            //Create the Controller and it doesn't know about the MainFrame yet
            AppController controller = new AppController();

            // Here: load your CSV
            controller.loadCsvSimple("Demo.csv");

            //Create an instance of our main window class and give IT a reference to the Controller
            MainFrame mainFrameInstance = new MainFrame(controller);

            //The controller doesn't have a reference to the MainFrame however this code will do this
            //The controller can send messages/updates back to the mainframe
            controller.setMainFrame(mainFrameInstance); //where the link is being made from App Controller
            //To make the frame visible we need to use this function
            mainFrameInstance.setVisible(true);
        }
       });
    }
}
 