/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop.viewsbase;

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
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jensoft.core.palette.InputFonts;

public class View2Group extends JComponent implements View2Litener {

    private Vector<View2> views = new Vector<View2>();
    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Vector<View2> getViews() {
        return views;
    }

    private Insets insetComandBar = new Insets(80, 6, 6, 6);

    @Override
    public Insets getInsets() {

        // getFontMetrics(f)

        FontMetrics metrics = getFontMetrics(f);
        int sheigh = metrics.getHeight();
        insetComandBar = new Insets(sheigh + verticalOffset + baseLineOffset
                + 6, 6, 16, 6);
        // TODO Auto-generated method stub
        // return super.getInsets();
        return insetComandBar;
    }

    public void addView(View2 ct, JComponent component) {

        ct.addComandGroupListener(this);
        ct.setComponent(component);
        views.add(ct);

    }

    @Override
    public void viewSelect(View2Event groupEvent) {
        // System.out.println("comand group :"+((ComandGroup)groupEvent.getSource()).getComandName()+" selected !");
        removeAll();
        setLayout(new BorderLayout());
        if (((View2) groupEvent.getSource()).getComponent() != null) {
            add(((View2) groupEvent.getSource()).getComponent(),
                BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    public View2Group() {

        setPreferredSize(new Dimension(400, 150));
        BarListener listener = new BarListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    class BarListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            // System.out.println("mouse move");
            // TODO Auto-generated method stub
            for (int i = 0; i < views.size(); i++) {
                View2 c = views.get(i);

                // Shape tabShape = c.getTabShape();
                // if(tabShape.contains(new Point2D.Double(e.getX(),e.getY()))){
                // System.out.println("is contains shape tab :"+c.getComandName());
                // }
            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("----------CHECK PRESSED BEGIN-----------");
            // TODO Auto-generated method stub
            for (int i = 0; i < views.size(); i++) {
                View2 c = views.get(i);

                // Shape tabShape = c.getTabShape();
                Shape tabShape = c.getSensibleTabShape();

                if (tabShape.contains(new Point2D.Double(e.getX(), e.getY()))) {
                    System.out.println("IN contains shape sensible tab :"
                            + c.getComandName());

                    for (int j = 0; j < views.size(); j++) {
                        View2 cd = views.get(j);
                        cd.setSelected(false);
                    }

                    c.setSelected(true);

                }
                // else{
                // System.out.println("NOT contains shape sensible tab :"+c.getComandName());
                // c.setSelected(false);
                // }
            }
            repaint();
            System.out.println("----------CHECK PRESSED END-----------");

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

    private int baseLine;
    private int baseLineOffset = 2;
    private int baseLineTab;
    private int verticalOffset = 50;
    private int offsetStart = 20;
    private int interTab = 5;

    private JPanel pSQouche = new JPanel();
    private Font f = new Font("tahoma", Font.PLAIN, 14);

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(pSQouche.getBackground());
        // g2d.fillRect(0, 0, getWidth(), getHeight());

        // g2d.setColor(Color.BLACK);
        // g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
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
        for (int i = 0; i < views.size(); i++) {
            View2 c = views.get(i);
            String name = c.getComandName();

            // sarttX
            int startX = offset + offsetStart;
            c.setStartX(startX);

            // endX
            int swidth = metrics.stringWidth(name.toUpperCase());
            c.setWidthMetrics(swidth);
            int widthIcon = 0;
            if (c.getTabIcon() != null) {
                widthIcon = c.getTabIcon().getIconWidth();
                c.setIconWidth(widthIcon + 4);
            }
            int endX = swidth + widthIcon;
            c.setEndX(endX);

            offset = offset + swidth + widthIcon + 20 + interTab;

            // g2d.drawString(name, offset, baseLine-5);
            // if(c.getTabIcon() != null)
            // g2d.drawImage(c.getTabIcon().getImage(),
            // c.getStartX(),baseLine-c.getTabIcon().getIconHeight(),
            // c.getTabIcon().getIconWidth(), c.getTabIcon().getIconHeight(),
            // null);
            //

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
            // arrondi
            // tabPath.lineTo(getWidth()-1-10, baseLineTab);
            // tabPath.quadTo(getWidth()-1, baseLineTab,getWidth()-1,
            // baseLineTab+10);

            // tabPath.lineTo(getWidth()-6, getHeight()-6);
            tabPath.lineTo(getWidth() - 6, getHeight() - 16);
            tabPath.quadTo(getWidth() - 6, getHeight() - 6, getWidth() - 16,
                           getHeight() - 6);
            tabPath.closePath();
            c.setTabShape(tabPath);

            // sensible
            GeneralPath tabSensiblePath = new GeneralPath();
            // tabSensiblePath.moveTo(5+10, getHeight()-6);
            // tabSensiblePath.quadTo(5, getHeight()-6,5, getHeight()-16);
            // tabSensiblePath.moveTo(5, baseLineTab);

            // tabSensiblePath.moveTo(c.getStartX()-15, baseLineTab);

            // tabSensiblePath.moveTo(c.getStartX()-10,
            // baseLineTab,c.getStartX()-10,baseLineTab-5);

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

            // tabSensiblePath.lineTo(getWidth()-6, baseLineTab);
            // //arrondi
            // //tabPath.lineTo(getWidth()-1-10, baseLineTab);
            // //tabPath.quadTo(getWidth()-1, baseLineTab,getWidth()-1,
            // baseLineTab+10);
            //
            // //tabPath.lineTo(getWidth()-6, getHeight()-6);
            // tabSensiblePath.lineTo(getWidth()-6, getHeight()-16);
            // tabSensiblePath.quadTo(getWidth()-6, getHeight()-6,getWidth()-16,
            // getHeight()-6);
            tabSensiblePath.closePath();
            c.setSensibleTabShape(tabSensiblePath);

            //
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
                                                                      getWidth() - 1, getHeight() - 1, 30, 30);
        //
        //
        //
        //
        // for (int j = 0; j < commands.size(); j++) {
        // ComandGroup c = commands.get(j);
        //
        // if(c.isSelected()){
        // GradientPaint gpbase = new GradientPaint(getWidth()/2,0,
        // Color.LIGHT_GRAY, getWidth()/2,getHeight(),
        // c.getTabColor(), false);
        // g2d.setPaint(gpbase);
        //
        // continue;
        // }
        // }
        GradientPaint gpbase = new GradientPaint(getWidth() / 2, 0,
                                                 Color.LIGHT_GRAY, getWidth() / 2, baseLine, Color.GRAY, false);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setPaint(gpbase);
        g2d.fill(roundBaseShape);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.draw(roundBaseShape);
        g2d.setStroke(new BasicStroke());

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(InputFonts.getFont(InputFonts.FATSANS, 16));
        g2d.drawString(title, 10, 20);
        g2d.setFont(f);
        View2 selectedTab = null;
        for (int j = 0; j < views.size(); j++) {
            View2 c = views.get(j);

            // g2d.setColor(Color.BLUE);
            // g2d.draw(c.getSensibleTabShape());

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

            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString(c.getComandName().toUpperCase(),
                           c.getStartX() + c.getIconWidth(), baseLine - 5);
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
            g2d.drawString(selectedTab.getComandName().toUpperCase(),
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

        // for (int j = 0; j < commands.size(); j++) {
        // ComandGroup c = commands.get(j);
        //
        //
        // g2d.setColor(Color.BLUE);
        // g2d.draw(c.getSensibleTabShape());
        // }

    }

}
