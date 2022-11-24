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
	String[] columnNames = { "ID", "身份", "用户名", "日期", "内容" }; // 定义表格列名数组
	// 定义表格数据数组
	String[][] tableValues = new String[30][6];

	public ForumPanel() {
		addBtn = new JButton("加行");
		del = new JButton("删除");
		save = new JButton("确认增加");
		exit = new JButton("退出");
		look = new JButton("查看");

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

		contexthint = new JLabel("具体论坛内容：");
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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String[][] input = new String[num][11];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 6; y++) {
				input[x][y] = tableValues[x][y];
			}
		}

		// 创建指定列名和数据的表格

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(20, 35, 750, 150);
		this.add(scrollPane2);

		tableModel = new DefaultTableModel(input, columnNames);
		table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());

		table.setBounds(20, 35, 750, 150);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置JTable的列宽随着列表内容的大小进行调整
		scrollPane2.setViewportView(table);// 为scrollPane指定显示对象为table

		JTableHeader jTableHeader2 = table.getTableHeader();
		jTableHeader2.setBounds(20, 10, 750, 20);
		this.add(jTableHeader2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("<html>\r\n" + "<body>\r\n"
				+ "<p>Attention: <br/>1. Id 字段主键自增；<br/>2. 身份、用户名默认为“管理员”、“root”，日期自动获取今日日期。<br/>&nbsp;&nbsp;&nbsp; 故最简便的方法仅需填入具体评论内容！<br/>"
				+ "3. 保持该行选中状态，点击查看可查看具体内容<br/>" + "4. 添加成功后，重新进入该界面</p>\r\n" + "</body></html>");
		hint.setBounds(20, 350, 750, 150);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		look.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String showIDEN, showNAME, showTIME, showCOMM;
				showIDEN = table.getValueAt(table.getSelectedRow(), 1).toString();
				showNAME = table.getValueAt(table.getSelectedRow(), 2).toString();
				showTIME = table.getValueAt(table.getSelectedRow(), 3).toString();
				showCOMM = table.getValueAt(table.getSelectedRow(), 4).toString();
				contentText
						.setText(showNAME + " - " + showIDEN + "\n            " + showTIME + "\r\n        " + showCOMM);
				contentText.setLineWrap(true); // 激活自动换行功能
				contentText.setWrapStyleWord(true); // 激活断行不断字功能
			}

		});

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
				Forum sing_F = new Forum();
				// sing_F.setIdentity(table.getValueAt(table.getSelectedRow(),1).toString());
				sing_F.setIdentity("管理员");
				// sing_F.setFname(table.getValueAt(table.getSelectedRow(),2).toString());
				sing_F.setFname("root");

				long now = System.currentTimeMillis(); // 获取出来的是当前时间的毫秒值

				// 把毫秒值转换成时间格式
				Date d = new Date(now);
				d.setTime(now);
				/**
				 * 创建格式化时间日期类 构造入参String类型就是我们想要转换成的时间形式
				 */
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				sing_F.setForumdate(format.format(d));

				sing_F.setComment(table.getValueAt(table.getSelectedRow(), 4).toString());

				try {
					int result = dao.insert(sing_F, con);
					if (result == 50) {
						JOptionPane.showMessageDialog(null, "添加成功！", "提示", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					// TODO 自动生成的 catch 块
					String ex01[] = e1.toString().split(":");
					// if(ex01[1].equals(" ORA-00001")) JOptionPane.showMessageDialog(null,
					// "主键冲突，请检查景点名！", "错误",JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "输入数据违反约束！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02291"))
						JOptionPane.showMessageDialog(null, "违反完整约束条件！请检查", "错误", JOptionPane.ERROR_MESSAGE);
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
				String id_key = table.getValueAt(table.getSelectedRow(), 0).toString();
				if (id_key.equals(""))
					JOptionPane.showMessageDialog(null, "存在空对象！", "错误", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				// System.out.println("id:"+a);

				try {
					int result = dao.delete(id_key, con);
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

class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
	public TableCellTextAreaRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// 计算当下行的最佳高度
		int maxPreferredHeight = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			setText("" + table.getValueAt(row, i));
			setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
			maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
		}

		if (table.getRowHeight(row) != maxPreferredHeight) // 少了这行则处理器瞎忙
			table.setRowHeight(row, maxPreferredHeight);

		setText(value == null ? "" : value.toString());
		return this;
	}
}
