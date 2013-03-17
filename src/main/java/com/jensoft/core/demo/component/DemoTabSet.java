/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.component;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jensoft.core.palette.RosePalette;

/**
 * do not use, only demo component for jensoft view.
 * 
 * @author sebastien janaud
 */
public class DemoTabSet extends JComponent implements DemoTabListener {

    private static final long serialVersionUID = 8990735952213763143L;

    private List<DemoTab> commands = new ArrayList<DemoTab>();
    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DemoTab> getCommands() {
        return commands;
    }

    private Insets insetComandBar = new Insets(80, 6, 6, 6);

    @Override
    public Insets getInsets() {
        FontMetrics metrics = getFontMetrics(f);
        int sheigh = metrics.getHeight();
        insetComandBar = new Insets(sheigh + verticalOffset + baseLineOffset
                + 6, 6, 16, 6);
        return insetComandBar;
    }

    public void addComandTab(DemoTab ct, JComponent component) {
        ct.addComandGroupListener(this);
        ct.setComponent(component);
        commands.add(ct);
    }

    @Override
    public void tabSelect(DemoTabEvent groupEvent) {
        removeAll();
        setLayout(new BorderLayout());
        if (((DemoTab) groupEvent.getSource()).getComponent() != null) {
            add(((DemoTab) groupEvent.getSource()).getComponent(),BorderLayout.CENTER);
            revalidate();
            ((DemoTab) groupEvent.getSource()).getComponent().requestFocus();
        }
    }

    public DemoTabSet() {

        setPreferredSize(new Dimension(400, 150));
        BarListener listener = new BarListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    class BarListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

            for (int i = 0; i < commands.size(); i++) {
                DemoTab c = commands.get(i);

                Shape tabShape = c.getSensibleTabShape();

                if (tabShape.contains(new Point2D.Double(e.getX(), e.getY()))) {

                    for (int j = 0; j < commands.size(); j++) {
                        DemoTab cd = commands.get(j);
                        cd.setSelected(false);
                    }

                    c.setSelected(true);

                }

            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

    }

    private int baseLine;
    private int baseLineOffset = 2;
    private int baseLineTab;
    private int verticalOffset = 50;
    private int offsetStart = 20;
    private int interTab = 5;
    
    private int cornerRadius=30;
    private boolean drawOutline=true;    

	/**
	 * @return the cornerRadius
	 */
	public int getCornerRadius() {
		return cornerRadius;
	}

	/**
	 * @param cornerRadius the cornerRadius to set
	 */
	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	/**
	 * @return the drawOutline
	 */
	public boolean isDrawOutline() {
		return drawOutline;
	}

	/**
	 * @param drawOutline the drawOutline to set
	 */
	public void setDrawOutline(boolean drawOutline) {
		this.drawOutline = drawOutline;
	}

	private JPanel pSQouche = new JPanel();
    private Font f = new Font("lucida console",Font.PLAIN,12);

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(pSQouche.getBackground());
        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);

        g2d.setFont(f);
        FontMetrics metrics = g.getFontMetrics(f);
        int sheigh = metrics.getHeight();
        baseLine = sheigh + verticalOffset;
        baseLineTab = baseLine + baseLineOffset;
        insetComandBar = new Insets(baseLineTab + 6, 6, 6, 6);
        int offset = offsetStart;
        g2d.setColor(Color.DARK_GRAY);
        for (int i = 0; i < commands.size(); i++) {
            DemoTab c = commands.get(i);
            String name = c.getComandName();

            // sarttX
            int startX = offset + offsetStart;
            c.setStartX(startX);

            // endX
            int swidth = metrics.stringWidth(name);
            c.setWidthMetrics(swidth);
            int widthIcon = 0;
            if (c.getTabIcon() != null) {
                widthIcon = c.getTabIcon().getIconWidth();
                c.setIconWidth(widthIcon + 4);
            }
            int endX = swidth + widthIcon;
            c.setEndX(endX);

            offset = offset + swidth + widthIcon + 20 + interTab;

            g2d.setColor(Color.DARK_GRAY);

            GeneralPath tabPath = new GeneralPath();
            tabPath.moveTo(5 + 10, getHeight() - 6);
            tabPath.quadTo(5, getHeight() - 6, 5, getHeight() - 16);
            tabPath.lineTo(5, baseLineTab);
            tabPath.lineTo(c.getStartX() - 15, baseLineTab);
            tabPath.quadTo(c.getStartX() - 10, baseLineTab, c.getStartX() - 10,
                           baseLineTab - 5);
            tabPath.lineTo(c.getStartX() - 10, baseLine - sheigh);
            tabPath.quadTo(c.getStartX() - 10, baseLine - sheigh - 10,
                           c.getStartX(), baseLine - sheigh - 10);
            tabPath.lineTo(c.getStartX() + c.getEndX(), baseLine - sheigh - 10);
            tabPath.quadTo(c.getStartX() + c.getEndX() + 10, baseLine - sheigh
                    - 10, c.getStartX() + c.getEndX() + 10, baseLine - sheigh);
            tabPath.lineTo(c.getStartX() + c.getEndX() + 10, baseLineTab);

            tabPath.lineTo(getWidth() - 6, baseLineTab);

            tabPath.lineTo(getWidth() - 6, getHeight() - 16);
            tabPath.quadTo(getWidth() - 6, getHeight() - 6, getWidth() - 16,
                           getHeight() - 6);
            tabPath.closePath();
            c.setTabShape(tabPath);

            GeneralPath tabSensiblePath = new GeneralPath();

            int innerMargin = 4;
            tabSensiblePath.moveTo(c.getStartX() - 10 + innerMargin,
                                   baseLineTab - 5);

            tabSensiblePath.lineTo(c.getStartX() - 10 + innerMargin, baseLine
                    - sheigh);
            tabSensiblePath.quadTo(c.getStartX() - 10 + innerMargin, baseLine
                    - sheigh - 10 + innerMargin, c.getStartX(), baseLine
                    - sheigh - 10 + innerMargin);
            tabSensiblePath.lineTo(c.getStartX() + c.getEndX(), baseLine
                    - sheigh - 10 + innerMargin);
            tabSensiblePath.quadTo(c.getStartX() + c.getEndX() + 10
                    - innerMargin, baseLine - sheigh - 10 + innerMargin,
                                   c.getStartX() + c.getEndX() + 10 - innerMargin, baseLine
                                           - sheigh);
            tabSensiblePath.lineTo(c.getStartX() + c.getEndX() + 10
                    - innerMargin, baseLineTab - 5);

            tabSensiblePath.closePath();
            c.setSensibleTabShape(tabSensiblePath);

            GeneralPath gpathDeco = new GeneralPath();
            gpathDeco.moveTo(5, baseLineTab);
            gpathDeco.lineTo(getWidth() - 6, baseLineTab);
            gpathDeco.lineTo(getWidth() - 6, getHeight() / 2);

            CubicCurve2D curve = new CubicCurve2D.Double(getWidth() - 6,
                                                         getHeight() / 2, getWidth() / 2, baseLineTab,
                                                         getWidth() / 2, getHeight(), 5, getHeight() / 2);
            gpathDeco.append(curve, true);
            gpathDeco.closePath();

            c.setCubicDeco(gpathDeco);

        }

