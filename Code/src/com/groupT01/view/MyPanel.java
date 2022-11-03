package com.groupT01.view;

import java.awt.Label;
import java.awt.Panel;

public class MyPanel extends Panel {
	public MyPanel(String msg) {
		this.add(new Label(msg));
	}
}
