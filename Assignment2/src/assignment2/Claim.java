/**
 * 
 */
package assignment2;

/**
 * 
 */
public class Claim {

    private String claimNumber;  // C_NO
    private double claimAmount;  // CLAIM_AMOUNT
    private String claimDate;    // CLAIM_DATE (String can be replaced with java.sql.Date)
    private String claimType;    // CLAIM_TYPE
    private String status;       // STATUS
    private String policyNumber; // P_NO

    // Constructor
    public Claim() {
    }

    // Getters and Setters
    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
}
