package assignment2;

import java.sql.*;

public class PolicyHolderAndPolicyDetails {
	
	public static String generateReport() {
		
		StringBuilder report = new StringBuilder();
		
		report.append(String.format("%-20s %-20s %-20s%n", "Last Name", "Policy Number", "Product Number", "Broker ID"));
		report.append("------------------------------------------------------------------------------------------------------\n");
		
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement()) {
			
			try (ResultSet rs = stmt.executeQuery(
					"SELECT"
					+ "PH.LAST_NAME AS Last_Name,"
					+ "PP_NO AS Policy_number"
					+ "PR.PRODUCT_ID AS product_ID"
					+ "B.BROK_ID AS broker_ID"
					+ "FROM POLICY_HOLDER PH"
					+ "JOIN POLICY P ON PH.P_HOLDER_ID = P.PHOLDER_ID"
					+ "JOIN PRODUCT PR ON PR.P_NO = P.P_NO"
					+ "JOIN BROKER B ON B.BROK_ID = PR.B_ID"
					+ "GROUP BY PH.LAST_NAME"
					+ "ORDER BY PH.LAST_NAME"
			))
			
			{
				while(rs.next()) {
					report.append(String.format("%-20s %-20s %-20s%n", 
                            rs.getString("Last_Name"), 
                            rs.getString("Policy_Number"), 
                            rs.getString("product_ID"),
                            rs.getString("broker_ID")));
				}
			}
		}
		catch (SQLException e) {
        report.append("Error: ").append(e.getMessage()).append("\n");
		}
		return report.toString();
		
	}

}
