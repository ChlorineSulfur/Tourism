package com.groupT01.view;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
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
import com.groupT01.dao.alterPlanDao;
import com.groupT01.model.alterPlan;
import com.groupT01.util.DataBase;

public class DaiXuanPanel extends Panel {
	Connection con;
	alterPlanDao plandao = new alterPlanDao();
	String scenic1, scenic2, scenic3, scenic4, scenic5, scenic6;
	Date startDate;
	int pid, travelDay;
	double money;
	JButton addBtn, del, save, exit, lookup, refresh;
	DefaultTableModel tableModel;
	JTable table;
	int num;

	String[] columnNames = { "ID", "开始时间", "旅游天数", "费用", "景点1", "景点2", "景点3", "景点4", "景点5", "景点6" }; // 定义表格列名数组
	// 定义表格数据数组
	String[][] planTable = new String[30][11];

	public DaiXuanPanel() {
		addBtn = new JButton("加行");
		del = new JButton("删除");
		save = new JButton("确认增加");
		exit = new JButton("退出");
		lookup = new JButton("查看景点");
		refresh = new JButton("刷新");
		this.setLayout(null);
		this.add(addBtn);
		this.add(lookup);

		lookup.setBounds(150, 500, 100, 30);
		addBtn.setBounds(275, 500, 100, 30);
		refresh.setBounds(25, 150, 100, 30);
		this.add(save);
		save.setBounds(400, 500, 100, 30);
		this.add(del);
		del.setBounds(525, 500, 100, 30);
		// this.add(back);
		this.add(exit);
		exit.setBounds(650, 500, 100, 30);

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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String[][] inputDaixuan = new String[num][11];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 11; y++) {
				inputDaixuan[x][y] = planTable[x][y];
			}
		}

		// 创建指定列名和数据的表格

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(20, 35, 750, 400);
		this.add(scrollPane2);

		tableModel = new DefaultTableModel(inputDaixuan, columnNames);
		table = new JTable(tableModel);
		table.setBounds(20, 35, 750, 400);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置JTable的列宽随着列表内容的大小进行调整
		scrollPane2.setViewportView(table);// 为scrollPane指定显示对象为table

		JTableHeader jTableHeader2 = table.getTableHeader();
		jTableHeader2.setBounds(20, 10, 750, 20);
		this.add(jTableHeader2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("Attention: Id 主键自增，不用填写！不安排景点输入“-” ！景点必须从已有景点选择，若不存在，请先返回城市景点管理");
		hint.setBounds(20, 440, 750, 50);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		lookup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				cityPoPBox citybox = new cityPoPBox();// 为跳转的界面
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
				alterPlan single_P = new alterPlan();
				single_P.setStartdate(table.getValueAt(table.getSelectedRow(), 1).toString());
				single_P.setDaynum(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 2).toString()));
				single_P.setMoney(Double.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString()));
				single_P.setScenic1(table.getValueAt(table.getSelectedRow(), 4).toString());
				single_P.setScenic2(table.getValueAt(table.getSelectedRow(), 5).toString());
				single_P.setScenic3(table.getValueAt(table.getSelectedRow(), 6).toString());
				single_P.setScenic4(table.getValueAt(table.getSelectedRow(), 7).toString());
				single_P.setScenic5(table.getValueAt(table.getSelectedRow(), 8).toString());
				single_P.setScenic6(table.getValueAt(table.getSelectedRow(), 9).toString());
				try {
					int result = plandao.insert(single_P, con);
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
						JOptionPane.showMessageDialog(null, "违反完整约束条件！请检查景点名是否包含", "错误", JOptionPane.ERROR_MESSAGE);
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
				String id_key = table.getValueAt(table.getSelectedRow(), 0).toString();

				if (id_key.equals(""))
					JOptionPane.showMessageDialog(null, "存在空对象！", "错误", JOptionPane.ERROR_MESSAGE);
				else {
					if (rowcount >= 0) {
						tableModel.removeRow(rowcount);
					}
				}
				try {
					int result = plandao.delete(id_key, con);
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
