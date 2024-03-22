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

/**
 * หน้าจอของพนักงาน
 */

public class StaffPage extends JFrame {
    // สร้างอ็อบเจ็กต์ของคลาส ScheduleMethod เพื่อใช้ในหน้านี้
    ScheduleMethod scheduleMethod = new ScheduleMethod();

    // ประกาศตัวแปรสำหรับองค์ประกอบต่าง ๆ ของหน้านี้
    private JPanel contentPane; // ประกาศตัวแปรสำหรับเก็บข้อมูล contentPane
    private JTextField memberIdTextField; // ประกาศตัวแปรสำหรับเก็บข้อมูล Member ID
    private JTextField bookingTextField; // ประกาศตัวแปรสำหรับเก็บข้อมูลการจอง
    private JTextField priceTextField; // ประกาศตัวแปรสำหรับเก็บข้อมูลราคา
    private JList<String> userList; // ประกาศตัวแปรสำหรับเก็บข้อมูลสมาชิก
    private JList<String> courtList; // ประกาศตัวแปรสำหรับเก็บข้อมูลสนาม
    // ประกาศตัวแปรสำหรับเก็บข้อมูลที่จะใช้ในการสร้างการจอง
    public String DayOfWeek;
    public int SumOfHour;
    public String DaysandHoursStrings;
    public String normalPrice;
    public String weekendPrice;
    public int totalPrice;
    public int price = 0;

    // ScheduleTable scheduleTable = new ScheduleTable();

    public void receiveScheduleData(String daysAndHours, int sumOfDays, String DaysandHoursStrings) {
        // จัดการข้อมูลที่ถูกส่งมาจาก ScheduleMethod ที่นี่ เช่น อัปเดต UI
        // หรือจัดเก็บข้อมูล

        // อัปเดตข้อมูลวันและชั่วโมงที่ส่งมา
        this.DayOfWeek = daysAndHours;
        this.SumOfHour = sumOfDays;
        // แยกข้อมูลวันและช่วงเวลาออกจากข้อความที่ส่งมา
        String part[] = DaysandHoursStrings.split(": ", 2);

        if (part.length > 1) {
            String timeslot = part[1].trim();
            this.DaysandHoursStrings = timeslot;
        }
        // System.out.println("Received scheduling data: " + daysAndHours + ", " +
        // sumOfDays + ", " + DaysandHoursStrings);
        // อัปเดตข้อความในช่องราคาให้แสดงข้อความต้องกดปุ่มคำนวณราคาเพื่อคำนวณ
        priceTextField.setText(String.valueOf("Please Click the Calculate Price button to calculate"));

        // คุณสามารถอัปเดตช่องข้อความหรือส่วนประกอบ UI อื่นๆ ได้ที่นี่
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // สร้างและแสดงหน้า StaffPage เมื่อเริ่มโปรแกรม
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

        // กำหนดค่าสำหรับหน้าต่าง StaffPage
        setTitle("Staff Page"); // ตั้งชื่อของหน้าต่าง Staff Page
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดหน้าต่างเมื่อปิดหน้าต่าง Staff Page
        setBounds(100, 100, 600, 600); // ตั้งขนาดของหน้าต่าง Staff Page (ปรับเป็นตามที่ต้องการ)
        // สร้างและกำหนดลักษณะของ contentPane ของ StaffPage
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // กำหนด Layout ให้เป็น null เพื่อให้สามารถกำหนดตำแหน่งของคอมโพเนนต์เองได้

        // เพิ่ม JLabel และ JTextField สำหรับกรอก Member ID
        JLabel lblMemberId = new JLabel("Member ID:");
        lblMemberId.setBounds(10, 11, 83, 14); // ตำแหน่งของ JLabel ใน contentPane
        contentPane.add(lblMemberId);// เพิ่ม JLabel ลงใน contentPane

        memberIdTextField = new JTextField();
        memberIdTextField.setBounds(103, 8, 298, 20); // ตำแหน่งของ JTextField ใน contentPane
        contentPane.add(memberIdTextField); // เพิ่ม JTextField ลงใน contentPane
        memberIdTextField.setColumns(10); // กำหนดขนาดของ JTextField

        // เพิ่ม JButton สำหรับค้นหาข้อมูลสมาชิก
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() { // สร้าง ActionListener สำหรับปุ่มค้นหาข้อมูลสมาชิก
            public void actionPerformed(ActionEvent e) { // สร้างเมธอดที่จะทำงานเมื่อมีการคลิกปุ่มค้นหาข้อมูลสมาชิก
                String memberId = memberIdTextField.getText();// รับค่า Member ID จาก JTextField
                DefaultListModel<String> userlistModel = new DefaultListModel<>(); // สร้าง DefaultListModel
                                                                                   // สำหรับเก็บข้อมูลสมาชิก

                try {// ใช้ try-catch เพื่อจัดการข้อผิดพลาดที่อาจเกิดขึ้น
                    List<String> userLines = Files.readAllLines(Paths.get("user.txt"));// อ่านข้อมูลจากไฟล์ user.txt
                    for (String line : userLines) {// วนลูปเพื่อค้นหาข้อมูลสมาชิกที่ตรงกับ Member ID ที่ระบุ
                        if (line.contains(memberId)) {// ถ้าพบข้อมูลสมาชิกที่ตรงกับ Member ID ที่ระบุ
                            userlistModel.addElement(line);// เพิ่มข้อมูลสมาชิกลงใน DefaultListModel
                        }
                    }

                } catch (IOException ex) {// จัดการข้อผิดพลาดที่เกิดขึ้น
                    ex.printStackTrace();// แสดงข้อผิดพลาดที่เกิดขึ้น
                    userlistModel.addElement("Error reading user file");// แสดงข้อความว่าเกิดข้อผิดพลาดในการอ่านไฟล์
                                                                        // user.txt

                }

                userList.setModel(userlistModel);// กำหนดข้อมูลใน JList ของสมาชิก

            }
        });
        btnSearch.setBounds(411, 7, 147, 23);// ตำแหน่งของปุ่มค้นหาข้อมูลสมาชิกใน contentPane
        contentPane.add(btnSearch);// เพิ่มปุ่มค้นหาข้อมูลสมาชิกลงใน contentPane

