/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.io.File;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.Spectral;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.PluginPlatform;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DToolkit;
import com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle;
import com.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.window.Window2D;

/**
 * <code>JavadocPortfolioDonut3D</code> doc files API
 * 
 * @author Sebastien Janaud
 */
public class JavadocPortfolioDonut3D {

    @Portfolio(name = "Donut3DRadialLabel")
    public static View2D getRadialLabelView() {

        View2D view = new View2D(0);
        Window2D window = new Window2D.Linear(-1, 1, -1, 1);
        window.setName("compatible donut3D window");
        view.registerWindow2D(window);

        Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
        window.registerPlugin(donut3DPlugin);

        RoundViewFill viewBackground = new RoundViewFill();
        Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
        viewBackground.setShader(sb);
        viewBackground.setOutlineStroke(new BasicStroke(2.5f));
        view.setBackgroundPainter(viewBackground);

        // DONUT
        // create donut
        Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 20, 60, 40,
                                                       60, 40);
        donut3DPlugin.addDonut(donut3d);

        // create sections
        Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1",
                                                            new Color(250, 250, 250), 45);
        Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2",
                                                            TangoPalette.BUTTER2, 5);
        Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3",
                                                            TangoPalette.CHAMELEON2, 30);
        Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4",
                                                            TangoPalette.SKYBLUE2, 20);
        // add section in donut
        Donut3DToolkit.pushSlices(donut3d, s1, s2, s3, s4);

        // LABELS
        float[] fractions = { 0f, 0.3f, 0.7f, 1f };
        Color[] c = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
                new Color(0, 0, 0, 150), new Color(0, 0, 0, 20) };

        // LABEL 1
        Donut3DRadialLabel label1 = Donut3DToolkit.createRadialLabel("Symbian",
                                                                     RosePalette.COALBLACK, InputFonts.getNeuropol(12),
                                                                     30, 20,
                                                                     Style.Both);
        label1.setLabelColor(ColorPalette.WHITE);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setShader(fractions, c);
        s1.addSliceLabel(label1);

        // LABEL 2
        Donut3DRadialLabel label2 = Donut3DToolkit.createRadialLabel("WiMo",
                                                                     RosePalette.COALBLACK, InputFonts.getNeuropol(12),
                                                                     30, 20,
                                                                     Style.Both);
        label2.setLabelColor(ColorPalette.WHITE);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setShader(fractions, c);
        s2.addSliceLabel(label2);

        // LABEL 3
        Donut3DRadialLabel label3 = Donut3DToolkit.createRadialLabel("iPhone",
                                                                     RosePalette.COALBLACK, InputFonts.getNeuropol(12),
                                                                     30, 20,
                                                                     Style.Both);
        label3.setLabelColor(ColorPalette.WHITE);
        label3.setOutlineColor(RosePalette.COBALT);
        label3.setShader(fractions, c);
        s3.addSliceLabel(label3);

        // LABEL 4
        Donut3DRadialLabel label4 = Donut3DToolkit.createRadialLabel("Android",
                                                                     RosePalette.COALBLACK, InputFonts.getNeuropol(12),
                                                                     30, 20,
                                                                     Style.Both);
        label4.setLabelColor(ColorPalette.WHITE);
        label4.setOutlineColor(RosePalette.EMERALD);
        label4.setShader(fractions, c);
        s4.addSliceLabel(label4);

        return view;
    }

    @Portfolio(name = "Donut3DBorderLabel")
    public static View2D getBorderLabelView() {

        View2D view = new View2D(0);
        Window2D window = new Window2D.Linear(-1, 1, -1, 1);
        window.setName("compatible donut3D window");
        view.registerWindow2D(window);

        Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
        window.registerPlugin(donut3DPlugin);

        RoundViewFill viewBackground = new RoundViewFill();
        Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
        viewBackground.setShader(sb);
        viewBackground.setOutlineStroke(new BasicStroke(2.5f));
        view.setBackgroundPainter(viewBackground);

        // DONUT
        // create donut
        Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 20, 60, 40, 80, 50);
        donut3DPlugin.addDonut(donut3d);

        // PAINT
        Donut3DDefaultPaint p = new Donut3DDefaultPaint(150);
        donut3d.setDonut3DPaint(p);
        // p.setAlphaTop(0.f);
        // p.setAlphaInner(0.6f);
        // p.setAlphaOuter(0.8f);
        // p.setAlphaFill(0.8f);

        // create sections
        Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1", new Color(250, 250, 250), 45);
        Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2", Spectral.SPECTRAL_RED, 5);
        Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3", TangoPalette.CHAMELEON2, 30);
        Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4", TangoPalette.SKYBLUE2, 20);
        // add section in donut
        Donut3DToolkit.pushSlices(donut3d, s1, s2, s3, s4);

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);

        // LABEL 1
        Donut3DBorderLabel label1 = Donut3DToolkit.createBorderLabel("Symbian", ColorPalette.WHITE,
                                                                     InputFonts.getNeuropol(12), 50);
        label1.setStyle(Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        label1.setLinkColor(RosePalette.REDWOOD);
        label1.setLinkExtends(30);
        label1.setLinkStyle(LinkStyle.Quad);
        s1.addSliceLabel(label1);

        // LABEL 2
        Donut3DBorderLabel label2 = Donut3DToolkit.createBorderLabel("WiMo", ColorPalette.WHITE,
                                                                     InputFonts.getNeuropol(12), 50);
        label2.setStyle(Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        label2.setLinkColor(RosePalette.LIME);
        label2.setLinkExtends(30);
        label2.setLinkStyle(LinkStyle.Quad);
        s2.addSliceLabel(label2);

        // LABEL 3
        Donut3DBorderLabel label3 = Donut3DToolkit.createBorderLabel("android", ColorPalette.WHITE,
                                                                     InputFonts.getNeuropol(12), 50);
        label3.setStyle(Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.COBALT);
        label3.setOutlineRound(20);
        label3.setLinkColor(RosePalette.COBALT);
        label3.setLinkExtends(30);
        label3.setLinkStyle(LinkStyle.Quad);
        s3.addSliceLabel(label3);

        Donut3DBorderLabel label4 = Donut3DToolkit.createBorderLabel("iPhone", ColorPalette.WHITE,
                                                                     InputFonts.getNeuropol(12), 50);
        label4.setStyle(Style.Both);
        label4.setOutlineStroke(s);
        label4.setOutlineColor(RosePalette.EMERALD);
        label4.setShader(fractions, colors);
        label4.setOutlineRound(20);
        label4.setLinkColor(RosePalette.EMERALD);
        label3.setLinkExtends(30);
        label4.setLinkStyle(LinkStyle.Quad);
        s4.addSliceLabel(label4);

        return view;
    }

    public void render() {
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', File.separatorChar);
        String docFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + packagePath
                + File.separator + "doc-files";
        PluginPlatform.createPortfolio(packageName, docFilePath, 400, 280);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JavadocPortfolioDonut3D docFilePortfolio = new JavadocPortfolioDonut3D();
        docFilePortfolio.render();
    }
}