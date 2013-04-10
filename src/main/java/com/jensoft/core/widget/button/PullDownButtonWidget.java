/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import com.jensoft.core.device.DevicePartComponent;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;

/**
 * PullDownButtonWidget
 * 
 * @author Sebastien Janaud
 */
public class PullDownButtonWidget<P extends AbstractPlugin> extends ButtonWidget<P> {

    /** the widget id */
    public final static String ID = "@widget/core/pulldownwidget";

    private PullOrderComparator pullComparator = new PullOrderComparator();
    private int pullInsetX = 5;
    private int pullInsetY = 7;
    private Rectangle2D pullFrame;
    private Rectangle2D sensiblePull;
    private GeneralPath pullPath;
    private List<PullItem> actionItems = new ArrayList<PullItem>();
    private Rectangle2D rectItemRegion;
    private int regionInsetX = 8;
    private int regionInsetY = 3;
    private Stroke defaultStroke = new BasicStroke();

    private Color pullDrawColor;
    private Color pullFillColor = Color.DARK_GRAY;

    private int pressX;
    private int pressY;

    /**
     * create Pull down button widget
     * 
     * @param id
     * @param width
     * @param height
     * @param xIndex
     * @param yIndex
     */
    public PullDownButtonWidget(String id, double width, double height,
            int xIndex, int yIndex) {
        super(id, width, height, xIndex, yIndex);
    }

    /**
     * update pull item set the last run item order to the max found item order
     * of pull and increase order
     * 
     * @param e
     */
    public void updatePull(PullItem pullItem) {

        int maxOrder = pullItem.getOrder();

        for (PullItem item : actionItems) {
            if (item.getOrder() > maxOrder) {
                maxOrder = item.getOrder();
            }
        }
        pullItem.setOrder(maxOrder + 2);
    }

    /**
     * sort the pull by order
     */
    public void sortPull() {
        Collections.sort(actionItems, pullComparator);
    }

    /**
     * compare order of each pull item
     */
    class PullOrderComparator implements Comparator<PullItem> {

        public PullOrderComparator() {
        }

        @Override
        public int compare(PullItem pi1, PullItem pi2) {

            if (pi1.getOrder() > pi2.getOrder()) {
                return -1;
            }
            else if (pi1.getOrder() < pi2.getOrder()) {
                return +1;
            }
            return 0;
        }

    }

    /**
     * add pull action
     * 
     * @param actionItem
     */
    public void addPullAction(PullItem actionItem) {
        actionItem.setPullDownWidget(this);
        actionItems.add(actionItem);
    }

    @Override
    public void interceptPress(int x, int y) {
        // get pressed coordinate before call super pressed.
        pressX = x;
        pressY = y;
        super.interceptPress(x, y);
    }

    /**
     * shop popup if press coordinates are contains in sensible pull sensible
     */
    private void pullDown() {

        JPopupMenu popup = getHost().getWindow2D().getDevice2D()
                .getDeviceMenuManager().getContextMenu();
        popup.removeAll();
        sortPull();
        for (PullItem actionItem : actionItems) {
            popup.add(actionItem.getPullEntry());
        }
        int x = (int) getWidgetFolder().getX();
        int y = (int) getWidgetFolder().getY();
        DevicePartComponent device = getHost().getWindow2D().getView2D()
                .getDevice2D();
        popup.show(device, x, y + (int) getWidgetFolder().getHeight());
    }

    @Override
    public void onPress() {
        super.onPress();
        if (sensiblePull.contains(pressX, pressY)) {
            pullDown();
        }
        else {

            if (rectItemRegion.contains(pressX, pressY)) {
                sortPull();
                if (actionItems.size() > 0) {
                    PullItem item = actionItems.get(0);
                    item.getPullEntry().doClick();
                }
            }
        }
    }

    /**
     * @return the pullDrawColor
     */
    public Color getPullDrawColor() {
        return pullDrawColor;
    }

    /**
     * @param pullDrawColor
     *            the pullDrawColor to set
     */
    public void setPullDrawColor(Color pullDrawColor) {
        this.pullDrawColor = pullDrawColor;
    }

    /**
     * @return the pullFillColor
     */
    public Color getPullFillColor() {
        return pullFillColor;
    }

    /**
     * @param pullFillColor
     *            the pullFillColor to set
     */
    public void setPullFillColor(Color pullFillColor) {
        this.pullFillColor = pullFillColor;
    }

