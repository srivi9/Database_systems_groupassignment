package assignment2;

import javax.swing.JOptionPane;
import java.sql.*;


public class TotalProfit {
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

	        // 3. Calculate Profit: Subscriptions - Claims Paid
	        double profit = totalSubscriptions - totalClaimsPaid;

	        // 4. Display the result in a message dialog
	        String profitMessage = "Profit Report for Year " + year + ":\n" +
	                               "Total Subscriptions: " + totalSubscriptions + "\n" +
	                               "Total Claims Paid: " + totalClaimsPaid + "\n" +
	                               "Profit: " + profit;

	        JOptionPane.showMessageDialog(null, profitMessage, "Profit Report", JOptionPane.INFORMATION_MESSAGE);

	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private static double getTotalSubscriptionsForYear(int year) {
	    double totalSubscriptions = 0;
	    try (Connection conn = DatabaseConnection.getConnection();
	         Statement stmt = conn.createStatement()) {

	        // SQL query to retrieve the total subscriptions for the given year
	        String query = "SELECT SUM(P.TOTAL_PREMIUM) AS Total_Subscriptions "
	                     + "FROM POLICY P "
	                     + "WHERE YEAR(P.ISSUE_DATE) = " + year;

	        try (ResultSet rs = stmt.executeQuery(query)) {
	            if (rs.next()) {
	                totalSubscriptions = rs.getDouble("Total_Subscriptions");
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error retrieving subscriptions: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
	    }
	    return totalSubscriptions;
	}

}
