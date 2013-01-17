/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.message.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalScrollButton;

import com.jensoft.core.palette.Spectral;

class MessageScrollButton extends MetalScrollButton {

    /** button theme color */
    private Color scrollerButtonColor = Spectral.SPECTRAL_BLUE2.brighter();

    public MessageScrollButton(int direction, int width, boolean freeStanding) {
        super(direction, width, freeStanding);
        setOpaque(false);
    }

    public MessageScrollButton(int direction, int width, boolean freeStanding, Color scrollerButtonColor) {
        super(direction, width, freeStanding);
        this.scrollerButtonColor = scrollerButtonColor;
        setOpaque(false);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(17, 17);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(scrollerButtonColor);
        switch (getDirection()) {
            case SwingConstants.WEST:
                GeneralPath pw = new GeneralPath();
                pw.moveTo(2, getHeight() / 2);
                pw.lineTo(getWidth() - 2, 2);
                pw.lineTo(getWidth() - 2, getHeight() - 2);
                pw.closePath();

                g2d.fill(pw);
                break;
            case SwingConstants.EAST:
                GeneralPath pe = new GeneralPath();
                pe.moveTo(getWidth() - 2, getHeight() / 2);
                pe.lineTo(2, 2);
                pe.lineTo(2, getHeight() - 2);
                pe.closePath();

                g2d.fill(pe);
                break;
            case SwingConstants.NORTH:
                GeneralPath pn = new GeneralPath();
                pn.moveTo(getWidth() / 2, 2);
                pn.lineTo(getWidth() - 2, getHeight() - 2);
                pn.lineTo(2, getHeight() - 2);
                pn.closePath();

                g2d.fill(pn);
                break;
            case SwingConstants.SOUTH:
                GeneralPath ps = new GeneralPath();
                ps.moveTo(getWidth() / 2, getHeight() - 2);
                ps.lineTo(getWidth() - 2, 2);
                ps.lineTo(2, 2);
                ps.closePath();

                g2d.fill(ps);
                break;
        }
    }
}
