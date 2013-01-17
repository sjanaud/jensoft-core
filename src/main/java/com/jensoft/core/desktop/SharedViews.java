/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import java.util.Vector;

public class SharedViews {

    public SharedViews() {

    }

    private Vector<View> sharedViews = new Vector<View>();

    public int countViews() {
        return sharedViews.size();
    }

    public View getView(int index) {
        return sharedViews.get(index);
    }

    public void registerView(View view) {

        View v = getSharedView(view.getClass());
        if (v != null) {
            System.out.println("View class already exit : " + v.getClass()
                    + " remove instance.");
            sharedViews.remove(v);
        }
        sharedViews.add(view);
        // System.out.println("SHARE NEW VIEW INSTANCE : "+view.getClass());
    }

    public View getSharedView(Class cl) {

        for (int i = 0; i < sharedViews.size(); i++) {
            View v = sharedViews.get(i);
            if (v.getClass().getName().equals(cl.getName())) {
                return v;
            }
        }
        return null;
    }

}
