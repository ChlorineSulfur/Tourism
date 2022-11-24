package com.groupT01.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.groupT01.dao.ForumDao;
import com.groupT01.model.Forum;
import com.groupT01.util.DataBase;

public class ForumPanel extends Panel {
	Connection con;
	ForumDao dao = new ForumDao();
	int id;
	String iden, name, content;
	Date day;
	JButton addBtn, del, save, exit, look;
	DefaultTableModel tableModel;
	JTable table;
	int num;

	JLabel hintLabel, contexthint;
	JTextArea contentText;
	String[] columnNames = { "ID", "���", "�û���", "����", "����" }; // ��������������
	// ��������������
	String[][] tableValues = new String[30][6];

	public ForumPanel() {
		addBtn = new JButton("����");
		del = new JButton("ɾ��");
		save = new JButton("ȷ������");
		exit = new JButton("�˳�");
		look = new JButton("�鿴");

		this.setLayout(null);
		this.add(addBtn);
		this.add(look);
		look.setBounds(150, 500, 100, 30);
		addBtn.setBounds(275, 500, 100, 30);
		this.add(save);
		save.setBounds(400, 500, 100, 30);
		this.add(del);
		del.setBounds(525, 500, 100, 30);
		// this.add(back);
		this.add(exit);
		exit.setBounds(650, 500, 100, 30);

		contexthint = new JLabel("������̳���ݣ�");
		contexthint.setBounds(20, 200, 750, 30);
		this.add(contexthint);
		contentText = new JTextArea();
		contentText.setBounds(20, 225, 750, 125);
		this.add(contentText);

		int i = 0;

		DataBase dbUtil = new DataBase();
		try {
			con = dbUtil.getCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			int forum_i = 0;
			ResultSet rs2 = dao.lookup(con);
			num = dao.num(con);

			while (rs2.next()) {
				id = rs2.getInt("fid");
				iden = rs2.getString("fiden");
				name = rs2.getString("fname");
				day = rs2.getDate("forumday");
				content = rs2.getString("comm");
				tableValues[forum_i][0] = String.valueOf(id);
				tableValues[forum_i][1] = (iden);
				tableValues[forum_i][2] = (name);
				tableValues[forum_i][3] = String.valueOf(day);
				tableValues[forum_i][4] = content;
				forum_i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] input = new String[num][11];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 6; y++) {
				input[x][y] = tableValues[x][y];
			}
		}

		// ����ָ�����������ݵı��

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(20, 35, 750, 150);
		this.add(scrollPane2);

		tableModel = new DefaultTableModel(input, columnNames);
		table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());

		table.setBounds(20, 35, 750, 150);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//����JTable���п������б����ݵĴ�С���е���
		scrollPane2.setViewportView(table);// ΪscrollPaneָ����ʾ����Ϊtable

		JTableHeader jTableHeader2 = table.getTableHeader();
		jTableHeader2.setBounds(20, 10, 750, 20);
		this.add(jTableHeader2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("<html>\r\n" + "<body>\r\n"
				+ "<p>Attention: <br/>1. Id �ֶ�����������<br/>2. ��ݡ��û���Ĭ��Ϊ������Ա������root���������Զ���ȡ�������ڡ�<br/>&nbsp;&nbsp;&nbsp; ������ķ���������������������ݣ�<br/>"
				+ "3. ���ָ���ѡ��״̬������鿴�ɲ鿴��������<br/>" + "4. ��ӳɹ������½���ý���</p>\r\n" + "</body></html>");
		hint.setBounds(20, 350, 750, 150);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		look.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String showIDEN, showNAME, showTIME, showCOMM;
				showIDEN = table.getValueAt(table.getSelectedRow(), 1).toString();
				showNAME = table.getValueAt(table.getSelectedRow(), 2).toString();
				showTIME = table.getValueAt(table.getSelectedRow(), 3).toString();
				showCOMM = table.getValueAt(table.getSelectedRow(), 4).toString();
				contentText
						.setText(showNAME + " - " + showIDEN + "\n            " + showTIME + "\r\n        " + showCOMM);
				contentText.setLineWrap(true); // �����Զ����й���
				contentText.setWrapStyleWord(true); // ������в����ֹ���
			}

		});

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
				Forum sing_F = new Forum();
				// sing_F.setIdentity(table.getValueAt(table.getSelectedRow(),1).toString());
				sing_F.setIdentity("����Ա");
				// sing_F.setFname(table.getValueAt(table.getSelectedRow(),2).toString());
				sing_F.setFname("root");

				long now = System.currentTimeMillis(); // ��ȡ�������ǵ�ǰʱ��ĺ���ֵ

				// �Ѻ���ֵת����ʱ���ʽ
				Date d = new Date(now);
				d.setTime(now);
				/**
				 * ������ʽ��ʱ�������� �������String���;���������Ҫת���ɵ�ʱ����ʽ
				 */
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				sing_F.setForumdate(format.format(d));

				sing_F.setComment(table.getValueAt(table.getSelectedRow(), 4).toString());

				try {
					int result = dao.insert(sing_F, con);
					if (result == 50) {
						JOptionPane.showMessageDialog(null, "��ӳɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					// TODO �Զ����ɵ� catch ��
					String ex01[] = e1.toString().split(":");
					// if(ex01[1].equals(" ORA-00001")) JOptionPane.showMessageDialog(null,
					// "������ͻ�����龰������", "����",JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "��������Υ��Լ��������", "����", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02291"))
						JOptionPane.showMessageDialog(null, "Υ������Լ������������", "����", JOptionPane.ERROR_MESSAGE);
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
				String id_key = table.getValueAt(table.getSelectedRow(), 0).toString();
				if (id_key.equals(""))
					JOptionPane.showMessageDialog(null, "���ڿն���", "����", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				// System.out.println("id:"+a);

				try {
					int result = dao.delete(id_key, con);
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

class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
	public TableCellTextAreaRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// ���㵱���е���Ѹ߶�
		int maxPreferredHeight = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			setText("" + table.getValueAt(row, i));
			setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
			maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
		}

		if (table.getRowHeight(row) != maxPreferredHeight) // ��������������Ϲæ
			table.setRowHeight(row, maxPreferredHeight);

		setText(value == null ? "" : value.toString());
		return this;
	}
}
