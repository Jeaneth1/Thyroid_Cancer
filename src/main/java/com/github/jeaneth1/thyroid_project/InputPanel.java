    // Ensure this package declaration matches the folder structure
    package com.github.jeaneth1.thyroid_project.view;

    // Import JPanel from the Swing library
    import javax.swing.JPanel;
    // We will import other components like JLabel, JTextField, JComboBox, JRadioButton, JButton, ButtonGroup later

    /**
     * Panel to hold all the input fields for the user's health data.
     */
    public class InputPanel extends JPanel {

        // We will declare our UI components (labels, text fields, dropdowns, etc.) as instance variables here
        // Example: private JLabel ageLabel;
        // Example: private JTextField ageField;

        /**
         * Constructor: Sets up the panel and its components.
         */
        public InputPanel() {
            // 1. Set a layout manager for this panel (important for arranging components)
            //    Example: setLayout(new GridLayout(0, 2)); // 0 rows, 2 columns

            // 2. Create and add the UI components (labels, input fields, button)
            //    We will do this step-by-step next.
            //    Example:
            //    ageLabel = new JLabel("Age:");
            //    add(ageLabel);
            //    ageField = new JTextField(5); // 5 columns wide
            //    add(ageField);

            //    // ... and so on for Gender, Smoking, etc. ...

            //    // Add the "Assess Risk" button
            //    assessButton = new JButton("Assess Risk");
            //    add(assessButton);
        }

        // Later, we might add methods here to get the values from the input fields
        // Example: public String getAge() { return ageField.getText(); }
    }
    
