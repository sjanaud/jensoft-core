package org.jensoft.core.x2d.template.pie;

import java.io.File;

import org.jensoft.core.x2d.X2D;
import org.jensoft.core.x2d.X2DException;

/**
 * <code>X2DPieTemplates</code>
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
           x2d.getView().createViewEmitter().emitAsImageFile(emitPath, viewKey);
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
