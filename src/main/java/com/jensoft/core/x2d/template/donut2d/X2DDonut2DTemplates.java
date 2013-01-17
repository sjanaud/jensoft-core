/**
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.template.donut2d;

import java.io.File;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

/**
 * JETPie is a class that produce various pies images from JET templates
 * 
 * @author Sebastien Janaud
 */
public class X2DDonut2DTemplates {

    public X2DDonut2DTemplates() {
    }

    
    public X2D donut2dTemplate() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
      
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "doc-files";
        try {
            x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return x2d;
    }

    
    public X2D donut2dTemplateBorderLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-label-border-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
    
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplateRadialLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-label-radial-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
       
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplatePathLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-label-path-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
       
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplateEffectReflection() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-effect-reflection-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
    
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplateEffectLinear() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-effect-linear-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
      
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplateFillDefault() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-fill-default-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
       
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    
    public X2D donut2dTemplateFillRadial() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "donut2d-fill-radial-xmlns-nons-xsi-local.xml";
        x2d.registerX2DFile(new File(templatePath));
        
        String viewKey = x2d.getViewKey();
        String emitPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath;
        try {
           x2d.getView2D().createViewEmitter().emitAsImageFile(emitPath, viewKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return x2d;
    }

    public static void main(String[] args) throws Exception {
        X2DDonut2DTemplates jetDonut3D = new X2DDonut2DTemplates();
        try {
            jetDonut3D.donut2dTemplate();
            jetDonut3D.donut2dTemplateBorderLabel();
            jetDonut3D.donut2dTemplateRadialLabel();
            jetDonut3D.donut2dTemplatePathLabel();
            jetDonut3D.donut2dTemplateEffectReflection();
            jetDonut3D.donut2dTemplateEffectLinear();

            jetDonut3D.donut2dTemplateFillDefault();
            jetDonut3D.donut2dTemplateFillRadial();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
