package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/********************************************
	 * Launch the application. *
	 ********************************************/
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
	 * end of main *
	 ********************************************/

	/**
	 * Create the frame.
	 */
	public MainPage() {
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

		JButton btnOwner_1 = new JButton("Promotion View");
		btnOwner_1.setBounds(10, 242, 414, 66);
		contentPane.add(btnOwner_1);

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
	}
}
