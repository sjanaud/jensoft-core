/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import javax.swing.JComponent;

/**
 * Dashboard
 */
public class DashboardDemo {

    private JComponent dashboard;

    public DashboardDemo() {
    }

    public DashboardDemo(JComponent dashboard) {
        super();
        this.dashboard = dashboard;
    }

    /**
     * @return the dashboard
     */
    public JComponent getDashboard() {
        return dashboard;
    }

    /**
     * @param dashboard
     *            the dashboard to set
     */
    public void setDashboard(JComponent dashboard) {
        this.dashboard = dashboard;
    }

}
