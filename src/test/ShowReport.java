package test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ShowReport extends JDialog {
    
    public ShowReport(Frame parent) {
        super(parent, "Summary Report", true);
        setSize(400, 300); // Adjust the size as needed
        getContentPane().setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Member ID");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Amount per Transaction");

        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            int count = 1;
            for (String line : lines) {
                String[] details = line.split(", ");
                String purchaseAmount = fetchPurchaseAmount(details[0]); // Implement this method to fetch the amount
                model.addRow(new Object[]{count++, details[0], details[1], details[2], purchaseAmount});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnNewButton = new JButton("New button");
        getContentPane().add(btnNewButton, BorderLayout.SOUTH);
    }

    private String fetchPurchaseAmount(String memberId) {
        return "0";
    }
}
