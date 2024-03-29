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
                return false;
            }
        };

        model.addColumn("Member ID");
        model.addColumn("Member Name");
        model.addColumn("Court Info");
        model.addColumn("Price");
        model.addColumn("Date Booking");
        model.addColumn("Time");

        loadDataFromFile(model);

        table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLUE);
        header.setForeground(Color.WHITE);

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
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshData(model);
            }
        });

        JTextField totalPriceField = new JTextField();
        totalPriceField.setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);
        buttonPanel.add(refreshButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(totalPriceField, BorderLayout.NORTH);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    String value = target.getValueAt(row, column).toString();
                    JOptionPane.showMessageDialog(null, "Selected value: " + value);
                }
            }
        });
    }

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshData(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        loadDataFromFile(model);
    }

    private void calculatePrice() {
        int rowCount = table.getRowCount();
        double totalPrice = 0;

        for (int i = 0; i < rowCount; i++) {
            double price = Double.parseDouble(table.getValueAt(i, 3).toString());
            totalPrice += price;
        }

        JTextField totalPriceField = (JTextField) ((BorderLayout) contentPane.getLayout())
                .getLayoutComponent(BorderLayout.NORTH);
        totalPriceField.setText("Total net profit: " + totalPrice + " baht");
    }
}