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
    ScheduleMethod scheduleMethod = new ScheduleMethod();

    private JPanel contentPane;
    private JTextField memberIdTextField;
    private JTextField bookingTextField;
    private JTextField priceTextField;
    private JList<String> userList;
    private JList<String> courtList;
    public String DayOfWeek;
    public int SumOfHour;
    public String DaysandHoursStrings;
    public String normalPrice;
    public String weekendPrice;
    public int totalPrice;
    public int price = 0;

    // ScheduleTable scheduleTable = new ScheduleTable();

    public void receiveScheduleData(String daysAndHours, int sumOfDays, String DaysandHoursStrings) {
        // Handle the data here, e.g., update the UI or store the data
        this.DayOfWeek = daysAndHours;
        this.SumOfHour = sumOfDays;

        String part[] = DaysandHoursStrings.split(": ", 2);

        if (part.length > 1) {
            String timeslot = part[1].trim();
            this.DaysandHoursStrings = timeslot;
        }
        // System.out.println("Received scheduling data: " + daysAndHours + ", " +
        // sumOfDays + ", " + DaysandHoursStrings);

        priceTextField.setText(String.valueOf("Please Click the Calculate Price button to calculate"));

        // You can update text fields or other UI components here
    }

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
        // scheduleTable.setVisible(false);
        setTitle("Staff Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 600); // Adjust frame size as needed
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblMemberId = new JLabel("Member ID:");
        lblMemberId.setBounds(10, 11, 83, 14);
        contentPane.add(lblMemberId);

        memberIdTextField = new JTextField();
        memberIdTextField.setBounds(103, 8, 298, 20);
        contentPane.add(memberIdTextField);
        memberIdTextField.setColumns(10);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdTextField.getText();
                DefaultListModel<String> userlistModel = new DefaultListModel<>();

                try {
                    List<String> userLines = Files.readAllLines(Paths.get("user.txt"));
                    for (String line : userLines) {
                        if (line.contains(memberId)) {
                            userlistModel.addElement(line);
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    userlistModel.addElement("Error reading user file");

                }

                userList.setModel(userlistModel);

            }
        });
        btnSearch.setBounds(411, 7, 147, 23);
        contentPane.add(btnSearch);

        JLabel lblBooking = new JLabel("Booking:");
        lblBooking.setBounds(10, 49, 83, 14);
        contentPane.add(lblBooking);

        bookingTextField = new JTextField();
        bookingTextField.setBounds(103, 46, 298, 20);
        contentPane.add(bookingTextField);
        bookingTextField.setColumns(10);

        JButton btnConfirmBooking = new JButton("Confirm Booking");
        btnConfirmBooking.setBounds(411, 45, 147, 23);
        contentPane.add(btnConfirmBooking);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(10, 89, 83, 14);
        contentPane.add(lblPrice);

        priceTextField = new JTextField();
        priceTextField.setEditable(false);
        priceTextField.setBounds(103, 86, 298, 20);
        contentPane.add(priceTextField);
        priceTextField.setColumns(10);

        JButton btnCalculatePrice = new JButton("Calculate Price");
        btnCalculatePrice.setBounds(411, 79, 147, 23);
        contentPane.add(btnCalculatePrice);

        DefaultListModel<String> userlistModel = new DefaultListModel<>();
        userList = new JList<>(userlistModel);
        JScrollPane userlistPane = new JScrollPane(userList);
        userlistPane.setBounds(32, 174, 258, 148);
        contentPane.add(userlistPane);

        DefaultListModel<String> courtListModel = new DefaultListModel<>();
        courtList = new JList<>(courtListModel);
        JScrollPane courtListPane = new JScrollPane(courtList);
        courtListPane.setBounds(300, 174, 258, 148);
        contentPane.add(courtListPane);

        JButton btnSelectTime = new JButton("Select Time To Booking");
        btnSelectTime.setBounds(32, 119, 526, 44);
        contentPane.add(btnSelectTime);

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

        btnSelectTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScheduleTable s = new ScheduleTable(StaffPage.this);
                s.setVisible(true);
            }
        });

        btnCalculatePrice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String courtInfo = courtList.getSelectedValue();
                String[] courtPrice = courtInfo.split(", ");
                String nPrice = courtPrice.length > 1 ? courtPrice[1].trim() : "";
                String wPrice = courtPrice[2].trim();

                Integer normalPrice = Integer.parseInt(nPrice);
                Integer weekendPrice = Integer.parseInt(wPrice);

                if (DayOfWeek.equals("Monday") || DayOfWeek.equals("Tuesday") || DayOfWeek.equals("Wednesday")
                        || DayOfWeek.equals("Thursday") || DayOfWeek.equals("Friday")) {
                    System.out.println("normal day");
                    System.out.println(SumOfHour);
                    System.out.println(nPrice);

                    price = calculatePrice(SumOfHour, normalPrice);
                    priceTextField.setText(String.valueOf(price));

                } else {
                    System.out.println("weekend day");
                    System.out.println(SumOfHour);
                    System.out.println(wPrice);
                    price = calculatePrice(SumOfHour, weekendPrice);
                    priceTextField.setText(String.valueOf(price));
                }

            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdTextField.getText();
                DefaultListModel<String> userlistModel = new DefaultListModel<>();

                try {
                    List<String> userLines = Files.readAllLines(Paths.get("user.txt"));
                    for (String line : userLines) {
                        if (line.contains(memberId)) {
                            userlistModel.addElement(line);
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                    userlistModel.addElement("Error reading user file");

                }

                userList.setModel(userlistModel);

            }
        });

        displayUsers();
        displayCourts();

        // Section: Receive Change Page
        JPanel receiveChangePanel = new JPanel();
        receiveChangePanel.setBorder(BorderFactory.createTitledBorder("Receive Change Page"));
        receiveChangePanel.setBounds(10, 340, 564, 192); // Adjust position and size as needed
        contentPane.add(receiveChangePanel);
        receiveChangePanel.setLayout(new BorderLayout(0, 0));

        JTextArea receiveChangeTextArea = new JTextArea();
        receiveChangeTextArea.setEditable(false);
        receiveChangePanel.add(new JScrollPane(receiveChangeTextArea), BorderLayout.CENTER);

        // Add ActionListener to btnConfirmBooking to update Receive Change Page
        btnConfirmBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Extract booking information
                String inputText = memberIdTextField.getText();
                String courtInfo = courtList.getSelectedValue();


                String[] parts = inputText.split(", ");
                String memberId = parts.length > 0 ? parts[0].trim() : ""; // แยกและเลือก memberId
                String userName = parts[1].trim();

                if (courtInfo != null) {
                    confirmBooking(memberId, userName, courtInfo, price, DayOfWeek, DaysandHoursStrings); // ใช้
                                                                                                          // memberId
                                                                                                          // ที่แยกได้
                    // Update the text in the Receive Change Page
                    String bookingDetails = "Member ID: " + memberId + "\nMember Name: " + userName + "\nCourt Info: "
                            + courtInfo
                            + "\nPrice: " + price + "\nDate Booking: " + DayOfWeek + "\nTime: " + DaysandHoursStrings;
                    receiveChangeTextArea.setText(bookingDetails);
                }
            }
        });

    }

    private int calculatePrice(int sumOfhour, int totalTime) {
        int x = sumOfhour * totalTime;
        return x;
    }

    private void confirmBooking(String memberId, String userName, String courtInfo, double price, String dateBooking,
            String timeBooking) {
        String bookingInfo = "Member ID: " + memberId + "\nMember Name: " + userName + "\nCourt Info: " + courtInfo
                + "\nPrice: " + price + "\nDate Booking: " + dateBooking + "\nTime: " + timeBooking;

        JOptionPane.showMessageDialog(this, "Booking confirmed!\n" + bookingInfo, "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);

        try {
            FileWriter writer = new FileWriter("courtbooking.txt", true);
            writer.write(bookingInfo + "\n");
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
