package com.groupT01.view;

import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.GuideDao;
import com.groupT01.model.Guide;
import com.groupT01.util.DataBase;

public class GuidePanel extends Panel {
	Connection con;
	GuideDao dao = new GuideDao();
	String name, sex, iden, username, pwd, grade;
	int id, age;
	JButton addBtn, del, update, save, exit;
	DefaultTableModel tableModel;
	JTable table;
	String tel;
	Date birth;
	int num;
	String[] columnNames = { "����", "�Ա�", "���֤��", "�绰", "����", "����", "�û���", "����", "�ȼ�" }; // ��������������
	// ��������������
	String[][] tableValues = new String[30][9];

	public GuidePanel() {
		addBtn = new JButton("����");
		del = new JButton("ɾ��");
		save = new JButton("ȷ������");
		exit = new JButton("�˳�");

		this.add(addBtn);
		this.add(save);
		this.add(del);
		// this.add(back);
		this.add(exit);
		int i = 0;
		DataBase dbUtil = new DataBase();
		try {
			con = dbUtil.getCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet rs = dao.lookup(con);
			num = dao.num(con);

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
				tableValues[i][0] = name;
				tableValues[i][1] = sex;
				tableValues[i][2] = iden;
				tableValues[i][3] = String.valueOf(tel);
				tableValues[i][4] = String.valueOf(birth);
				tableValues[i][5] = String.valueOf(age);
				tableValues[i][6] = username;
				tableValues[i][7] = pwd;
				tableValues[i][8] = grade;
				i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][9];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 9; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		// ����ָ�����������ݵı��
		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		// ������ʾ���Ĺ������
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(200, 100, 550, 650);
		scrollPane.setViewportView(table);// ������Ҫ
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		add(scrollPane);

		JTableHeader jTableHeader = table.getTableHeader();
		this.add(jTableHeader);
		this.add(table);
		JLabel hint = new JLabel("Attention: ѡ���к���ɾ������д����������Ϣ�����ָ���ѡ��״̬�����ȷ������");
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		// ����
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				tableModel.addRow(new Vector());
			}

		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowcount = table.getSelectedRow();
				Guide single_G = new Guide();
				single_G.setName(table.getValueAt(table.getSelectedRow(), 0).toString());
				single_G.setSex(table.getValueAt(table.getSelectedRow(), 1).toString());
				single_G.setIden(table.getValueAt(table.getSelectedRow(), 2).toString());
				single_G.setTel(table.getValueAt(table.getSelectedRow(), 3).toString());
				single_G.setBirth(table.getValueAt(table.getSelectedRow(), 4).toString());
				single_G.setAge(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 5).toString()));
				single_G.setUser(table.getValueAt(table.getSelectedRow(), 6).toString());
				single_G.setPWD(table.getValueAt(table.getSelectedRow(), 7).toString());
				single_G.setGrade(table.getValueAt(table.getSelectedRow(), 8).toString());

				try {
					int result = dao.insert(single_G, con);
					if (result == 50) {
						JOptionPane.showMessageDialog(null, "��ӳɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {

					// TODO �Զ����ɵ� catch ��
					String ex01[] = e1.toString().split(":");
					if (ex01[1].equals(" ORA-00001"))
						JOptionPane.showMessageDialog(null, "������ͻ���������֤�ź��ֻ��ţ�", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "��������Υ��Լ��������", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01847"))
						JOptionPane.showMessageDialog(null, " �·����յ�ֵ������� 1 �͵������һ��֮�䣡����", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01843"))
						JOptionPane.showMessageDialog(null, " ��Ч���·ݣ�����", "����", JOptionPane.ERROR_MESSAGE);

				}
			}

		});

		// ɾ��
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// ɾ��ָ����
				int rowcount = table.getSelectedRow();
				String iden_id = table.getValueAt(table.getSelectedRow(), 2).toString();
				// System.out.println("id:"+a);
				if (iden_id.equals(""))
					JOptionPane.showMessageDialog(null, "���ڿն���", "����", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				try {
					int result = dao.delete(iden_id, con);
					if (result == 100) {
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
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
