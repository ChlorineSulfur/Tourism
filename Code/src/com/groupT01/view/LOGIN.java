package com.groupT01.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.groupT01.model.GuideLoginUser;
import com.groupT01.dao.GuideloginDao;
import com.groupT01.util.DataBase;

public class LOGIN extends JFrame {
	public static GuideLoginUser guide_login = new GuideLoginUser();
	public static int login_GID;
	static JTextField userText;
	static JTextField passwordText;
	public static int count = 0;
	public static JFrame frame;
	static Connection con;
	static JButton exitbutton, loginButton;;
	static JLabel TimeLabel;

	public LOGIN() {
		// ���� JFrame ʵ��
		frame = new JFrame("����Ա/���� ��¼");
		// Setting the width and height of frame
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		// ������
		frame.add(panel);
		/*
		 * �����û�����ķ����������������
		 */
		placeComponents(panel);

		// ���ý���ɼ�
		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		/*
		 * ���ֲ���������߲��������� ������ò���Ϊ null
		 */
		panel.setLayout(null);

		// ���� JLabel
		JLabel userLabel = new JLabel("UserID:");
		/*
		 * ������������������λ�á� setBounds(x, y, width, height) x �� y ָ�����Ͻǵ���λ�ã��� width �� height
		 * ָ���µĴ�С��
		 */
		userLabel.setBounds(45, 20, 80, 25);
		panel.add(userLabel);

		/*
		 * �����ı��������û�����
		 */
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);

		// ����������ı���
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(32, 50, 80, 25);
		panel.add(passwordLabel);

		/*
		 * �����������������ı��� �����������Ϣ���Ե�Ŵ��棬���ڰ�������İ�ȫ��
		 */
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);

		TimeLabel = new JLabel("���ܹ���3�λ���");
		TimeLabel.setBounds(100, 80, 200, 25);
		panel.add(TimeLabel);

		// ������¼��ť
		loginButton = new JButton("login");
		loginButton.setBounds(100, 110, 75, 25);
		panel.add(loginButton);

		exitbutton = new JButton("exit");
		exitbutton.setBounds(190, 110, 75, 25);
		panel.add(exitbutton);
	}

	public static void main(String[] args) {
		LOGIN lg = new LOGIN();
		ActionListener login_ls = new ActionListener() {
			GuideloginDao dao = new GuideloginDao();

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String admin = userText.getText();
				String password = passwordText.getText();
				if (admin.equals("root")) {
					if (password.equals("123")) {
						// System.out.println(admin);
						// System.out.println(password);
						mainLayout ml = new mainLayout();// Ϊ��ת�Ľ���
						lg.frame.dispose();// ���ٵ�ǰ����
					} else {
						count++;
						String str = String.valueOf(3 - count);
						TimeLabel.setText("ʣ��" + str + "�λ��ᣡ");
						System.out.println("error ���ܴ���");
						if (count == 3) {
							lg.frame.dispose();
						}
					}
				} else {
					DataBase dbUtil = new DataBase();
					try {
						con = dbUtil.getCon();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						guide_login = dao.find(con, admin, password);
						if (guide_login != null) {

							login_GID = guide_login.getGID();

							guideLayout gl = new guideLayout();// Ϊ��ת�Ľ���
							lg.frame.dispose();// ���ٵ�ǰ����
						} else {
							count++;
							String str = String.valueOf(3 - count);
							TimeLabel.setText("ʣ��" + str + "�λ��ᣡ");
							System.out.println("error");
							if (count == 3) {
								lg.frame.dispose();
							}
						}
					} catch (SQLException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}

			}

		};
		loginButton.addActionListener(login_ls);
		ActionListener exit_ls = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				System.exit(0);// ��ֹ��ǰ����
			}

		};
		exitbutton.addActionListener(exit_ls);

	}
}