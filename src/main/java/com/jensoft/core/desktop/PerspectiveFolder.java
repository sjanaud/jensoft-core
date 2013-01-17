/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PerspectiveFolder extends JPanel {

    private List<Perspective> perspectives;
    private Class currentPerspective;

    public PerspectiveFolder() {

        perspectives = new ArrayList<Perspective>();

        setLayout(new BorderLayout());

        Border emptyBdr = BorderFactory.createEmptyBorder(10, 5, 5, 5);
        setBorder(emptyBdr);

        setVisible(true);
    }

    public int countPerspective() {
        return perspectives.size();
    }

    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    public Perspective getPerspective(int index) {
        return perspectives.get(index);
    }

    public void registerPerspective(Perspective perspective) {

        perspective.createViews();
        perspective.createViewset();
        perspectives.add(perspective);

    }

    public Perspective getPerspective(Class perspective) {

        for (int i = 0; i < perspectives.size(); i++) {
            Perspective p = perspectives.get(i);
            if (p.getClass().getName().equals(perspective.getName())) {
                return p;
            }
        }
        return null;
    }

    public void openPerspective(Class perspective) {

        // remove current perspective
        removeAll();

        // close current perspective if different
        if (getCurrentPerspective() != null
                && !perspective.equals(getCurrentPerspective())) {
            Perspective p = getPerspective(getCurrentPerspective());
            p.closeCurrentViewset();
            p.closePerspective();

        }
        // get the specified perspective
        Perspective pInstance = getPerspective(perspective);

        // control on the specified perspective
        if (pInstance == null) {
            throw new NullPointerException("Perspective [" + perspective
                    + "] seems to be not registered into perspective folder");
        }

        if (pInstance.getCurrentViewset() == null) {
            if (pInstance.getDefaultViewset() == null) {

                throw new NullPointerException("Perspective [" + perspective
                        + "] Any default viewset has been configured.");
            }
            else {
                pInstance.openViewset(pInstance.getDefaultViewset());
            }
        }
        else {
            pInstance.openViewset(pInstance.getCurrentViewset());
        }

        // add the specified perspective
        add(pInstance, BorderLayout.CENTER);

        // open the new perspective
        pInstance.openPerspective();

        setCurrentPerspective(perspective);

        add(pInstance.getComandBar(), BorderLayout.NORTH);

        revalidate();
        repaint();
    }

    public Class getCurrentPerspective() {
        return currentPerspective;
    }

    public void setCurrentPerspective(Class currentPerspective) {
        this.currentPerspective = currentPerspective;
    }

}
