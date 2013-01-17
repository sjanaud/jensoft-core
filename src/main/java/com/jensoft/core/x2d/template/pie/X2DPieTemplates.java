/*
 * This source file is part of JenSoft SW2D Chart Framework
 * For the latest info, see http://www.jensoft.org
 * Copyright (c) 2008-2012 JenSoft
 * Project Lead: S�bastien Janaud (sebastien.janaud@gmail.com)
 * Developers: S�bastien Janaud
 * Marc Antoine Tessier
 * This program is distributed under a dual-licensing scheme:
 * 1. The first license, which is the default one, state that this software
 * is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (LGPL) as published by the
 * Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 * 2. The second license, which is not free, apply only for licensee who got
 * a written agreement from JenSoft. The exact wording of this
 * license can be obtained from JenSoft. In essence this
 * JenSoft Unrestricted License state that the GNU Lesser General Public License
 * applies except that the software is distributed with no limitation or
 * requirements to publish or give back to the JenSoft project changes made
 * to the JenSoft source code. If you want create SDK or any Tool using JenSoft API
 * with commercial license without give back modification to JenSoft.
 * By default, the first type of license applies (the GNU LGPL), the JenSoft
 * Unrestricted License apply only for those who got a written agreement
 * from JenSoft.
 * Under both licenses, this program is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt .
 * For the JenSoft Unrestricted License contact JenSoft
 */
package com.jensoft.core.x2d.template.pie;

import java.io.File;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

/**
 * JETPie is a class that produce various pies images from JET templates
 * 
 * @author Sebastien Janaud
 */
public class X2DPieTemplates {

    public X2DPieTemplates() {
    }

    
    public X2D pieTemplate() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateBorderLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-label-border-xmlns-nons-xsi-local.xml";
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
    
    
    public X2D pieTemplateCompoundEffect() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-compound-effect-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateRadialLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-label-radial-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateBoundLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-label-bound-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplatePathLabel() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-label-path-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateEffectLinear() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-effect-linear-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateEffectRadial() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-effect-radial-xmlns-nons-xsi-local.xml";
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

    
    public X2D pieTemplateEffectReflection() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "pie-effect-reflection-xmlns-nons-xsi-local.xml";
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
        X2DPieTemplates x2dPies = new X2DPieTemplates();
        try {
            x2dPies.pieTemplate();
            x2dPies.pieTemplateBorderLabel();
            x2dPies.pieTemplateRadialLabel();
            x2dPies.pieTemplateBoundLabel();
            x2dPies.pieTemplatePathLabel();

            x2dPies.pieTemplateEffectLinear();
            x2dPies.pieTemplateEffectRadial();
            x2dPies.pieTemplateEffectReflection();
            x2dPies.pieTemplateCompoundEffect();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