        JLabel lblBooking = new JLabel("Booking:");// เพิ่ม JLabel สำหรับแสดงข้อความ "Booking"
        lblBooking.setBounds(10, 49, 83, 14);// ตำแหน่งของ JLabel ใน contentPane
        contentPane.add(lblBooking);// เพิ่ม JLabel ลงใน contentPane

        bookingTextField = new JTextField();// เพิ่ม JTextField สำหรับกรอกข้อมูลการจอง
        bookingTextField.setBounds(103, 46, 298, 20); // ตำแหน่งของ JTextField ใน contentPane
        contentPane.add(bookingTextField);// เพิ่ม JTextField ลงใน contentPane
        bookingTextField.setColumns(10); // กำหนดขนาดของ JTextField

        JButton btnConfirmBooking = new JButton("Confirm Booking"); // เพิ่ม JButton สำหรับยืนยันการจอง
        btnConfirmBooking.setBounds(411, 45, 147, 23); // ตำแหน่งของปุ่มยืนยันการจองใน contentPane
        contentPane.add(btnConfirmBooking); // เพิ่มปุ่มยืนยันการจองลงใน contentPane

        JLabel lblPrice = new JLabel("Price:"); // เพิ่ม JLabel สำหรับแสดงข้อความ "Price"
        lblPrice.setBounds(10, 89, 83, 14); // ตำแหน่งของ JLabel ใน contentPane
        contentPane.add(lblPrice); // เพิ่ม JLabel ลงใน contentPane

        priceTextField = new JTextField(); // เพิ่ม JTextField สำหรับแสดงราคา
        priceTextField.setEditable(false); // กำหนดให้ไม่สามารถแก้ไขข้อมูลใน JTextField ได้
        priceTextField.setBounds(103, 86, 298, 20); // ตำแหน่งของ JTextField ใน contentPane
        contentPane.add(priceTextField); // เพิ่ม JTextField ลงใน contentPane
        priceTextField.setColumns(10);// กำหนดขนาดของ JTextField

        JButton btnCalculatePrice = new JButton("Calculate Price"); // เพิ่ม JButton สำหรับคำนวณราคา
        btnCalculatePrice.setBounds(411, 79, 147, 23);// ตำแหน่งของปุ่มคำนวณราคาใน contentPane
        contentPane.add(btnCalculatePrice); // เพิ่มปุ่มคำนวณราคาลงใน contentPane

