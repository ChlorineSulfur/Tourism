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
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.travelPlanDao;
import com.groupT01.model.travelPlan;
import com.groupT01.util.DataBase;

public class GuideTravelPanel extends Panel{
	Connection con;
	travelPlanDao dao = new travelPlanDao();
	int sid, gid, pid;
	int travelday;
	double money;
	String gname, gsex, gtel;
	Date beginday;
	JButton addBtn, del, save, exit, update;
	DefaultTableModel tableModel;
	JTable table;
	int num;

	JLabel hintLabel, contexthint;
	JTextArea contentText;
	String[] columnNames = { "SID", "GID", "PID", "��������", "�Ա�", "�绰", "��ʼ����", "��������", "����" }; // ��������������
	// ��������������
	String[][] tableValues = new String[30][10];

	public GuideTravelPanel() {
		addBtn = new JButton("����");
		del = new JButton("ɾ��");
		save = new JButton("ȷ������");
		exit = new JButton("�˳�");
		update = new JButton("�鿴������·");
		this.setLayout(null);
		this.add(addBtn);
		this.add(update);

		update.setBounds(50, 500, 200, 30);
		addBtn.setBounds(275, 500, 100, 30);
		save.setBounds(400, 500, 100, 30);
		del.setBounds(525, 500, 100, 30);
		exit.setBounds(650, 500, 100, 30);
		this.add(save);
		this.add(del);
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
			int scheme_i = 0;
			ResultSet rs2 = dao.lookup_G(con,String.valueOf(LOGIN.login_GID));
			num = dao.num_G(con,String.valueOf(LOGIN.login_GID));

			while (rs2.next()) {
				sid = rs2.getInt("sid");
				gid = rs2.getInt("gid");
				pid = rs2.getInt("pid");
				gname = rs2.getString("GNAME");
				gsex = rs2.getString("GSEX");
				gtel = rs2.getString("GTEL");
				beginday = rs2.getDate("beginday");
				travelday = rs2.getInt("travelday");
				money = rs2.getDouble("money");
				tableValues[scheme_i][0] = String.valueOf(sid);
				tableValues[scheme_i][1] = String.valueOf(gid);
				tableValues[scheme_i][2] = String.valueOf(pid);
				tableValues[scheme_i][3] = gname;
				tableValues[scheme_i][4] = gsex;
				tableValues[scheme_i][5] = gtel;
				tableValues[scheme_i][6] = String.valueOf(beginday);
				tableValues[scheme_i][7] = String.valueOf(travelday);
				tableValues[scheme_i][8] = String.valueOf(money);

				scheme_i++;
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		String[][] input = new String[num][11];
		for (int x = 0; x < num; x++) {
			for (int y = 0; y < 10; y++) {
				input[x][y] = tableValues[x][y];
			}
		}

		// ����ָ�����������ݵı��

		final JScrollPane scrollPane2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setBounds(20, 35, 750, 300);
		this.add(scrollPane2);

		tableModel = new DefaultTableModel(input, columnNames);
		table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());

		table.setBounds(20, 35, 750, 300);
		this.add(table);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//����JTable���п������б����ݵĴ�С���е���
		scrollPane2.setViewportView(table);// ΪscrollPaneָ����ʾ����Ϊtable

		JTableHeader jTableHeader2 = table.getTableHeader();
		jTableHeader2.setBounds(20, 10, 750, 20);
		this.add(jTableHeader2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		JLabel hint = new JLabel("<html>\r\n" + "<body>\r\n" + "<p>Attention: <br/>0. SID: ���η�����ţ�GID: ������ţ�PID: ����·�����"
				+ "<br/>1. SID �ֶ�����������<br/>2. ���ʱ����ӵ����ı����ѡ����ʵ� PID ������Ӧ�ո��ύ���Զ������Լ���<br/>"
				+ "3. ���ָ���ѡ��״̬������鿴�ɲ鿴��������<br/>" + "4. ��ӳɹ������½���ý���</p>\r\n" + "</body></html>");
		hint.setBounds(20, 310, 750, 200);
		this.add(hint);
		MyEvent();
	}

	public void MyEvent() {

		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				DaixuanPop daixuanbox = new DaixuanPop();
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
				travelPlan sing_TP = new travelPlan();
				// sing_TP.setIdentity(table.getValueAt(table.getSelectedRow(),1).toString());

				sing_TP.setPid(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 2).toString()));

				try {
					int result = dao.insert_G(sing_TP, LOGIN.login_GID,con);
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
						JOptionPane.showMessageDialog(null, "Υ������Լ�����������鵼�α�ź�·�߱��", "����", JOptionPane.ERROR_MESSAGE);
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
