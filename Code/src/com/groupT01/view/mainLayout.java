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
	MenuBar menuBar; // 菜单栏
	Menu daoyou, city, fangan, forum, lvyou; // 菜单
	MenuItem show, cityshow, fanganshow, forumshow, lvyoushow; // 菜单项
	MenuItem enroll;
	Panel contentPanel; // 内容面板，其上用于添加其他待切换的面板

	public mainLayout() {
		setTitle("旅游信息管理系统V1.0-管理员");
		setBounds(300, 100, 800, 600);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 初始化组件
		menuBar = new MenuBar();
		daoyou = new Menu("导游");
		city = new Menu("城市景点");
		fangan = new Menu("旅游线路");
		show = new MenuItem("管理");
		cityshow = new MenuItem("管理");
		fanganshow = new MenuItem("管理");
		lvyou = new Menu("旅游方案");
		forum = new Menu("论坛");
		forumshow = new MenuItem("管理");
		lvyoushow = new MenuItem("管理");
		enroll = new MenuItem("查看报名");
		// 菜单项添加事件监听器
		show.addActionListener(this);
		cityshow.addActionListener(this);
		fanganshow.addActionListener(this);
		lvyoushow.addActionListener(this);
		forumshow.addActionListener(this);
		enroll.addActionListener(this);
		// 设置窗口的菜单栏，菜单和各个菜单项。
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

		// 设置布局为边界布局管理器。将contentPanel添加到窗口中心位置。
		this.setContentPane(new MyPanel("欢迎管理员 Admin!"));
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

	// 切换内容面板
	public void changeContentPane(Container contentPane) {
		this.setContentPane(contentPane);
		this.revalidate();
	}

}