        RoundRectangle2D roundBaseShape = new RoundRectangle2D.Double(0, 0,
                                                                      getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        GradientPaint gpbase = new GradientPaint(getWidth() / 2, 0,
                                                 Color.LIGHT_GRAY, getWidth() / 2, baseLine, Color.GRAY, false);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setPaint(gpbase);
        g2d.fill(roundBaseShape);
        g2d.setStroke(new BasicStroke(2));
        
        if(drawOutline){
        	g2d.setColor(Color.WHITE);
        	//g2d.draw(roundBaseShape);
        	g2d.setStroke(new BasicStroke());
        }
        g2d.setColor(RosePalette.AEGEANBLUE);

        g2d.setFont(f);
        DemoTab selectedTab = null;
        for (int j = 0; j < commands.size(); j++) {
            DemoTab c = commands.get(j);

            if (c.isSelected()) {
                selectedTab = c;
                continue;
            }

            g2d.setColor(Color.LIGHT_GRAY);

            GradientPaint gp = new GradientPaint(getWidth() / 2, baseLine
                    - sheigh - 10, Color.WHITE, getWidth() / 2,
                                                 baseLineTab + 5, c.getTabColor(), false);
            g2d.setPaint(gp);
            g2d.fill(c.getTabShape());
            g2d.setColor(Color.WHITE);
            g2d.draw(c.getTabShape());

            g2d.setColor(Color.BLACK);
            g2d.drawString(c.getComandName(), c.getStartX() + c.getIconWidth(),
                           baseLine - 5);
            if (c.getTabIcon() != null) {
                g2d.drawImage(c.getTabIcon().getImage(), c.getStartX(),
                              baseLine - c.getTabIcon().getIconHeight() - 2, c
                                      .getTabIcon().getIconWidth(), c.getTabIcon()
                                      .getIconHeight(), null);
            }

        }

        if (selectedTab != null) {
            GradientPaint gp = new GradientPaint(getWidth() / 2, 20,
                                                 Color.WHITE, getWidth() / 2, getHeight() / 2,
                                                 selectedTab.getTabColor(), false);
            g2d.setPaint(gp);
            g2d.fill(selectedTab.getTabShape());

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.WHITE);
            g2d.draw(selectedTab.getTabShape());
            g2d.setStroke(new BasicStroke());

            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString(selectedTab.getComandName(),
                           selectedTab.getStartX() + selectedTab.getIconWidth(),
                           baseLine - 5);
            if (selectedTab.getTabIcon() != null) {
                g2d.drawImage(selectedTab.getTabIcon().getImage(), selectedTab
                        .getStartX(), baseLine
                        - selectedTab.getTabIcon().getIconHeight() - 2,
                              selectedTab.getTabIcon().getIconWidth(), selectedTab
                                      .getTabIcon().getIconHeight(), null);
            }

            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 0.15f));
            g2d.setColor(Color.WHITE);
            g2d.fill(selectedTab.getCubicDeco());
            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 1f));

        }

    }

}
