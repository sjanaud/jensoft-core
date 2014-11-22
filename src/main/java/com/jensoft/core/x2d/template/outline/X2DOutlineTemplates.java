/**
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.template.outline;

import java.io.File;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

public class X2DOutlineTemplates {

    public X2DOutlineTemplates() {
    }

    
    public X2D jetTemplateOutline() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "outline-xmlns-nons-xsi-local.xml";

        x2d.registerX2DFile(new File(templatePath));

     

        String viewKey = x2d.getViewKey();
       

        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;

        try {
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    public static void main(String[] args) throws Exception {

        X2DOutlineTemplates jetSample = new X2DOutlineTemplates();

        try {
            jetSample.jetTemplateOutline();
        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }

}
