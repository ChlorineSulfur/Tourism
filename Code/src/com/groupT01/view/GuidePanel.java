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
	String[] columnNames = { "姓名", "性别", "身份证号", "电话", "生日", "年龄", "用户名", "密码", "等级" }; // 定义表格列名数组
	// 定义表格数据数组
	String[][] tableValues = new String[30][9];

	public GuidePanel() {
		addBtn = new JButton("加行");
		del = new JButton("删除");
		save = new JButton("确认增加");
		exit = new JButton("退出");

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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String[][] inputTable = new String[num][9];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 9; y++) {
				inputTable[x][y] = tableValues[x][y];
			}
		}

		// 创建指定列名和数据的表格
		tableModel = new DefaultTableModel(inputTable, columnNames);
		table = new JTable(tableModel);
		// 创建显示表格的滚动面板
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(200, 100, 550, 650);
		scrollPane.setViewportView(table);// 这句很重要
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		add(scrollPane);

		JTableHeader jTableHeader = table.getTableHeader();
		this.add(jTableHeader);
		this.add(table);
		JLabel hint = new JLabel("Attention: 选中行后点击删除；填写完整所有信息，保持该行选中状态，点击确认增加");
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
						JOptionPane.showMessageDialog(null, "添加成功！", "提示", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (SQLException e1) {

					// TODO 自动生成的 catch 块
					String ex01[] = e1.toString().split(":");
					if (ex01[1].equals(" ORA-00001"))
						JOptionPane.showMessageDialog(null, "主键冲突，请检查身份证号和手机号！", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-02290"))
						JOptionPane.showMessageDialog(null, "输入数据违反约束！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01847"))
						JOptionPane.showMessageDialog(null, " 月份中日的值必须介于 1 和当月最后一日之间！请检查", "错误", JOptionPane.ERROR_MESSAGE);
					if (ex01[1].equals(" ORA-01843"))
						JOptionPane.showMessageDialog(null, " 无效的月份！请检查", "错误", JOptionPane.ERROR_MESSAGE);

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
				String iden_id = table.getValueAt(table.getSelectedRow(), 2).toString();
				// System.out.println("id:"+a);
				if (iden_id.equals(""))
					JOptionPane.showMessageDialog(null, "存在空对象！", "错误", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				try {
					int result = dao.delete(iden_id, con);
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
