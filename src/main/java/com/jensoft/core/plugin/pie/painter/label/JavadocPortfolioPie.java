/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.io.File;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.palette.color.RosePalette;
import com.jensoft.core.palette.color.TangoPalette;
import com.jensoft.core.plugin.PluginPlatform;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.animator.PieDivergenceAnimator;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel.LinkStyle;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.View;
import com.jensoft.core.view.background.ViewDefaultBackground;

/**
 * <code>JavadocPortfolioPie</code>
 * 
 * @author Sebastien Janaud
 */
public class JavadocPortfolioPie {

    @Portfolio(name = "PieRadialLabel", width = 800, height = 800)
    public static View getPieRadialLabel() {

        View view = new View(0);
        Projection proj = new Projection.Linear(-1, 1, -1, 1);
        proj.setName("compatible donut3D");
        view.registerProjection(proj);

        PiePlugin piePlugin = new PiePlugin();
        proj.registerPlugin(piePlugin);
        Pie pie = PieToolkit.createPie("pie", 60);
        pie.setStartAngleDegree(20);
        PieLinearEffect fx1 = new PieLinearEffect(120);
        fx1.setOffsetRadius(5);
        pie.setPieEffect(fx1);

        PieSlice s1 = PieToolkit.createSlice("gray", new Color(240, 240, 240,
                                                               240), 45, 0);
        PieSlice s2 = PieToolkit.createSlice("gray",
                                             ColorPalette.alpha(TangoPalette.BUTTER2, 240), 5, 0);
        PieSlice s3 = PieToolkit.createSlice("chameleon",
                                             ColorPalette.alpha(TangoPalette.CHAMELEON2, 240), 30, 0);
        PieSlice s4 = PieToolkit.createSlice("blue",
                                             ColorPalette.alpha(TangoPalette.SKYBLUE2, 240), 20, 0);
        PieToolkit.pushSlices(pie, s1, s2, s3, s4);
        piePlugin.addPie(pie);

        pie.addPieAnimator(new PieDivergenceAnimator());

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);
        pie.setPassiveLabelAtMinPercent(0);

