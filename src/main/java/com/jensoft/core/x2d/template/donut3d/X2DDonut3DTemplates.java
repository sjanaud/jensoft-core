/**
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.template.donut3d;

import java.io.File;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

/**
 * JETPie is a class that produce various pies images from JET templates
 * 
 * @author Sebastien Janaud
 */
public class X2DDonut3DTemplates {

    public X2DDonut3DTemplates() {
    }

    
    public X2D donut3dTemplate() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut3d-xmlns-nons-xsi-local.xml";
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

    
    public X2D donut3dTemplateBorderLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut3d-borderlabel-xmlns-nons-xsi-local.xml";
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

    
    public X2D donut3dTemplateRadialLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut3d-radiallabel-xmlns-nons-xsi-local.xml";
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

    
    public X2D donut3dTemplatePathLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut3d-pathlabel-xmlns-nons-xsi-local.xml";
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
        X2DDonut3DTemplates jetDonut3D = new X2DDonut3DTemplates();
        try {
            jetDonut3D.donut3dTemplate();
            jetDonut3D.donut3dTemplateBorderLabel();
            jetDonut3D.donut3dTemplateRadialLabel();
            jetDonut3D.donut3dTemplatePathLabel();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
