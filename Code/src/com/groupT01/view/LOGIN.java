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
		// 创建 JFrame 实例
		frame = new JFrame("管理员/导游 登录");
		// Setting the width and height of frame
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		// 添加面板
		frame.add(panel);
		/*
		 * 调用用户定义的方法并添加组件到面板
		 */
		placeComponents(panel);

		// 设置界面可见
		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		/*
		 * 布局部分我们这边不多做介绍 这边设置布局为 null
		 */
		panel.setLayout(null);

		// 创建 JLabel
		JLabel userLabel = new JLabel("UserID:");
		/*
		 * 这个方法定义了组件的位置。 setBounds(x, y, width, height) x 和 y 指定左上角的新位置，由 width 和 height
		 * 指定新的大小。
		 */
		userLabel.setBounds(45, 20, 80, 25);
		panel.add(userLabel);

		/*
		 * 创建文本域用于用户输入
		 */
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);

		// 输入密码的文本域
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(32, 50, 80, 25);
		panel.add(passwordLabel);

		/*
		 * 这个类似用于输入的文本域 但是输入的信息会以点号代替，用于包含密码的安全性
		 */
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);

		TimeLabel = new JLabel("您总共有3次机会");
		TimeLabel.setBounds(100, 80, 200, 25);
		panel.add(TimeLabel);

		// 创建登录按钮
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
				// TODO 自动生成的方法存根
				String admin = userText.getText();
				String password = passwordText.getText();
				if (admin.equals("root")) {
					if (password.equals("123")) {
						// System.out.println(admin);
						// System.out.println(password);
						mainLayout ml = new mainLayout();// 为跳转的界面
						lg.frame.dispose();// 销毁当前界面
					} else {
						count++;
						String str = String.valueOf(3 - count);
						TimeLabel.setText("剩余" + str + "次机会！");
						System.out.println("error 账密错误");
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

							guideLayout gl = new guideLayout();// 为跳转的界面
							lg.frame.dispose();// 销毁当前界面
						} else {
							count++;
							String str = String.valueOf(3 - count);
							TimeLabel.setText("剩余" + str + "次机会！");
							System.out.println("error");
							if (count == 3) {
								lg.frame.dispose();
							}
						}
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}

			}

		};
		loginButton.addActionListener(login_ls);
		ActionListener exit_ls = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);// 终止当前程序
			}

		};
		exitbutton.addActionListener(exit_ls);

	}
}