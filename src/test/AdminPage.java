package test; // Use the same package as your MainPage 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AdminPage extends JFrame {

    // สร้างอ็อบเจ็กต์ของ DefaultListModel ที่เก็บข้อมูลประเภท String และตั้งชื่อว่า
    // userlistModel
    DefaultListModel<String> userlistModel = new DefaultListModel<>();
    // สร้างอ็อบเจ็กต์ JList ที่เก็บข้อมูลประเภท String ชื่อว่า userList
    // โดยใช้ข้อมูลจาก userlistModel ที่เราสร้างขึ้นมาก่อนหน้านี้
    JList<String> userList = new JList<>(userlistModel);

    // สร้างอ็อบเจ็กต์ของ DefaultListModel ที่เก็บข้อมูลประเภท String และตั้งชื่อว่า
    // courtlistModel
    DefaultListModel<String> courtListModel = new DefaultListModel<>();
    // สร้างอ็อบเจ็กต์ JList ที่เก็บข้อมูลประเภท String ชื่อว่า courtList
    // โดยใช้ข้อมูลจาก courtListModel ที่เราสร้างขึ้นมาก่อนหน้านี้
    JList<String> courtList = new JList<>(courtListModel);

    // สร้างตัวแปร private ชนิด long ชื่อว่า baseKey และกำหนดค่าเริ่มต้นให้เป็น
    // 6606021420059L
    private long baseKey = 6606021420059L; // This is Default value when user was null

    // สร้าง constructor ของคลาส AdminPage แบบไม่มีพารามิเตอร์
    public AdminPage() {
        /**
         * ทำการเรียกใช้method displayUsers เพื่อแสดงUserทั้งหมด
         * ซึ่งในmethod นี้มีการเรียกใช้ method purgeOldUsers
         * และ updateUserPromotions
         */
        displayUsers();
        /**
         * ทำการเรียกใช้method displayCourts เพื่อแสดงCourtทั้งหมดมาแสดง
         */
        displayCourts();
        /**
         * ทำการเรียกใช้method loadBaseKey เพื่อเช็คว่าควรใช้baseKey+1
         * เพื่อกำหนดให้เป็นprimarykey
         */
        loadBaseKey();
        setResizable(false); // ตั้งค่าให้ไม่สามารถปรับขนาดหน้าต่างได้
        setTitle("Admin Page"); // ตั้งชื่อหน้าต่างเป็น "Admin Page"
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // กำหนดการปิดหน้าต่างเมื่อกดปุ่มปิด
        setBounds(100, 100, 600, 550); // กำหนดขนาดของหน้าต่าง
        getContentPane().setLayout(null); // กำหนด layout ของหน้าต่างเป็น null

        // สร้าง JLabel ชื่อว่า lblNewLabel และกำหนดข้อความเป็น "What do you want to
        // do?"
        JLabel lblNewLabel = new JLabel("What do you want to do?");
        // กำหนด font ของข้อความเป็น "Franklin Gothic Demi Cond", Font.BOLD, 24
        lblNewLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 24));
        // กำหนดให้ข้อความอยู่ตรงกลาง
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // กำหนดตำแหน่งของ JLabel ที่ตำแหน่ง x=109, y=11, width=364, height=53
        lblNewLabel.setBounds(109, 11, 364, 53);
        // เพิ่ม JLabel ลงในหน้าต่าง
        getContentPane().add(lblNewLabel);

        // สร้าง JLabel ชื่อว่า lblManageUser และกำหนดข้อความเป็น "Manage User"
        JLabel lblManageUser = new JLabel("Manage User");
        // กำหนดให้ข้อความอยู่ตรงกลาง
        lblManageUser.setHorizontalAlignment(SwingConstants.CENTER);
        // กำหนด font ของข้อความเป็น "Franklin Gothic Demi Cond", Font.BOLD, 16
        lblManageUser.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        // กำหนดตำแหน่งของ JLabel ที่ตำแหน่ง x=10, y=52, width=133, height=53
        lblManageUser.setBounds(10, 52, 133, 53);
        // เพิ่ม JLabel ลงในหน้าต่าง
        getContentPane().add(lblManageUser);

        // สร้าง JLabel ชื่อว่า lblManageCourt และกำหนดข้อความเป็น "Manage Court"
        JLabel lblManageCourt = new JLabel("Manage Court");
        // กำหนดให้ข้อความอยู่ตรงกลาง
        lblManageCourt.setHorizontalAlignment(SwingConstants.CENTER);
        // กำหนด font ของข้อความเป็น "Franklin Gothic Demi Cond", Font.BOLD, 16
        lblManageCourt.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        // กำหนดตำแหน่งของ JLabel ที่ตำแหน่ง x=268, y=95, width=133, height=53
        lblManageCourt.setBounds(10, 95, 133, 53);
        // เพิ่ม JLabel ลงในหน้าต่าง
        getContentPane().add(lblManageCourt);

        // สร้าง JLabel ชื่อว่า lblUsersList และกำหนดข้อความเป็น "Users List"
        JLabel lblUsersList = new JLabel("Users List");
        // กำหนดให้ข้อความอยู่ตรงกลาง
        lblUsersList.setHorizontalAlignment(SwingConstants.CENTER);
        // กำหนด font ของข้อความเป็น "Franklin Gothic Demi Cond", Font.BOLD, 16
        lblUsersList.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        // กำหนดตำแหน่งของ JLabel ที่ตำแหน่ง x=0, y=135, width=133, height=53
        lblUsersList.setBounds(0, 135, 133, 53);
        // เพิ่ม JLabel ลงในหน้าต่าง
        getContentPane().add(lblUsersList);

        // สร้าง JLabel ชื่อว่า lblCourtList และกำหนดข้อความเป็น "Court List"
        JLabel lblCourtList = new JLabel("Court List");
        // กำหนดให้ข้อความอยู่ตรงกลาง
        lblCourtList.setHorizontalAlignment(SwingConstants.CENTER);
        // กำหนด font ของข้อความเป็น "Franklin Gothic Demi Cond", Font.BOLD, 16
        lblCourtList.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        // กำหนดตำแหน่งของ JLabel ที่ตำแหน่ง x=268, y=134, width=133, height=53
        lblCourtList.setBounds(268, 134, 133, 53);
        // เพิ่ม JLabel ลงในหน้าต่าง
        getContentPane().add(lblCourtList);

        // สร้าง JButton ชื่อว่า addUser และกำหนดข้อความเป็น "Add User"
        JButton addUser = new JButton("Add User");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=156, y=67, width=97, height=23
        addUser.setBounds(156, 67, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(addUser);

        // สร้าง JButton ชื่อว่า remUser และกำหนดข้อความเป็น "Remove User"
        JButton remUser = new JButton("Remove User");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=370, y=67, width=97, height=23
        remUser.setFont(new Font("Tahoma", Font.PLAIN, 9));
        // เพิ่ม JButton ลงในหน้าต่าง
        remUser.setBounds(370, 67, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(remUser);

        // สร้าง JButton ชื่อว่า editUser และกำหนดข้อความเป็น "Edit User"
        JButton editUser = new JButton("Edit User");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=263, y=67, width=97, height=23
        editUser.setBounds(263, 67, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(editUser);

        // สร้าง JButton ชื่อว่า findUser และกำหนดข้อความเป็น "Find"
        JButton findUser = new JButton("Find");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=477, y=67, width=97, height=23
        findUser.setBounds(477, 67, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(findUser);

        // สร้าง JButton ชื่อว่า addCourt และกำหนดข้อความเป็น "Add Court"
        JButton addCourt = new JButton("Add Court");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=156, y=110, width=97, height=23
        addCourt.setBounds(156, 110, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(addCourt);

        // สร้าง JButton ชื่อว่า remCourt และกำหนดข้อความเป็น "Remove Court"
        JButton remCourt = new JButton("Remove Court");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=370, y=110, width=97, height=23
        remCourt.setFont(new Font("Tahoma", Font.PLAIN, 9));
        // เพิ่ม JButton ลงในหน้าต่าง
        remCourt.setBounds(370, 110, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(remCourt);

        // สร้าง JButton ชื่อว่า editCourt และกำหนดข้อความเป็น "Edit Court"
        JButton editCourt = new JButton("Edit Court");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=263, y=110, width=97, height=23
        editCourt.setBounds(263, 110, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(editCourt);

        // สร้าง JButton ชื่อว่า findCourt และกำหนดข้อความเป็น "Find"
        JButton findCourt = new JButton("Find");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=477, y=110, width=97, height=23
        findCourt.setBounds(477, 110, 97, 23);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(findCourt);

        // สร้าง JButton ชื่อว่า refreshButton และกำหนดข้อความเป็น "Refresh"
        JButton refreshButton = new JButton("Refresh");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=250, y=337, width=97, height=25
        refreshButton.setBounds(250, 337, 97, 25);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(refreshButton);

        // สร้าง JButton ชื่อว่า reportButton และกำหนดข้อความเป็น "Show Report"
        JButton reportButton = new JButton("Show Report");
        // กำหนดตำแหน่งของ JButton ที่ตำแหน่ง x=360, y=337, width=120, height=25
        reportButton.setBounds(360, 337, 120, 25);
        // เพิ่ม JButton ลงในหน้าต่าง
        getContentPane().add(reportButton);

        // สร้าง JLabel ชื่อว่า lblUserList และกำหนดข้อความเป็น "User List"
        userList.setBounds(31, 243, 324, 148);
        // เพิ่ม JList ลงในหน้าต่าง
        JScrollPane userlistPane = new JScrollPane(userList);
        // กำหนดตำแหน่งของ JScrollPane ที่ตำแหน่ง x=32, y=174, width=258, height=148
        userlistPane.setBounds(32, 174, 258, 148);
        // เพิ่ม JScrollPane ลงในหน้าต่าง
        getContentPane().add(userlistPane);

        // สร้าง JLabel ชื่อว่า lblCourtList และกำหนดข้อความเป็น "Court List"
        courtList.setBounds(22, 243, 324, 148);
        // เพิ่ม JList ลงในหน้าต่าง
        JScrollPane courtlistPane = new JScrollPane(courtList);
        // กำหนดตำแหน่งของ JScrollPane ที่ตำแหน่ง x=300, y=174, width=258, height=148
        courtlistPane.setBounds(300, 174, 258, 148);
        // เพิ่ม JScrollPane ลงในหน้าต่าง
        getContentPane().add(courtlistPane);

        // Action listener list ต่างๆ
        // User management
        addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม addUser จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด
                 * actionPerformed ภายในนั้นจะทำการรับข้อมูล username และ surname จากผู้ใช้ผ่าน
                 * JOptionPane แล้วบันทึกข้อมูลเหล่านี้ลงไฟล์ 'user.txt' ด้วยเมธอด savetoFile
                 * และทำการแสดงผู้ใช้ทั้งหมดด้วยเมธอด displayUsers
                 */
                String username = JOptionPane.showInputDialog("Enter username");
                String surname = JOptionPane.showInputDialog("Enter surname");

                savetoFile(username, surname, "user.txt");

                displayUsers();
            }
        });

        remUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม remUser จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด actionPerformed ภายในนั้นจะทำการรับ index
                 * ของผู้ใช้ที่ถูกเลือกจาก userList
                 * หากมีผู้ใช้ถูกเลือกและผู้ใช้ยืนยันการลบผ่านการแสดง dialog
                 * ด้วยเมธอด showConfirmationDialog จะทำการลบผู้ใช้นั้นออกจาก userlistModel
                 * และไฟล์ 'user.txt' ด้วยเมธอด removeItemFromList
                 */
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1
                        && showConfirmationDialog("Are you sure you want to delete this user?", "Confirm Deletion")) {
                    removeItemFromList(userlistModel, "user.txt", selectedIndex);
                }
            }
        });

        editUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม editUser จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด
                 * actionPerformed ภายในนั้นจะทำการรับ index ของผู้ใช้ที่ถูกเลือกจาก userList
                 * หากมีผู้ใช้ถูกเลือก จะทำการรับข้อมูลผู้ใช้ที่ถูกเลือกจาก userlistModel
                 * แล้วส่งข้อมูลนี้ไปยังเมธอด
                 * editUserData เพื่อทำการแก้ไขข้อมูลผู้ใช้
                 */
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedUser = userlistModel.getElementAt(selectedIndex);
                    editUserData(selectedUser); // Call a new method to handle editing
                }
            }
        });

        findUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม findUser จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด actionPerformed ภายในนั้นจะทำการเรียกเมธอด
                 * searchUser ที่ทำหน้าที่ค้นหาผู้ใช้
                 */
                searchUser();
            }
        });
        // End of user management

        // Court management
        addCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม addCourt จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด
                 * actionPerformed ภายในนั้นจะทำการรับข้อมูลชื่อสนาม, ราคาวันธรรมดา,
                 * และราคาวันหยุด
                 * จากผู้ใช้ผ่าน JOptionPane หากข้อมูลทั้งหมดถูกต้องและไม่ว่างเปล่า
                 * จะทำการตรวจสอบว่าราคาวันธรรมดาและราคาวันหยุดเป็นตัวเลขที่ถูกต้อง
                 * หากถูกต้องจะทำการบันทึกข้อมูลสนามลงไฟล์ 'courtList.txt' ด้วยเมธอด
                 * saveCourtToFile
                 * และทำการแสดงสนามทั้งหมดด้วยเมธอด displayCourts
                 * หากมีข้อผิดพลาดจะแสดงข้อความแจ้งเตือน
                 */
                String courtName = showInputDialog("Enter court name", "Input Court Name", null);
                String normalDayPrice = showInputDialog("Enter price for normal days", "Input Price", null);
                String weekendPrice = showInputDialog("Enter price for weekends", "Input Price", null);
                if (courtName != null && !courtName.trim().isEmpty() &&
                        normalDayPrice != null && !normalDayPrice.trim().isEmpty() &&
                        weekendPrice != null && !weekendPrice.trim().isEmpty()) {
                    // Ensure that normalDayPrice and weekendPrice are valid numbers (optional
                    // validation)
                    try {
                        Double.parseDouble(normalDayPrice);
                        Double.parseDouble(weekendPrice);
                        String courtDetails = courtName + ", " + normalDayPrice + ", " + weekendPrice;
                        saveCourtToFile(courtDetails, "courtList.txt");
                        displayCourts();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Normal day price and weekend price must be valid numbers.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Court name, normal day price, and weekend price cannot be empty.");
                }
            }
        });

        remCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม remCourt จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด
                 * actionPerformed ภายในนั้นจะทำการรับ index ของสนามที่ถูกเลือกจาก courtList
                 * หากมีสนามถูกเลือก จะทำการแสดง dialog ยืนยันการลบ หากผู้ใช้ยืนยันการลบ
                 * จะทำการลบสนามนั้นออกจาก courtListModel และไฟล์ 'courtList.txt' ด้วยเมธอด
                 * removeItemFromList
                 */
                int selectedIndex = courtList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this court?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        removeItemFromList(courtListModel, "courtList.txt", selectedIndex);
                    }
                }
            }
        });

        editCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม editCourt จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด
                 * actionPerformed ภายในนั้นจะทำการรับ index ของสนามที่ถูกเลือกจาก courtList
                 * หากมีสนามถูกเลือก
                 * จะทำการรับข้อมูลสนามที่ถูกเลือกจาก courtListModel แล้วส่งข้อมูลนี้ไปยังเมธอด
                 * editCourtData
                 * เพื่อทำการแก้ไขข้อมูลสนาม
                 */
                int courtselectedIndex = courtList.getSelectedIndex();
                if (courtselectedIndex != -1) {
                    String selectedCourt = courtListModel.getElementAt(courtselectedIndex);
                    editCourtData(selectedCourt); // Call a new method to handle editing
                }
            }
        });

        findCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม findCourt จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด actionPerformed
                 * ภายในนั้นจะทำการรับคำค้นหาจากผู้ใช้ผ่าน JOptionPane
                 * หากคำค้นหาว่างเปล่าหรือไม่มีค่า จะไม่ทำอะไรแล้วหยุดการทำงานของเมธอด
                 * 
                 * จากนั้นจะทำการค้นหาสนามที่มีชื่อตรงกับคำค้นหา
                 * หากไม่พบจะทำการค้นหาสนามที่มีราคาตรงกับคำค้นหา
                 * 
                 * หากพบสนามที่ตรงกับคำค้นหา จะแสดงข้อมูลสนามนั้นผ่าน JOptionPane
                 * หากไม่พบจะแสดงข้อความ 'No matching court found.
                 */
                String searchTerm = JOptionPane.showInputDialog("Enter search term:");
                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    // Handle cancel action or empty input
                    return;
                }

                boolean found = false;
                String foundDetails = "";

                // First pass: Look for an exact match in the court names
                for (int i = 0; i < courtListModel.getSize(); i++) {
                    String courtDetails = courtListModel.getElementAt(i);
                    String[] details = courtDetails.split(", ");
                    if (details[0].contains(searchTerm)) {
                        found = true;
                        foundDetails = courtDetails;
                        break; // Stop searching after finding the first match in names
                    }
                }

                // Second pass: If no name matches, look for a match in prices
                if (!found) {
                    for (int i = 0; i < courtListModel.getSize(); i++) {
                        String courtDetails = courtListModel.getElementAt(i);
                        String[] details = courtDetails.split(", ");
                        if (details[1].contains(searchTerm) || details[2].contains(searchTerm)) {
                            found = true;
                            foundDetails = courtDetails;
                            break; // Stop searching after finding the first match in prices
                        }
                    }
                }

                if (found) {
                    // Court found
                    String[] details = foundDetails.split(", ");
                    String message = "Court found: " + details[0] + "\nNormalday Price: " + details[1]
                            + "\nWeekend Price: " + details[2];
                    JOptionPane.showMessageDialog(null, message);
                } else {
                    JOptionPane.showMessageDialog(null, "No matching court found.");
                }
            }
        });

        // End of court management

        refreshButton.addActionListener(new ActionListener() {
            /**
             * เมื่อมีการคลิกที่ปุ่ม refreshButton จะทำการสร้าง ActionListener
             * ที่จะทำงานตามที่กำหนดในเมธอด actionPerformed ภายในนั้นจะทำการเรียกเมธอด
             * displayUsers และ displayCourts ที่ทำหน้าที่แสดงรายการผู้ใช้และสนามทั้งหมด
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                displayUsers();
                displayCourts();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * เมื่อมีการคลิกที่ปุ่ม reportButton จะทำการสร้าง ActionListener
                 * ที่จะทำงานตามที่กำหนดในเมธอด actionPerformed ภายในนั้นจะทำการสร้าง dialog
                 * ใหม่จากคลาส ShowReport และตั้งค่าให้ dialog นี้แสดงอยู่ตรงกลางหน้าจอ
                 * แล้วทำการแสดง
                 * dialog นี้
                 */
                ShowReport reportDialog = new ShowReport(AdminPage.this);
                reportDialog.setLocationRelativeTo(null); // Center on screen
                reportDialog.setVisible(true);
            }
        });

        // End of action listener list

    } // End of constructor

    // User management methods

    private void savetoFile(String username, String surname, String fileName) {
        /**
         * เมธอด savetoFile ทำหน้าที่บันทึกข้อมูลผู้ใช้ลงไฟล์ โดยรับ username, surname,
         * และชื่อไฟล์เป็นพารามิเตอร์ จากนั้นจะสร้างวันที่ปัจจุบัน, สร้าง primaryKey
         * ด้วยเมธอด generatePrimaryKey และสร้างข้อมูลผู้ใช้ที่จะบันทึกลงไฟล์
         * หลังจากนั้นจะทำการเขียนข้อมูลผู้ใช้ลงไฟล์ด้วย BufferedWriter
         * หากมีข้อผิดพลาดในการเขียนไฟล์ จะแสดงรายละเอียดข้อผิดพลาด
         */
        LocalDate currentDate = LocalDate.now();
        String primaryKey = generatePrimaryKey();
        String userData = primaryKey + ", " + username + ", " + surname + ", " + currentDate + ", " + 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userData);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void editUserData(String selectedUser) {
        /**
         * เมธอด editUserData ทำหน้าที่แก้ไขข้อมูลผู้ใช้ที่ถูกเลือก
         * โดยรับข้อมูลผู้ใช้ที่ถูกเลือกเป็นพารามิเตอร์
         * จากนั้นจะทำการแยกข้อมูลผู้ใช้เป็นส่วนๆ และรับ username และ surname
         * ใหม่จากผู้ใช้ผ่าน JOptionPane หาก username หรือ surname ใหม่ว่างเปล่า
         * จะใช้ค่าเดิมแทน
         * 
         * หลังจากนั้นจะทำการสร้างข้อมูลผู้ใช้ที่อัปเดตแล้ว
         * และทำการอัปเดตข้อมูลผู้ใช้ในไฟล์ 'user.txt' โดยการอ่านข้อมูลทั้งหมดจากไฟล์
         * แล้วทำการแทนที่ข้อมูลผู้ใช้ที่ถูกเลือกด้วยข้อมูลผู้ใช้ที่อัปเดตแล้ว
         * แล้วเขียนข้อมูลทั้งหมดลงไฟล์อีกครั้ง หากมีข้อผิดพลาดในการอ่านหรือเขียนไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาด
         * 
         * สุดท้ายจะทำการแสดงรายการผู้ใช้ทั้งหมดด้วยเมธอด displayUsers
         */
        String[] data = selectedUser.split(", ");
        String primaryKey = data[0]; // Preserve the primary key
        String oldUsername = data[1];
        String oldSurname = data[2];

        String newUsername = JOptionPane.showInputDialog("Enter new username", oldUsername);
        String newSurname = JOptionPane.showInputDialog("Enter new surname", oldSurname);

        // Ensure the new values aren't empty; if they are, use the old values
        if (newUsername == null || newUsername.isEmpty()) {
            newUsername = oldUsername;
        }
        if (newSurname == null || newSurname.isEmpty()) {
            newSurname = oldSurname;
        }

        // Construct the updated user string; primary key remains unaltered
        String updatedUser = primaryKey + ", " + newUsername + ", " + newSurname + ", " + data[3] + ", " + data[4];

        // Update the file
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(selectedUser)) {
                    lines.set(i, updatedUser);
                    break; // Update only the first matching entry
                }
            }
            Files.write(Paths.get("user.txt"), lines);
            displayUsers(); // Refresh the list
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void searchUser() {
        /**
         * เมธอด searchUser ทำหน้าที่ค้นหาผู้ใช้จาก ID หรือชื่อ
         * โดยรับคำค้นหาจากผู้ใช้ผ่าน JOptionPane หากคำค้นหาว่างเปล่าหรือไม่มีค่า
         * จะไม่ทำอะไรแล้วหยุดการทำงานของเมธอด
         * 
         * จากนั้นจะทำการวนลูปเพื่อค้นหาผู้ใช้ที่มี ID หรือชื่อตรงกับคำค้นหา
         * หากพบจะแสดงข้อมูลผู้ใช้และให้ผู้ใช้เลือกทำการแก้ไขหรือลบผู้ใช้นี้
         * หากเลือกแก้ไขจะเรียกเมธอด editUserData หากเลือกลบจะทำการลบผู้ใช้นี้ออกจาก
         * userlistModel และไฟล์ 'user.txt'
         * 
         * หากไม่พบผู้ใช้ที่ตรงกับคำค้นหา จะแสดงข้อความ 'No matching user found.
         */
        String searchInput = JOptionPane.showInputDialog("Enter ID or name to search:");
        if (searchInput == null || searchInput.trim().isEmpty()) {
            // Handle cancel action or empty input
            return;
        }

        // Iterate through the user list to find matching users
        for (int i = 0; i < userlistModel.getSize(); i++) {
            String userDetails = userlistModel.getElementAt(i);
            String[] details = userDetails.split(", ");
            // Check if the searchInput matches the primary key or username
            if (details[0].equals(searchInput) || details[1].equalsIgnoreCase(searchInput)) {
                // User found
                String primaryKey = details[0];
                String username = details[1];
                String surname = details[2];
                String dateRegistry = details[3];
                String promotion = details[4].equals("1") ? "Free 7 Days" : "20% Discount";

                // Display user details in a JOptionPane
                String message = "ID: " + primaryKey + "\nName: " + username + "\nSurname: " + surname
                        + "\nDate-Registry: " + dateRegistry + "\nPromotion: " + promotion;
                Object[] options = { "Edit", "Delete", "Cancel" };
                int choice = JOptionPane.showOptionDialog(null, message, "User Details",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);

                if (choice == JOptionPane.YES_OPTION) {
                    // Edit option selected
                    editUserData(userDetails);
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Delete option selected, ask for confirmation
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        userlistModel.removeElement(userDetails);
                        try {
                            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
                            lines.removeIf(line -> line.contains(userDetails));
                            Files.write(Paths.get("user.txt"), lines);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return; // Exit after processing the first match
            }
        }

        JOptionPane.showMessageDialog(null, "No matching user found.");
    }

    private void purgeOldUsers() {
        /**
         * เมธอด purgeOldUsers ทำหน้าที่ลบผู้ใช้งานที่ลงทะเบียนมากกว่า 1 ปี
         * โดยเริ่มจากการอ่านข้อมูลผู้ใช้งานทั้งหมดจากไฟล์ 'user.txt'
         * แล้ววนลูปตรวจสอบวันที่ลงทะเบียนของแต่ละผู้ใช้
         * หากผู้ใช้งานคนใดลงทะเบียนมาไม่เกิน 1 ปี จะถูกเพิ่มเข้าไปในรายการ updatedLines
         * ที่จะใช้แทนข้อมูลเดิมในไฟล์ 'user.txt' หากมีข้อผิดพลาดในการอ่านหรือเขียนไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาด
         */
        LocalDate today = LocalDate.now();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] details = line.split(", ");
                LocalDate dateRegistry = LocalDate.parse(details[3]);
                long yearsBetween = ChronoUnit.YEARS.between(dateRegistry, today);

                if (yearsBetween <= 1) {
                    updatedLines.add(line); // Keep the user if they have been in the system for 1 year or less
                }
            }

            // Overwrite the user.txt file with the updated lines
            Files.write(Paths.get("user.txt"), updatedLines);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateUserPromotions() {
        /**
         * เมธอด updateUserPromotions ทำหน้าที่อัปเดตสถานะโปรโมชั่นของผู้ใช้งาน
         * โดยเริ่มจากการอ่านข้อมูลผู้ใช้งานทั้งหมดจากไฟล์ 'user.txt'
         * แล้ววนลูปตรวจสอบวันที่ลงทะเบียนของแต่ละผู้ใช้
         * หากผู้ใช้งานคนใดลงทะเบียนมาเกิน 7 วัน สถานะโปรโมชั่นจะถูกเปลี่ยนเป็น '0'
         * แต่ถ้าไม่เกิน 7 วัน สถานะโปรโมชั่นจะถูกเปลี่ยนเป็น '1'
         * หลังจากนั้นจะสร้างข้อมูลผู้ใช้งานใหม่ด้วยข้อมูลที่อัปเดตแล้ว
         * และใช้ข้อมูลใหม่นี้แทนข้อมูลเดิมในไฟล์ 'user.txt'
         * หากมีข้อผิดพลาดในการอ่านหรือเขียนไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาด
         */
        LocalDate today = LocalDate.now();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] details = line.split(", ");
                LocalDate dateRegistry = LocalDate.parse(details[3]);
                long daysBetween = ChronoUnit.DAYS.between(dateRegistry, today);

                if (daysBetween > 7) {
                    // Change the promotion status to "0" if the registration was more than 7 days
                    // ago
                    details[4] = "0";
                } else {
                    details[4] = "1";
                }
                // Reconstruct the line with updated details
                String updatedLine = String.join(", ", details);
                updatedLines.add(updatedLine);
            }

            // Overwrite the user.txt file with the updated lines
            Files.write(Paths.get("user.txt"), updatedLines);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayUsers() {
        /**
         * เมธอด displayUsers ทำหน้าที่แสดงผู้ใช้งาน โดยเริ่มจากการเรียกเมธอด
         * purgeOldUsers
         * และ updateUserPromotions ก่อน จากนั้นจะล้างข้อมูลใน userlistModel
         * และพยายามอ่านข้อมูลผู้ใช้งานจากไฟล์ 'user.txt' ทีละบรรทัด แล้วเพิ่มลงใน
         * userlistModel
         * หากมีข้อผิดพลาดในการอ่านไฟล์ จะแสดงข้อความ 'Error reading file'
         * และรายละเอียดข้อผิดพลาด
         */
        purgeOldUsers();
        updateUserPromotions();
        userlistModel.clear();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            for (String line : lines) {
                userlistModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            userlistModel.addElement("Error reading file");
        }

    }

    // Court management methods
    private void saveCourtToFile(String courtDetails, String fileName) {
        /**
         * เมธอด saveCourtToFile ทำหน้าที่บันทึกข้อมูลสนามลงไฟล์
         * โดยรับข้อมูลสนามและชื่อไฟล์เป็นพารามิเตอร์
         * จากนั้นจะทำการเขียนข้อมูลสนามลงไฟล์ด้วย BufferedWriter
         * หากมีข้อผิดพลาดในการเขียนไฟล์ จะแสดงรายละเอียดข้อผิดพลาด
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(courtDetails);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void editCourtData(String selectedCourt) {
        /**
         * เมธอด editCourtData ทำหน้าที่แก้ไขข้อมูลสนามที่ถูกเลือก
         * โดยรับข้อมูลสนามที่ถูกเลือกเป็นพารามิเตอร์
         * จากนั้นจะทำการแยกข้อมูลสนามเป็นส่วนๆ และรับชื่อสนาม, ราคาวันธรรมดา,
         * และราคาวันหยุดใหม่จากผู้ใช้ผ่าน JOptionPane
         * และทำการตรวจสอบว่าราคาวันธรรมดาและราคาวันหยุดใหม่เป็นตัวเลขที่ถูกต้อง
         * 
         * หากชื่อสนาม, ราคาวันธรรมดา, หรือราคาวันหยุดใหม่ว่างเปล่า จะใช้ค่าเดิมแทน
         * แล้วทำการสร้างข้อมูลสนามที่อัปเดตแล้ว
         * 
         * หลังจากนั้นจะทำการอัปเดตข้อมูลสนามในไฟล์ 'courtList.txt'
         * โดยการอ่านข้อมูลทั้งหมดจากไฟล์
         * แล้วทำการแทนที่ข้อมูลสนามที่ถูกเลือกด้วยข้อมูลสนามที่อัปเดตแล้ว
         * แล้วเขียนข้อมูลทั้งหมดลงไฟล์อีกครั้ง หากมีข้อผิดพลาดในการอ่านหรือเขียนไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาด
         * 
         * สุดท้ายจะทำการแสดงรายการสนามทั้งหมดด้วยเมธอด displayCourts
         */
        String[] data = selectedCourt.split(", ");
        String oldCourtName = data[0];
        String oldNormalDayPrice = data[1];
        String oldWeekendPrice = data[2];

        String newCourtName = JOptionPane.showInputDialog("Enter new court name", oldCourtName);
        String newNormalDayPrice = JOptionPane.showInputDialog("Enter new normal day price", oldNormalDayPrice);
        String newWeekendPrice = JOptionPane.showInputDialog("Enter new weekend price", oldWeekendPrice);

        // Validate the input to ensure that prices are numbers
        try {
            Double.parseDouble(newNormalDayPrice);
            Double.parseDouble(newWeekendPrice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Normal day price and weekend price must be valid numbers.");
            return;
        }

        if (newCourtName == null || newCourtName.isEmpty()) {
            newCourtName = oldCourtName;
        }
        if (newNormalDayPrice == null || newNormalDayPrice.isEmpty()) {
            newNormalDayPrice = oldNormalDayPrice;
        }
        if (newWeekendPrice == null || newWeekendPrice.isEmpty()) {
            newWeekendPrice = oldWeekendPrice;
        }

        String updatedCourt = newCourtName + ", " + newNormalDayPrice + ", " + newWeekendPrice;

        // Update the file
        try {
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(selectedCourt)) {
                    lines.set(i, updatedCourt);
                    break; // Update only the first matching entry
                }
            }
            Files.write(Paths.get("courtList.txt"), lines);
            displayCourts(); // Refresh the list
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayCourts() {
        /**
         * เมธอด displayCourts ทำหน้าที่แสดงรายการของสนามกีฬา
         * โดยเริ่มจากการล้างข้อมูลใน courtListModel แล้วพยายามอ่านข้อมูลสนามกีฬาจากไฟล์
         * 'courtList.txt'
         * ทีละบรรทัด แล้วเพิ่มลงใน courtListModel หากมีข้อผิดพลาดในการอ่านไฟล์
         * จะแสดงข้อความ 'Error reading file' และรายละเอียดข้อผิดพลาด
         */
        try {
            courtListModel.clear();
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt"));

            for (String line : lines) {
                courtListModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            courtListModel.addElement("Error reading file");
        }
    }
    // end of court management methods

    // Generate a unique primary key

    private void loadBaseKey() {
        /**
         * เมธอด loadBaseKey ทำหน้าที่โหลดค่า key ล่าสุดจากไฟล์ 'lastkey.txt'
         * หากไฟล์นี้ไม่ว่างเปล่า ค่า key ล่าสุดจะถูกแปลงเป็นตัวเลขประเภท long
         * แล้วเก็บไว้ในตัวแปร baseKey
         * หากมีข้อผิดพลาดในการอ่านไฟล์ จะแสดงข้อความ 'Error loading last key, using
         * default.'
         * และใช้ค่าเริ่มต้นของ baseKey
         */
        try {
            List<String> lines = Files.readAllLines(Paths.get("lastkey.txt"));
            if (!lines.isEmpty()) {
                baseKey = Long.parseLong(lines.get(0));
            }
        } catch (IOException ex) {
            System.err.println("Error loading last key, using default.");
        }
    }

    private void saveBaseKey() {
        /**
         * เมธอด saveBaseKey ทำหน้าที่บันทึกค่า baseKey ลงไฟล์ 'lastkey.txt'
         * โดยการเขียนค่า baseKey ลงไฟล์ด้วย BufferedWriter
         * หากมีข้อผิดพลาดในการเขียนไฟล์ จะแสดงข้อความ 'Error saving last key.
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lastkey.txt"))) {
            writer.write(String.valueOf(baseKey));
        } catch (IOException ex) {
            System.err.println("Error saving last key.");
        }
    }

    private String generatePrimaryKey() {
        /**
         * เมธอด generatePrimaryKey ทำหน้าที่สร้าง primaryKey โดยการเพิ่มค่า baseKey
         * ทุกครั้งที่เรียกเมธอดนี้ แล้วแปลงค่า baseKey ที่เพิ่มแล้วเป็น String
         * 
         * หากความยาวของ String นี้น้อยกว่า 13 จะทำการเติม '0' ไปด้านหน้า String
         * นี้จนกว่าความยาวจะเท่ากับ 13
         * 
         * หลังจากนั้นจะทำการบันทึกค่า baseKey ด้วยเมธอด saveBaseKey แล้วคืนค่า String
         * ที่ได้จากการแปลงค่า baseKey
         */

        // Increment on each call
        long currentKey = baseKey++;

        String currentKeyStr = String.valueOf(currentKey);

        // Pad with leading zeros if needed
        while (currentKeyStr.length() < 13) {
            currentKeyStr = "0" + currentKeyStr;
        }

        saveBaseKey();

        return currentKeyStr;
    }

    private List<String> readFile(String filePath) {
        /**
         * เมธอด readFile ทำหน้าที่อ่านข้อมูลทั้งหมดจากไฟล์ที่ระบุ
         * โดยรับพารามิเตอร์เป็นที่ตั้งของไฟล์ แล้วคืนค่าเป็น List ของ String ที่แต่ละ
         * String คือบรรทัดในไฟล์ หากมีข้อผิดพลาดในการอ่านไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาดแล้วคืนค่าเป็น List ว่าง
         */
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeFile(String filePath, List<String> lines) {
        /**
         * เมธอด writeFile ทำหน้าที่เขียนข้อมูลลงไฟล์ที่ระบุ
         * โดยรับพารามิเตอร์เป็นที่ตั้งของไฟล์และ List ของ String ที่แต่ละ String
         * คือบรรทัดที่จะเขียนลงไฟล์ หากมีข้อผิดพลาดในการเขียนไฟล์
         * จะแสดงรายละเอียดข้อผิดพลาด
         */
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeItemFromList(DefaultListModel<String> listModel, String filePath, int index) {
        /**
         * เมธอด removeItemFromList ทำหน้าที่ลบรายการที่ถูกเลือกออกจาก listModel
         * และไฟล์ที่ระบุ โดยรับ listModel, ที่ตั้งของไฟล์, และ index
         * ของรายการที่ถูกเลือกเป็นพารามิเตอร์ หาก index ไม่เท่ากับ -1
         * จะทำการลบรายการที่ถูกเลือกออกจาก listModel และอ่านข้อมูลทั้งหมดจากไฟล์
         * แล้วลบบรรทัดที่มีข้อมูลรายการที่ถูกเลือกออกจากข้อมูลที่อ่านได้
         * แล้วเขียนข้อมูลที่ลบบรรทัดที่มีข้อมูลรายการที่ถูกเลือกแล้วลงไฟล์
         */
        if (index != -1) {
            String selectedItem = listModel.remove(index);
            List<String> lines = readFile(filePath);
            lines.removeIf(line -> line.contains(selectedItem));
            writeFile(filePath, lines);
        }
    }

    private String showInputDialog(String message, String title, String defaultValue) {
        /**
         * เมธอด showInputDialog ทำหน้าที่แสดง dialog ให้ผู้ใช้ป้อนข้อมูล
         * โดยรับข้อความที่จะแสดง, ชื่อของ dialog, และค่าเริ่มต้นเป็นพารามิเตอร์
         * จะวนลูปแสดง dialog จนกว่าผู้ใช้จะป้อนข้อมูลที่ไม่ว่างเปล่า
         * หากผู้ใช้ป้อนข้อมูลว่างเปล่าหรือไม่ป้อนข้อมูล และมีค่าเริ่มต้น
         * จะคืนค่าเริ่มต้น หากไม่มีค่าเริ่มต้น จะแสดงข้อความ 'Input cannot be empty.
         * Please try again.' แล้ววนลูปแสดง dialog อีกครั้ง
         * หากผู้ใช้ป้อนข้อมูลที่ไม่ว่างเปล่า จะคืนค่าที่ผู้ใช้ป้อน
         */
        while (true) {
            String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                if (defaultValue != null) {
                    return defaultValue;
                }
                JOptionPane.showMessageDialog(null, "Input cannot be empty. Please try again.");
            } else {
                return input;
            }
        }
    }

    private boolean showConfirmationDialog(String message, String title) {
        /**
         * เมธอด showConfirmationDialog ทำหน้าที่แสดง dialog ยืนยันให้ผู้ใช้เลือก
         * โดยรับข้อความที่จะแสดงและชื่อของ dialog เป็นพารามิเตอร์ จะแสดง dialog
         * ที่มีปุ่มยืนยันและปุ่มยกเลิก แล้วคืนค่าว่าผู้ใช้เลือกยืนยันหรือไม่
         * หากผู้ใช้เลือกยืนยัน จะคืนค่า true หากไม่เลือกยืนยัน จะคืนค่า false
         */
        int choice = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return choice == JOptionPane.YES_OPTION;
    }

}