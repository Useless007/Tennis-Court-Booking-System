package notuse;
JPanel receiveChangePanel = new JPanel();
receiveChangePanel.setBorder(BorderFactory.createTitledBorder("Receive Change Page"));
receiveChangePanel.setBounds(10, 340, 564, 192); // Adjust position and size as needed
contentPane.add(receiveChangePanel);
receiveChangePanel.setLayout(new BorderLayout(0, 0));

JTextArea receiveChangeTextArea = new JTextArea();
receiveChangeTextArea.setEditable(false);

// นำข้อมูลการจองและการคำนวณราคามาใส่ใน JTextArea
String bookingInfo = "Member ID: 6606021420075\n" +
                     "Member Name: asd adad\n" +
                     "Court Info: Batminton2, 120, 100\n" +
                     "Price: 240.0\n" +
                     "Date Booking: Monday\n" +
                     "Time: [7:00 - 8:00, 8:00 - 9:00]";

receiveChangeTextArea.setText(bookingInfo);

JScrollPane receiveChangeScrollPane = new JScrollPane(receiveChangeTextArea);
receiveChangePanel.add(receiveChangeScrollPane, BorderLayout.CENTER);
