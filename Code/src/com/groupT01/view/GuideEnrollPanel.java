package com.groupT01.view;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.enrollDao;
import com.groupT01.util.DataBase;

public class GuideEnrollPanel extends Panel{
	Connection con;
	enrollDao dao = new enrollDao();
	String name, sex, vip;
	String tel;
	int cid, sid, age;
	JButton lookxianlu, query, exit;
	DefaultTableModel tableModel;
	JTable table;
	JTextArea xianshi;
	JTextField shuru;
	JLabel shuruHint;
	int num;
	String[] columnNames = { "CID", "SID", "客户姓名", "性别", "电话", "年龄", "VIP" }; // 定义表格列名数组
	// 定义表格数据数组
	String[][] tableValues = new String[30][7];

	public GuideEnrollPanel() {
		this.setLayout(null);

		lookxianlu = new JButton("查看对应方案");
		query = new JButton("查询报名人数");
		exit = new JButton("退出");
		shuru = new JTextField();
		xianshi = new JTextArea();
		shuruHint = new JLabel("输入方案编号(SID):");
		xianshi.setBounds(25, 320, 750, 110);
		lookxianlu.setBounds(50, 500, 150, 30);
		query.setBounds(475, 500, 150, 30);
		exit.setBounds(650, 500, 100, 30);
		shuru.setBounds(350, 500, 100, 30);
		shuruHint.setBounds(225, 500, 175, 30);
		this.add(shuruHint);
		this.add(lookxianlu);
		this.add(query);
		this.add(xianshi);
		this.add(shuru);
		this.add(exit);
		
		JLabel fuze= new JLabel();
		fuze.setBounds(25,290,300,30);
		this.add(fuze);
		
		int i = 0;
		DataBase dbUtil = new DataBase();
		try {
			con = dbUtil.getCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			String sid_ok = "";
			ResultSet rst = dao.lookup_scheme(con, LOGIN.login_GID);
			while (rst.next()) {
				sid_ok=sid_ok+rst.getInt("sid")+",";
			}
			String s = sid_ok.substring(0,sid_ok.length()-1);
		//	System.out.println(s);
			ResultSet rs = dao.query_g(con,s);
			num = dao.num_g(con,s);
			fuze.setText("您负责的方案："+s);
			
			while (rs.next()) {
				cid = rs.getInt("cid");
				sid = rs.getInt("sid");
				name = rs.getString("cname");
				sex = rs.getString("csex");
				tel = rs.getString("ctel");
				age = rs.getInt("cage");
				vip = rs.getString("vip");
				tableValues[i][0] = String.valueOf(cid);
				tableValues[i][1] = String.valueOf(sid);
				tableValues[i][2] = name;
				tableValues[i][3] = sex;
				tableValues[i][4] = String.valueOf(tel);
				tableValues[i][5] = String.valueOf(age);
				tableValues[i][6] = vip;

				i++;
			}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][7];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 7; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		final JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(25, 30, 750, 250);
		this.add(scrollPane);

		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		table.setBounds(25, 30, 750, 250);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置JTable的列宽随着列表内容的大小进行调整
		scrollPane.setViewportView(table);// 为scrollPane指定显示对象为table

		JTableHeader jTableHeader = table.getTableHeader();
		jTableHeader.setBounds(25, 10, 750, 20);
		this.add(jTableHeader);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("<html>\r\n" + "<body>\r\n"
				+ "<p>Attention: <br/>0. CID: 客户编号，SID: 旅游方案编号  1. 选中行后查看旅游方案信息  2. 输入方案编号可查看所有报名该方案的客户</p>\r\n"
				+ "</body></html>");
		hint.setBounds(25, 440, 750, 50);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		lookxianlu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				String id = (table.getValueAt(table.getSelectedRow(), 1).toString());
				try {
					ResultSet rst = dao.look(con, id);
					while (rst.next()) {

						xianshi.setText("旅游方案：" + rst.getInt("sid") + "\n导游编号：" + rst.getInt("gid") + "\n" + "    姓名："
								+ rst.getString("gname") + "  性别：" + rst.getString("gsex") + "  电话："
								+ rst.getString("gtel") + "  年龄：" + rst.getInt("gage") + "  等级："
								+ rst.getString("grade") + "\n旅游路线：" + rst.getInt("pid") + "\n" + "    出发日期："
								+ rst.getDate("beginday") + "  旅游天数：" + rst.getInt("travelday") + "  价格："
								+ rst.getDouble("money") + "\n    景点列表：" + rst.getString("scenic1") + " "
								+ rst.getString("scenic2") + " " + rst.getString("scenic3") + " "
								+ rst.getString("scenic4") + " " + rst.getString("scenic5") + " "
								+ rst.getString("scenic6"));
						xianshi.setLineWrap(true); // 激活自动换行功能
						xianshi.setWrapStyleWord(true); // 激活断行不断字功能
					}
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

			}
		});

		query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String SID = shuru.getText();
				for (int k = table.getRowCount() - 1; k >= 0; k--) {
					tableModel.removeRow(k);
				}
				try {
					ResultSet rsst = dao.query(con, SID);
					while (rsst.next()) {
						cid = rsst.getInt("cid");
						sid = rsst.getInt("sid");
						name = rsst.getString("cname");
						sex = rsst.getString("csex");
						tel = rsst.getString("ctel");
						age = rsst.getInt("cage");
						vip = rsst.getString("vip");
						String Temp1[] = { String.valueOf(cid), String.valueOf(sid), name, sex, tel,
								String.valueOf(age), vip };
						tableModel.addRow(Temp1);
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
