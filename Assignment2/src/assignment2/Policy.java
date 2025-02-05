package assignment2;

public class Policy{
    private String pNo;
    private float totalPremium;
    private String startDate;
    private String endDate;
    private String pHolderID;
 
    // Getters and Setters for each field
    public String getpNo() { return pNo; }
    public void setPno(String pNo) { this.pNo = pNo; }
    
    public float gettotalPremium() { return totalPremium; }
    public void settotalPremium(float totalPremium) { this.totalPremium = totalPremium; }
    
    public String getstartDate() { return startDate; }
    public void setstartDate(String startDate) { this.startDate = startDate; }
    
    public String getendDate() { return endDate; }
    public void setendDate(String endDate) { this.endDate = endDate; }
    
    public String getpHolderID() { return pHolderID; }
    public void setpHolderID(String pHolderID) { this.pHolderID = pHolderID; }

}
