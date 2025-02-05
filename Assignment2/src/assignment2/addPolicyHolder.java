package assignment2;

import java.sql.*;

public class addPolicyHolder {
    
    private static Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public static void createPolicyHolder(String policyHolderId, String firstName, String lastName, String dob, String omang, String address, String phoneNumber, String email) throws SQLException {
        String insertPolicyHolderSql = "INSERT INTO POLICY_HOLDER (P_HOLDER_ID, FIRST_NAME, LAST_NAME, DOB, OMANG, ADDRESS, PH_PHONE_NUMBER, PH_EMAIL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertPolicyHolderSql)) {

            // Insert policy holder data
            insertStmt.setString(1, policyHolderId);
            insertStmt.setString(2, firstName);
            insertStmt.setString(3, lastName);
            insertStmt.setString(4, dob);
            insertStmt.setString(5, omang);
            insertStmt.setString(6, address);
            insertStmt.setString(7, phoneNumber);
            insertStmt.setString(8, email);
            insertStmt.executeUpdate();
        }
    }
}
