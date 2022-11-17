package com.groupT01.view;

import java.awt.Container;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class guideLayout extends JFrame implements ActionListener {

	// private static final long serialVersionUID = 1L;
	MenuBar menuBar; // �˵���
	Menu daoyou, city, fangan, forum, lvyou; // �˵�
	MenuItem show, fanganshow, forumshow, lvyoushow; // �˵���
	MenuItem enroll;
	Panel contentPanel; // ������壬������������������л������

	public guideLayout() {
		setTitle("���ι���ϵͳ-����");
		setBounds(300, 100, 800, 600);

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// ��ʼ�����
		menuBar = new MenuBar();
		daoyou = new Menu("������Ϣ");
		fangan = new Menu("������·");
		lvyou = new Menu("���η���");
		forum = new Menu("��̳");
		show = new MenuItem("�޸�");
		fanganshow = new MenuItem("����");
		forumshow = new MenuItem("����");
		lvyoushow = new MenuItem("����");
		enroll = new MenuItem("�鿴����");
		// �˵�������¼�������
		show.addActionListener(this);
		fanganshow.addActionListener(this);
		lvyoushow.addActionListener(this);
		forumshow.addActionListener(this);
		enroll.addActionListener(this);
		// ���ô��ڵĲ˵������˵��͸����˵��
		daoyou.add(show);
		fangan.add(fanganshow);
		forum.add(forumshow);
		lvyou.add(lvyoushow);
		lvyou.add(enroll);
		menuBar.add(daoyou);
		menuBar.add(fangan);
		menuBar.add(lvyou);
		menuBar.add(forum);
		setMenuBar(menuBar);

		// ���ò���Ϊ�߽粼�ֹ���������contentPanel��ӵ���������λ�á�
		this.setContentPane(new MyPanel("��ӭ���� " + LOGIN.guide_login.getName() + "����ţ�" + LOGIN.guide_login.getGID()));
		// this.setLayout(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == show) {
			changeContentPane(new GuideUpdatePanel());
		} else if (source == fanganshow) {
			changeContentPane(new DaiXuanPanel());

		} else if (source == forumshow) {
			changeContentPane(new GuideForumPanel());

		} else if (source == lvyoushow) {
			changeContentPane(new GuideTravelPanel());

		} else if (source == enroll) {
			changeContentPane(new GuideEnrollPanel());

		}
	}

	// �л��������
	public void changeContentPane(Container contentPane) {
		this.setContentPane(contentPane);
		this.revalidate();
	}
}
