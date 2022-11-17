package com.groupT01.view;

import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.groupT01.dao.GuideDao;
import com.groupT01.model.Guide;
import com.groupT01.util.DataBase;

public class GuideUpdatePanel extends Panel {
	JLabel showInfo, hint, title, polish;
	JTextField input = new JTextField();
	JLabel inputhint = new JLabel("请输入");
	JButton submit, exit;
	Connection con;
	GuideDao dao = new GuideDao();
	String name, iden, sex, tel, username, pwd, grade;
	String selected;
	int age;
	Date birth;
	private RadioButtonListener radioButtonListener = new RadioButtonListener();

	public GuideUpdatePanel() {

		this.setLayout(null);
		submit = new JButton("提交");
		exit = new JButton("退出");
		showInfo = new JLabel();
		title = new JLabel("您的个人信息如下：");
		polish = new JLabel("您可以修改的信息：");
		hint = new JLabel();
		hint.setText("<html><body><p>Attention:<br/>" + "编号和用户名为公司分配，姓名、身份证号、等级有误请及时与管理员联系！" + "</p></body></html>");
		polish.setBounds(400, 80, 150, 30);
		showInfo.setBounds(100, 100, 200, 300);
		hint.setBounds(100, 450, 500, 50);
		title.setBounds(100, 80, 150, 30);
		submit.setBounds(600, 300, 100, 30);
		exit.setBounds(600, 350, 100, 30);
		input.setBounds(600, 200, 100, 30);
		inputhint.setBounds(600, 160, 100, 30);
		this.add(submit);
		this.add(exit);
		this.add(showInfo);
		this.add(hint);
		this.add(title);
		this.add(polish);
		this.add(input);
		this.add(inputhint);

		DataBase dbUtil = new DataBase();
		try {
			con = dbUtil.getCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet rs = dao.find(con, String.valueOf(LOGIN.login_GID));

			while (rs.next()) {

				name = rs.getString("GNAME");
				sex = rs.getString("GSEX");
				iden = rs.getString("GIDEN");
				tel = rs.getString("GTEL");
				birth = rs.getDate("GBIRTH");
				age = rs.getInt("GAGE");
				username = rs.getString("GUSERNAME");
				pwd = rs.getString("GPWD");
				grade = rs.getString("GRADE");
				String content = "<html><body><p>" + "编号：" + String.valueOf(LOGIN.login_GID) + "<br/>" + "姓名：" + name
						+ "<br/>" + "性别：" + sex + "<br/>" + "身份证号：" + iden + "<br/>" + "电话：" + tel + "<br/>" + "生日："
						+ birth + "<br/>" + "年龄：" + age + "<br/>" + "用户名：" + username + "<br/>" + "密码：" + pwd + "<br/>"
						+ "等级：" + grade + "<br/>" + "</p></body></html>";
				showInfo.setText(content);
			}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		JRadioButton sex_pls = new JRadioButton("性别-GSEX");
		JRadioButton tel_pls = new JRadioButton("电话-GTEL");
		JRadioButton birth_pls = new JRadioButton("生日-GBIRTH");
		JRadioButton age_pls = new JRadioButton("年龄-GAGE");
		JRadioButton pwd_pls = new JRadioButton("密码-GPWD");
		// add the JRadioButtons to the ButtonGroup
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sex_pls);
		buttonGroup.add(tel_pls);
		buttonGroup.add(birth_pls);
		buttonGroup.add(age_pls);
		buttonGroup.add(pwd_pls);
		sex_pls.setBounds(400, 160, 150, 20);
		tel_pls.setBounds(400, 200, 150, 20);
		birth_pls.setBounds(400, 240, 150, 20);
		age_pls.setBounds(400, 280, 150, 20);
		pwd_pls.setBounds(400, 320, 150, 20);
		sex_pls.addActionListener(radioButtonListener);
		tel_pls.addActionListener(radioButtonListener);
		birth_pls.addActionListener(radioButtonListener);
		age_pls.addActionListener(radioButtonListener);
		pwd_pls.addActionListener(radioButtonListener);
		this.add(sex_pls);
		this.add(tel_pls);
		this.add(birth_pls);
		this.add(age_pls);
		this.add(pwd_pls);
		MyEvent();
	}

	public class RadioButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JRadioButton temp = (JRadioButton) arg0.getSource();
			if (temp.isSelected()) {
				selected = temp.getText();
			}

		}

	}

	public void MyEvent() {

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String ziduan[] = selected.split("-");
				String updateValue = input.getText();
				int result = 0;
				try {
					result = dao.update(ziduan[1], updateValue, iden, con);
						
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					//e1.printStackTrace();
					String ex01[] = e1.toString().split(":");
					if (ex01[1].equals(" ORA-00001"))
						JOptionPane.showMessageDialog(null, "输入数据违反唯一约束！请检查电话", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "输入数据违反约束！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02291"))
						JOptionPane.showMessageDialog(null, "违反完整约束条件！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01847"))
						JOptionPane.showMessageDialog(null, "月份中日的值必须介于 1 和当月最后一日之间！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01843"))
						JOptionPane.showMessageDialog(null, "无效的月份！请检查", "错误", JOptionPane.ERROR_MESSAGE);
				
				}
				if (result == 25) {
					JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.PLAIN_MESSAGE);

				}
			}

		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}

		});

	}
}
