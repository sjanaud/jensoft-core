package com.jensoft.core.x2d.template.function.area;

import java.io.File;

import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;


public class X2DAreaTemplates {
    
    public static void main(String[] args) throws Exception {
        X2DAreaTemplates x2dAreas = new X2DAreaTemplates();
        try {
            x2dAreas.area();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public X2D area() throws X2DException {
        X2D x2d = new X2D();
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String templatePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "area-xmlns-nons-xsi-local.xml";
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
}
