package test;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/********************************************
	 * 			Launch the application. 		*
	 ********************************************/
	/**
	 * เมธอด main นี้เป็นจุดเริ่มต้นของโปรแกรม โดยจะทำการสร้างอินสแตนซ์ของ MainPage
	 * แล้วทำให้มันแสดงขึ้นมา การสร้างและแสดง MainPage นี้จะทำใน
	 * EventQueue.invokeLater เพื่อให้แน่ใจว่าทุกอย่างทำงานใน Event Dispatch Thread
	 * ซึ่งเป็นสิ่งที่จำเป็นสำหรับ Swing หากมีข้อผิดพลาดใดๆ
	 * จะทำการแสดงรายละเอียดข้อผิดพลาด
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/********************************************
	 * 				end of main 				*
	 ********************************************/

	/**
	 * Create the frame.
	 */
	public MainPage() {
		/**
		 * เมธอด MainPage เป็น constructor ของคลาส MainPage
		 * ทำหน้าที่สร้างหน้าต่างหลักของโปรแกรม โดยตั้งค่าต่างๆ ของหน้าต่าง และสร้างปุ่ม
		 * 'Admin', 'Staff', 'Owner', และ 'Promotion View' แล้วเพิ่ม ActionListener
		 * ให้กับแต่ละปุ่ม
		 * 
		 * เมื่อผู้ใช้คลิกปุ่ม 'Owner' จะสร้างหน้าต่าง OwnerPage
		 * แล้วแสดงหน้าต่างนี้ขึ้นมา
		 * 
		 * เมื่อผู้ใช้คลิกปุ่ม 'Staff' จะสร้างหน้าต่าง StaffPage
		 * แล้วแสดงหน้าต่างนี้ขึ้นมา
		 * 
		 * เมื่อผู้ใช้คลิกปุ่ม 'Admin' จะสร้างหน้าต่าง AdminPage
		 * แล้วแสดงหน้าต่างนี้ขึ้นมา
		 * 
		 * เมื่อผู้ใช้คลิกปุ่ม 'Promotion View' จะสร้างหน้าต่าง ShowPromotion
		 * แล้วแสดงหน้าต่างนี้ขึ้นมา
		 */
		setResizable(false);
		setTitle("Select some shit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setBounds(10, 11, 414, 66);
		contentPane.add(btnAdmin);

		JButton btnStaff = new JButton("Staff");
		btnStaff.setBounds(10, 88, 414, 66);
		contentPane.add(btnStaff);

		JButton btnOwner = new JButton("Owner");
		btnOwner.setBounds(10, 165, 414, 66);
		contentPane.add(btnOwner);

		JButton btnShowpromo = new JButton("Promotion View");
		btnShowpromo.setBounds(10, 242, 414, 66);
		contentPane.add(btnShowpromo);

		// ActionListener for the "Owner" button
		btnOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OwnerPage OwnerPage = new OwnerPage(); // Create the new JFrame
				OwnerPage.setVisible(true); // Show the new window
			}
		});

		// ActionListener for the "Staff" button
		btnStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffPage staffPage = new StaffPage(); // Create the new JFrame
				staffPage.setVisible(true); // Show the new window
			}
		});

		// ActionListener for the "Admin" button
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPage adminPage = new AdminPage(); // Create the new JFrame
				adminPage.setVisible(true); // Show the new window
			}
		});

		// ActionListener for the "Show Promotion" button
		btnShowpromo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowPromotion Promo = new ShowPromotion();
				Promo.setVisible(true);
			}
		});
	}
}
