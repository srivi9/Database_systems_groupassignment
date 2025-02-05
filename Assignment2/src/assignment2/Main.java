/**
 * 
 */
package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Policy Holder Viewer - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);

        // Create a custom JPanel with a sweeping gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Define the gradient colors (Ubuntu colors: purple and orange)
                Color color1 = new Color(48, 23, 91);  // Dark purple
                Color color2 = new Color(255, 87, 34); // Ubuntu orange

                // Create a sweeping gradient from top-left to bottom-right
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        gradientPanel.setLayout(null);  // Use null layout to place components manually
        frame.setContentPane(gradientPanel);
        frame.setLayout(new FlowLayout());

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the menu items
        JMenu policyHolderMenu = new JMenu("Policy Holder");
        JMenu claimMenu = new JMenu("Claims");
        JMenu reportMenu = new JMenu("Reports");
        JMenu productDetailsMenu = new JMenu("Product");
        
        
        // Add items to the policy holder menu
        JMenuItem viewPolicyHolderItem = new JMenuItem("View Policy Holders");
        JMenuItem addPolicyHolderItem = new JMenuItem("Add Policy Holders");
        JMenuItem TotalCommissionAndTotalCostMenuItem = new JMenuItem("Show Total Commission And Total Cost");
        JMenuItem ViewPolicyHolderDetailsMenuItem = new JMenuItem("View Policy Holder Details");
        
        
        viewPolicyHolderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPolicyHolders();
            }
        });
        policyHolderMenu.add(viewPolicyHolderItem);
        
        addPolicyHolderItem.addActionListener(e -> new CreatePolicyDialog(frame).setVisible(true));
        policyHolderMenu.add(addPolicyHolderItem);
        
       
        
     // 'Search Policies by Surname' menu item under Policy Holder menu
        JMenuItem searchPolicyBySurnameItem = new JMenuItem("Search Policies by Surname");
        searchPolicyBySurnameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the policy holder's surname
                String surname = JOptionPane.showInputDialog(frame, "Enter Policy Holder's Surname:");

                // If a surname is provided, call the searchPolicies method with surname
                if (surname != null && !surname.trim().isEmpty()) {
                    searchPolicies(null, surname); // Passing null for claim number
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid surname.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        policyHolderMenu.add(searchPolicyBySurnameItem);
        
        
        ViewPolicyHolderDetailsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the PolicyHolderGUI to display policy holder details
                PolicyHolderGUI policyHolderGUI = new PolicyHolderGUI();
                policyHolderGUI.setVisible(true); // Display the Policy Holder GUI window
            }
        });

        addPolicyHolderItem.addActionListener(e -> new CreatePolicyDialog(frame).setVisible(true));
        
        policyHolderMenu.add(ViewPolicyHolderDetailsMenuItem);
     // Add items to the claim menu
        JMenuItem viewClaimsItem = new JMenuItem("View Claims"); // New menu item for claims
        viewClaimsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewClaims(); // Method to view claims
            }
        });
        claimMenu.add(viewClaimsItem); // Add the claims menu item to the claim menu
        
     // Add items to the claim menu
        JMenuItem viewClaimsReportItem = new JMenuItem("View Claims Report");
        viewClaimsReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewClaimsReport();
            }
        });
        reportMenu.add(viewClaimsReportItem); // Add report generation to the claim menu
        
        // 'Search Policies by Claim Number' menu item under Claim menu
        JMenuItem searchPolicyByClaimItem = new JMenuItem("Search Policies by Claim Number");
        searchPolicyByClaimItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the claim number
                String claimNumber = JOptionPane.showInputDialog(frame, "Enter Claim Number:");

                // If a claim number is provided, call the searchPolicies method with claim number
                if (claimNumber != null && !claimNumber.trim().isEmpty()) {
                    searchPolicies(claimNumber, null); // Passing null for surname
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid claim number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        claimMenu.add(searchPolicyByClaimItem);

     // Add 'Calculate Profit' menu item under the Reports menu
        JMenuItem calculateProfitItem = new JMenuItem("Calculate Profit for Year");
        calculateProfitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProfitReport(); // Call viewProfitReport directly
            }
        });
        
        TotalCommissionAndTotalCostMenuItem.addActionListener(e -> {
            String report = TotalCommissionAndTotalCost.generateReports();
            JOptionPane.showMessageDialog(frame, report, "Total Commission And Total Cost", JOptionPane.INFORMATION_MESSAGE);
        });
        
        reportMenu.add(TotalCommissionAndTotalCostMenuItem);
        reportMenu.add(calculateProfitItem);
        
        //Product
        JMenuItem addProductItem = new JMenuItem("Add Product");
        addProductItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductGUI(); // Create and display the AddProductGUI
            }
        });
        productDetailsMenu.add(addProductItem);
        
        // Add menus to the menu bar
        menuBar.add(policyHolderMenu);
        menuBar.add(claimMenu);
        menuBar.add(productDetailsMenu);
        menuBar.add(reportMenu);
        
        
        // Set the menu bar for the frame
        frame.setJMenuBar(menuBar);

        // Display the frame
        frame.setVisible(true);
    }

    private static void viewPolicyHolders() {
        // Retrieve policy holders from the database
        PolicyHolderDAO policyHolderDAO = new PolicyHolderDAO();
        List<PolicyHolder> policyHolders = policyHolderDAO.getAllPolicyHolders();

        // Create a table model to hold policy holder data
        String[] columnNames = {"ID", "First Name", "Last Name", "DOB", "Omang", "Address", "Phone Number", "Email"};
        Object[][] data = new Object[policyHolders.size()][]; // Initialize the data array

        // Populate the data array with policy holder details
        for (int i = 0; i < policyHolders.size(); i++) {
            PolicyHolder holder = policyHolders.get(i);
            data[i] = new Object[]{
                holder.getId(),
                holder.getFirstName(),
                holder.getLastName(),
                holder.getDob(),
                holder.getOmang(),
                holder.getAddress(),
                holder.getPhoneNumber(),
                holder.getEmail()
            };
        }

        // Create a JTable with the data
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table); // Add scroll capability
        table.setFillsViewportHeight(true); // Make the table fill the viewport

        // Create a dialog to display the table
        JOptionPane.showMessageDialog(null, scrollPane, "Policy Holders", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void searchPolicies(String claimNumber, String surname) {
        PolicyDAO policyDAO = new PolicyDAO(); 
        List<Policy> policies;

        // Check if searching by claim number or surname
        if (claimNumber != null && !claimNumber.trim().isEmpty()) {
            // Search by claim number
            policies = policyDAO.getPoliciesByClaimNumber(claimNumber);
        } else if (surname != null && !surname.trim().isEmpty()) {
            // Search by surname
            policies = policyDAO.searchPoliciesBySurname(surname);
        } else {
            // If neither is provided, show an error
            JOptionPane.showMessageDialog(null, "Please enter either a claim number or surname.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If no policies are found
        if (policies.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No policies found for the given search criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create table model to display policy details
        String[] columnNames = {"Policy Number", "Total Premium", "Start Date", "End Date"};
        Object[][] data = new Object[policies.size()][];

        // Populate the data array with policy details
        for (int i = 0; i < policies.size(); i++) {
            Policy policy = policies.get(i);
            data[i] = new Object[]{
                policy.getpNo(),
                policy.gettotalPremium(),
                policy.getstartDate(),
                policy.getendDate()
            };
        }

        // Create a JTable to display the policies
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Display the table in a dialog
        JOptionPane.showMessageDialog(null, scrollPane, "Policy Search Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void viewProfitReport() {
	    // Prompt the user for the year they want to search
	    String year = JOptionPane.showInputDialog(null, "Enter year (e.g., 2024):", "Enter Year", JOptionPane.PLAIN_MESSAGE);

	    // If the user cancels or enters invalid data, return
	    if (year == null || year.trim().isEmpty() || year.trim().length() != 4) {
	        JOptionPane.showMessageDialog(null, "Please enter a valid year! A year has to be of length 4 e.g 2024", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    try {
	        int searchYear = Integer.parseInt(year.trim());

	        // 1. Retrieve Total Subscriptions and Commissions (from part 5)
	        double totalSubscriptions = getTotalSubscriptionsForYear(searchYear);

	        // 2. Retrieve Total Claims Paid Out (from part 4)
	        ClaimDAO claimDAO = new ClaimDAO();
	        double totalClaimsPaid = claimDAO.getTotalApprovedClaimsForYear(searchYear);
	        
	        double comm = getTotalCommissionForYear(searchYear);

	        // 3. Calculate Profit: Subscriptions - Claims Paid
	        double profit = totalSubscriptions - totalClaimsPaid;

	        // 4. Display the result in a message dialog
	        String profitMessage = "Profit Report for Year " + year + ":\n" +
	                               "Total Subscriptions: " + totalSubscriptions + "\n" +
	                               "Total Claims Paid: " + totalClaimsPaid + "\n" +
	                               "Total Commission Fees: " + comm + "\n" +
	                               "Profit: " + (profit - comm);

	        JOptionPane.showMessageDialog(null, profitMessage, "Profit Report", JOptionPane.INFORMATION_MESSAGE);

	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	    }
	}

    private static double getTotalSubscriptionsForYear(int year) {
        double totalSubscriptions = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            System.out.println("Year: " + year); // Debug output
            // Updated SQL query using EXTRACT function
            String query = "SELECT SUM(P.TOTAL_PREMIUM) AS Total_Subscriptions " +
                           "FROM POLICY P " +
                           "WHERE EXTRACT(YEAR FROM START_DATE) = " + year;

            try (ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    totalSubscriptions = rs.getDouble("Total_Subscriptions");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print full stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error retrieving subscriptions: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return totalSubscriptions;
    }
    
    // Method to get the total commission for approved claims in a given year
    private static double getTotalCommissionForYear(int year) {
        double totalCommission = 0.0;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Year: " + year); // Debug output

            // SQL query to sum the commissions from approved claims in the given year
            String query = "SELECT SUM(S.COMMISION_AMOUNT) AS Total_Commission "
                         + "FROM CLAIM C "
                         + "JOIN SELLS S ON C.P_NO = S.P_NO "
                         + "WHERE C.STATUS = 'Approved' "
                         + "AND EXTRACT(YEAR FROM C.CLAIM_DATE) = " + year;

            try (ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    totalCommission = rs.getDouble("Total_Commission");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print full stack trace for debugging
            JOptionPane.showMessageDialog(null, "Error retrieving commission: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return totalCommission;
    }



    
    


 // New method to display claims
    private static void viewClaims() {
        // Retrieve claims from the database
        ClaimDAO claimDAO = new ClaimDAO();
        List<Claim> claims = claimDAO.getAllClaims();

        // Create a table model to hold claim data
        String[] columnNames = {"Claim Number", "Claim Amount", "Claim Date", "Claim Type", "Status", "Policy Number"};
        Object[][] data = new Object[claims.size()][]; // Initialize the data array

        // Populate the data array with claim details
        for (int i = 0; i < claims.size(); i++) {
            Claim claim = claims.get(i);
            data[i] = new Object[]{
                claim.getClaimNumber(),
                claim.getClaimAmount(),
                claim.getClaimDate(),
                claim.getClaimType(),
                claim.getStatus(),
                claim.getPolicyNumber()
            };
        }

        // Create a JTable with the data
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table); // Add scroll capability
        table.setFillsViewportHeight(true); // Make the table fill the viewport

        // Create a dialog to display the table
        JOptionPane.showMessageDialog(null, scrollPane, "Claims", JOptionPane.INFORMATION_MESSAGE);
    }
 // New method to display claims report based on the year entered by the user
    private static void viewClaimsReport() {
        // Prompt the user for the year they want to search
        String year = JOptionPane.showInputDialog(null, "Enter year (e.g., 2024):", "Enter Year", JOptionPane.PLAIN_MESSAGE);

        // If the user cancels or enters invalid data, just return
        if (year == null || year.trim().isEmpty() || year.trim().length() != 4) {
            JOptionPane.showMessageDialog(null, "Please enter a valid year! A year has to be of length 4 e.g 2024", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int searchYear = Integer.parseInt(year.trim());

            // Retrieve the total amount of approved claims for the entered year
            ClaimDAO claimDAO = new ClaimDAO();
            // Retrieve the list of approved claims for the given year
            List<Claim> approvedClaims = claimDAO.getApprovedClaimsForYear(searchYear);
            double totalAmount = claimDAO.getTotalApprovedClaimsForYear(searchYear);
            

            // Show the total result in a message dialog (must keep)
            //JOptionPane.showMessageDialog(null, "Total amount of money paid in claims in " + year + " was : " + totalAmount, "Claims Report", JOptionPane.INFORMATION_MESSAGE);


            // If there are no approved claims, show a message and return
            if (approvedClaims.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No claims paid for the year " + year, "Claims Report", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Create a table to display the approved claims
            String[] columnNames = {"Claim Number", "Claim Amount", "Claim Date", "Claim Type", "Status"};
            Object[][] data = new Object[approvedClaims.size()][];

            // Populate the data array with approved claim details
            for (int i = 0; i < approvedClaims.size(); i++) {
                Claim claim = approvedClaims.get(i);
                data[i] = new Object[]{
                    claim.getClaimNumber(),
                    claim.getClaimAmount(),
                    claim.getClaimDate(),
                    claim.getClaimType(),
                    claim.getStatus(),
                    
                };
            }

            // Create a JTable to display the approved claims
            JTable claimsTable = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(claimsTable);
            claimsTable.setFillsViewportHeight(true);

         // Your custom message to be printed along with the table
            String reportMessage = "This is a detailed report of the claims that were paid out in " + year + ".\nThe total amount of claims paid out were " + totalAmount + ".";

            // Create a dialog to display the claims table and the message
            JOptionPane.showMessageDialog(null, new Object[]{scrollPane, reportMessage}, "Detailed report of Claims paid in Year " + year, JOptionPane.INFORMATION_MESSAGE);
           

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

}
