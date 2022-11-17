package com.groupT01.view;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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

import com.groupT01.dao.CityDao;
import com.groupT01.model.City;
import com.groupT01.util.DataBase;

public class CityPanel extends Panel {
	Connection con;
	CityDao dao = new CityDao();
	String city, spot;
	JButton addBtn, del, back, save, exit;
	DefaultTableModel tableModel;
	JTable table;
	int num;
	String[] columnNames = { "城市名", "景点名" }; // 定义表格列名数组
	// 定义表格数据数组
	String[][] tableValues = new String[30][3];

	public CityPanel() {
		this.setLayout(null);
		addBtn = new JButton("加行");
		del = new JButton("删除");
		save = new JButton("确认增加");
		back = new JButton("返回");
		exit = new JButton("退出");
	
		this.add(addBtn);
		this.add(save);
		this.add(del);
		this.add(exit);
		
		addBtn.setBounds(600, 30, 100, 30);
		save.setBounds(600, 80, 100, 30);
		del.setBounds(600, 130, 100, 30);
		exit.setBounds(600, 180, 100, 30);
		
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
		scrollPane.setBounds(100, 35, 300, 450);
		this.add(scrollPane);

		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		table.setBounds(100, 35, 300, 450);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置JTable的列宽随着列表内容的大小进行调整
		scrollPane.setViewportView(table);// 为scrollPane指定显示对象为table

		JTableHeader jTableHeader = table.getTableHeader();
		jTableHeader.setBounds(100, 10, 300, 20);
		this.add(jTableHeader);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("<html>\r\n" + "<body>\r\n"
				+ "<p>Attention: <br/>1. 选中行后点击删除；<br/>2. 填写完整所有信息，保持该行选中状态，点击确认增加</p>\r\n" + "</body></html>");
		hint.setBounds(425, 250, 175, 125);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		// 增加
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				tableModel.addRow(new Vector());
			}

		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowcount = table.getSelectedRow();
				City single_C = new City();
				single_C.setCity(table.getValueAt(table.getSelectedRow(), 0).toString());
				single_C.setSpot(table.getValueAt(table.getSelectedRow(), 1).toString());

				try {
					int result = dao.insert(single_C, con);
					if (result == 50) {
						JOptionPane.showMessageDialog(null, "添加成功！", "提示", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {

					// TODO 自动生成的 catch 块
					String ex01[] = e1.toString().split(":");
					if (ex01[1].equals(" ORA-00001"))
						JOptionPane.showMessageDialog(null, "主键冲突，请检查景点名！", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "输入数据违反约束！请检查", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		// 删除
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// 删除指定行
				int rowcount = table.getSelectedRow();
				String spot_key = table.getValueAt(table.getSelectedRow(), 1).toString();
				if (spot_key.equals(""))
					JOptionPane.showMessageDialog(null, "存在空对象！", "错误", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				try {
					int result = dao.delete(spot_key, con);
					if (result == 100) {
						JOptionPane.showMessageDialog(null, "删除成功！", "提示", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
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
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}

		});

	}
}
