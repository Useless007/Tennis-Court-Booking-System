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

        // ตั้งค่าขนาดและรูปแบบของเซลในตาราง
        table.setRowHeight(25);// ตั้งค่าขนาดของเซลในตาราง
        table.setSelectionBackground(Color.GRAY);// ตั้งค่าสีพื้นหลังของเซลที่ถูกเลือก

        // ตั้งค่าให้ข้อมูลในเซลอยู่ตรงกลาง
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); // สร้าง DefaultTableCellRenderer
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);// ตั้งค่าให้ข้อมูลในเซลอยู่ตรงกลาง
        for (int i = 0; i < model.getColumnCount(); i++) { // วนลูปเพื่อตั้งค่าให้ข้อมูลในเซลอยู่ตรงกลาง
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // ตั้งค่าให้ข้อมูลในเซลอยู่ตรงกลาง
        }// สิ้นสุดการวนลูป

        // สร้าง JScrollPane เพื่อใส่ตาราง
        JScrollPane scrollPane = new JScrollPane(table);// สร้าง JScrollPane เพื่อใส่ตาราง
        contentPane.add(scrollPane, BorderLayout.CENTER);// ใส่ JScrollPane ลงใน content pane

        // สร้างปุ่ม Calculate
        JButton calculateButton = new JButton("Calculate"); // สร้างปุ่ม Calculate
        calculateButton.setPreferredSize(new Dimension(100, 35));// ตั้งค่าขนาดของปุ่ม Calculate
        calculateButton.addActionListener(new ActionListener() {// สร้างการทำงานเมื่อปุ่ม Calculate ถูกคลิก
            public void actionPerformed(ActionEvent e) {// สร้างการทำงานเมื่อปุ่ม Calculate ถูกคลิก
                calculatePrice();// คำนวณราคา
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // สร้าง JPanel เพื่อใส่ปุ่ม Calculate และปุ่ม Refresh
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
        try (BufferedReader br = new BufferedReader(new FileReader("courtbooking.txt"))) {// อ่านไฟล์ courtbooking.txt
            String memberId = "";// สร้างตัวแปร memberId
            String memberName = "";// สร้างตัวแปร memberName
            String courtInfo = "";// สร้างตัวแปร courtInfo
            String price = "";// สร้างตัวแปร price
            String dateBooking = "";// สร้างตัวแปร dateBooking
            String time = "";// สร้างตัวแปร time
            String line;// สร้างตัวแปร line
            while ((line = br.readLine()) != null) {// วนลูปเพื่ออ่านข้อมูลจากไฟล์
                String[] data = line.split(": ");// แบ่งข้อมูลด้วยเครื่องหมาย :
                if (data.length == 2) {// ตรวจสอบว่าข้อมูลมี 2 ส่วนหรือไม่
                    if (data[0].equals("Member ID")) {// ตรวจสอบว่าข้อมูลเป็น Member ID หรือไม่
                        memberId = data[1];// กำหนดค่าให้ memberId
                    } else if (data[0].equals("Member Name")) {// ตรวจสอบว่าข้อมูลเป็น Member Name หรือไม่
                        memberName = data[1];// กำหนดค่าให้ memberName
                    } else if (data[0].equals("Court Info")) {// ตรวจสอบว่าข้อมูลเป็น Court Info หรือไม่
                        courtInfo = data[1];// กำหนดค่าให้ courtInfo
                    } else if (data[0].equals("Price")) {// ตรวจสอบว่าข้อมูลเป็น Price หรือไม่
                        price = data[1];// กำหนดค่าให้ price
                    } else if (data[0].equals("Date Booking")) {// ตรวจสอบว่าข้อมูลเป็น Date Booking หรือไม่
                        dateBooking = data[1];// กำหนดค่าให้ dateBooking
                    } else if (data[0].equals("Time")) {// ตรวจสอบว่าข้อมูลเป็น Time หรือไม่
                        time = data[1];// กำหนดค่าให้ time
                        model.addRow(new String[]{memberId, memberName, courtInfo, price, dateBooking, time});// เพิ่มข้อมูลลงในแบบจำลอง
                    }
                }// สิ้นสุดการตรวจสอบว่าข้อมูลมี 2 ส่วนหรือไม่
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
        int rowCount = table.getRowCount();// นับจำนวนแถวในตาราง
        double totalPrice = 0; // สร้างตัวแปร totalPrice

        for (int i = 0; i < rowCount; i++) { // วนลูปเพื่อคำนวณราคา
            double price = Double.parseDouble(table.getValueAt(i, 3).toString()); // อ่านค่าราคาจากตาราง
            totalPrice += price; // บวกค่าราคาเข้ากับ totalPrice
        }

        // แสดงราคาทั้งหมด
        JTextField totalPriceField = (JTextField) ((BorderLayout) contentPane.getLayout()) // แสดงราคาทั้งหมด
                .getLayoutComponent(BorderLayout.NORTH);// แสดงราคาทั้งหมด
        totalPriceField.setText("Total net profit: " + totalPrice + " baht");// แสดงราคาทั้งหมด
    }
}
// สิ้นสุดคลาส OwnerPage