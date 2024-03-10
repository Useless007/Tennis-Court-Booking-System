package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffPage extends JFrame {

    private JPanel contentPane;
    private JTextField memberIdTextField;
    private JTextField bookingTextField;
    private JTextField priceTextField;
    private JList<String> userList;
    private JList<String> courtList;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StaffPage frame = new StaffPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StaffPage() {
        setTitle("Staff Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblMemberId = new JLabel("Member ID:");
        lblMemberId.setBounds(10, 11, 83, 14);
        contentPane.add(lblMemberId);

        memberIdTextField = new JTextField();
        memberIdTextField.setBounds(103, 8, 146, 20);
        contentPane.add(memberIdTextField);
        memberIdTextField.setColumns(10);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdTextField.getText();
                DefaultListModel<String> userlistModel = new DefaultListModel<>();
                DefaultListModel<String> courtListModel = new DefaultListModel<>();

                try {
                    List<String> userLines = Files.readAllLines(Paths.get("user.txt"));
                    for (String line : userLines) {
                        if (line.contains(memberId)) {
                            userlistModel.addElement(line);
                        }
                    }

                    List<String> courtLines = Files.readAllLines(Paths.get("courtList.txt"));
                    for (String line : courtLines) {
                        if (line.contains(memberId)) {
                            courtListModel.addElement(line);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    userlistModel.addElement("Error reading user file");
                    courtListModel.addElement("Error reading court file");
                }

                userList.setModel(userlistModel);
                courtList.setModel(courtListModel);
            }
        });
        btnSearch.setBounds(259, 7, 89, 23);
        contentPane.add(btnSearch);

        JLabel lblBooking = new JLabel("Booking:");
        lblBooking.setBounds(10, 49, 83, 14);
        contentPane.add(lblBooking);

        bookingTextField = new JTextField();
        bookingTextField.setBounds(103, 46, 146, 20);
        contentPane.add(bookingTextField);
        bookingTextField.setColumns(10);

        JButton btnConfirmBooking = new JButton("Confirm Booking");
        btnConfirmBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdTextField.getText(); // Get Member ID from TextField
                String courtInfo = courtList.getSelectedValue(); // Get selected court info from JList
                if (courtInfo != null) {
                    double price = calculatePrice(courtInfo); // Calculate price
                    String memberName = getMemberName(memberId); // Get member name from ID
                    String bookingInfo = "Member ID: " + memberId + "\nMember Name: " + memberName + "\nCourt Info: " + courtInfo + "\nPrice: " + price;
                    confirmBooking(memberId, courtInfo, price, memberName); // Confirm booking
                } else {
                    JOptionPane.showMessageDialog(StaffPage.this, "Please select a court.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnConfirmBooking.setBounds(259, 45, 147, 23);
        contentPane.add(btnConfirmBooking);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(10, 89, 83, 14);
        contentPane.add(lblPrice);

        priceTextField = new JTextField();
        priceTextField.setEditable(false);
        priceTextField.setBounds(103, 86, 146, 20);
        contentPane.add(priceTextField);
        priceTextField.setColumns(10);

        JButton btnCalculatePrice = new JButton("Calculate Price");
        btnCalculatePrice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courtInfo = courtList.getSelectedValue(); // Get selected court info from JList
                if (courtInfo != null) {
                    double price = calculatePrice(courtInfo); // Calculate price
                    priceTextField.setText(String.valueOf(price)); // Display price
                } else {
                    JOptionPane.showMessageDialog(StaffPage.this, "Please select a court.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnCalculatePrice.setBounds(259, 85, 147, 23);
        contentPane.add(btnCalculatePrice);

        DefaultListModel<String> userlistModel = new DefaultListModel<>();
        userList = new JList<>(userlistModel);
        JScrollPane userlistPane = new JScrollPane(userList);
        userlistPane.setBounds(32, 174, 258, 148);
        contentPane.add(userlistPane);

        DefaultListModel<String> courtListModel = new DefaultListModel<>();
        courtList = new JList<>(courtListModel);
        JScrollPane courtListPane = new JScrollPane(courtList);
        courtListPane.setBounds(320, 174, 258, 148);
        contentPane.add(courtListPane);

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    String selectedUser = userList.getSelectedValue();

                    if (selectedUser != null) {
                        String[] parts = selectedUser.split(", ");
                        if (parts.length >= 3) {
                            String selectedMemberId = parts[0].trim();
                            String selectedName = parts[1].trim();
                            String selectedSurname = parts[2].trim();
                            memberIdTextField.setText(selectedMemberId + ", " + selectedName + " " + selectedSurname);
                        }
                    }
                }
            }
        });

        courtList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    String selectedCourt = courtList.getSelectedValue();
                    if (selectedCourt != null) {
                        String[] parts = selectedCourt.split(", ");
                        if (parts.length >= 2) {
                            String name = parts[0];
                            String price = parts[1];

                            // Show data in the input field
                            bookingTextField.setText(name + " " + price); // Add price to bookingTextField
                        }
                    }
                }
            }
        });

        displayUsers();
        displayCourts();
    }

    private double calculatePrice(String courtInfo) {
        // This method calculates the price based on the selected court information
        // You should add the code to calculate the price based on your logic here
        // Here, we just return 0 for testing purposes
        return 0.0;
    }

    private String getMemberName(String memberId) {
        try {
            List<String> bookingLines = Files.readAllLines(Paths.get("courtbooking.txt"));
            for (String line : bookingLines) {
                if (line.contains("Member ID: " + memberId)) {
                    // Extract member name from the line
                    String[] parts = line.split(", ");
                    for (String part : parts) {
                        if (part.startsWith("Member Name: ")) {
                            return part.substring("Member Name: ".length());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Unknown"; // Return "Unknown" if member name not found
    }


    private void confirmBooking(String memberId, String courtInfo, double price, String memberName) {
        String bookingInfo = "Member ID: " + memberId + "\nMember Name: " + memberName + "\nCourt Info: " + courtInfo + "\nPrice: " + price;
        JOptionPane.showMessageDialog(this, "Booking confirmed!\n" + bookingInfo, "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);

        try {
            FileWriter writer = new FileWriter("courtbooking.txt", true);
            writer.write(bookingInfo);
            writer.write("\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayUsers() {
        DefaultListModel<String> userlistModel = new DefaultListModel<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            for (String line : lines) {
                userlistModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            userlistModel.addElement("Error reading user file");
        }
        userList.setModel(userlistModel);
    }

    private void displayCourts() {
        DefaultListModel<String> courtListModel = new DefaultListModel<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt"));
            for (String line : lines) {
                courtListModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            courtListModel.addElement("Error reading court file");
        }
        courtList.setModel(courtListModel);
    }
}
