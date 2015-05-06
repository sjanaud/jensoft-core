/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d;

import java.awt.Color;
import java.io.File;

import org.jensoft.core.palette.color.Spectral;
import org.jensoft.core.plugin.PluginPlatform;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.Portfolio;
import org.jensoft.core.view.View;

/**
 * <code>DocFilesPortfolioDonut3D</code>
 * 
 * @author Sebastien Janaud
 */
public class DocFilesPortfolioDonut3D {

    @Portfolio(name = "Donut3D_1")
    public static View getDonut3D_1() {

    	 View view = new View(0);
         Projection projection = new Projection.Linear(-1, 1, -1, 1);
         projection.setName("compatible donut3");
         view.registerProjection(projection);

         Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
         projection.registerPlugin(donut3DPlugin);
        
        
        Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 30, 60, 40, 60, 50);
        donut3DPlugin.addDonut(donut3d);
        // create slices
        Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1", new Color(250, 250, 250), 45);
        Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2", Spectral.SPECTRAL_RED, 5);
        Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3", Spectral.SPECTRAL_PURPLE1, 30);
        Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4", Spectral.SPECTRAL_GREEN, 20);
        // add slices in donut
        Donut3DToolkit.pushSlices(donut3d, s1, s2, s3, s4);

        return view;
    }

    @Portfolio(name = "Donut3D_2")
    public static View getDonut3D_2() {

    	 View view = new View(0);
         Projection proj = new Projection.Linear(-1, 1, -1, 1);
         proj.setName("compatible donut3D window");
         view.registerProjection(proj);

         Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
         proj.registerPlugin(donut3DPlugin);
        Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 30, 60, 40, 60, 50);
        donut3DPlugin.addDonut(donut3d);
        // create slices
        Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1", new Color(250, 250, 250), 45);
        Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2", Spectral.SPECTRAL_RED, 5);
        Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3", Spectral.SPECTRAL_BLUE2, 30);
        Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4", Spectral.SPECTRAL_GREEN, 20);
        // add slices in donut
        Donut3DToolkit.pushSlices(donut3d, s1, s2, s3, s4);

        s3.setDivergence(20);

        return view;
    }

    public void render() {
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String docFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "doc-files";

        PluginPlatform.createPortfolio(packageName, docFilePath, 200, 180);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        DocFilesPortfolioDonut3D docFilePortfolio = new DocFilesPortfolioDonut3D();
        docFilePortfolio.render();
    }
}
