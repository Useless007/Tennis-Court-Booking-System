package test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OwnerPage extends JFrame {

    private JPanel contentPane;
    private JTable table;

    public OwnerPage() {
        setTitle("Court Booking List - Owner Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make all cells non-editable
        // ตั้งค่า JFrame
        setTitle("Court Booking List - Owner Page"); // ชื่อหน้าต่าง
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    // ปิดหน้าต่างแล้วจบการทำงาน
        setResizable(false); // ไม่สามารถปรับขนาดหน้าต่างได้
        setBounds(100, 100, 800, 600); // ตำแหน่งและขนาดของหน้าต่าง
        contentPane = new JPanel(); // สร้าง JPanel
        setContentPane(contentPane); // ตั้งค่าให้ JPanel เป็น content pane
        contentPane.setLayout(new BorderLayout()); // ตั้งค่า layout ของ content pane เป็น BorderLayout

        // สร้างแบบจำลองตาราง
        DefaultTableModel model = new DefaultTableModel() {// สร้างแบบจำลองตาราง
            @Override // ทำให้เซลในตารางไม่สามารถแก้ไขได้
            public boolean isCellEditable(int row, int column) {// ทำให้เซลในตารางไม่สามารถแก้ไขได้
                // สร้างเซลในตารางไม่สามารถแก้ไขได้
                return false;
                // สร้างเซลในตารางสามารถแก้ไขได้
            }
        };

        // สร้างหัวตาราง
        model.addColumn("Member ID"); // สร้างหัวตาราง
        model.addColumn("Member Name"); // สร้างหัวตาราง
        model.addColumn("Court Info"); // สร้างหัวตาราง
        model.addColumn("Price"); // สร้างหัวตาราง
        model.addColumn("Date Booking"); // สร้างหัวตาราง
        model.addColumn("Time"); // สร้างหัวตาราง

        // โหลดข้อมูลจากไฟล์เข้าไปในแบบจำลอง
        loadDataFromFile(model);

        // สร้างตาราง
        table = new JTable(model);
        table.setBackground(Color.WHITE);// ตั้งค่าสีพื้นหลังของตาราง
        table.setForeground(Color.BLACK); // ตั้งค่าสีตัวอักษรของตาราง
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ตั้งค่าให้สามารถเลือกได้เพียงแค่ 1 แถว

        // ตั้งค่าสีพื้นหลังและสีตัวอักษรของหัวตาราง
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLUE);// ตั้งค่าสีพื้นหลังของหัวตาราง
        header.setForeground(Color.WHITE); // ตั้งค่าสีตัวอักษรของหัวตาราง

        // ตั้งค่าขนาดและรูปแบบตัวอักษรของตาราง
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        header.setFont(new Font("Tahoma", Font.BOLD, 14));

        table.setRowHeight(25);
        table.setSelectionBackground(Color.GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(100, 35));
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculatePrice();
            }
        });// สิ้นสุดการทำงานเมื่อปุ่ม Calculate ถูกคลิก

        JButton refreshButton = new JButton("Refresh"); // สร้างปุ่ม Refresh
        refreshButton.setPreferredSize(new Dimension(100, 35)); // ตั้งค่าขนาดของปุ่ม Refresh
        refreshButton.addActionListener(new ActionListener() {// สร้างการทำงานเมื่อปุ่ม Refresh ถูกคลิก
            public void actionPerformed(ActionEvent e) {// สร้างการทำงานเมื่อปุ่ม Refresh ถูกคลิก
                refreshData(model);// รีเฟรชข้อมูล
            }// สิ้นสุดการทำงานเมื่อปุ่ม Refresh ถูกคลิก
        });

        // สร้าง JTextField เพื่อแสดงราคาทั้งหมด
        JTextField totalPriceField = new JTextField();
        totalPriceField.setEditable(false);// ตั้งค่าให้ไม่สามารถแก้ไขได้

        // สร้าง JPanel เพื่อใส่ปุ่ม Calculate และปุ่ม Refresh
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // สร้าง JPanel เพื่อใส่ปุ่ม Calculate
                                                                            // และปุ่ม Refresh
        buttonPanel.add(calculateButton);// ใส่ปุ่ม Calculate ลงใน JPanel
        buttonPanel.add(refreshButton);// ใส่ปุ่ม Refresh ลงใน JPanel

        // ใส่ JPanel ที่มีปุ่ม Calculate และปุ่ม Refresh ลงใน content pane
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(totalPriceField, BorderLayout.NORTH);// ใส่ JTextField ที่แสดงราคาทั้งหมดลงใน content pane

        // ตั้งค่าให้แสดงหน้าต่าง
        table.addMouseListener(new MouseAdapter() {// ตั้งค่าให้แสดงหน้าต่าง
            public void mouseClicked(MouseEvent e) {// ตั้งค่าให้แสดงหน้าต่าง
                if (e.getClickCount() == 2) { // ตรวจสอบว่าเมื่อคลิก 2 ครั้ง
                    JTable target = (JTable) e.getSource(); // ตั้งค่าให้ target เป็นตารางที่ถูกคลิก
                    int row = target.getSelectedRow();// ตั้งค่าให้ row เป็นแถวที่ถูกคลิก
                    int column = target.getSelectedColumn(); // ตั้งค่าให้ column เป็นคอลัมน์ที่ถูกคลิก
                    String value = target.getValueAt(row, column).toString();// ตั้งค่าให้ value เป็นค่าที่ถูกคลิก
                    JOptionPane.showMessageDialog(null, "Selected value: " + value); // แสดงข้อความว่าค่าที่ถูกคลิกคืออะไร
                }
            }
        });// สิ้นสุดการตั้งค่าให้แสดงหน้าต่าง
    }

    // เมดตอดสำหรับโหลดข้อมูลจากไฟล์
    private void loadDataFromFile(DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader("courtbooking.txt"))) {
            String memberId = "";
            String memberName = "";
            String courtInfo = "";
            String price = "";
            String dateBooking = "";
            String time = "";
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(": ");
                if (data.length == 2) {
                    if (data[0].equals("Member ID")) {
                        memberId = data[1];
                    } else if (data[0].equals("Member Name")) {
                        memberName = data[1];
                    } else if (data[0].equals("Court Info")) {
                        courtInfo = data[1];
                    } else if (data[0].equals("Price")) {
                        price = data[1];
                    } else if (data[0].equals("Date Booking")) {
                        dateBooking = data[1];
                    } else if (data[0].equals("Time")) {
                        time = data[1];
                        model.addRow(new String[] { memberId, memberName, courtInfo, price, dateBooking, time });
                    }
                } // สิ้นสุดการตรวจสอบว่าข้อมูลมี 2 ส่วนหรือไม่
            }
        } catch (IOException e) {// ตรวจสอบข้อผิดพลาด
            e.printStackTrace();// แสดงข้อผิดพลาด
        }
    }

    // เมดตอดสำหรับรีเฟรชข้อมูล
    private void refreshData(DefaultTableModel model) {
        // ล้างข้อมูลในแบบจำลอง
        while (model.getRowCount() > 0) { // วนลูปเพื่อลบข้อมูลในแบบจำลอง
            model.removeRow(0); // ลบข้อมูลในแบบจำลอง
        }
        // โหลดข้อมูลจากไฟล์เข้าไปในแบบจำลอง
        loadDataFromFile(model);
    }

    // เมดตอดสำหรับคำนวณราคา
    private void calculatePrice() {
        int rowCount = table.getRowCount();
        double totalPrice = 0;

        for (int i = 0; i < rowCount; i++) { // วนลูปเพื่อคำนวณราคา
            double price = Double.parseDouble(table.getValueAt(i, 3).toString()); // อ่านค่าราคาจากตาราง
            totalPrice += price; // บวกค่าราคาเข้ากับ totalPrice
        }

        JTextField totalPriceField = (JTextField) ((BorderLayout) contentPane.getLayout())
                .getLayoutComponent(BorderLayout.NORTH);
        totalPriceField.setText("Total net profit: " + totalPrice + " baht");
    }
}
// สิ้นสุดคลาส OwnerPage