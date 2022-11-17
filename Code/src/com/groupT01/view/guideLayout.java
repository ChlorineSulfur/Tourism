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
	MenuBar menuBar; // 菜单栏
	Menu daoyou, city, fangan, forum, lvyou; // 菜单
	MenuItem show, fanganshow, forumshow, lvyoushow; // 菜单项
	MenuItem enroll;
	Panel contentPanel; // 内容面板，其上用于添加其他待切换的面板

	public guideLayout() {
		setTitle("旅游管理系统-导游");
		setBounds(300, 100, 800, 600);

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 初始化组件
		menuBar = new MenuBar();
		daoyou = new Menu("个人信息");
		fangan = new Menu("旅游线路");
		lvyou = new Menu("旅游方案");
		forum = new Menu("论坛");
		show = new MenuItem("修改");
		fanganshow = new MenuItem("管理");
		forumshow = new MenuItem("管理");
		lvyoushow = new MenuItem("管理");
		enroll = new MenuItem("查看报名");
		// 菜单项添加事件监听器
		show.addActionListener(this);
		fanganshow.addActionListener(this);
		lvyoushow.addActionListener(this);
		forumshow.addActionListener(this);
		enroll.addActionListener(this);
		// 设置窗口的菜单栏，菜单和各个菜单项。
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

		// 设置布局为边界布局管理器。将contentPanel添加到窗口中心位置。
		this.setContentPane(new MyPanel("欢迎导游 " + LOGIN.guide_login.getName() + "，编号：" + LOGIN.guide_login.getGID()));
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

	// 切换内容面板
	public void changeContentPane(Container contentPane) {
		this.setContentPane(contentPane);
		this.revalidate();
	}
}
