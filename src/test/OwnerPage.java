package test;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        setTitle("Court Booking List - Owner Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 600); // Increased size of the window
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Section: Court Booking List
        JTextArea courtBookingTextArea = new JTextArea();
        courtBookingTextArea.setEditable(false);
        JScrollPane courtBookingScrollPane = new JScrollPane(courtBookingTextArea);
        contentPane.add(courtBookingScrollPane, BorderLayout.CENTER);

        // Load data from courtbooking.txt and display in JTextArea
        StringBuilder courtBookingData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("courtbooking.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                courtBookingData.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            courtBookingData.append("Error reading courtbooking file");
        }
        courtBookingTextArea.setText(courtBookingData.toString());
    }
}
