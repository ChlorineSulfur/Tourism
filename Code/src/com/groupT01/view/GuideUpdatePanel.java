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
	JLabel inputhint = new JLabel("������");
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
		submit = new JButton("�ύ");
		exit = new JButton("�˳�");
		showInfo = new JLabel();
		title = new JLabel("���ĸ�����Ϣ���£�");
		polish = new JLabel("�������޸ĵ���Ϣ��");
		hint = new JLabel();
		hint.setText("<html><body><p>Attention:<br/>" + "��ź��û���Ϊ��˾���䣬���������֤�š��ȼ������뼰ʱ�����Ա��ϵ��" + "</p></body></html>");
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
				String content = "<html><body><p>" + "��ţ�" + String.valueOf(LOGIN.login_GID) + "<br/>" + "������" + name
						+ "<br/>" + "�Ա�" + sex + "<br/>" + "���֤�ţ�" + iden + "<br/>" + "�绰��" + tel + "<br/>" + "���գ�"
						+ birth + "<br/>" + "���䣺" + age + "<br/>" + "�û�����" + username + "<br/>" + "���룺" + pwd + "<br/>"
						+ "�ȼ���" + grade + "<br/>" + "</p></body></html>";
				showInfo.setText(content);
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		JRadioButton sex_pls = new JRadioButton("�Ա�-GSEX");
		JRadioButton tel_pls = new JRadioButton("�绰-GTEL");
		JRadioButton birth_pls = new JRadioButton("����-GBIRTH");
		JRadioButton age_pls = new JRadioButton("����-GAGE");
		JRadioButton pwd_pls = new JRadioButton("����-GPWD");
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
				// TODO �Զ����ɵķ������
				String ziduan[] = selected.split("-");
				String updateValue = input.getText();
				int result = 0;
				try {
					result = dao.update(ziduan[1], updateValue, iden, con);
						
				} catch (SQLException e1) {
					// TODO �Զ����ɵ� catch ��
					//e1.printStackTrace();
					String ex01[] = e1.toString().split(":");
					if (ex01[1].equals(" ORA-00001"))
						JOptionPane.showMessageDialog(null, "��������Υ��ΨһԼ��������绰", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "��������Υ��Լ��������", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02291"))
						JOptionPane.showMessageDialog(null, "Υ������Լ������������", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01847"))
						JOptionPane.showMessageDialog(null, "�·����յ�ֵ������� 1 �͵������һ��֮�䣡����", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01843"))
						JOptionPane.showMessageDialog(null, "��Ч���·ݣ�����", "����", JOptionPane.ERROR_MESSAGE);
				
				}
				if (result == 25) {
					JOptionPane.showMessageDialog(null, "�޸ĳɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);

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
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}

		});

	}
}
