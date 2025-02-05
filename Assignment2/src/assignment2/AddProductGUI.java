
package assignment2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddProductGUI extends JFrame {
    
    // Components of the form
    private JTextField productNoField, productNameField, typeField, priceField, pNoField, bIdField;
    private JButton submitButton;

    // Constructor
    public AddProductGUI() {
        // Form layout
        setLayout(new GridLayout(7, 2));

        // Initialize components
        productNoField = new JTextField(20);
        productNameField = new JTextField(20);
        typeField = new JTextField(20);
        priceField = new JTextField(20);
        pNoField = new JTextField(20);
        bIdField = new JTextField(20);
        submitButton = new JButton("Add Product");

        // Add components to the frame
        add(new JLabel("Product Number:"));
        add(productNoField);
        add(new JLabel("Product Name:"));
        add(productNameField);
        add(new JLabel("Type:"));
        add(typeField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel("Policy Number (P_NO):"));
        add(pNoField);
        add(new JLabel("Business Area ID (B_ID):"));
        add(bIdField);
        add(submitButton);

        // Set button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // Frame settings
        setTitle("Add New Product");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to add product to the database
    private void addProduct() {
        // Get values from the form
        String productNo = productNoField.getText();
        String productName = productNameField.getText();
        String type = typeField.getText();
        String price = priceField.getText();
        String pNo = pNoField.getText();
        String bId = bIdField.getText();

        // Database connection
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load JDBC driver (adjust depending on your DB)
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.0.18.2:1521:orcl", "kad08203", "kad08203");

            // SQL query to insert new product
            String sql = "INSERT INTO PRODUCT (PRODUCT_NO, PRODUCT_NAME, TYPE, PRICE, P_NO, B_ID) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productNo);
            stmt.setString(2, productName);
            stmt.setString(3, type);
            stmt.setFloat(4, Float.parseFloat(price));
            stmt.setString(5, pNo);
            stmt.setString(6, bId);

            // Execute query
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product added successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
