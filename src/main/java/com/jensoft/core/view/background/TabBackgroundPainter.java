/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.view.View2D;

/**
 * @author Sebastien Janaud
 */
public class TabBackgroundPainter extends BackgroundPainter {

    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private Insets insetComandBar = new Insets(80, 6, 6, 6);

    // @Override
    // public Insets getInsets() {
    //
    // FontMetrics metrics = getFontMetrics(f);
    // int sheigh = metrics.height;
    // insetComandBar = new Insets(sheigh + verticalOffset + baseLineOffset
    // + 6, 6, 16, 6);
    // return insetComandBar;
    // }

    private int baseLine;
    private int baseLineOffset = 2;
    private int baseLineTab;
    private int verticalOffset = 0;// 50;
    private int offsetStart = 20;
    private int interTab = 5;

    private JPanel pSQouche = new JPanel();
    private Font f = new Font("tahoma", Font.PLAIN, 14);

    @Override
    public void paintViewBackground(View2D view,int viewWidth,int viewHeight, Graphics2D g2d) {

        g2d.setColor(pSQouche.getBackground());
        RenderingHints hints = new RenderingHints(
                                                  RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);

        int width = view.getWidth();
        int height = view.getHeight();

        g2d.setFont(f);
        FontMetrics metrics = g2d.getFontMetrics(f);
        int sheigh = metrics.getHeight();
        baseLine = sheigh + verticalOffset;
        baseLineTab = baseLine + baseLineOffset;
        insetComandBar = new Insets(baseLineTab + 6, 6, 6, 6);
        int offset = offsetStart;
        g2d.setColor(Color.DARK_GRAY);

        // for (int i = 0; i < commands.size(); i++) {
        // ComandGroup c = commands.get(i);
        // String name = c.getComandName();

        // sarttX
        int startX = offset + offsetStart;
        // c.setStartX(startX);

        // endX
        int swidth = metrics.stringWidth("toto");
        // c.setWidthMetrics(swidth);
        int widthIcon = 0;
        // if (c.getTabIcon() != null) {
        // widthIcon = c.getTabIcon().getIconWidth();
        // c.setIconWidth(widthIcon + 4);
        // }
        int endX = swidth + widthIcon;
        // c.setEndX(endX);

        offset = offset + swidth + widthIcon + 20 + interTab;

        g2d.setColor(Color.DARK_GRAY);

        GeneralPath tabPath = new GeneralPath();
        tabPath.moveTo(5 + 10, height - 6);
        tabPath.quadTo(5, height - 6, 5, height - 16);
        tabPath.lineTo(5, baseLineTab);
        tabPath.lineTo(startX - 15, baseLineTab);
        tabPath.quadTo(startX - 10, baseLineTab, startX - 10, baseLineTab - 5);
        tabPath.lineTo(startX - 10, baseLine - sheigh);
        tabPath.quadTo(startX - 10, baseLine - sheigh - 10, startX, baseLine
                - sheigh - 10);
        tabPath.lineTo(startX + endX, baseLine - sheigh - 10);
        tabPath.quadTo(startX + endX + 10, baseLine - sheigh - 10, startX
                + endX + 10, baseLine - sheigh);
        tabPath.lineTo(startX + endX + 10, baseLineTab);

        tabPath.lineTo(width - 6, baseLineTab);

        tabPath.lineTo(width - 6, height - 16);
        tabPath.quadTo(width - 6, height - 6, width - 16, height - 6);
        tabPath.closePath();
        // c.setTabShape(tabPath);

        // sensible
        GeneralPath tabSensiblePath = new GeneralPath();

        int innerMargin = 4;
        tabSensiblePath.moveTo(startX - 10 + innerMargin, baseLineTab - 5);

        tabSensiblePath.lineTo(startX - 10 + innerMargin, baseLine - sheigh);
        tabSensiblePath.quadTo(startX - 10 + innerMargin, baseLine - sheigh
                - 10 + innerMargin, startX, baseLine - sheigh - 10
                + innerMargin);
        tabSensiblePath.lineTo(startX + endX, baseLine - sheigh - 10
                + innerMargin);
        tabSensiblePath.quadTo(startX + endX + 10 - innerMargin, baseLine
                - sheigh - 10 + innerMargin, startX + endX + 10 - innerMargin,
                               baseLine - sheigh);
        tabSensiblePath.lineTo(startX + endX + 10 - innerMargin,
                               baseLineTab - 5);

        tabSensiblePath.closePath();
        // c.setSensibleTabShape(tabSensiblePath);

        GeneralPath gpathDeco = new GeneralPath();
        gpathDeco.moveTo(5, baseLineTab);
        gpathDeco.lineTo(width - 6, baseLineTab);
        gpathDeco.lineTo(width - 6, height / 2);

        CubicCurve2D curve = new CubicCurve2D.Double(width - 6, height / 2,
                                                     width / 2, baseLineTab, width / 2, height, 5, height / 2);
        gpathDeco.append(curve, true);
        gpathDeco.closePath();

        // c.setCubicDeco(gpathDeco);

        // }

        RoundRectangle2D roundBaseShape = new RoundRectangle2D.Double(0, 0,
                                                                      width - 1, height - 1, 30, 30);

        GradientPaint gpbase = new GradientPaint(width / 2, 0,
                                                 Color.LIGHT_GRAY, width / 2, baseLine, Color.GRAY, false);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setPaint(gpbase);
        g2d.fill(roundBaseShape);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.draw(roundBaseShape);
        g2d.setStroke(new BasicStroke());

        g2d.setColor(RosePalette.AEGEANBLUE);
        g2d.setFont(InputFonts.getFont(InputFonts.ELEMENT, 16));
        // g2d.drawString(title, 10, 20);
        g2d.setFont(f);
        // // ComandGroup selectedTab = null;
        // // for (int j = 0; j < commands.size(); j++) {
        // // ComandGroup c = commands.get(j);
        //
        // // g2d.setColor(Color.BLUE);
        // // g2d.draw(c.getSensibleTabShape());
        // //
        // // if (c.isSelected()) {
        // // selectedTab = c;
        // // continue;
        // // }
        //
        // g2d.setColor(Color.LIGHT_GRAY);
        //
        // GradientPaint gp = new GradientPaint(width / 2, baseLine
        // - sheigh - 10, Color.WHITE, width / 2,
        // baseLineTab + 5, c.getTabColor(), false);
        // g2d.setPaint(gp);
        // g2d.fill(c.getTabShape());
        // g2d.setColor(Color.WHITE);
        // g2d.draw(c.getTabShape());
        //
        // g2d.setColor(Color.BLACK);
        // g2d.drawString("toto",
        // startX , baseLine - 5);
        // // if (c.getTabIcon() != null)
        // // g2d.drawImage(c.getTabIcon().getImage(), startX,
        // // baseLine - c.getTabIcon().getIconHeight() - 2, c
        // // .getTabIcon().getIconWidth(), c.getTabIcon()
        // // .getIconHeight(), null);
        //
        // // }

        //
        // if (selectedTab != null) {
        Color color = Color.DARK_GRAY;
        GradientPaint gp = new GradientPaint(width / 2, 20, Color.WHITE,
                                             width / 2, height / 2, color, false);
        g2d.setPaint(gp);
        g2d.fill(tabPath);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.WHITE);
        g2d.draw(tabPath);
        g2d.setStroke(new BasicStroke());

        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("toto", startX, baseLine - 5);
        // if (selectedTab.getTabIcon() != null)
        // g2d.drawImage(selectedTab.getTabIcon().getImage(), selectedTab
        // .getStartX(), baseLine
        // - selectedTab.getTabIcon().getIconHeight() - 2,
        // selectedTab.getTabIcon().getIconWidth(), selectedTab
        // .getTabIcon().getIconHeight(), null);

        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 0.15f));
        g2d.setColor(Color.WHITE);
        g2d.fill(gpathDeco);
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));

        // }

    }

}
