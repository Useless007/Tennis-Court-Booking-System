package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OwnerPage extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OwnerPage frame = new OwnerPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OwnerPage() {
        setTitle("Owner Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 600); // Increased size of the window
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 1, 0, 0)); // Changed to accommodate the new section

        // Section: Financial Overview Page
        JPanel financePanel = new JPanel();
        financePanel.setBorder(BorderFactory.createTitledBorder("Financial Overview Page"));
        contentPane.add(financePanel);
        financePanel.setLayout(new BorderLayout(0, 0));

        JTextArea financeTextArea = new JTextArea();
        financeTextArea.setEditable(false);
        financeTextArea.setText("- Monday: Income 22229.99\n- Tuesday: Income\n- Wednesday: Income\n- Thursday: Income\n- Friday: Income\n- Saturday: Income\n- Sunday: Income");
        JScrollPane financeScrollPane = new JScrollPane(financeTextArea);
        financePanel.add(financeScrollPane);

        // Section: Operational Performance Report
        JPanel reportPanel = new JPanel();
        reportPanel.setBorder(BorderFactory.createTitledBorder("Operational Performance Report"));
        contentPane.add(reportPanel);
        reportPanel.setLayout(new BorderLayout(0, 0));

        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setText(String.format("%-4s%-15s%-30s%-8s\n", "No.", "ID", "Name", "Total"));
        reportTextArea.append(String.format("%-4s%-15s%-30s%-8s\n", "1", "6606021420083", "Pulled name from main page", "User's amount, e.g. 22229.99"));
        JScrollPane reportScrollPane = new JScrollPane(reportTextArea);
        reportPanel.add(reportScrollPane);

        // Section: Edit Data Page
        JPanel editDataPanel = new JPanel();
        editDataPanel.setBorder(BorderFactory.createTitledBorder("Edit Data Page"));
        contentPane.add(editDataPanel);
        editDataPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblStaffId = new JLabel("Staff ID:");
        editDataPanel.add(lblStaffId);

        JTextField staffIdTextField = new JTextField();
        editDataPanel.add(staffIdTextField);
        staffIdTextField.setColumns(10);

        JButton btnEditStaff = new JButton("Edit Staff");
        editDataPanel.add(btnEditStaff);

        JLabel lblAdminId = new JLabel("Admin ID:");
        editDataPanel.add(lblAdminId);

        JTextField adminIdTextField = new JTextField();
        editDataPanel.add(adminIdTextField);
        adminIdTextField.setColumns(10);

        JButton btnEditAdmin = new JButton("Edit Admin");
        editDataPanel.add(btnEditAdmin);

        // Section: Admin List
        JPanel adminListPanel = new JPanel();
        adminListPanel.setBorder(BorderFactory.createTitledBorder("Admin List"));
        contentPane.add(adminListPanel);
        adminListPanel.setLayout(new BorderLayout(0, 0));

        JTextArea adminListTextArea = new JTextArea();
        adminListTextArea.setEditable(false);
        // Add admin list data here
        JScrollPane adminListScrollPane = new JScrollPane(adminListTextArea);
        adminListPanel.add(adminListScrollPane);

        // Section: Staff List
        JPanel staffListPanel = new JPanel();
        staffListPanel.setBorder(BorderFactory.createTitledBorder("Staff List"));
        contentPane.add(staffListPanel);
        staffListPanel.setLayout(new BorderLayout(0, 0));

        JTextArea staffListTextArea = new JTextArea();
        staffListTextArea.setEditable(false);
        // Add staff list data here
        JScrollPane staffListScrollPane = new JScrollPane(staffListTextArea);
        staffListPanel.add(staffListScrollPane);
    }
}