    public void paintItemButton(View2D v2d, Graphics2D g2d,
            Rectangle2D itemRegion) {

        sortPull();
        if (actionItems.size() > 0) {
            PullItem item = actionItems.get(0);
            ImageIcon icon = item.getPullIcon();
            if (icon.getIconWidth() > itemRegion.getWidth()
                    && icon.getIconHeight() > itemRegion.getHeight()) {
                g2d.drawImage(icon.getImage(), (int) itemRegion.getX(),
                              (int) itemRegion.getY(), (int) itemRegion.getWidth(),
                              (int) itemRegion.getHeight(), null);
            }
            else if (icon.getIconWidth() > itemRegion.getWidth()
                    && !(icon.getIconHeight() > itemRegion.getHeight())) {
                g2d.drawImage(
                              icon.getImage(),
                              (int) itemRegion.getX(),
                              (int) (itemRegion.getY() + itemRegion.getHeight() / 2 - icon
                                      .getIconHeight() / 2), (int) itemRegion
                                      .getWidth(), icon.getIconHeight(), null);
            }
            else if (!(icon.getIconWidth() > itemRegion.getWidth())
                    && icon.getIconHeight() > itemRegion.getHeight()) {
                g2d.drawImage(icon.getImage(), (int) (itemRegion.getX()
                        + itemRegion.getWidth() / 2 - icon.getIconWidth() / 2),
                              (int) itemRegion.getY(), icon.getIconWidth(),
                              (int) itemRegion.getHeight(), null);
            }
            else {
                g2d.drawImage(
                              icon.getImage(),
                              (int) (itemRegion.getX() + itemRegion.getWidth() / 2 - icon
                                      .getIconWidth() / 2),
                              (int) (itemRegion.getY() + itemRegion.getHeight() / 2 - icon
                                      .getIconHeight() / 2), icon.getIconWidth(),
                              icon.getIconHeight(), null);
            }

        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.button.ButtonWidget#paintButton(com.jensoft.core.view.View2D, java.awt.Graphics2D, java.awt.geom.Rectangle2D)
     */
    @Override
    public void paintButton(View2D v2d, Graphics2D g2d,
            Rectangle2D buttonDrawingRegion) {

        // paint base button
        super.paintButton(v2d, g2d, buttonDrawingRegion);

        // paint pull down
        Rectangle2D r = buttonDrawingRegion;
        double squarePull = r.getHeight() - 2 * pullInsetY;

        pullFrame = new Rectangle2D.Double(r.getX() + r.getWidth() - pullInsetX
                - squarePull, r.getY() + pullInsetY, squarePull, squarePull);

        pullPath = new GeneralPath();
        pullPath.moveTo(pullFrame.getX(), pullFrame.getY());
        pullPath.lineTo(pullFrame.getX() + pullFrame.getWidth(),
                        pullFrame.getY());
        pullPath.lineTo(pullFrame.getX() + pullFrame.getWidth() / 2,
                        pullFrame.getY() + pullFrame.getHeight());
        pullPath.closePath();

        if (pullFillColor != null) {
            g2d.setColor(pullFillColor);
            g2d.fill(pullPath);
        }

        if (pullDrawColor != null) {
            g2d.setColor(pullDrawColor);
            g2d.setStroke(defaultStroke);
            g2d.draw(pullPath);
        }

        double x1 = pullFrame.getX() - pullInsetX;
        double y1 = r.getY();

        double x2 = pullFrame.getX() - pullInsetX;
        double y2 = r.getY() + r.getHeight();

        if (getOutlineColor() != null) {
            g2d.setColor(getOutlineColor());
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }
            g2d.draw(new Line2D.Double(x1, y1 + 2, x2, y2 - 2));
        }

        sensiblePull = new Rectangle2D.Double(x1, y1, Math.abs(r.getX()
                + r.getWidth() - x1), r.getHeight());
        rectItemRegion = new Rectangle2D.Double(r.getX() + regionInsetX,
                                                r.getY() + regionInsetY, Math.abs(r.getX() - x1) - 2
                                                        * regionInsetX, r.getHeight() - 2 * regionInsetY);

        clearSensibleShape();
        addSensibleShape(sensiblePull);
        addSensibleShape(rectItemRegion);

        paintItemButton(v2d, g2d, rectItemRegion);

        // debug
        g2d.setColor(Color.RED);
        // g2d.draw(rectItemRegion);
        g2d.setColor(Color.CYAN);
        // g2d.draw(pullFrame);
    }

}
