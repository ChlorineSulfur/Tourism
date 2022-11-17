package com.groupT01.view;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import com.groupT01.util.DataBase;

public class cityPoPBox extends JFrame {
	JFrame frame;
	static Connection con;
	static CityDao dao = new CityDao();
	static String city;
	static String spot;
	static DefaultTableModel tableModel;
	static JTable table;
	static int num;
	static String[] columnNames = { "������", "������" }; // ����������
	// ��������������
	static String[][] tableValues = new String[30][3];

	public cityPoPBox() {
		frame = new JFrame("���о�����Ϣ");
		// Setting the width and height of frame
		frame.setSize(250, 600);

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
		DataBase dbUtil = new DataBase();
		int i = 0;
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
				city = rs.getString("cityname");
				spot = rs.getString("scenicname");
				tableValues[i][0] = city;
				tableValues[i][1] = spot;

				i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][3];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 3; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		// ����ָ�����������ݵı��

		final JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 35, 200, 500);
		panel.add(scrollPane);

		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		table.setBounds(20, 35, 200, 500);
		panel.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//����JTable���п������б����ݵĴ�С���е���
		scrollPane.setViewportView(table);// ΪscrollPaneָ����ʾ����Ϊtable

		JTableHeader jTableHeader = table.getTableHeader();
		jTableHeader.setBounds(20, 10, 200, 20);
		panel.add(jTableHeader);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

	}
}