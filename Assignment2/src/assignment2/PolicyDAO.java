package assignment2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyDAO {

    // Method to get policies by claim number
    public List<Policy> getPoliciesByClaimNumber(String claimNumber) {
        List<Policy> policies = new ArrayList<>();
        
        String query = "SELECT p.P_NO, p.TOTAL_PREMIUM, p.START_DATE, p.END_DATE "
                     + "FROM POLICY p "
                     + "JOIN CLAIM c ON p.P_NO = c.P_NO "
                     + "WHERE c.C_NO = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claimNumber);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Policy policy = new Policy();
                policy.setPno(rs.getString("P_NO"));
                policy.settotalPremium(rs.getFloat("TOTAL_PREMIUM"));
                policy.setstartDate(rs.getString("START_DATE"));
                policy.setendDate(rs.getString("END_DATE"));
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return policies;
    }

    // Method to get policies by policy holder's surname
    public List<Policy> searchPoliciesBySurname(String surname) {
        List<Policy> policies = new ArrayList<>();
        
        String query = "SELECT p.P_NO, p.TOTAL_PREMIUM, p.START_DATE, p.END_DATE "
                     + "FROM POLICY p "
                     + "JOIN POLICY_HOLDER ph ON p.PHOLDER_ID = ph.P_HOLDER_ID "
                     + "WHERE ph.LAST_NAME = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, surname);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Policy policy = new Policy();
                policy.setPno(rs.getString("P_NO"));
                policy.settotalPremium(rs.getFloat("TOTAL_PREMIUM"));
                policy.setstartDate(rs.getString("START_DATE"));
                policy.setendDate(rs.getString("END_DATE"));
                policies.add(policy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return policies;
    }
    
    public double getTotalSubscriptionsForYear(int year) {
        double totalSubscriptions = 0;
        String query = "SELECT SUM(TOTAL_PREMIUM) FROM Policy WHERE EXTRACT(YEAR FROM TO_DATE(START_DATE, 'DD-MON-RR')) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year); // Set the year parameter
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalSubscriptions = rs.getDouble(1); // Get the sum of TOTAL_PREMIUM
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSubscriptions;
    }
    
    public double getTotalClaimsForYear(int year) {
        double totalClaims = 0;
        String query = "SELECT SUM(CLAIM_AMOUNT) FROM Claim WHERE EXTRACT(YEAR FROM TO_DATE(CLAIM_DATE, 'DD-MON-RR')) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, year); // Set the year parameter
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalClaims = rs.getDouble(1); // Get the sum of CLAIM_AMOUNT
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalClaims;
    }

}