        DefaultListModel<String> userlistModel = new DefaultListModel<>();// สร้าง DefaultListModel
                                                                          // สำหรับเก็บข้อมูลสมาชิก
        userList = new JList<>(userlistModel);// สร้าง JList สำหรับแสดงข้อมูลสมาชิก
        JScrollPane userlistPane = new JScrollPane(userList); // สร้าง JScrollPane สำหรับแสดงข้อมูลสมาชิก
        userlistPane.setBounds(32, 174, 258, 148); // ตำแหน่งของ JScrollPane ใน contentPane
        contentPane.add(userlistPane); // เพิ่ม JScrollPane ลงใน contentPane

        DefaultListModel<String> courtListModel = new DefaultListModel<>(); // สร้าง DefaultListModel
                                                                            // สำหรับเก็บข้อมูลสนาม
        courtList = new JList<>(courtListModel); // สร้าง JList สำหรับแสดงข้อมูลสนาม
        JScrollPane courtListPane = new JScrollPane(courtList); // สร้าง JScrollPane สำหรับแสดงข้อมูลสนาม
        courtListPane.setBounds(300, 174, 258, 148); // ตำแหน่งของ JScrollPane ใน contentPane
        contentPane.add(courtListPane); // เพิ่ม JScrollPane ลงใน contentPane

        JButton btnSelectTime = new JButton("Select Time To Booking"); // เพิ่ม JButton สำหรับเลือกเวลาที่จะจอง
        btnSelectTime.setBounds(32, 119, 526, 44); // ตำแหน่งของปุ่มเลือกเวลาที่จะจองใน contentPane
        contentPane.add(btnSelectTime); // เพิ่มปุ่มเลือกเวลาที่จะจองลงใน contentPane

        userList.addMouseListener(new MouseAdapter() { // สร้าง MouseAdapter สำหรับจัดการเมื่อมีการคลิกที่ JList
                                                       // ของสมาชิก
            @Override // ใช้เมื่อต้องการเขียนเมธอดที่เป็น Override
                      // จากคลาสหรืออินเทอร์เฟซที่เราสร้างขึ้น
            public void mouseClicked(MouseEvent evt) { // สร้างเมธอดที่จะทำงานเมื่อมีการคลิกที่ JList ของสมาชิก
                if (evt.getClickCount() == 1) { // ตรวจสอบว่ามีการคลิกเพียง 1 ครั้งหรือไม่
                    String selectedUser = userList.getSelectedValue(); // รับข้อมูลสมาชิกที่ถูกเลือก

                    if (selectedUser != null) {// ตรวจสอบว่าข้อมูลสมาชิกที่ถูกเลือกไม่ใช่ค่าว่าง
                        String[] parts = selectedUser.split(", "); // แยกข้อมูลสมาชิกออกเป็นส่วนๆ
                        if (parts.length >= 3) { // ตรวจสอบว่าข้อมูลสมาชิกมีอย่างน้อย 3 ส่วน
                            String selectedMemberId = parts[0].trim(); // แยกและเลือก Member ID
                            String selectedName = parts[1].trim(); // แยกและเลือกชื่อ
                            String selectedSurname = parts[2].trim();// แยกและเลือกนามสกุล
                            memberIdTextField.setText(selectedMemberId + ", " + selectedName + " " + selectedSurname); // แสดงข้อมูล
                                                                                                                       // Member
                                                                                                                       // ID
                                                                                                                       // และชื่อ-นามสกุล
                                                                                                                       // ใน
                                                                                                                       // JTextField
                        }
                    }
                }
            }
        });

