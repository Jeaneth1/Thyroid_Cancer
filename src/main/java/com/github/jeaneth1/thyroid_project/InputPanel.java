    // Ensure this package declaration matches the folder structure
    package com.github.jeaneth1.thyroid_project.view;

    // Import JPanel from the Swing library
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import jaxax.swing.JTextField;
import javax.swing.JComboBox; 
import jaxax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;  
import javax.swing.JButton; // Submit Button
import java.awt.GridLayout; // for arranging components in grid
import java.awt.FlowLayout; //arranging radio button within their own panel

    // We will import other components like JLabel, JTextField, JComboBox, JRadioButton, JButton, ButtonGroup later

    /**
     * Panel to hold all the input fields for the user's health data.
     */
    public class InputPanel extends JPanel {

        //age
        private JLabel ageLabel;
        private JTextField ageField;

        //Gender
        private JLabel genderLabel;
        private JComboBox <String> genderComboBox;
        
        //Smoking 
        private JLabel smokingLabel;
        private JRadioButton smokingYesRadio;
        private JRadioButton smokingNoRadio;
        private JRadioButton smokingIdkRadio;
        private ButtonGroup smokingGroup;

        //IodineLabel
        private JLabel iodineLabel;
        private JComboBox <String> iodineComboBox;

        //Obesity
        private JLabel obesityLabel;
        private JRadioButton obesityYesRadio;
        private JRadioButton obesityNoRadio;
        private JRadioButton obesityIdkRadio;
        private ButtonGroup obesityGroup;

        //RadiationLabel
        private Jlabel RadiationLabel;
        private JRadioButton radiationYesRadio;
        private JRadioButton radiationNoRadio;
        private JRadioButton radiationIdkRadio;
        private ButtonGroup radiationGroup;
        
        // Diabetes Label
        private Jlabel diabetesLabel;
        private JRadioButton diabetesYesRadio;
        private JRadioButton diabetesNoRadio;
        private JRadioButton diabetesIdkRadio;
        private ButtonGroup diabetesGroup;
        
        //family history
        
        private JLabel familyhistory;
        private JRadioButton yesfamilyhistory;
        private JRadioButton nofamilyhistory;
        private JRadioButton idkfamilyhistory;
        private ButtonGroup familyHistoryButtonGroup;
        
        // our submit Button 

        private JButton submitButton; 
        /**
         * Constructor: Sets up the panel and its components.
         */
        public InputPanel() {
            // Create the actual layout manager for the panel
            //Grid Layout parameters represent (rows, cols, hgap, vgap)
            setLayout(new GridLayout(0,2,10,10)); 
            // So in the row 0 means as many as we need. gaps it 10px gaps

           //AGE Creation 
           ageLabel= new JLabel("Age");
           add(ageLabel);

           ageField= new JTextField(5);
           add(ageField);

           //now lets create one for gender
           genderLabel= new JLabel ("Gender");
           add(genderLabel);
           String [] genderOptions = {"-- Select --", "Male", "Female"};
           genderComboBox= new JComboBox<>(genderOptions);
           add(genderComboBox);

        // SMOKING CREATION

        smokingLabel = new Jlabel ("Smoking");
        add (smokingLabel);
        JPanel smokingRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

        smokingYesRadio = new JRadioButton ("Yes");
        smokingNoRadio = new JRadioButton ("No");
        smokingIdkRadio = new JRadioButton ("Idk");

        // Group the radio buttons

        smokingGroup = new ButtonGroup ();
        smokingGroup.add (smokingYesRadio);
        smokingGroup.add (smokingNoRadio);
        smokingGroup.add (smokingIdkRadio);
        
        // add radio buttons to the panel
        
       smokingRadioPanel.add (smokingYesRadio);
       smokingRadioPanel.add (smokingNoRadio);
       smokingRadioPanel.add (smokingIdkRadio);
       add (smokingRadioPanel);

        

        //Iodine Level 
        
        iodineLabel= new JLabel("Iodine Level: ");
        add(iodineLabel);

        String [] idoineOptions= {"-- Select --", "Low", "Normal", "High"};
        iodineComboBox = new JComboBox <> (iodineOptions);
        add(iodineComboBox);
        

        //Obesity Label
        
        obesityLabel = new JLabel ("Obesity");
        add (obesityLabel);

        //Creating the panel
        JPanel obesityRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        
        obesityYesRadio = new JRadioButton ("Yes");
        obesityNoRadio = new JRadioButton ("No");
        obesityIdkRadio = new JRadioButton ("Idk");

        // Group obesity radio buttons

        obesityGroup = new ButtonGroup ();
        obesityGroup.add (obesityYesRadio);
        obesityGroup.add (obesityNoRadio);
        obesityGroup.add (obesityIdkRadio);

        // Add obesity radio buttons to panel
        
        obesityRadioPanel.add (obesityYesRadio);
        obesityRadioPanel.add (obesityNoRadio);
        obesityRadioPanel.add (obesityIdkRadio);
        add(obesityRadioPanel);
        
        //Radiation CREATION
        radiationLabel= new JLabel ("Radiation Exposure: ");
        add(radiationLabel);
        JPanel radiationRadioPanel= new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
        radiationYesRadio = new JRadioButton("Yes");
        radiationNoRadio= new JRadioButton("No");
        radiationIdkRadio= new JRadioButton("I Don't Know");
        radiationGroup= new ButtonGroup(); // creating our group

        //adding to the group
        radiationGroup.add(radiationYesRadio);
        radiationGroup.add(radiationNoRadio);
        radiationGroup.add(radiationIdkRadio);
        
        //adding 
        radiationRadioPanel.add(radiationYesRadio);
        radiationRadioPanel.add(radiationNoRadio);
        radiationRadioPanel.add(radiationIdkRadio);
        
        add(radiationRadioPanel);
        
        //  Diabetes Label

        diabetesLabel = new Jlabel ("Diabetes");
        add (diabetesLabel);
        JPanel diabetesRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));

        diabetesYesRadio = new JRadioButton ("yes");
        diabetesNoRadio = new JRadioButton ("No");
        diabetesIdkRadio = new JRadioButton ("Idk");

        // add diabetes 
              
        diabetesRadioPanel.add(diabetesYesRadio);
        diabetesRadioPanel.add(diabetesNoRadio);
        diabetesRadioPanel.add(diabetesIdkRadio);
        add (diabetesRadioPanel);
        
        //Family History Label 
        familyhistory= new JLabel ("Family History with Thyroid cancer");
        add(familyhistory);
        JPanel familyHistoryRadioPanel= new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        yesfamilyhistory = new JRadioButton ("Yes");
        nofamilyhistory = new JRadioButton("No");
        idkfamilyhistory = new JRadioButton("I don't know");

            // Grouping
        familyHistoryButtonGroup = new ButtonGroup();
        familyHistoryButtonGroup.add(yesfamilyhistory);
        familyHistoryButtonGroup.add(nofamilyhistory);
        familyHistoryButtonGroup.add(idkfamilyhistory);

             //add
        familyHistoryRadioPanel.add(yesfamilyhistory);
        familyHistoryRadioPanel.add(nofamilyhistory);
        familyHistoryRadioPanel.add(idkfamilyhistory);
        add(familyHistoryRadioPanel);
        

        // SUBMIT BUTTON
        add(new JLabel()); // Empty label for spacing in the first column
        submitButton = new JButton("Assess Risk");
        add(submitButton);
        // We will add an ActionListener to this button later to make it do something
        }

        // Later, we might add methods here to get the values from the input fields
        // Example: public String getAge() { return ageField.getText(); }
    }
    


