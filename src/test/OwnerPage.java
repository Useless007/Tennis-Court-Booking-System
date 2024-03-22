package test;

import javax.swing.*; 
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OwnerPage extends JFrame {
    
    private JPanel contentPane;
    // สร้างหน้าต่างของ OwnerPage
    public OwnerPage() {
        setTitle("Court Booking List - Owner Page");// ตั้งชื่อหน้าต่าง
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดหน้าต่างเมื่อกดปุ่มปิด
        setBounds(100, 100, 600, 600); // ตั้งขนาดของหน้าต่าง
        contentPane = new JPanel();// สร้าง JPanel
        setContentPane(contentPane);// ตั้งค่าของ contentPane
        contentPane.setLayout(new BorderLayout()); // ตั้งค่า layout ของ contentPane เป็น BorderLayout

        // สร้าง JTextArea และ JScrollPane และเพิ่ม JTextArea ลงใน JScrollPane และ JScrollPane ลงใน contentPane
        JTextArea courtBookingTextArea = new JTextArea();// สร้าง JTextArea
        courtBookingTextArea.setEditable(false);// ตั้งให้ JTextArea ไม่สามารถแก้ไขได้
        JScrollPane courtBookingScrollPane = new JScrollPane(courtBookingTextArea); // สร้าง JScrollPane และเพิ่ม JTextArea ลงใน JScrollPane
        contentPane.add(courtBookingScrollPane, BorderLayout.CENTER); // เพิ่ม JScrollPane ลงใน contentPane

        //  อ่านข้อมูลจากไฟล์ courtbooking.txt และเขียนข้อมูลลงใน JTextArea
        StringBuilder courtBookingData = new StringBuilder();// สร้าง StringBuilder
        try (BufferedReader br = new BufferedReader(new FileReader("courtbooking.txt"))) { // อ่านข้อมูลจากไฟล์ courtbooking.txt
            String line; // สร้างตัวแปร line
            while ((line = br.readLine()) != null) { // วนลูปเพื่ออ่านข้อมูลจากไฟล์
                courtBookingData.append(line).append("\n"); // เขียนข้อมูลลงใน StringBuilder
            }// จบการวนลูป
        } catch (IOException e) { // ถ้าเกิดข้อผิดพลาด
            e.printStackTrace(); // แสดงข้อผิดพลาด
            courtBookingData.append("Error reading courtbooking file"); // เขียนข้อความลงใน StringBuilder
        }// จบการอ่านข้อมูลจากไฟล์
        courtBookingTextArea.setText(courtBookingData.toString()); // แสดงข้อมูลใน JTextArea
    }// จบการสร้างหน้าต่างของ OwnerPage
}
