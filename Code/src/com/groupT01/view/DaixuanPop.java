package com.groupT01.view;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.CityDao;
import com.groupT01.dao.alterPlanDao;
import com.groupT01.util.DataBase;

public class DaixuanPop extends JFrame {
	JFrame frame;
	static Connection con;
	static alterPlanDao plandao = new alterPlanDao();
	static String scenic1, scenic2, scenic3, scenic4, scenic5, scenic6;
	static Date startDate;
	static int pid, travelDay;
	static double money;
	static DefaultTableModel tableModel;
	static JTable table;
	static int num;
	static String[] columnNames = { "ID", "��ʼʱ��", "��������", "����", "����1", "����2", "����3", "����4", "����5", "����6" }; // ��������������
	// ��������������
	static String[][] planTable = new String[30][11];

	public DaixuanPop() {
		frame = new JFrame("����·����Ϣ");
		// Setting the width and height of frame
		frame.setSize(620, 430);

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
			int daixuan_i = 0;
			ResultSet rs2 = plandao.lookup(con);
			num = plandao.num(con);

			while (rs2.next()) {
				pid = rs2.getInt("pid");
				startDate = rs2.getDate("beginday");
				travelDay = rs2.getInt("travelday");
				money = rs2.getDouble("money");
				scenic1 = rs2.getString("scenic1");
				scenic2 = rs2.getString("scenic2");
				scenic3 = rs2.getString("scenic3");
				scenic4 = rs2.getString("scenic4");
				scenic5 = rs2.getString("scenic5");
				scenic6 = rs2.getString("scenic6");
				planTable[daixuan_i][0] = String.valueOf(pid);
				planTable[daixuan_i][1] = String.valueOf(startDate);
				planTable[daixuan_i][2] = String.valueOf(travelDay);
				planTable[daixuan_i][3] = String.valueOf(money);
				planTable[daixuan_i][4] = scenic1;
				planTable[daixuan_i][5] = scenic2;
				planTable[daixuan_i][6] = scenic3;
				planTable[daixuan_i][7] = scenic4;
				planTable[daixuan_i][8] = scenic5;
				planTable[daixuan_i][9] = scenic6;
				daixuan_i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] inputDaixuan = new String[num][11];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 11; y++) {
				inputDaixuan[x][y] = planTable[x][y];
			}
		}

		// ����ָ�����������ݵı��

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(5, 20, 600, 360);
		panel.add(scrollPane2);

		tableModel = new DefaultTableModel(inputDaixuan, columnNames);
		table = new JTable(tableModel);
		table.setBounds(5, 20, 600, 360);
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