package test;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class ShowReport extends JDialog {

    public ShowReport(Frame parent) {
        /**
         * เมธอด ShowReport เป็น constructor ของคลาส ShowReport
         * ทำหน้าที่สร้างหน้าต่างรายงานสรุป โดยตั้งค่าต่างๆ ของหน้าต่าง
         * และสร้างตารางด้วย DefaultTableModel แล้วเพิ่มคอลัมน์ 'No.', 'Member ID',
         * 'Name', 'Surname', และ 'Amount per Transaction' ลงไป
         * 
         * จากนั้นจะทำการอ่านข้อมูลทั้งหมดจากไฟล์ 'user.txt'
         * แล้วแยกข้อมูลแต่ละบรรทัดเป็นส่วนๆ แล้วเรียกเมธอด fetchPurchaseAmount ด้วย ID
         * ของสมาชิกเพื่อดึงจำนวนการซื้อ แล้วเพิ่มข้อมูลเหล่านี้เป็นแถวในตาราง
         * หากมีข้อผิดพลาดในการอ่านไฟล์ จะแสดงรายละเอียดข้อผิดพลาด
         * 
         * หลังจากนั้นจะสร้าง JTable ด้วย model ที่สร้างขึ้น แล้วเพิ่ม JTable นี้ลง
         * JScrollPane แล้วเพิ่ม JScrollPane นี้ลงหน้าต่าง
         * 
         * สุดท้ายจะสร้างปุ่ม 'New button' แล้วเพิ่มปุ่มนี้ลงหน้าต่าง
         */
        super(parent, "Summary Report", true);
        setSize(400, 300); // Adjust the size as needed
        getContentPane().setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Member ID");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Total");

        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            int count = 1;
            for (String line : lines) {
                String[] details = line.split(", ");
                String purchaseAmount = fetchPurchaseAmount(details[0]); // Implement this method to fetch the amount
                model.addRow(new Object[] { count++, details[0], details[1], details[2], purchaseAmount });
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Set the column width for "No."
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn noColumn = columnModel.getColumn(0);
        noColumn.setPreferredWidth(30);
        noColumn.setMaxWidth(30);
        noColumn.setMinWidth(30);

        TableColumn idColumn = columnModel.getColumn(1);
        idColumn.setPreferredWidth(100);
        idColumn.setMaxWidth(100);
        idColumn.setMinWidth(100);
        

        JButton btnNewButton = new JButton("New button");
        getContentPane().add(btnNewButton, BorderLayout.SOUTH);
    }

    private String fetchPurchaseAmount(String memberId) {
        return "0";
    }
}
