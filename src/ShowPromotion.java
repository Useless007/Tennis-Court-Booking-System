// import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowPromotion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ShowPromotion() {
		setTitle("โปรโมชั่นของทางสนาม");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("1. โปรโมชั่นสำหรับลูกค้าที่สมัครสมาชิกใหม่ใช้สนามฟรี7วัน");
		lblNewLabel.setFont(new Font("Angsana New", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 33, 414, 66);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_0 = new JLabel("0. ลูกค้าเก่าลดราคา20%ตลอดทั้งปี");
		lblNewLabel_0.setFont(new Font("Angsana New", Font.PLAIN, 20));
		lblNewLabel_0.setBounds(55, 110, 369, 66);
		contentPane.add(lblNewLabel_0);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(160, 214, 106, 36);
		contentPane.add(btnNewButton);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // ออกจากโปรแกรมโดยไม่กระทบต่อหน้าต่างอื่นๆ
			}
		});


	}
}
