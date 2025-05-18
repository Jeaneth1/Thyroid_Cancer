package com.thyrohealth.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
 import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.thyrohealth.controller.AppController;
import com.thyrohealth.model.PredictionResult;
import com.thyrohealth.model.UserInput;

public class MainFrame extends JFrame {
    private final AppController controller;

    private JTextField ageField;
    private JComboBox<String> genderBox, iodineBox;
    private JCheckBox smokingBox, obesityBox, radiationBox, familyHistoryBox, diabetesBox;
    private JButton predictButton;
    private JTextArea resultArea;

    public MainFrame(AppController controller) {
        this.controller = controller;
        setTitle("ThyroHealth: Thyroid Risk Predictor");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(0,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        form.add(new JLabel("Age:"));
        ageField = new JTextField(); form.add(ageField);

        form.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Male","Female"});
        form.add(genderBox);

        form.add(new JLabel("Iodine Level:"));
        iodineBox = new JComboBox<>(new String[]{"Low","Normal","High"});
        form.add(iodineBox);

        form.add(new JLabel("Smoking:"));         smokingBox = new JCheckBox("Smoker?");         form.add(smokingBox);
        form.add(new JLabel("Obesity:"));         obesityBox = new JCheckBox("Obese?");          form.add(obesityBox);
        form.add(new JLabel("Radiation Exposure:")); radiationBox = new JCheckBox("Exposed?");     form.add(radiationBox);
        form.add(new JLabel("Family History:"));  familyHistoryBox = new JCheckBox("Yes");       form.add(familyHistoryBox);
        form.add(new JLabel("Diabetes:"));        diabetesBox = new JCheckBox("Yes");            form.add(diabetesBox);

        predictButton = new JButton("Predict Risk");
        predictButton.addActionListener(e -> onPredict());
        form.add(predictButton);

        resultArea = new JTextArea(10,30);
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);

        getContentPane().add(form,   BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void onPredict() {
        try {
            UserInput input = new UserInput(
                Integer.parseInt(ageField.getText()),
                (String)genderBox.getSelectedItem(),
                (String)iodineBox.getSelectedItem(),
                smokingBox.isSelected(),
                obesityBox.isSelected(),
                radiationBox.isSelected(),
                familyHistoryBox.isSelected(),
                diabetesBox.isSelected()
            );
            PredictionResult res = controller.predict(input);

            StringBuilder sb = new StringBuilder()
                .append("Prediction: ")
                .append(res.isMalignant() ? "Malignant (High Risk)" : "Benign (Low Risk)")
                .append("\n\nRecommendations:\n");
            for (String r : res.getRecommendations())
                sb.append(" - ").append(r).append("\n");

            resultArea.setText(sb.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid integer for age.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
