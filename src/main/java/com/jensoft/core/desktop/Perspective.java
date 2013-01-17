/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class Perspective extends JComponent {

    /** viewsets collection */
    private List<Viewset> viewsets = new ArrayList<Viewset>();

    /** shared views */
    private SharedViews sharedViews = new SharedViews();

    /** perspective name */
    private String perspectiveName;

    /** default viewset */
    private Class defaultViewset;

    /** current viewset */
    private Class currentViewset;

    /** close state ref value */
    public static String CLOSE_STATE = "close";

    /** open state ref value */
    public static String OPEN_STATE = "open";

    /** perspective state, default is the close state */
    private String state = CLOSE_STATE;

    /** workbench */
    private Workbench workbench;

    private JPanel comandBar;

    /**
     * return the default Viewset if any viewset is open in perspective
     * 
     * @return the default viewset, if the default viewset is null, return the
     *         first registered viewset
     */
    public Class getDefaultViewset() {

        if (defaultViewset == null) {
            return viewsets.get(0).getClass();
        }

        return defaultViewset;
    }

    /**
     * set the default Viewset for this perspective
     * 
     * @param defaultViewset
     */
    public void setDefaultViewset(Class defaultViewset) {
        this.defaultViewset = defaultViewset;
    }

    @Override
    public String toString() {
        return "Perspective : " + getClass().getName();
    }

    public JPanel getComandBar() {
        if (comandBar == null) {
            comandBar = new JPanel();
        }
        return comandBar;
    }

    public void setComandBar(JPanel comandBar) {
        this.comandBar = comandBar;
    }

    /**
     * register the specified viewset into this perspective
     * 
     * @param viewset
     *            the viewset to register
     */
    public void registerViewset(Viewset viewset) {
        System.out.println("[Perspective :" + getClass().getSimpleName() + "]"
                + " --> Register viewset : "
                + viewset.getClass().getSimpleName());

        viewset.setPerspective(this);
        viewsets.add(viewset);

    }

    /***
     * register the specified view into this perspective instance
     * 
     * @param v
     *            the view to register
     */
    public void registerView(View view) {
        System.out.println("[Perspective :" + getClass().getSimpleName() + "]"
                + " --> Register view : " + view.getClass().getSimpleName());

        view.setPerspective(this);

        getSharedViews().registerView(view);
    }

    /**
     * delegate method to get a shared view of this perspective instance
     * 
     * @param viewClass
     * @return the shared view
     */
    public View getSharedView(Class viewClass) {
        return getSharedViews().getSharedView(viewClass);
    }

    /**
     * return the shared views which have been registered into this perspective
     * 
     * @return the shared views
     */
    public SharedViews getSharedViews() {
        return sharedViews;
    }

    /**
     * open the viewset into this perspective
     * 
     * @param viewsetClass
     */
    public void openViewset(Class viewsetClass) {
        removeAll();
        setLayout(new BorderLayout());

        if (getCurrentViewset() != null) {
            getViewset(getCurrentViewset()).closeViewset();
        }

        Viewset vs = getViewset(viewsetClass);

        if (vs == null) {
            throw new IllegalArgumentException("Illegal Viewset "
                    + viewsetClass.getName()
                    + " is not register in this perspective");
        }

        vs.packViews();

        vs.openViewset();

        setCurrentViewset(viewsetClass);

        add(vs, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /**
     * close the current viewset into this perspective
     * 
     * @param viewsetClass
     */
    public void closeCurrentViewset() {
        removeAll();
        setLayout(new BorderLayout());

        if (getCurrentViewset() != null) {
            getViewset(getCurrentViewset()).closeViewset();
        }

    }

    /**
     * return the specified viewset instance
     * 
     * @param viewsetClass
     * @return the viewset
     */
    public Viewset getViewset(Class viewsetClass) {
        for (int i = 0; i < viewsets.size(); i++) {
            Viewset v = viewsets.get(i);
            if (v.getClass().getName().equals(viewsetClass.getName())) {
                return v;
            }
        }
        return null;
    }

    /**
     * return all registered viewset for this perspective
     * 
     * @return the viewsets
     */
    public List<Viewset> getRegisterViewset() {
        return viewsets;
    }

    /**
     * return if the specified viewset is register in this perspective instance
     * 
     * @param viewsetClass
     * @return
     */
    public boolean isRegisterViewset(Class viewsetClass) {
        if (getViewset(viewsetClass) != null) {
            return true;
        }
        return false;
    }

    /**
     * return the perspective name
     * 
     * @return the perspective name
     */
    public String getPerspectiveName() {
        return perspectiveName;
    }

    /**
     * set the perspective name
     * 
     * @param perspectiveName
     */
    public void setPerspectiveName(String perspectiveName) {
        this.perspectiveName = perspectiveName;
    }

    /**
     * get the current viewset class
     * 
     * @return the current viewset
     */
    public Class getCurrentViewset() {
        return currentViewset;
    }

    /***
     * set the current viewset
     * 
     * @param currentViewset
     */
    public void setCurrentViewset(Class currentViewset) {
        this.currentViewset = currentViewset;
    }

    /**
     * set the open state
     */
    public void openPerspective() {
        state = OPEN_STATE;
    }

    /**
     * set the close state
     */
    public void closePerspective() {
        state = CLOSE_STATE;
        Viewset v = getViewset(getCurrentViewset());
        if (v != null) {
            v.closeViewset();
        }
    }

    /**
     * get the perspective state
     * 
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * return true if the state is open, false otherwise
     * 
     * @return the open state
     */
    public boolean isOpen() {
        return state.equals(OPEN_STATE);
    }

    /**
     * return true if the state is close, false otherwise
     * 
     * @return the close state
     */
    public boolean isClose() {
        return state.equals(CLOSE_STATE);
    }

    public abstract void createViews();

    public abstract void createViewset();

}