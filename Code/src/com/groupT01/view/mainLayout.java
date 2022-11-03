package com.groupT01.view;

import java.awt.Container;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.groupT01.dao.CityDao;
import com.groupT01.dao.GuideDao;
import com.groupT01.util.DataBase;

public class mainLayout extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	MenuBar menuBar; // �˵���
	Menu daoyou, city, fangan, forum, lvyou; // �˵�
	MenuItem show, cityshow, fanganshow, forumshow, lvyoushow; // �˵���
	MenuItem enroll;
	Panel contentPanel; // ������壬������������������л������

	public mainLayout() {
		setTitle("������Ϣ����ϵͳV1.0-����Ա");
		setBounds(300, 100, 800, 600);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ��ʼ�����
		menuBar = new MenuBar();
		daoyou = new Menu("����");
		city = new Menu("���о���");
		fangan = new Menu("������·");
		show = new MenuItem("����");
		cityshow = new MenuItem("����");
		fanganshow = new MenuItem("����");
		lvyou = new Menu("���η���");
		forum = new Menu("��̳");
		forumshow = new MenuItem("����");
		lvyoushow = new MenuItem("����");
		enroll = new MenuItem("�鿴����");
		// �˵�������¼�������
		show.addActionListener(this);
		cityshow.addActionListener(this);
		fanganshow.addActionListener(this);
		lvyoushow.addActionListener(this);
		forumshow.addActionListener(this);
		enroll.addActionListener(this);
		// ���ô��ڵĲ˵������˵��͸����˵��
		daoyou.add(show);
		city.add(cityshow);
		fangan.add(fanganshow);
		forum.add(forumshow);
		lvyou.add(lvyoushow);
		lvyou.add(enroll);
		menuBar.add(daoyou);
		menuBar.add(city);
		menuBar.add(fangan);
		menuBar.add(lvyou);
		menuBar.add(forum);
		setMenuBar(menuBar);

		// ���ò���Ϊ�߽粼�ֹ���������contentPanel��ӵ���������λ�á�
		this.setContentPane(new MyPanel("��ӭ����Ա Admin!"));
		// this.setLayout(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == show) {
			changeContentPane(new GuidePanel());
		} else if (source == cityshow) {
			changeContentPane(new CityPanel());

		} else if (source == fanganshow) {
			changeContentPane(new DaiXuanPanel());

		} else if (source == forumshow) {
			changeContentPane(new ForumPanel());

		} else if (source == lvyoushow) {
			changeContentPane(new TravelPanel());

		} else if (source == enroll) {
			changeContentPane(new EnrollPanel());

		}
	}

	// �л��������
	public void changeContentPane(Container contentPane) {
		this.setContentPane(contentPane);
		this.revalidate();
	}

}
