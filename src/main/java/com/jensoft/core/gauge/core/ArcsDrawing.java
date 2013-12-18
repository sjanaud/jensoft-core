package com.jensoft.core.gauge.core;

import java.awt.Graphics;

import javax.swing.JFrame;

public class ArcsDrawing extends JFrame {
	public ArcsDrawing() {
		setTitle("Arcs Drawing");
		setSize(525, 300);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.drawArc(60, 70, 200, 200, 0, 180);
		g.fillArc(300, 70, 200, 200, 0, 270);
	}

	public static void main(String args[]) {
		new ArcsDrawing();
	}
}