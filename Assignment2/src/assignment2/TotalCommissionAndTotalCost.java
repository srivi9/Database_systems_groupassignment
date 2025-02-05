package assignment2;
import java.sql.*;

public class TotalCommissionAndTotalCost {

	public static String generateReports() {
		StringBuilder report = new StringBuilder();
		
		report.append(String.format("%-20s %-20s %-20s%n", "Business Area", "Total Subscriptions", "Total Commissions"));
	    report.append("----------------------------------------------------------------------------------------------\n");

		 try (Connection conn = DatabaseConnection.getConnection();
	             Statement stmt = conn.createStatement()) {

	            // a) Customer who ordered more than 5 products with Product No EL-58K
	            try (ResultSet rs = stmt.executeQuery(
	                    "SELECT "
	                    + "    BA.NAME AS Business_Area,"
	                    + "    SUM(P.TOTAL_PREMIUM) AS Total_Subscriptions,"
	                    + "    SUM(S.COMMISION_AMOUNT) AS Total_Commissions "
	                    + "FROM "
	                    + "    BUSINESS_AREA BA "
	                    + "JOIN "
	                    + "    PRODUCT PD ON BA.B_ID = PD.B_ID "
	                    + "JOIN "
	                    + "    POLICY P ON PD.P_NO = P.P_NO "
	                    + "JOIN "
	                    + "    SELLS S ON P.P_NO = S.P_NO "
	                    + "GROUP BY "
	                    + "    BA.NAME "
	                    + "ORDER BY"
	                    + "   BA.NAME"
	                    
	            )) {
	                while (rs.next()) {
	                	report.append(String.format("%-20s %-20s %-20s%n", 
	                            rs.getString("Business_Area"), 
	                            rs.getDouble("Total_Subscriptions"), 
	                            rs.getDouble("Total_Commissions")));	                
	                }
	            }
		 } catch (SQLException e) {
	         report.append("Error: ").append(e.getMessage()).append("\n");
		 }
		 return report.toString();
	} 
	 
	//Execute the query and return a ResultSet
	 public static ResultSet executeQuery(String query) throws SQLException {
	     try (Connection conn = DatabaseConnection.getConnection();
	          Statement stmt = conn.createStatement()) {
	         return stmt.executeQuery(query);
	     }
	 }
}
