package assignment2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PolicyHolderGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public PolicyHolderGUI() {
        setTitle("Policy Holder Information");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table model and JTable setup
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Last Name", "Policy No", "Product", "Reinsurer", "Broker", "Start Date", "End Date"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        // Fetch data when the GUI is initialized
        fetchData();
    }

    private void fetchData() {
        // Clear previous data
        model.setRowCount(0);

        // SQL query to fetch policy holders and related details
        String query = """
            SELECT ph.LAST_NAME, p.P_NO, pr.PRODUCT_NAME, r.R_NAME, b.BROK_NAME, p.START_DATE, p.END_DATE
            FROM POLICY_HOLDER ph
            JOIN POLICY p ON ph.P_HOLDER_ID = p.PHOLDER_ID
            JOIN PRODUCT pr ON p.P_NO = pr.P_NO
            JOIN SELLS s ON p.P_NO = s.P_NO
            JOIN BROKER b ON s.BROK_ID = b.BROK_ID
            JOIN REINSURES_WITH rw ON p.P_NO = rw.P_NO
            JOIN REINSURER r ON rw.R_ID = r.R_ID
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String lastName = rs.getString("LAST_NAME");
                int policyNo = rs.getInt("P_NO");
                String productName = rs.getString("PRODUCT_NAME");
                String reinsurerName = rs.getString("R_NAME");
                String brokerName = rs.getString("BROK_NAME");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");

                model.addRow(new Object[]{lastName, policyNo, productName, reinsurerName, brokerName, startDate, endDate});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
        }
    }


}
