package com.groupT01.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.GuideDao;
import com.groupT01.dao.alterPlanDao;
import com.groupT01.util.DataBase;

public class GuidePop {
	static Connection con;
	JFrame frame;
	static GuideDao dao = new GuideDao();
	static Date birth;
	static String tel;
	static String name, sex, iden, username, pwd, grade;
	static int id, age;
	static DefaultTableModel tableModel;
	static JTable table;
	static int num;
	static String[] columnNames = { "ID", "����", "�Ա�", "���֤��", "�绰", "����", "����", "�û���", "����", "�ȼ�" }; // ��������������
	static String[][] tableValues = new String[30][10];

	public GuidePop() {
		frame = new JFrame("������Ϣ");
		// Setting the width and height of frame
		frame.setSize(620, 220);

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
				id = rs.getInt("gid");
				name = rs.getString("GNAME");
				sex = rs.getString("GSEX");
				iden = rs.getString("GIDEN");
				tel = rs.getString("GTEL");
				birth = rs.getDate("GBIRTH");
				age = rs.getInt("GAGE");
				username = rs.getString("GUSERNAME");
				pwd = rs.getString("GPWD");
				grade = rs.getString("GRADE");
				tableValues[i][0] = String.valueOf(id);
				tableValues[i][1] = name;
				tableValues[i][2] = sex;
				tableValues[i][3] = iden;
				tableValues[i][4] = String.valueOf(tel);
				tableValues[i][5] = String.valueOf(birth);
				tableValues[i][6] = String.valueOf(age);
				tableValues[i][7] = username;
				tableValues[i][8] = pwd;
				tableValues[i][9] = grade;
				i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][10];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 10; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(5, 20, 600, 150);
		panel.add(scrollPane2);

		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		table.setBounds(5, 20, 600, 150);
		panel.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//����JTable���п������б����ݵĴ�С���е���
		scrollPane2.setViewportView(table);// ΪscrollPaneָ����ʾ����Ϊtable

		JTableHeader jTableHeader2 = table.getTableHeader();
		jTableHeader2.setBounds(5, 0, 600, 20);
		panel.add(jTableHeader2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

	}
}