        courtList.addMouseListener(new MouseAdapter() {// สร้าง MouseAdapter สำหรับจัดการเมื่อมีการคลิกที่ JList ของสนาม
            @Override
            public void mouseClicked(MouseEvent event) {// สร้างเมธอดที่จะทำงานเมื่อมีการคลิกที่ JList ของสนาม
                if (event.getClickCount() == 1) {// ตรวจสอบว่ามีการคลิกเพียง 1 ครั้งหรือไม่
                    String selectedCourt = courtList.getSelectedValue();// รับข้อมูลสนามที่ถูกเลือก
                    if (selectedCourt != null) { // ตรวจสอบว่าข้อมูลสนามที่ถูกเลือกไม่ใช่ค่าว่าง
                        String[] parts = selectedCourt.split(", "); // แยกข้อมูลสนามออกเป็นส่วนๆ
                        if (parts.length >= 2) { // ตรวจสอบว่าข้อมูลสนามมีอย่างน้อย 2 ส่วน
                            String name = parts[0]; // แยกและเลือกชื่อสนาม
                            String price = parts[1]; // แยกและเลือกราคา

                            // แสดงชื่อสนามและราคาใน JTextField
                            bookingTextField.setText(name + " " + price); // แสดงชื่อสนามและราคาใน JTextField
                        }
                    }
                }
            }
        });
        // สร้าง ActionListener สำหรับปุ่มเลือกเวลาที่จะจอง
        btnSelectTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {// สร้างเมธอดที่จะทำงานเมื่อมีการคลิกปุ่มเลือกเวลาที่จะจอง
                ScheduleTable s = new ScheduleTable(StaffPage.this);// สร้าง ScheduleTable และส่งค่า StaffPage ไปให้
                                                                    // ScheduleTable
                s.setVisible(true); // แสดง ScheduleTable
            }
        });
        // สร้าง ActionListener สำหรับปุ่มคำนวณราคา
        btnCalculatePrice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {// สร้างเมธอดที่จะทำงานเมื่อมีการคลิกปุ่มคำนวณราคา

                String courtInfo = courtList.getSelectedValue();// รับข้อมูลสนามที่ถูกเลือก
                String[] courtPrice = courtInfo.split(", ");// แยกข้อมูลสนามออกเป็นส่วนๆ
                String nPrice = courtPrice.length > 1 ? courtPrice[1].trim() : ""; // แยกและเลือกราคาปกติ
                String wPrice = courtPrice[2].trim(); // แยกและเลือกราคาวันเสาร์-อาทิตย์

                Integer normalPrice = Integer.parseInt(nPrice);// แปลงข้อความราคาปกติเป็นตัวเลข
                Integer weekendPrice = Integer.parseInt(wPrice);// แปลงข้อความราคาวันเสาร์-อาทิตย์เป็นตัวเลข

                if (DayOfWeek.equals("Monday") || DayOfWeek.equals("Tuesday") || DayOfWeek.equals("Wednesday") // ตรวจสอบว่าเป็นวันธรรมดาหรือวันหยุด
                        || DayOfWeek.equals("Thursday") || DayOfWeek.equals("Friday")) {// ถ้าเป็นวันธรรมดา
                    // System.out.println("normal day");// แสดงข้อความว่าเป็นวันธรรมดา
                    // System.out.println(SumOfHour);// แสดงจำนวนชั่วโมงที่จอง
                    // System.out.println(nPrice);// แสดงราคาปกติ

                    price = calculatePrice(SumOfHour, normalPrice);// คำนวณราคา
                    priceTextField.setText(String.valueOf(price));// แสดงราคาใน JTextField

                } else {// ถ้าเป็นวันหยุด
                    // System.out.println("weekend day");// แสดงข้อความว่าเป็นวันหยุด
                    // System.out.println(SumOfHour);// แสดงจำนวนชั่วโมงที่จอง
                    // System.out.println(wPrice);// แสดงราคาวันหยุด
                    price = calculatePrice(SumOfHour, weekendPrice);// คำนวณราคา
                    priceTextField.setText(String.valueOf(price));// แสดงราคาใน JTextField
                }

            }
        });
        // สร้าง ActionListener สำหรับปุ่มยืนยันการจอง
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // สร้างเมธอดที่จะทำงานเมื่อมีการคลิกปุ่มค้นหาข้อมูลสมาชิก
                String memberId = memberIdTextField.getText(); // รับค่า Member ID จาก JTextField
                DefaultListModel<String> userlistModel = new DefaultListModel<>();// สร้าง DefaultListModel
                                                                                  // สำหรับเก็บข้อมูลสมาชิก

                try {// ใช้ try-catch เพื่อจัดการข้อผิดพลาดที่อาจเกิดขึ้น
                    List<String> userLines = Files.readAllLines(Paths.get("user.txt"));// อ่านข้อมูลจากไฟล์ user.txt
                    for (String line : userLines) {// วนลูปเพื่อค้นหาข้อมูลสมาชิกที่ตรงกับ Member ID ที่ระบุ
                        if (line.contains(memberId)) {// ถ้าพบข้อมูลสมาชิกที่ตรงกับ Member ID ที่ระบุ
                            userlistModel.addElement(line);// เพิ่มข้อมูลสมาชิกลงใน DefaultListModel
                        }
                    }
                    // แสดงข้อมูลสมาชิกใน JList
                } catch (IOException ex) {
                    ex.printStackTrace();// แสดงข้อผิดพลาดที่เกิดขึ้น
                    userlistModel.addElement("Error reading user file");// แสดงข้อความว่าเกิดข้อผิดพลาดในการอ่านไฟล์
                                                                        // user.txt

                }
                // กำหนดข้อมูลใน JList ของสมาชิก
                userList.setModel(userlistModel);

            }
        });
        // สร้าง ActionListener สำหรับปุ่มยืนยันการจอง
        displayUsers();
        displayCourts();

        // สร้าง JPanel สำหรับแสดงข้อมูลการจอง
        JPanel receiveChangePanel = new JPanel();
        receiveChangePanel.setBorder(BorderFactory.createTitledBorder("Receive Change Page")); // ตั้งชื่อของ JPanel
        receiveChangePanel.setBounds(10, 340, 564, 192); // ปรับตำแหน่งและขนาดตามต้องการ
        contentPane.add(receiveChangePanel); // เพิ่ม JPanel ลงใน contentPane
        receiveChangePanel.setLayout(new BorderLayout(0, 0)); // กำหนด Layout ของ JPanel เป็น BorderLayout

        JTextArea receiveChangeTextArea = new JTextArea();// สร้าง JTextArea สำหรับแสดงข้อมูลการจอง
        receiveChangeTextArea.setEditable(false); // กำหนดให้ไม่สามารถแก้ไขข้อมูลใน JTextArea ได้
        receiveChangePanel.add(new JScrollPane(receiveChangeTextArea), BorderLayout.CENTER); // เพิ่ม JScrollPane ลงใน
                                                                                             // JPanel

        // เพิ่ม ActionListener ไปที่ btnConfirmBooking เพื่ออัปเดตหน้ารับการเปลี่ยนแปลง
        btnConfirmBooking.addActionListener(new ActionListener() { // สร้าง ActionListener สำหรับปุ่มยืนยันการจอง
            public void actionPerformed(ActionEvent e) { // สร้างเมธอดที่จะทำงานเมื่อมีการคลิกปุ่มยืนยันการจอง
                // ดึงข้อมูลการจอง
                String inputText = memberIdTextField.getText(); // ดึงข้อมูลจาก JTextField ของ Member ID
                String courtInfo = courtList.getSelectedValue(); // ดึงข้อมูลจาก JList ของสนาม
                boolean isOverlapping = false;
                String[] parts = inputText.split(", "); // แยกข้อมูลสมาชิกออกเป็นส่วนๆ
                String memberId = parts.length > 0 ? parts[0].trim() : ""; // แยกและเลือก memberId
                String userName = parts[1].trim(); // แยกและเลือกชื่อสมาชิก


                if (!isOverlapping) {
                    if (courtInfo != null) {// ตรวจสอบว่าข้อมูลสนามที่ถูกเลือกไม่ใช่ค่าว่าง
                        confirmBooking(memberId, userName, courtInfo, price, DayOfWeek, DaysandHoursStrings); // ใช้
                                                                                                              // memberId
                                                                                                              // ที่แยกได้
                        // อัปเดตข้อความในหน้ารับการเปลี่ยนแปลง
                        String bookingDetails = "Member ID: " + memberId + "\nMember Name: " + userName
                                + "\nCourt Info: " // แสดงข้อมูลการจอง
                                + courtInfo// แสดงข้อมูลการจอง
                                + "\nPrice: " + price + "\nDate Booking: " + DayOfWeek + "\nTime: "
                                + DaysandHoursStrings; // แสดงข้อมูลการจอง
                        receiveChangeTextArea.setText(bookingDetails);// แสดงข้อมูลการจองใน JTextArea
                    }
                }

            }
        });

    }

    // สร้างเมธอดสำหรับคำนวณราคา
    private int calculatePrice(int sumOfhour, int totalTime) {
        int x = sumOfhour * totalTime; // คำนวณราคาโดยการคูณจำนวนชั่วโมงที่จองด้วยราคาต่อชั่วโมง
        return x; // ส่งค่าราคาที่คำนวณได้กลับ
    }

    // สร้างเมธอดสำหรับยืนยันการจอง
    private void confirmBooking(String memberId, String userName, String courtInfo, double price, String dateBooking, // สร้างเมธอดสำหรับยืนยันการจอง
            String timeBooking) {// สร้างเมธอดสำหรับยืนยันการจอง
        String bookingInfo = "Member ID: " + memberId + "\nMember Name: " + userName + "\nCourt Info: " + courtInfo // สร้างข้อมูลการจอง
                + "\nPrice: " + price + "\nDate Booking: " + dateBooking + "\nTime: " + timeBooking; // สร้างข้อมูลการจอง

        JOptionPane.showMessageDialog(this, "Booking confirmed!\n" + bookingInfo, "Confirmation", // แสดงข้อความยืนยันการจอง
                JOptionPane.INFORMATION_MESSAGE);//// ส่งค่าราคาที่คำนวณได้กลับ

        try {// ใช้ try-catch เพื่อจัดการข้อผิดพลาดที่อาจเกิดขึ้น
            FileWriter writer = new FileWriter("courtbooking.txt", true); // สร้าง FileWriter
                                                                          // สำหรับเขียนข้อมูลการจองลงในไฟล์
            writer.write(bookingInfo + "\n"); // เขียนข้อมูลการจองลงในไฟล์
            writer.close(); // ปิด FileWriter
        } catch (IOException ex) { // จัดการข้อผิดพลาดที่เกิดขึ้น
            ex.printStackTrace();// แสดงข้อผิดพลาดที่เกิดขึ้น
        }
    }

    // สร้างเมธอดสำหรับแสดงข้อมูลสมาชิก
    private void displayUsers() {
        DefaultListModel<String> userlistModel = new DefaultListModel<>(); // สร้าง DefaultListModel
                                                                           // สำหรับเก็บข้อมูลสมาชิก
        try {// ใช้ try-catch เพื่อจัดการข้อผิดพลาดที่อาจเกิดขึ้น
            List<String> lines = Files.readAllLines(Paths.get("user.txt")); // อ่านข้อมูลจากไฟล์ user.txt
            // วนลูปผ่านทุกบรรทัดของไฟล์และเพิ่มข้อมูลเข้าใน userlistModel
            for (String line : lines) {
                userlistModel.addElement(line);
            } // คำนวณเสร็จสิ้นของการอ่านและเพิ่มข้อมูล
        } catch (IOException ex) {
            // จัดการข้อผิดพลาดที่เกิดขึ้นในกรณีที่เกิดข้อผิดพลาดในการอ่านไฟล์
            ex.printStackTrace(); // แสดงข้อผิดพลาดที่เกิดขึ้น
            userlistModel.addElement("Error reading user file"); // แสดงข้อความว่าเกิดข้อผิดพลาดในการอ่านไฟล์ user.txt
        }
        userList.setModel(userlistModel);// กำหนดข้อมูลใน JList ของสมาชิก
    }

    // สร้างเมธอดสำหรับแสดงข้อมูลสนาม
    private void displayCourts() {
        DefaultListModel<String> courtListModel = new DefaultListModel<>();// สร้าง DefaultListModel
                                                                           // สำหรับเก็บข้อมูลสนาม
        try {// ใช้ try-catch เพื่อจัดการข้อผิดพลาดที่อาจเกิดขึ้น
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt")); // อ่านข้อมูลจากไฟล์ courtList.txt
            for (String line : lines) { // วนลูปผ่านทุกบรรทัดของไฟล์และเพิ่มข้อมูลเข้าใน courtListModel
                courtListModel.addElement(line); // เพิ่มข้อมูลลงใน courtListModel
            }
        } catch (IOException ex) {// จัดการข้อผิดพลาดที่เกิดขึ้นในกรณีที่เกิดข้อผิดพลาดในการอ่านไฟล์
            ex.printStackTrace();// แสดงข้อผิดพลาดที่เกิดขึ้น
            courtListModel.addElement("Error reading court file"); // แสดงข้อความว่าเกิดข้อผิดพลาดในการอ่านไฟล์
                                                                   // courtList.txt
        } // คำนวณเสร็จสิ้นของการอ่านและเพิ่มข้อมูล
        courtList.setModel(courtListModel);// กำหนดข้อมูลใน JList ของสนาม
    }
}
