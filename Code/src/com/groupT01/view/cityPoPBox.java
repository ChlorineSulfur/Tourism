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
	static String[] columnNames = { "城市名", "景点名" }; // 定义表格列名
	// 定义表格数据数组
	static String[][] tableValues = new String[30][3];

	public cityPoPBox() {
		frame = new JFrame("城市景点信息");
		// Setting the width and height of frame
		frame.setSize(250, 600);

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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][3];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 3; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		// 创建指定列名和数据的表格

		final JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 35, 200, 500);
		panel.add(scrollPane);

		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		table.setBounds(20, 35, 200, 500);
		panel.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置JTable的列宽随着列表内容的大小进行调整
		scrollPane.setViewportView(table);// 为scrollPane指定显示对象为table

		JTableHeader jTableHeader = table.getTableHeader();
		jTableHeader.setBounds(20, 10, 200, 20);
		panel.add(jTableHeader);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

	}
}