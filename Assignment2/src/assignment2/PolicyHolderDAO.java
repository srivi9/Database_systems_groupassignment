package assignment2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PolicyHolderDAO {
    
    public List<PolicyHolder> getAllPolicyHolders() {
        List<PolicyHolder> policyHolders = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        
        try {
            String query = "SELECT * FROM POLICY_HOLDER";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PolicyHolder policyHolder = new PolicyHolder();
                policyHolder.setId(rs.getString("P_HOLDER_ID"));
                policyHolder.setFirstName(rs.getString("FIRST_NAME"));
                policyHolder.setLastName(rs.getString("LAST_NAME"));
                policyHolder.setDob(rs.getString("DOB"));
                policyHolder.setIdentityNumber(rs.getString("OMANG"));
                policyHolder.setAddress(rs.getString("ADDRESS"));
                policyHolder.setPhoneNumber(rs.getInt("PH_PHONE_NUMBER"));
                policyHolder.setEmail(rs.getString("PH_EMAIL"));
                
                policyHolders.add(policyHolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return policyHolders;
    }
}

