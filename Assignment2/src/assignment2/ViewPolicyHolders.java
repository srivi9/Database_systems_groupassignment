package assignment2;


import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewPolicyHolders extends JFrame {

    public ViewPolicyHolders() {
        setTitle("Policy Holders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PolicyHolderDAO dao = new PolicyHolderDAO();
        List<PolicyHolder> policyHolders = dao.getAllPolicyHolders();
        
        String[] columns = {"P_HOLDER_ID", "FIRST_NAME", "LAST_NAME", "DOB", "OMANG", "ADDRESS", "PH_PHONE_NUMBER", "PH_EMAIL"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (PolicyHolder holder : policyHolders) {
            Object[] row = {
                    holder.getId(),
                    holder.getFirstName(),
                    holder.getLastName(),
                    holder.getDob(),
                    holder.getOmang(),
                    holder.getAddress(),
                    holder.getPhoneNumber(),
                    holder.getEmail()
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    
}
