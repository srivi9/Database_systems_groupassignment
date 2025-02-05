package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class CreatePolicyDialog extends JDialog {
    private JTextField P_HOLDER_ID;
    private JTextField FIRST_NAME; // Combined name into one field
    private JTextField LAST_NAME;
    private JTextField DAY; // Day part of DOB
    private JComboBox<String> MONTH; // Month part of DOB
    private JTextField YEAR; // Year part of DOB
    private JTextField OMANG; // Omang Number
    private JTextField ADDRESS; // Address
    private JFormattedTextField PH_PHONE_NUMBER; // Phone Number
    private JTextField PH_EMAIL; // Email
    private JButton createButton;
    private JButton cancelButton;

    // Constructor
    public CreatePolicyDialog(Frame parent) {
        super(parent, "Create Policy Holder", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Policy Holder ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Policy Holder ID:"), gbc);
        gbc.gridx = 1;
        P_HOLDER_ID = new JTextField(20);
        add(P_HOLDER_ID, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        FIRST_NAME = new JTextField(20);
        add(FIRST_NAME, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        LAST_NAME = new JTextField(20);
        add(LAST_NAME, gbc);

        // Date of Birth (DD-MMM-YY format)
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Date of Birth (DD-MMM-YY):"), gbc);

        gbc.gridx = 1;
        // Day input
        DAY = new JTextField(2);  // Two digits for day
        add(DAY, gbc);

        // Month dropdown
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        MONTH = new JComboBox<>(months);
        MONTH.setPreferredSize(new Dimension(60, 20));
        gbc.gridx = 2;
        add(MONTH, gbc);

        // Year input
        YEAR = new JTextField(2); 
        gbc.gridx = 3;
        add(YEAR, gbc);

        // Omang Number
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Omang Number:"), gbc);
        gbc.gridx = 1;
        OMANG = new JTextField(20);
        add(OMANG, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        ADDRESS = new JTextField(20);
        add(ADDRESS, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("#######");
            PH_PHONE_NUMBER = new JFormattedTextField(phoneFormatter);
            PH_PHONE_NUMBER.setColumns(10);
            add(PH_PHONE_NUMBER, gbc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Email
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        PH_EMAIL = new JTextField(20);
        add(PH_EMAIL, gbc);

        // Create Button
        gbc.gridx = 0;
        gbc.gridy = 8;
        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPolicyHolder();
            }
        });
        add(createButton, gbc);

        // Cancel Button
        gbc.gridx = 1;
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton, gbc);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    // Method to get a database connection
    private static Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // this method will create a policy holder using the data from the GUI
    private void createPolicyHolder() {
        try {
            String policyHolderId = P_HOLDER_ID.getText();
            String firstName = FIRST_NAME.getText();
            String lastName = LAST_NAME.getText();
            String day = DAY.getText();
            String month = (String) MONTH.getSelectedItem();
            String year = YEAR.getText();
            String dob = day + "-" + month + "-" + year;  // Format the date as DD-MMM-YY
            String omang = OMANG.getText();
            String address = ADDRESS.getText();
            String phoneNumber = PH_PHONE_NUMBER.getText(); // Ensure it's formatted
            String email = PH_EMAIL.getText();

            // Perform the database operation
            addPolicyHolder.createPolicyHolder(policyHolderId, firstName, lastName, dob, omang, address, phoneNumber, email);
            JOptionPane.showMessageDialog(this, "Policy Holder created successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

