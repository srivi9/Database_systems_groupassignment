/**
 * 
 */
package assignment2;

/**
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {
    
    // Method to retrieve all claims from the database
    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        
        try {
            String query = "SELECT * FROM CLAIM";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Claim claim = new Claim();
                
                // Mapping columns to the Claim class attributes
                claim.setClaimNumber(rs.getString("C_NO"));
                claim.setClaimAmount(rs.getDouble("CLAIM_AMOUNT"));
                claim.setClaimDate(rs.getString("CLAIM_DATE")); // Consider using rs.getDate() for java.sql.Date
                claim.setClaimType(rs.getString("CLAIM_TYPE"));
                claim.setStatus(rs.getString("STATUS"));
                claim.setPolicyNumber(rs.getString("P_NO"));
                
                // Adding the claim object to the list
                claims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return claims;
    }
    
    public double getTotalApprovedClaimsForYear(int year) {
        double totalAmount = 0.0;
        Connection connection = DatabaseConnection.getConnection();
        
        try {
            String query = "SELECT SUM(CLAIM_AMOUNT) AS total FROM CLAIM WHERE EXTRACT(YEAR FROM CLAIM_DATE) = ? AND STATUS = 'Approved'";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                totalAmount = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return totalAmount;
    }
    
 // Method to get all approved claims for a given year
    public List<Claim> getApprovedClaimsForYear(int year) {
        List<Claim> approvedClaims = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM CLAIM WHERE STATUS = 'Approved' AND EXTRACT(YEAR FROM CLAIM_DATE) = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Claim claim = new Claim();
                claim.setClaimNumber(rs.getString("C_NO"));
                claim.setClaimAmount(rs.getDouble("CLAIM_AMOUNT"));
                claim.setClaimDate(rs.getDate("CLAIM_DATE").toString());
                claim.setClaimType(rs.getString("CLAIM_TYPE"));
                claim.setStatus(rs.getString("STATUS"));

                approvedClaims.add(claim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approvedClaims;
    }
}