        Font f = new Font("Dialog", Font.PLAIN, 12);
        // LABEL 1
        PieRadialLabel label1 = PieToolkit.createRadialLabel("Symbian",
                                                             ColorPalette.WHITE, f, 20);
        label1.setStyle(Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        s1.addSliceLabel(label1);

        // LABEL 2
        PieRadialLabel label2 = PieToolkit.createRadialLabel("WiMo",
                                                             ColorPalette.WHITE, f, 20);
        label2.setStyle(Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        s2.addSliceLabel(label2);

        // LABEL 3
        PieRadialLabel label3 = PieToolkit.createRadialLabel("iPhone",
                                                             ColorPalette.WHITE, f, 20);
        label3.setStyle(Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.EMERALD);
        label3.setOutlineRound(20);
        s3.addSliceLabel(label3);

        PieRadialLabel label4 = PieToolkit.createRadialLabel("Android",
                                                             ColorPalette.WHITE, f, 20);
        label4.setStyle(Style.Both);
        label4.setOutlineStroke(s);
        label4.setOutlineColor(RosePalette.COBALT);
        label4.setShader(fractions, colors);
        label4.setOutlineRound(20);
        s4.addSliceLabel(label4);

        return view;
    }

    @Portfolio(name = "PiePathLabel", width = 800, height = 800)
    public static View createView() {

        View view = new View(0);
        Projection proj = new Projection.Linear(-1, 1, -1, 1);
        proj.setName("compatible donut3D window");
        view.registerProjection(proj);

        PiePlugin piePlugin = new PiePlugin();
        proj.registerPlugin(piePlugin);

        ViewDefaultBackground viewBackground = new ViewDefaultBackground();
        Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
        viewBackground.setShader(sb);
        viewBackground.setOutlineStroke(new BasicStroke(2.5f));
        view.setBackgroundPainter(viewBackground);

        Pie pie = PieToolkit.createPie("pie", 90);
        pie.setPieEffect(new PieLinearEffect());
        PieSlice s1 = PieToolkit.createSlice("gray", new Color(240, 240, 240,
                                                               240), 45, 0);
        PieSlice s2 = PieToolkit.createSlice("gray",
                                             ColorPalette.alpha(TangoPalette.BUTTER2, 240), 5, 0);
        PieSlice s3 = PieToolkit.createSlice("chameleon",
                                             ColorPalette.alpha(TangoPalette.CHAMELEON2, 240), 30, 0);
        PieSlice s4 = PieToolkit.createSlice("blue",
                                             ColorPalette.alpha(TangoPalette.SKYBLUE2, 240), 20, 0);
        PieToolkit.pushSlices(pie, s1, s2, s3, s4);
        piePlugin.addPie(pie);

        pie.addPieAnimator(new PieDivergenceAnimator());

        PiePathLabel ppl = new PiePathLabel(TextPosition.Right, "My name is S�bastien");
        ppl.setPathSide(PathSide.Below);
        Font f = new Font("Dialog", Font.PLAIN, 12);
        ppl.setLabelFont(f);
        ppl.setLabelColor(RosePalette.MANDARIN);
        ppl.setDivergence(2);
        s1.addSliceLabel(ppl);

        PiePathLabel ppl12 = PieToolkit.createPathLabel("JenSoft API",
                                                        RosePalette.INDIGO,
                                                        f,
                                                        TextPosition.Middle);
        s1.addSliceLabel(ppl12);

        // PiePathLabel ppl2 = new PiePathLabel(TextPosition.Right, "J",
        // TangoPalette.BUTTER2.darker());
        // float[] fractions2 = { 0f, 0.15f, 0.5f, 0.85f, 1f };
        // Color[] colors2 = { Color.ORANGE, Color.GRAY, Color.DARK_GRAY,
        // Color.GRAY, Color.WHITE };
        // ppl2.setLabelFont(InputFonts.getPTFNordic(16));
        // ppl2.setShader(fractions2, colors2);
        // ppl2.setPathSide(PathSide.Below);
        //
        // s2.addSliceLabel(ppl2);

        PiePathLabel ppl3 = new PiePathLabel(TextPosition.Left, "Pie Path Label",
                                             TangoPalette.CHAMELEON2.darker());
        float[] fractions = { 0f, 1f };
        Color[] colors = { Color.BLACK, RosePalette.AMETHYST };
        ppl3.setLabelFont(f);
        ppl3.setTextShader(fractions, colors);
        ppl3.setPathSide(PathSide.Above);
        ppl3.setDivergence(2);

        s3.addSliceLabel(ppl3);

        PiePathLabel ppl4 = new PiePathLabel(TextPosition.Right, "JenSoft");
        ppl4.setPathSide(PathSide.Below);
        ppl4.setLabelFont(f);
        ppl4.setDivergence(0);

        // s4.addSliceLabel(ppl4);

        return view;
    }

    @Portfolio(name = "PieBoundLabel", width = 800, height = 800)
    public static View getPieBoundLabel() {

        View view = new View(0);
        Projection proj = new Projection.Linear(-1, 1, -1, 1);
        proj.setName("compatible donut3D window");
        view.registerProjection(proj);

        PiePlugin piePlugin = new PiePlugin();
        proj.registerPlugin(piePlugin);
        ViewDefaultBackground viewBackground = new ViewDefaultBackground();
        Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
        viewBackground.setShader(sb);
        viewBackground.setOutlineStroke(new BasicStroke(2.5f));
        view.setBackgroundPainter(viewBackground);

        Pie pie = PieToolkit.createPie("pie", 80);
        pie.setPieEffect(new PieLinearEffect());
        PieSlice s1 = PieToolkit.createSlice("gray", new Color(240, 240, 240,
                                                               240), 45, 0);
        PieSlice s2 = PieToolkit.createSlice("gray",
                                             ColorPalette.alpha(TangoPalette.BUTTER2, 240), 5, 0);
        PieSlice s3 = PieToolkit.createSlice("chameleon",
                                             ColorPalette.alpha(TangoPalette.CHAMELEON2, 240), 30, 0);
        PieSlice s4 = PieToolkit.createSlice("blue",
                                             ColorPalette.alpha(TangoPalette.SKYBLUE2, 240), 20, 0);
        PieToolkit.pushSlices(pie, s1, s2, s3, s4);
        piePlugin.addPie(pie);

        pie.addPieAnimator(new PieDivergenceAnimator());

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);
        pie.setPassiveLabelAtMinPercent(0);

        Font f = new Font("Dialog", Font.PLAIN, 10);
        // LABEL 1
        PieBoundLabel label1 = PieToolkit.createBoundLabel("Symbian",
                                                           ColorPalette.WHITE, f);
        label1.setStyle(Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        s1.addSliceLabel(label1);

        PieBoundLabel label2 = PieToolkit.createBoundLabel("Wimo",
                                                           ColorPalette.WHITE, f);
        label2.setStyle(Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        s2.addSliceLabel(label2);

        PieBoundLabel label3 = PieToolkit.createBoundLabel("IPhone",
                                                           ColorPalette.WHITE, f);
        label3.setStyle(Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.EMERALD);
        label3.setOutlineRound(20);
        s3.addSliceLabel(label3);

        PieBoundLabel label4 = PieToolkit.createBoundLabel("Android",
                                                           ColorPalette.WHITE, f);
        label4.setStyle(Style.Both);
        label4.setOutlineStroke(s);
        label4.setShader(fractions, colors);
        label4.setOutlineColor(RosePalette.AEGEANBLUE);
        label4.setOutlineRound(20);
        s4.addSliceLabel(label4);

        return view;
    }

    @Portfolio(name = "PieBorderLabel", width = 800, height = 800)
    public static View getPieBorderLabel() {

        View view = new View(0);
        Projection proj = new Projection.Linear(-1, 1, -1, 1);
        proj.setName("compatible donut3D window");
        view.registerProjection(proj);

        PiePlugin piePlugin = new PiePlugin();
        proj.registerPlugin(piePlugin);

        ViewDefaultBackground viewBackground = new ViewDefaultBackground();
        Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
        viewBackground.setShader(sb);
        viewBackground.setOutlineStroke(new BasicStroke(2.5f));
        view.setBackgroundPainter(viewBackground);

        Pie pie = PieToolkit.createPie("pie", 60);
        pie.setPieEffect(new PieLinearEffect());
        PieSlice s1 = PieToolkit.createSlice("s1", new Color(240, 240, 240, 240), 45, 0);
        PieSlice s2 = PieToolkit.createSlice("s2", ColorPalette.alpha(TangoPalette.BUTTER2, 240), 5, 0);
        PieSlice s3 = PieToolkit.createSlice("s3", ColorPalette.alpha(TangoPalette.CHAMELEON2, 240), 30, 0);
        PieSlice s4 = PieToolkit.createSlice("s4", ColorPalette.alpha(TangoPalette.SKYBLUE2, 240), 20, 0);
        PieToolkit.pushSlices(pie, s1, s2, s3, s4);
        piePlugin.addPie(pie);

        pie.setPassiveLabelAtMinPercent(18);

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);
        pie.setPassiveLabelAtMinPercent(0);

        Font f = new Font("Dialog", Font.PLAIN, 10);
        // LABEL 1
        PieBorderLabel label1 = PieToolkit.createBorderLabel("View",
                                                             ColorPalette.WHITE, f, 30);
        label1.setStyle(Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        label1.setLinkColor(RosePalette.REDWOOD);
        label1.setLinkStyle(LinkStyle.Quad);
        label1.setLinkExtends(40);
        label1.setMargin(50);

        s1.addSliceLabel(label1);

        // LABEL 2
        PieBorderLabel label2 = PieToolkit.createBorderLabel("Window",
                                                             ColorPalette.WHITE, f, 30);
        label2.setStyle(Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        label2.setLinkColor(RosePalette.LIME);
        label2.setLinkExtends(40);
        label2.setLinkStyle(LinkStyle.Quad);
        label2.setMargin(50);
        s2.addSliceLabel(label2);

        // LABEL 3
        PieBorderLabel label3 = PieToolkit.createBorderLabel("plugin",
                                                             ColorPalette.WHITE, f, 30);
        label3.setStyle(Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.EMERALD);
        label3.setOutlineRound(20);
        label3.setLinkColor(RosePalette.EMERALD);
        label3.setLinkStyle(LinkStyle.Quad);
        label3.setLinkExtends(40);
        label3.setMargin(50);
        s3.addSliceLabel(label3);

        PieBorderLabel label4 = PieToolkit.createBorderLabel("widget",
                                                             ColorPalette.WHITE, f, 30);
        label4.setStyle(Style.Both);
        label4.setOutlineStroke(s);
        label4.setOutlineColor(RosePalette.COBALT);
        label4.setShader(fractions, colors);
        label4.setOutlineRound(20);
        label4.setLinkColor(RosePalette.COBALT);
        label4.setLinkStyle(LinkStyle.Quad);
        label4.setLinkExtends(40);
        label4.setMargin(50);
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
        JavadocPortfolioPie docFilePortfolio = new JavadocPortfolioPie();
        docFilePortfolio.render();
    }
}
