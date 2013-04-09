/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.io.File;
import java.util.Random;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.Spectral;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.PluginPlatform;
import com.jensoft.core.plugin.bubble.Bubble;
import com.jensoft.core.plugin.bubble.BubblePlugin;
import com.jensoft.core.plugin.bubble.painter.effect.BubbleEffect1;
import com.jensoft.core.plugin.bubble.painter.effect.BubbleEffect2;
import com.jensoft.core.plugin.bubble.painter.effect.BubbleEffect3;
import com.jensoft.core.plugin.bubble.painter.effect.BubbleEffect4;
import com.jensoft.core.plugin.bubble.painter.fill.BubbleDefaultFill;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;
import com.jensoft.core.plugin.donut2d.Donut2DPlugin;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DToolkit;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import com.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;
import com.jensoft.core.plugin.function.FunctionPlugin;
import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunctionPlugin;
import com.jensoft.core.plugin.function.FunctionPlugin.CurveFunctionPlugin;
import com.jensoft.core.plugin.function.FunctionPlugin.ScatterFunctionPlugin;
import com.jensoft.core.plugin.function.area.AreaFunction;
import com.jensoft.core.plugin.function.area.painter.draw.AreaDefaultDraw;
import com.jensoft.core.plugin.function.area.painter.fill.AreaGradientFill;
import com.jensoft.core.plugin.function.curve.CurveFunction;
import com.jensoft.core.plugin.function.curve.CurveToolkit;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.scatter.ScatterFunction;
import com.jensoft.core.plugin.function.scatter.morphe.EllipseMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.QInverseMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.QStarMorphe;
import com.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import com.jensoft.core.plugin.function.source.AffineSourceFunction;
import com.jensoft.core.plugin.function.source.SplineSourceFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.SourceFunctionToolkit;
import com.jensoft.core.plugin.gradient.GradientPlugin;
import com.jensoft.core.plugin.gradient.Night;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.plugin.grid.GridPlugin;
import com.jensoft.core.plugin.legend.Legend;
import com.jensoft.core.plugin.legend.LegendConstraints;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.LegendPlugin;
import com.jensoft.core.plugin.legend.LegendToolkit;
import com.jensoft.core.plugin.legend.painter.fill.LegendGradientFill;
import com.jensoft.core.plugin.marker.MarkerPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Multiplier3Metrics;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModelRangeCollections;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.animator.PieDivergenceAnimator;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel.LinkStyle;
import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.Ray.RayNature;
import com.jensoft.core.plugin.ray.Ray.ThicknessType;
import com.jensoft.core.plugin.ray.RayToolkit;
import com.jensoft.core.plugin.ray.RayView;
import com.jensoft.core.plugin.ray.painter.fill.RayDefaultFill;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.painter.StripePalette;
import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.BarSymbol.MorpheStyle;
import com.jensoft.core.plugin.symbol.BarSymbolGroup;
import com.jensoft.core.plugin.symbol.BarSymbolLayer;
import com.jensoft.core.plugin.symbol.Stack;
import com.jensoft.core.plugin.symbol.StackedBarSymbol;
import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.plugin.symbol.SymbolPlugin;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.SymbolToolkit;
import com.jensoft.core.plugin.symbol.painter.draw.AbstractBarDraw;
import com.jensoft.core.plugin.symbol.painter.draw.BarDefaultDraw;
import com.jensoft.core.plugin.symbol.painter.effect.BarEffect3;
import com.jensoft.core.plugin.symbol.painter.fill.BarFill2;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.HorizontalAlignment;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.VerticalAlignment;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import com.jensoft.core.plugin.zoom.objectif.ObjectifX;
import com.jensoft.core.plugin.zoom.objectif.ObjectifY;
import com.jensoft.core.plugin.zoom.objectif.ZoomObjectifPlugin;
import com.jensoft.core.plugin.zoom.percent.ZoomPercentPlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

public class ExamplePortfolioViews {

    public static void main(String[] args) {
        String outputDir = System.getProperty("user.home") + File.separator + ".jensoft" + File.separator + "portfolio"
                + File.separator + "example-portfolio";
        PluginPlatform.createPortfolio(ExamplePortfolioViews.class.getPackage().getName(), outputDir);
    }

    @Portfolio(name = "pieBorderLabel1")
    public static View2D getPieBorderLabelView() {      
        
        View2D view = new View2D(0);
        Window2D window = new Window2D.Linear(-1, 1, -1, 1);
        window.setName("compatible donut3D window");
        view.registerWindow2D(window);

        PiePlugin piePlugin = new PiePlugin();
        window.registerPlugin(piePlugin);
        
        Pie pie = PieToolkit.createPie("pie", 30);
        pie.setStartAngleDegree(-20);
        PieLinearEffect fx1 = new PieLinearEffect(300);
        fx1.setOffsetRadius(4);
        pie.setPieEffect(fx1);
        PieSlice s1 = PieToolkit.createSlice("gray", new Color(240, 240, 240,
                                                               240), 45, 0);
        PieSlice s2 = PieToolkit.createSlice("gray",
                                             ColorPalette.alpha(TangoPalette.BUTTER2, 255), 5, 0);
        PieSlice s3 = PieToolkit.createSlice("chameleon",
                                             ColorPalette.alpha(TangoPalette.CHAMELEON2, 255), 30, 0);
        PieSlice s4 = PieToolkit.createSlice("blue",
                                             ColorPalette.alpha(TangoPalette.SKYBLUE2, 255), 20, 0);
        PieToolkit.pushSlices(pie, s1, s2, s3, s4);
        piePlugin.addPie(pie);

        // pieView.getWindow2D().registerPlugin(new OutlinePlugin());
        pie.addPieAnimator(new PieDivergenceAnimator());

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);
        pie.setPassiveLabelAtMinPercent(0);

        Font f = InputFonts.getNoMove(10);
        // LABEL 1
        PieBorderLabel label1 = PieToolkit.createBorderLabel("View",
                                                             ColorPalette.WHITE, f, 30);
        label1.setStyle(Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        label1.setLinkColor(Color.BLACK);
        label1.setLinkStyle(LinkStyle.Quad);
        label1.setLinkExtends(20);

        s1.addSliceLabel(label1);

        // LABEL 2
        PieBorderLabel label2 = PieToolkit.createBorderLabel("Window",
                                                             ColorPalette.WHITE, f, 30);
        label2.setStyle(Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        label2.setLinkColor(Color.BLACK);
        label2.setLinkExtends(20);
        label2.setLinkStyle(LinkStyle.Quad);
        s2.addSliceLabel(label2);

        // LABEL 3
        PieBorderLabel label3 = PieToolkit.createBorderLabel("plugin",
                                                             ColorPalette.WHITE, f, 30);
        label3.setStyle(Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.COBALT);
        label3.setOutlineRound(20);
        label3.setLinkColor(Color.BLACK);
        label3.setLinkStyle(LinkStyle.Quad);
        label3.setLinkExtends(20);
        s3.addSliceLabel(label3);

        PieBorderLabel label4 = PieToolkit.createBorderLabel("widget",
                                                             ColorPalette.WHITE, f, 30);
        label4.setStyle(Style.Both);
        label4.setOutlineStroke(s);
        label4.setOutlineColor(RosePalette.EMERALD);
        label4.setShader(fractions, colors);
        label4.setOutlineRound(20);
        label4.setLinkColor(Color.BLACK);
        label4.setLinkStyle(LinkStyle.Quad);
        label4.setLinkExtends(20);
        s4.addSliceLabel(label4);

        return view;
    }

    @Portfolio(name = "pie3DBorderLabel1")
    public static View2D getPie3DLabelBorderView1() {
        View2D view = new View2D(0);
        Window2D window = new Window2D.Linear(-1, 1, -1, 1);
        window.setName("compatible donut3D window");
        view.registerWindow2D(window);

        Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
        window.registerPlugin(donut3DPlugin);

        // DONUT
        // create donut
        Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 16, 40, 30,
                                                       80, 60);
        donut3DPlugin.addDonut(donut3d);
        Font f = InputFonts.getNoMove(10);

        // PAINT
        Donut3DDefaultPaint p = new Donut3DDefaultPaint(150);
        donut3d.setDonut3DPaint(p);
        // p.setAlphaTop(0.f);
        // p.setAlphaInner(0.6f);
        // p.setAlphaOuter(0.8f);
        // p.setAlphaFill(0.8f);

        // create sections
        Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1",
                                                            new Color(250, 250, 250), 45);
        Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2",
                                                            Spectral.SPECTRAL_RED, 5);
        Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3",
                                                            Spectral.SPECTRAL_BLUE2, 30);
        Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4",
                                                            Spectral.SPECTRAL_PURPLE1, 20);
        // add section in donut
        Donut3DToolkit.pushSlices(donut3d, s1, s2, s3, s4);

        // //LABELS
        // float[] fractions = {0f,0.3f,0.7f,1f};
        // Color[] c = {new Color(0,0,0,20),new Color(0,0,0,150),new
        // Color(0,0,0,150),new Color(0,0,0,20)};
        //
        // //LABEL 1
        // Donut3DBorderLabel label1 =
        // Donut3DToolkit.createBorderLabel("Symbian", RosePalette.COALBLACK,
        // InputFonts.getNeuropol(12),20,Style.Both);
        // label1.setLinkColor(RosePalette.LEMONPEEL);
        // //label1.setLinkExtends(20);
        // label1.setLabelColor(ColorPalette.WHITE);
        // label1.setOutlineColor(Color.BLACK);
        // label1.setShader(fractions,c);
        // s1.setSectionLabel(label1);
        //
        //
        //
        // //LABEL 2
        // Donut3DBorderLabel label2 = Donut3DToolkit.createBorderLabel("WiMo",
        // RosePalette.COALBLACK, InputFonts.getNeuropol(12),20,Style.Both);
        // label2.setLinkColor(RosePalette.LEMONPEEL);
        // label2.setLinkExtends(30);
        // label2.setLabelColor(ColorPalette.WHITE);
        // label2.setOutlineColor(Color.BLACK);
        // label2.setShader(fractions,c);
        // s2.setSectionLabel(label2);
        //
        // //LABEL 3
        // Donut3DBorderLabel label3 =
        // Donut3DToolkit.createBorderLabel("iPhone", RosePalette.COALBLACK,
        // InputFonts.getNeuropol(12),20,Style.Both);
        // label3.setLinkColor(RosePalette.LEMONPEEL);
        // label3.setLinkExtends(30);
        // label3.setLabelColor(ColorPalette.WHITE);
        // label3.setOutlineColor(Color.BLACK);
        // label3.setShader(fractions,c);
        // s3.setSectionLabel(label3);
        //
        // //LABEL 4
        // Donut3DBorderLabel label4 =
        // Donut3DToolkit.createBorderLabel("Android", RosePalette.COALBLACK,
        // InputFonts.getNeuropol(12),20,Style.Both);
        // label4.setLinkColor(RosePalette.LEMONPEEL);
        // label4.setLinkExtends(30);
        // label4.setLabelColor(ColorPalette.WHITE);
        // label4.setOutlineColor(Color.BLACK);
        // label4.setShader(fractions,c);
        // s4.setSectionLabel(label4);

        // LABELS
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 255) };
        Stroke s = new BasicStroke(2);

        // LABEL 1
        Donut3DBorderLabel label1 = Donut3DToolkit.createBorderLabel("Symbian",
                                                                     ColorPalette.WHITE, f, 20, 30);
        label1.setStyle(com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style.Both);
        label1.setOutlineStroke(s);
        label1.setShader(fractions, colors);
        label1.setOutlineColor(RosePalette.REDWOOD);
        label1.setOutlineRound(20);
        label1.setLinkColor(Color.BLACK);
        label1.setLinkExtends(20);
        label1.setLinkStyle(com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle.Quad);
        s1.addSliceLabel(label1);

        // LABEL 2
        Donut3DBorderLabel label2 = Donut3DToolkit.createBorderLabel("WiMo",
                                                                     ColorPalette.WHITE, f, 20, 30);
        label2.setStyle(com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style.Both);
        label2.setOutlineStroke(s);
        label2.setShader(fractions, colors);
        label2.setOutlineColor(RosePalette.LIME);
        label2.setOutlineRound(20);
        label2.setLinkColor(Color.BLACK);
        label2.setLinkExtends(20);
        label2.setLinkStyle(com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle.Quad);
        s2.addSliceLabel(label2);

        // LABEL 3
        Donut3DBorderLabel label3 = Donut3DToolkit.createBorderLabel("android",
                                                                     ColorPalette.WHITE, f, 20, 30);
        label3.setStyle(com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style.Both);
        label3.setOutlineStroke(s);
        label3.setShader(fractions, colors);
        label3.setOutlineColor(RosePalette.COBALT);
        label3.setOutlineRound(20);
        label3.setLinkColor(Color.BLACK);
        label3.setLinkExtends(20);
        label3.setLinkStyle(com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle.Quad);
        s3.addSliceLabel(label3);

        Donut3DBorderLabel label4 = Donut3DToolkit.createBorderLabel("iPhone",
                                                                     ColorPalette.WHITE, f, 20, 30);
        label4.setStyle(com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style.Both);
        label4.setOutlineStroke(s);
        label4.setOutlineColor(RosePalette.EMERALD);
        label4.setShader(fractions, colors);
        label4.setOutlineRound(20);
        label4.setLinkColor(Color.BLACK);
        label3.setLinkExtends(20);
        label4.setLinkStyle(com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel.LinkStyle.Quad);
        s4.addSliceLabel(label4);

        // OUTLINE
        // donutView.registerPlugin(new OutlinePlugin());

        // LEGEND
        Legend legend = new Legend("Slice Border Label");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE, FilPalette.GREEN5));
        legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        LegendPlugin legendPlg = new LegendPlugin();
        legendPlg.addLegend(legend);
        // donutView.registerPlugin(legendPlg);

        return view;
    }

    @Portfolio(name = "stackedHorizontalBar1")
    public static View2D getStackeHorizontalBar() {

        View2D view = new View2D(1);
        view.setPartBackground(Color.WHITE, WindowPart.getAll());

        Color orange = Spectral.SPECTRAL_BLUE2;
        Color green = Spectral.SPECTRAL_PURPLE1;
        Color blue = Spectral.SPECTRAL_GREEN;
        Color pink = Spectral.SPECTRAL_YELLOW;
        Stroke s = new BasicStroke(0.7f);
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] c = { new Color(0, 0, 0, 200), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 200) };
        Shader labelShader = new Shader(fractions, c);
        AbstractBarDraw barDraw = new BarDefaultDraw(Color.DARK_GRAY, s);
        Font f = InputFonts.getElements(10);
        Font f2 = InputFonts.getNoMove(10);
        // Font fontGroup = InputFonts.getElements(10);
        Font fontGroup = InputFonts.getNoMove(10);
        // bar symbol 1 for group 1
        StackedBarSymbol b1g1 = new StackedBarSymbol();
        b1g1.setThemeColor(new Color(255, 255, 255));
        b1g1.setAscentValue(60);

        // bar symbol 1 label
        BarSymbolRelativeLabel b1g1RelativeLabel = new BarSymbolRelativeLabel(
                                                                              VerticalAlignment.Middle,
                                                                              HorizontalAlignment.WestRight);
        b1g1RelativeLabel.setText("Symbol 1");
        b1g1RelativeLabel.setTextColor(Color.WHITE);
        b1g1RelativeLabel.setFont(f);

        b1g1.setBarLabel(b1g1RelativeLabel);

        b1g1.addStack("orange", orange, 12);
        b1g1.addStack("green", green, 20);
        b1g1.addStack("blue", blue, 40);
        b1g1.addStack("pink", pink, 5);

        StackedBarSymbol b2g1 = new StackedBarSymbol();
        b2g1.setThemeColor(new Color(255, 255, 255));
        b2g1.setAscentValue(80);

        BarSymbolRelativeLabel b2g1RelativeLabel = new BarSymbolRelativeLabel(
                                                                              VerticalAlignment.Middle,
                                                                              HorizontalAlignment.WestRight);
        b2g1RelativeLabel.setText("Symbol 2");
        b2g1RelativeLabel.setTextColor(Color.WHITE);
        b2g1RelativeLabel.setFont(f);

        b2g1.setBarLabel(b2g1RelativeLabel);

        b2g1.addStack("orange", orange, 36);
        b2g1.addStack("green", green, 17);
        b2g1.addStack("blue", blue, 8);
        b2g1.addStack("pink", pink, 21);

        BarSymbolRelativeLabel groupRelativeLabel = new BarSymbolRelativeLabel(
                                                                               VerticalAlignment.Middle,
                                                                               HorizontalAlignment.EastRight);
        groupRelativeLabel.setText("Group 1");
        groupRelativeLabel.setDrawColor(Spectral.SPECTRAL_GREEN);
        groupRelativeLabel.setTextColor(Color.WHITE);
        groupRelativeLabel.setOutlineRound(10);
        groupRelativeLabel.setOutlineStroke(new BasicStroke(2f));
        // groupRelativeLabel.setOffsetX(20);
        // groupRelativeLabel.setTextPaddingX(6);
        groupRelativeLabel.setShader(labelShader);
        // b2g1.setBarLabel(rl3);
        groupRelativeLabel.setFont(fontGroup);

        BarSymbolGroup group1 = new BarSymbolGroup();

        group1.addSymbol(b1g1);
        group1.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group1.addSymbol(b2g1);

        group1.setSymbol("Group 1");
        group1.setName("group1");
        group1.setBase(-20);
        group1.setThickness(12);
        group1.setRound(6);
        group1.setMorpheStyle(MorpheStyle.Round);

        group1.setBarDraw(barDraw);
        group1.setBarFill(new BarFill2());
        // group1.setBarEffect(new BarEffect2());

        group1.setBarLabel(groupRelativeLabel);

        b1g1.setName("b1");
        b1g1.setSymbol("bar 1");
        b2g1.setName("b2");
        b2g1.setSymbol("bar 2");

        // Group2
        StackedBarSymbol b1g2 = new StackedBarSymbol();
        b1g2.setThemeColor(new Color(255, 255, 255));
        b1g2.setAscentValue(73);

        Stack s1b1g2 = SymbolToolkit.createStack("orange", orange, 12);
        Stack s2b1g2 = SymbolToolkit.createStack("green", green, 28);
        Stack s3b1g2 = SymbolToolkit.createStack("blue", blue, 13);
        Stack s4b1g2 = SymbolToolkit.createStack("pink", pink, 9);

        BarSymbolRelativeLabel s1b1g2Label = new BarSymbolRelativeLabel(
                                                                        VerticalAlignment.Middle,
                                                                        HorizontalAlignment.Middle);
        s1b1g2Label.setText("sk1");
        s1b1g2Label.setTextColor(Color.BLACK);
        s1b1g2Label.setFont(f2);
        s1b1g2.setBarLabel(s1b1g2Label);

        BarSymbolRelativeLabel s2b1g2Label = new BarSymbolRelativeLabel(
                                                                        VerticalAlignment.Middle,
                                                                        HorizontalAlignment.Middle);
        s2b1g2Label.setText("sk2");
        s2b1g2Label.setTextColor(Color.BLACK);
        s2b1g2Label.setFont(f2);

        s2b1g2.setBarLabel(s2b1g2Label);

        SymbolToolkit.pushStacks(b1g2, s1b1g2, s2b1g2, s3b1g2, s4b1g2);

        StackedBarSymbol b2g2 = new StackedBarSymbol();
        b2g2.setThemeColor(new Color(255, 255, 255));
        b2g2.setAscentValue(38);
        b2g2.addStack("orange", orange, 7);
        b2g2.addStack("green", green, 15);
        b2g2.addStack("blue", blue, 23);
        b2g2.addStack("pink", pink, 16);

        BarSymbolGroup group2 = new BarSymbolGroup();
        group2.addSymbol(b1g2);
        group2.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group2.addSymbol(b2g2);

        BarSymbolRelativeLabel group2RelativeLabel = new BarSymbolRelativeLabel(
                                                                                VerticalAlignment.Middle,
                                                                                HorizontalAlignment.WestLeft);
        group2RelativeLabel.setText("Group 2");
        group2RelativeLabel.setDrawColor(Spectral.SPECTRAL_BLUE1);
        group2RelativeLabel.setTextColor(Color.WHITE);
        group2RelativeLabel.setOutlineRound(10);
        group2RelativeLabel.setOutlineStroke(new BasicStroke(2));
        // groupRelativeLabel.setOffsetX(20);
        // groupRelativeLabel.setTextPaddingX(6);
        group2RelativeLabel.setShader(labelShader);
        // b2g1.setBarLabel(rl3);
        group2RelativeLabel.setFont(fontGroup);

        group2.setBarLabel(group2RelativeLabel);

        group2.setSymbol("Group 2");
        group2.setName("group2");
        group2.setBase(30);
        group2.setThickness(12);
        group2.setRound(6);
        group2.setMorpheStyle(MorpheStyle.Round);
        group2.setBarDraw(barDraw);
        group2.setBarFill(new BarFill2());
        // group2.setBarEffect(new BarEffect1());

        b1g2.setName("b3");
        b1g2.setSymbol("bar 3");
        b2g2.setName("b4");
        b2g2.setSymbol("bar 4");

        // Group3
        StackedBarSymbol b1g3 = new StackedBarSymbol();
        b1g3.setThemeColor(new Color(255, 255, 255));
        b1g3.setAscentValue(53);
        b1g3.addStack("orange", orange, 39);
        b1g3.addStack("green", green, 6);
        b1g3.addStack("blue", blue, 17);
        b1g3.addStack("pink", pink, 10);

        StackedBarSymbol b2g3 = new StackedBarSymbol();
        b2g3.setThemeColor(new Color(255, 255, 255));
        b2g3.setAscentValue(22);
        b2g3.addStack("orange", orange, 6);
        b2g3.addStack("green", green, 45);
        b2g3.addStack("blue", blue, 7);
        b2g3.addStack("pink", pink, 13);

        BarSymbolGroup group3 = new BarSymbolGroup();
        group3.addSymbol(b1g3);
        group3.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group3.addSymbol(b2g3);

        group3.setSymbol("Group 3");
        group3.setName("group 3");
        group3.setBase(0);
        group3.setThickness(12);
        group3.setRound(6);
        group3.setMorpheStyle(MorpheStyle.Round);
        group3.setBarDraw(barDraw);
        group3.setBarFill(new BarFill2());
        // group3.setBarEffect(new BarEffect1());

        BarSymbolRelativeLabel group3RelativeLabel = new BarSymbolRelativeLabel(
                                                                                VerticalAlignment.Middle,
                                                                                HorizontalAlignment.EastRight);
        group3RelativeLabel.setText("Group 2");
        group3RelativeLabel.setDrawColor(Spectral.SPECTRAL_PURPLE2);
        group3RelativeLabel.setTextColor(Color.WHITE);
        group3RelativeLabel.setOutlineRound(10);
        group3RelativeLabel.setOutlineStroke(new BasicStroke(2));
        // groupRelativeLabel.setOffsetX(20);
        // groupRelativeLabel.setTextPaddingX(6);
        group3RelativeLabel.setShader(labelShader);
        group3RelativeLabel.setFont(fontGroup);
        // b2g1.setBarLabel(rl3);

        group3.setBarLabel(group3RelativeLabel);

        b1g3.setName("b4");
        b1g3.setSymbol("bar 4");
        b2g3.setName("b5");
        b2g3.setSymbol("bar 5");

        // Window Projection
        Window2D w2d = new Window2D.Linear(-30, 120, 0, 0);
        view.registerWindow2D(w2d);

        SymbolPlugin barPlugin = new SymbolPlugin();
        barPlugin.setNature(SymbolNature.Horizontal);
        w2d.registerPlugin(barPlugin);

       
        // AxisMilliMetrics miliWest = new AxisMilliMetrics(0,Axis.AxisSouth);
        // miliWest.setMajor(40);
        // miliWest.setMedian(20);
        // miliWest.setMinor(5);
        // w2d.registerPlugin(miliWest);

        BarSymbolLayer barLayer = new BarSymbolLayer();

        barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
        barLayer.addSymbol(group1);
        barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 10));
        barLayer.addSymbol(group2);
        barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 10));
        barLayer.addSymbol(group3);
        barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));

        barPlugin.addLayer(barLayer);
        return view;
    }

    @Portfolio(name = "simpleVerticalBar")
    public static View2D getVerticalBal() {
      
        View2D view = new View2D();
    	Window2D w2d = new Window2D.Linear(0,0,-70, 80);
		view.registerWindow2D(w2d);
		
		SymbolPlugin barPlugin = new SymbolPlugin();
		barPlugin.setNature(SymbolNature.Vertical);
		w2d.registerPlugin(barPlugin);

        view.setPlaceHolder(2);

        Font f = InputFonts.getElements(10);
        Font f2 = InputFonts.getNoMove(10);
        // Font fontGroup = InputFonts.getElements(10);
        Font fontGroup = InputFonts.getNoMove(10);

       
        // Color chameleon = TangoPalette.CHAMELEON2;
        // Color butter = TangoPalette.BUTTER2;
        // Color orange = TangoPalette.ORANGE2;

        // Color orange = Spectral.SPECTRAL_BLUE2;
        Color chameleon = Spectral.SPECTRAL_GREEN;
        Color butter = Spectral.SPECTRAL_BLUE2;
        Color orange = Spectral.SPECTRAL_YELLOW;
        Stroke s = new BasicStroke(0.7f);
        float[] fractions = { 0f, 0.5f, 1f };
        Color[] c = { new Color(0, 0, 0, 200), new Color(0, 0, 0, 255),
                new Color(0, 0, 0, 200) };
        Shader labelShader = new Shader(fractions, c);
        AbstractBarDraw barDraw = new BarDefaultDraw(Color.DARK_GRAY, s);
        // Create 3 groups
        AbstractBarDraw chameleonDraw = new BarDefaultDraw(chameleon.darker());
        AbstractBarDraw buterDraw = new BarDefaultDraw(butter.darker());
        AbstractBarDraw orangeDraw = new BarDefaultDraw(orange.darker());
        // create bars symbol for group 1
        BarSymbol b1g1 = new BarSymbol("bar 1", "bar 1");
        b1g1.setAscentValue(62);
        b1g1.setThemeColor(chameleon);
        b1g1.setBarDraw(chameleonDraw);
        // b1g1.setBarLabel(new BarSymbolDefaultLabel(-20,-40));

        BarSymbol b2g1 = new BarSymbol("bar 2", "bar 2");
        b2g1.setAscentValue(83);
        b2g1.setThemeColor(butter);
        b2g1.setBarDraw(buterDraw);

        BarSymbolRelativeLabel b2g1Label = new BarSymbolRelativeLabel(
                                                                      VerticalAlignment.NorthBottom,
                                                                      HorizontalAlignment.WestLeft);
        b2g1Label.setText("API");
        b2g1Label.setDrawColor(Spectral.SPECTRAL_BLUE2);
        b2g1Label.setTextColor(Color.WHITE);
        b2g1Label.setOutlineRound(10);
        b2g1Label.setOutlineStroke(new BasicStroke(2));
        // groupRelativeLabel.setOffsetX(20);
        // groupRelativeLabel.setTextPaddingX(6);
        b2g1Label.setShader(labelShader);
        b2g1Label.setFont(fontGroup);
        // b2g1.setBarLabel(rl3);

        b2g1.setBarLabel(b2g1Label);
        // b2g1.setBarLabel(new BarSymbolDefaultLabel(10,0));

        BarSymbol b3g1 = new BarSymbol("bar 3", "bar 3");
        b3g1.setAscentValue(47);
        b3g1.setThemeColor(orange);
        b3g1.setBarDraw(orangeDraw);
        // b3g1.setBarLabel(new BarSymbolDefaultLabel(0,+40));

        // lay out bars in group 1 symbol
        BarSymbolGroup group1 = new BarSymbolGroup("Group 1", "Group 1");
        group1.setBase(-30);
        group1.setThickness(12);
        group1.setRound(4);
        group1.setMorpheStyle(MorpheStyle.Round);
        // group1.setBarDraw(barDraw);
        group1.setBarFill(new BarFill2());
        group1.setBarEffect(new BarEffect3());

        group1.addSymbol(b1g1);
        group1.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group1.addSymbol(b2g1);
        group1.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group1.addSymbol(b3g1);

        // create bars symbol for group 2
        BarSymbol b1g2 = new BarSymbol("bar 4", "bar 4");
        b1g2.setAscentValue(64);
        b1g2.setThemeColor(chameleon);
        b1g2.setBarDraw(chameleonDraw);
        // b1g2.setBarLabel(new
        // BarSymbolDefaultLabel(-60,0,"label",Color.DARK_GRAY,new
        // Color(40,40,40,40),Color.WHITE));

        BarSymbol b2g2 = new BarSymbol("bar 5", "bar 5");
        b2g2.setAscentValue(77);
        b2g2.setThemeColor(butter);
        b2g2.setBarDraw(buterDraw);

        BarSymbolRelativeLabel b2g2Label = new BarSymbolRelativeLabel(
                                                                      VerticalAlignment.NorthBottom,
                                                                      HorizontalAlignment.EastRight);
        b2g2Label.setText("JenSoft");
        b2g2Label.setDrawColor(Spectral.SPECTRAL_PURPLE1);
        b2g2Label.setTextColor(Color.WHITE);
        b2g2Label.setOutlineRound(10);
        b2g2Label.setOutlineStroke(new BasicStroke(2));
        // groupRelativeLabel.setOffsetX(20);
        // groupRelativeLabel.setTextPaddingX(6);
        b2g2Label.setShader(labelShader);
        b2g2Label.setFont(fontGroup);
        // b2g1.setBarLabel(rl3);

        b2g2.setBarLabel(b2g2Label);

        // b2g2.setBarLabel(new BarSymbolDefaultLabel(0,0));

        BarSymbol b3g2 = new BarSymbol("bar 6", "bar 6");
        b3g2.setAscentValue(32);
        b3g2.setThemeColor(orange);
        b3g2.setBarDraw(orangeDraw);
        // b3g2.setBarLabel(new BarSymbolDefaultLabel(0,0));

        BarSymbolGroup group2 = new BarSymbolGroup("Group 2", "Group 2");
        group2.setBase(-30);
        group2.setThickness(12);
        group2.setRound(4);
        group2.setMorpheStyle(MorpheStyle.Round);
        // group2.setBarDraw(barDraw);
        group2.setBarFill(new BarFill2());
        group2.setBarEffect(new BarEffect3());

        // lay out bar in group 2 symbol
        group2.addSymbol(b1g2);
        group2.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group2.addSymbol(b2g2);
        group2.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group2.addSymbol(b3g2);

        // create bars symbol for group 3
        BarSymbol b1g3 = new BarSymbol("bar 7", "bar 7");
        b1g3.setAscentValue(30);
        b1g3.setThemeColor(chameleon);
        b1g3.setBarDraw(chameleonDraw);

        // b1g3.setBarLabel(new BarSymbolDefaultLabel(0,0));

        BarSymbol b2g3 = new BarSymbol("bar 8", "bar 8");
        b2g3.setAscentValue(40);
        b2g3.setThemeColor(butter);
        b2g3.setBarDraw(buterDraw);
        // b2g3.setBarLabel(new BarSymbolDefaultLabel(0,0));

        BarSymbol b3g3 = new BarSymbol("bar 9", "bar 9");
        b3g3.setAscentValue(50);
        b3g3.setThemeColor(orange);
        b3g3.setBarDraw(orangeDraw);
        // b3g3.setBarLabel(new BarSymbolDefaultLabel(0,0));

        BarSymbolGroup group3 = new BarSymbolGroup("Group 3", "Group 3");
        group3.setBase(-30);
        group3.setThickness(12);
        group3.setRound(4);
        group3.setMorpheStyle(MorpheStyle.Round);
        // group3.setBarDraw(barDraw);
        group3.setBarFill(new BarFill2());
        group3.setBarEffect(new BarEffect3());

        // lay out bar in group 3 symbol
        group3.addSymbol(b1g3);
        group3.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group3.addSymbol(b2g3);
        group3.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 4));
        group3.addSymbol(b3g3);

        

        BarSymbolLayer barLayer = new BarSymbolLayer();
        barPlugin.addLayer(barLayer);

        barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
        barLayer.addSymbol(group1);
        barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 15));
        barLayer.addSymbol(group2);
        barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 15));
        barLayer.addSymbol(group3);
        barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));

        // LEGEND
        Legend legend = new Legend("Classic Bar Label");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE, FilPalette.COPPER2));
        legend.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        LegendPlugin lgendL = new LegendPlugin();
        lgendL.addLegend(legend);
        // view.registerPlugin(lgendL);

        TranslatePlugin toolTranslate = new TranslatePlugin();
        // view.registerPlugin(toolTranslate);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        // view.registerPlugin(zoomWheel);
        return view;
    }

    @Portfolio(name = "simpleLineCurve")
    public static View2D getCurveLine1() {
        
        // view
        View2D view = new View2D();
        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);
        // window projection
        Window2D window = new Window2D.Linear(0, 10, 0, 18);
        view.registerWindow2D(window);

        Font font = new Font("lucida console", Font.PLAIN, 10);

        // create modeled axis plug-in in south part
        AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
        window.registerPlugin(southMetrics);
        southMetrics.setMetricsFont(font);
        southMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        // create modeled axis plug-in in west part
        AxisMetricsPlugin.ModeledMetrics westMetrics = new AxisMetricsPlugin.ModeledMetrics.W();
        window.registerPlugin(westMetrics);
        westMetrics.setMetricsFont(font);
        westMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        FunctionPlugin curveFunctions = new CurveFunctionPlugin();
        window.registerPlugin(curveFunctions);
        
        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);
        window.setThemeColor(RosePalette.EMERALD.brighter());
        

        
       


        double[] xValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] yValues1 = { 6, 1.8, 15, 1.9, 3.4, 6.1, 4.2, 8.5, 9.9, 12, 8 };

        double[] xValues2 = { 0, 1, 2, 3, 4, 5.2, 6, 7, 8, 9, 10 };
        double[] yValues2 = { 3, 1, 5, 4, 4.8, 7.3, 2, 3, 7, 10, 6 };

        double[] xValues3 = { 0, 1.4, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] yValues3 = { 0.4, 7.5, 5, 1, 2.8, 5, 7, 9, 2, 4, 1 };

        // create a series
        AffineSourceFunction serie1 = SourceFunctionToolkit.createSplineSourceFunction(xValues1,
                                                                                            yValues1, 0.1);
        AffineSourceFunction serie2 = SourceFunctionToolkit.createSplineSourceFunction(xValues2,
                                                                                            yValues2, 0.1);
        AffineSourceFunction serie3 = SourceFunctionToolkit.createSplineSourceFunction(xValues3,
                                                                                            yValues3, 0.1);

        // create curves
        CurveFunction curve1 = CurveToolkit.createCurveFunction(serie1, PetalPalette.PETAL1_HC,
                                                new CurveDefaultDraw());
        CurveFunction curve2 = CurveToolkit.createCurveFunction(serie2, PetalPalette.PETAL2_HC,
                                                new CurveDefaultDraw());
        CurveFunction curve3 = CurveToolkit.createCurveFunction(serie3, PetalPalette.PETAL3_HC,
                                                new CurveDefaultDraw());

        // add curves in curve view
        curveFunctions.addFunction(curve1);
        curveFunctions.addFunction(curve2);
        curveFunctions.addFunction(curve3);

       

        GlyphMetric metric = new GlyphMetric();
        metric.setValue(2.5);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setMetricsLabel("2.5");
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(
                                                           PetalPalette.PETAL1_HC, Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        curve1.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(1.5);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setMetricsLabel("1.5");
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL2_HC));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(
                                                           PetalPalette.PETAL2_HC, Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        curve2.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(5.6);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setMetricsLabel("5.6");
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL3_HC));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(
                                                           PetalPalette.PETAL3_HC, Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        curve3.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(8.2);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel("8.2");
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL2_HC));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(
                                                           PetalPalette.PETAL2_HC, Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        curve2.addMetricsLabel(metric);

        // zoom and translate tool plugin
        ZoomBoxPlugin zoomPlugin = new ZoomBoxPlugin();
        window.registerPlugin(zoomPlugin);

        TranslatePlugin translatePlugin = new TranslatePlugin();
        window.registerPlugin(translatePlugin);

        ZoomWheelPlugin wheelPlugin = new ZoomWheelPlugin();
        window.registerPlugin(wheelPlugin);

        // decoration a night gradient (dark blue to black)
        GradientPlugin night = new Night();
        night.setAlpha(1f);
        window.registerPlugin(night);

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(new Color(255, 255, 255, 40));
        bp.addPaint(new Color(40, 40, 40, 40));
        stripes.setStripePalette(bp);
        stripes.setAlpha(0.3f);
        window.registerPlugin(stripes);

        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout2);

        return view;
    }

    @Portfolio(name = "simpleAreaCurve")
    public static View2D getSimpleArea() {
        Font font = new Font("lucida console", Font.PLAIN, 10);

        // view
        View2D view = new View2D();
        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);
        // window projection
        Window2D window = new Window2D.Linear(0, 10, 0, 18);
        view.registerWindow2D(window);

        // device outline plug-in
        window.registerPlugin(new OutlinePlugin(RosePalette.MANDARIN));

        // create modeled axis plug-in in south part
        AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
        window.registerPlugin(southMetrics);
        southMetrics.setMetricsFont(font);
        southMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        // create modeled axis plug-in in west part
        AxisMetricsPlugin.ModeledMetrics westMetrics = new AxisMetricsPlugin.ModeledMetrics.W();
        window.registerPlugin(westMetrics);
        westMetrics.setMetricsFont(font);
        westMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));

        window.setThemeColor(RosePalette.LIME);
        view.setBackgroundPainter(roundViewFill);

        AreaFunctionPlugin areaFunctions = new AreaFunctionPlugin();
        window.registerPlugin(areaFunctions);

        // CREATE SERIE
        double[] xValues1 = { 0, 1, 2, 3, 4, 5.2, 6, 7, 8, 9, 10 };
        double[] yValues1 = { 3, 1, 5, 4, 4.8, 7.3, 2, 3, 7, 10, 6 };
        SourceFunction is = SourceFunctionToolkit.createSplineSourceFunction(xValues1,
                                                                                  yValues1, 0.1);

        // CREATE CURVE
        AreaFunction areaCurve = new AreaFunction(is);
        areaCurve.setAreaFill(new AreaGradientFill());
        areaCurve.setAreaDraw(new AreaDefaultDraw(Color.WHITE, new BasicStroke(
                                                                               1f)));
        areaCurve.setThemeColor(FilPalette.GREEN4);
        areaCurve.setAreaBase(0);
        areaFunctions.addFunction(areaCurve);

        // CREATE SOME CURVE GLYPH METRICS
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(4.3);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.GREEN4));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(FilPalette.GREEN5,
                                                           Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        areaCurve.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(1.5);
        metric.setStylePosition(StylePosition.Tangent);
        metric.setDivergence(10);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.GREEN4));
        metric.setGlyphMetricMarkerPainter(new RoundMarker(FilPalette.GREEN5,
                                                           Color.white));
        metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        areaCurve.addMetricsLabel(metric);

        // LEGEND
        Legend legend = new Legend("Simple Area Label");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE, RosePalette.LIME));
        legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));

        LegendPlugin legends = new LegendPlugin(legend);
        window.registerPlugin(legends);

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(new Color(255, 255, 255, 40));
        bp.addPaint(ColorPalette.alpha(TangoPalette.ORANGE3, 40));
        stripes.setStripePalette(bp);
        stripes.setAlpha(0.3f);
        window.registerPlugin(stripes);

        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout2);

        // ZOOM AND TRANSLATE TOOL
        ZoomBoxPlugin zoomTool = new ZoomBoxPlugin();
        window.registerPlugin(zoomTool);

        TranslatePlugin toolTranslate = new TranslatePlugin();
        window.registerPlugin(toolTranslate);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        window.registerPlugin(zoomWheel);

        return view;
    }

    @Portfolio(name = "multipleAreaCurve")
    public static View2D getMultipleAreaCurve() {

        Font font = new Font("lucida console", Font.PLAIN, 10);

        // view
        View2D view = new View2D();
        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);
        // window projection
        Window2D window = new Window2D.Linear(0, 10, 0, 18);
        view.registerWindow2D(window);

        // device outline plug-in
        window.registerPlugin(new OutlinePlugin(RosePalette.MANDARIN));

        // create modeled axis plug-in in south part
        AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
        window.registerPlugin(southMetrics);
        southMetrics.setMetricsFont(font);
        southMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        // create modeled axis plug-in in west part
        AxisMetricsPlugin.ModeledMetrics westMetrics = new AxisMetricsPlugin.ModeledMetrics.W();
        window.registerPlugin(westMetrics);
        westMetrics.setMetricsFont(font);
        westMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        AreaFunctionPlugin areaFunctions = new AreaFunctionPlugin();
        window.registerPlugin(areaFunctions);
        
        
        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));

        window.setThemeColor(RosePalette.LIME);
        view.setBackgroundPainter(roundViewFill);



    
        // SERIES
        double[] xValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] yValues1 = { 2, 1.8, 1.9, 15, 0.4, 1.4, 1.2, 0.2, 0.6, 0.4,
                0.7 };
        SplineSourceFunction serie1 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues1, yValues1, 0.1);

        double[] xValues2 = { 0, 1, 2, 3, 4, 5.2, 6, 7, 8, 9, 10 };
        double[] yValues2 = { 3, 1, 5, 4, 4.8, 7.3, 2, 3, 7, 10, 6 };
        SplineSourceFunction serie2 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues2, yValues2, 0.1);

        double[] xValues3 = { 0, 1.4, 2, 3, 4, 5, 6, 6.5, 7, 8, 9, 10 };
        double[] yValues3 = { 0.4, 7.5, 5, 1, 2.8, 1, 4, 7, 9, 2, 4, 1 };
        SplineSourceFunction serie3 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues3, yValues3, 0.1);

        // CURVES STROKES
        Stroke sDash1 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                                        BasicStroke.JOIN_ROUND, 10, new float[] { 40, 5, 2, 5 }, 0);
        Stroke sDash2 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                                        BasicStroke.JOIN_ROUND, 10, new float[] { 20, 8, 10, 8 }, 0);
        Stroke sDash3 = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                                        BasicStroke.JOIN_ROUND, 10, new float[] { 6, 3, 6, 3 }, 0);

        // CREATE CURVES
        AreaFunction curve1 = new AreaFunction(serie1);
        curve1.setThemeColor(NanoChromatique.BLUE);
        // curve1.setAreaDraw(new AreaDefaultDraw(Color.WHITE,sDash1));
        curve1.setAreaFill(new AreaGradientFill());
        curve1.setAreaBase(-3);

        AreaFunction curve2 = new AreaFunction(serie2);
        curve2.setThemeColor(NanoChromatique.GREEN);
        // curve2.setAreaDraw(new AreaDefaultDraw(Color.WHITE,sDash2));
        curve2.setAreaFill(new AreaGradientFill());
        curve2.setAreaBase(-3);

        AreaFunction curve3 = new AreaFunction(serie3);
        curve3.setThemeColor(FilPalette.GRAY6);
        // curve3.setAreaDraw(new AreaDefaultDraw(Color.WHITE,sDash3));
        curve3.setAreaFill(new AreaGradientFill());
        curve3.setAreaBase(-3);

        areaFunctions.addFunction(curve1);
        areaFunctions.addFunction(curve2);
        areaFunctions.addFunction(curve3);

       

        // ADD SOME GLYPHS ON CURVES
        GlyphMetric metric = new GlyphMetric();
        metric.setValue(4.04);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel("4.04");
        metric.setDivergence(20);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.GREEN4));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(FilPalette.GREEN5));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 12));
        curve2.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(8.2);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsLabel("8.2");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.GREEN4));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(FilPalette.GREEN5));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 12));
        curve2.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(5.5);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsLabel("5.5");
        metric.setDivergence(20);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.BLUE14));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(FilPalette.BLUE14));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 12));
        curve3.addMetricsLabel(metric);

        metric = new GlyphMetric();
        metric.setValue(2.3);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsLabel("2.3");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, FilPalette.BLUE14));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(FilPalette.BLUE14));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 12));
        curve3.addMetricsLabel(metric);

       

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(new Color(255, 255, 255, 40));
        bp.addPaint(ColorPalette.alpha(TangoPalette.ORANGE3, 40));
        stripes.setStripePalette(bp);
        stripes.setAlpha(0.3f);
        window.registerPlugin(stripes);

        // HORIZONTAL AND VERTICAL GRID DECORATOR
        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout2);

        // ZOOM AND TRANSLATE TOOL
        ZoomBoxPlugin zoomTool = new ZoomBoxPlugin();
        window.registerPlugin(zoomTool);

        TranslatePlugin toolTranslate = new TranslatePlugin();
        window.registerPlugin(toolTranslate);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        window.registerPlugin(zoomWheel);

        return view;
    }

    @Portfolio(name = "scatter1")
    public static View2D getScatterCurve() {
        // view
        View2D view = new View2D();
        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);
        // window projection
        Window2D window = new Window2D.Linear(2, 8, 0, 18);
        view.registerWindow2D(window);
        Font font = new Font("lucida console", Font.PLAIN, 10);
        // device outline plug-in
        window.registerPlugin(new OutlinePlugin(RosePalette.MANDARIN));

        // create modeled axis plug-in in south part
        AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
        window.registerPlugin(southMetrics);
        southMetrics.setMetricsFont(font);
        southMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        // create modeled axis plug-in in west part
        AxisMetricsPlugin.ModeledMetrics westMetrics = new AxisMetricsPlugin.ModeledMetrics.W();
        window.registerPlugin(westMetrics);
        westMetrics.setMetricsFont(font);
        westMetrics.registerMetricsModels(MetricsModelRangeCollections.NanoGiga);

        // scatter function plug-in
        ScatterFunctionPlugin scatters = new ScatterFunctionPlugin();
        window.registerPlugin(scatters);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        // roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);

        window.setThemeColor(RosePalette.MELON);

        // legend plug-in
        Legend legend = new Legend("Scatter Curves");
        legend.setLegendFill(new LegendGradientFill(Spectral.SPECTRAL_YELLOW.brighter(), Spectral.SPECTRAL_RED.brighter()));
        legend.setFont(InputFonts.getFont(InputFonts.ELEMENT, 14));
        legend.setConstraints(new LegendConstraints(LegendPosition.East, 0.4f,
                                                    LegendAlignment.Rigth));

        double[] xValues1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] yValues1 = { 6, 1.8, 15, 1.9, 3.4, 6.1, 4.2, 8.5, 9.9, 12, 8 };
        SplineSourceFunction serie1 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues1, yValues1, 0.3);

        double[] xValues2 = { 0, 1, 2, 3, 4, 5.2, 6, 7, 8, 9, 10 };
        double[] yValues2 = { 3, 1, 5, 4, 4.8, 7.3, 2, 3, 7, 10, 6 };
        SplineSourceFunction serie2 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues2, yValues2, 0.3);

        double[] xValues3 = { 0, 1.4, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] yValues3 = { 0.4, 7.5, 5, 1, 2.8, 5, 7, 9, 2, 4, 1 };
        SplineSourceFunction serie3 = SourceFunctionToolkit
                .createSplineSourceFunction(xValues3, yValues3, 0.3);

        ScatterFunction scatter1 = new ScatterFunction(serie1);
        scatter1.setThemeColor(Spectral.SPECTRAL_GREEN);
        scatter1.setScatterFill(new ScatterDefaultFill());
        scatter1.setScatterMorphe(new QInverseMorphe(5, 3));

        ScatterFunction scatter2 = new ScatterFunction(serie2);
        scatter2.setThemeColor(Spectral.SPECTRAL_RED.brighter());
        scatter2.setScatterFill(new ScatterDefaultFill());
        scatter2.setScatterMorphe(new QStarMorphe(3, 6, 5));

        ScatterFunction scatter3 = new ScatterFunction(serie3);
        scatter3.setThemeColor(Spectral.SPECTRAL_YELLOW.brighter());
        scatter3.setScatterFill(new ScatterDefaultFill());
        scatter3.setScatterMorphe(new EllipseMorphe(8, 8));

        scatters.addFunction(scatter1);
        scatters.addFunction(scatter2);
        scatters.addFunction(scatter3);

        // decoration a night gradient (dark blue to black)
        GradientPlugin night = new Night();
        window.registerPlugin(night);

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(new Color(255, 255, 255, 40));
        bp.addPaint(new Color(40, 40, 40, 40));
        stripes.setStripePalette(bp);
        stripes.setAlpha(0.3f);
        window.registerPlugin(stripes);

        // HORIZONTAL AND VERTICAL GRID DECORATOR
        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        window.registerPlugin(gridLayout2);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        window.registerPlugin(zoomWheel);

        return view;
    }

    @Portfolio(name = "ray1")
    public static View2D getRay1() {
        // CREATE VIEW
        // compatible view (view + window + ray plugin + other plugin decorator)
        RayView view = RayToolkit.createCompatibleView(-260, 260, -250, 250);

        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);
        view.getRayWindow().setThemeColor(RosePalette.LEMONPEEL.brighter());

        // CREATE RAY WITH RANDOM
        Random r = new Random();
        for (int i = -250; i <= 250; i = i + 10) {

            Ray ray = new Ray();
            ray.setName("ray" + i);
            ray.setThicknessType(ThicknessType.Device);
            ray.setThickness(2);
            ray.setRayNature(RayNature.XRay);
            ray.setRayBase(0);

            // int style = r.nextInt(2);

            // if(style == 0){
            ray.setAscentValue(80 + r.nextInt(120));
            ray.setThemeColor(Spectral.SPECTRAL_YELLOW.brighter());
            // }
            // else{
            // ray.setDescentValue(100+r.nextInt(140));
            // ray.setThemeColor(Spectral.SPECTRAL_BLUE2.brighter());
            // }
            ray.setRay(i);

            // ray.setRayDraw(new RayDefaultDraw(Color.WHITE));
            ray.setRayFill(new RayDefaultFill());

            view.addRay(ray);
        }

        for (int i = -250; i <= 250; i = i + 10) {

            Ray ray = new Ray();
            ray.setName("ray" + i);
            ray.setThicknessType(ThicknessType.Device);
            ray.setThickness(2);
            ray.setRayNature(RayNature.XRay);
            ray.setRayBase(0);

            // int style = r.nextInt(2);

            // if(style == 0){
            // ray.setAscentValue(100+r.nextInt(140));
            // ray.setThemeColor(Spectral.SPECTRAL_RED.brighter());
            // }
            // else{
            ray.setDescentValue(80 + r.nextInt(120));
            ray.setThemeColor(Spectral.SPECTRAL_GREEN);
            // }
            ray.setRay(i);

            // ray.setRayDraw(new RayDefaultDraw(Color.WHITE));
            ray.setRayFill(new RayDefaultFill());

            view.addRay(ray);
        }

        // OUTLINE
        view.registerPlugin(new OutlinePlugin());

        // LEGEND
        Legend legend = new Legend("Ray Diagram");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE,
                                             Spectral.SPECTRAL_YELLOW.brighter()));
        legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 12));
        legend.setConstraints(new LegendConstraints(LegendPosition.East, 0.4f,
                                                    LegendAlignment.Rigth));
        LegendPlugin lgendL = new LegendPlugin();
        lgendL.addLegend(legend);
        view.registerPlugin(lgendL);

        // TOOLS
        ZoomBoxPlugin zoomTool = new ZoomBoxPlugin();
        view.registerPlugin(zoomTool);

        TranslatePlugin toolTranslate = new TranslatePlugin();
        view.registerPlugin(toolTranslate);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        view.registerPlugin(zoomWheel);
        return view;
    }

    @Portfolio(name = "bubble1")
    public static View2D getBubbleView1() {

        View2D view = new View2D();

        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);

        Window2D w2d = new Window2D.Linear(-180, 180, -180, 180);
        w2d.setThemeColor(RosePalette.LIME);
        view.registerWindow2D(w2d);

        Bubble b1 = new Bubble(-30, -80, 20, RosePalette.AEGEANBLUE);
        b1.setBubbleEffect(new BubbleEffect1());
        b1.setBubbleFill(new BubbleDefaultFill());

        Bubble b2 = new Bubble(10, 20, 25, RosePalette.EMERALD);
        b2.setBubbleEffect(new BubbleEffect2());
        b2.setBubbleFill(new BubbleDefaultFill());

        Bubble b3 = new Bubble(70, 50, 20, RosePalette.MANDARIN);
        b3.setBubbleEffect(new BubbleEffect3());
        b3.setBubbleFill(new BubbleDefaultFill());

        Bubble b4 = new Bubble(75, -80, 17, RosePalette.FLANNELGRAY);
        b4.setBubbleEffect(new BubbleEffect4());
        b4.setBubbleFill(new BubbleDefaultFill());

        Bubble b5 = new Bubble(50, 150, 15, RosePalette.FLAMINGO);
        b5.setBubbleEffect(new BubbleEffect4());
        b5.setBubbleFill(new BubbleDefaultFill());

        Bubble b6 = new Bubble(-50, 120, 22, RosePalette.LIME);
        b6.setBubbleEffect(new BubbleEffect4());
        b6.setBubbleFill(new BubbleDefaultFill());

        BubblePlugin bubbleLayout = new BubblePlugin();
        w2d.registerPlugin(bubbleLayout);

        bubbleLayout.addBubble(b1);
        bubbleLayout.addBubble(b2);
        bubbleLayout.addBubble(b3);
        bubbleLayout.addBubble(b4);
        bubbleLayout.addBubble(b5);
        bubbleLayout.addBubble(b6);

        // /LEGEND
        Legend legend = new Legend("Bubble Chart");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE,
                                             Spectral.SPECTRAL_BLUE1.brighter()));
        legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 12));
        legend.setConstraints(new LegendConstraints(LegendPosition.East, 0.4f,
                                                    LegendAlignment.Rigth));
        LegendPlugin lgendL = new LegendPlugin();
        lgendL.addLegend(legend);
        w2d.registerPlugin(lgendL);

        // HORIZONTAL AND VERTICAL GRID DECORATOR
        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 80, GridOrientation.Horizontal);
        gridLayout.getGridManager().setGridColor(new Color(59, 89, 152, 100));
        w2d.registerPlugin(gridLayout);

        Multiplier3Metrics miliWest = new Multiplier3Metrics(0, Axis.AxisWest);
        miliWest.setMajor(40);
        miliWest.setMedian(20);
        miliWest.setMinor(5);
        w2d.registerPlugin(miliWest);

        Multiplier3Metrics miliSouth = new Multiplier3Metrics(0, Axis.AxisSouth);
        miliSouth.setMajor(50);
        miliSouth.setMedian(25);
        miliSouth.setMinor(10);
        w2d.registerPlugin(miliSouth);

        w2d.registerPlugin(new OutlinePlugin());

        ZoomBoxPlugin zoomTool = new ZoomBoxPlugin();
        w2d.registerPlugin(zoomTool);

        TranslatePlugin toolTranslate = new TranslatePlugin();
        w2d.registerPlugin(toolTranslate);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        w2d.registerPlugin(zoomWheel);

        return view;
    }

    @Portfolio(name = "metrics")
    public static View2D getMetricsView1() {
        View2D view = new View2D();

        view.setPlaceHolder(45);
        view.setPlaceHolderAxisWest(50);
        view.setPlaceHolderAxisSouth(40);
        view.setPlaceHolderAxisNorth(30);
        view.setPlaceHolderAxisEast(40);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);
        // view.getCurveWindow().setThemeColor(Color.WHITE);
        view.setPlaceHolderAxisSouth(100);
        // view.setPlaceHolderAxisWEST(120);
        // view.setPlaceHolderAxisEAST(120);

        Window2D w2d = new Window2D.Linear(-3000, 3000, -2500, 2500);
        w2d.setName("metrics window");

        Window2D w2d2 = new Window2D.Linear(-1000, 1000, -800, 800);

        Window2D w2d3 = new Window2D.Linear(-300, 1200, -300, 80);

        Multiplier3Metrics miliWest = new AxisMetricsPlugin.Multiplier3Metrics(0, Axis.AxisWest);
        miliWest.setMajor(1000);
        miliWest.setMedian(500);
        miliWest.setMinor(100);

        w2d.registerPlugin(miliWest);

        Multiplier3Metrics miliSouth = new AxisMetricsPlugin.Multiplier3Metrics(0, Axis.AxisSouth);
        miliSouth.setMajor(1000);
        miliSouth.setMedian(500);
        miliSouth.setMinor(100);
        miliSouth.setAxisSpacing(0);
        miliSouth.setMetricsMarkerColor(TangoPalette.CHAMELEON3);
        miliSouth.setMetricsBaseLineColor(TangoPalette.CHAMELEON3);
        w2d.registerPlugin(miliSouth);

        Multiplier3Metrics miliSouth2 = new AxisMetricsPlugin.Multiplier3Metrics(0, Axis.AxisSouth);
        miliSouth2.setAxisSpacing(30);

        miliSouth2.setMajor(500);
        miliSouth2.setMedian(250);
        miliSouth2.setMinor(50);
        miliSouth2.setMetricsMarkerColor(TangoPalette.BUTTER3);
        miliSouth2.setMetricsBaseLineColor(TangoPalette.BUTTER3);
        w2d2.registerPlugin(miliSouth2);

        Multiplier3Metrics miliSouth3 = new AxisMetricsPlugin.Multiplier3Metrics(0, Axis.AxisSouth);
        miliSouth3.setAxisSpacing(60);

        miliSouth3.setMajor(200);
        miliSouth3.setMedian(50);
        miliSouth3.setMinor(10);
        miliSouth3.setMetricsMarkerColor(TangoPalette.ORANGE3);
        miliSouth3.setMetricsBaseLineColor(TangoPalette.ORANGE3);
        w2d3.registerPlugin(miliSouth3);

        w2d.registerPlugin(new OutlinePlugin());
        // /LEGEND
        Legend legend = new Legend("Multiple Window");
        legend.setLegendFill(new LegendGradientFill(Color.WHITE,
                                             Spectral.SPECTRAL_BLUE1.brighter()));
        legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 12));
        legend.setConstraints(new LegendConstraints(LegendPosition.East, 0.4f,
                                                    LegendAlignment.Rigth));
        LegendPlugin lgendL = new LegendPlugin();
        lgendL.addLegend(legend);
        w2d.registerPlugin(lgendL);

        view.registerWindow2D(w2d);
        view.registerWindow2D(w2d2);
        view.registerWindow2D(w2d3);

        return view;
    }

    @Portfolio(name = "tool")
    public static View2D getToolView() {
        View2D v2d = new View2D();

        v2d.setPlaceHolder(45);
        v2d.setPlaceHolderAxisWest(50);
        v2d.setPlaceHolderAxisSouth(40);
        v2d.setPlaceHolderAxisNorth(30);
        v2d.setPlaceHolderAxisEast(40);

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        v2d.setBackgroundPainter(roundViewFill);

        // v2d.setPlaceHolder(100);
        Window2D w2d = new Window2D.Linear(-10, 10, -10, 10);
        v2d.registerWindow2D(w2d);
        w2d.registerPlugin(new OutlinePlugin());
        w2d.setThemeColor(RosePalette.AEGEANBLUE);
        w2d.setThemeColor(RosePalette.LEMONPEEL.brighter());

        LegendPlugin legendPlugin = new LegendPlugin();
        w2d.registerPlugin(legendPlugin);

        Legend l1 = LegendToolkit.createLegend("Objectif Bars Widget",
                                               InputFonts.getNeuropol(14), RosePalette.LIME,
                                               RosePalette.MANDARIN);
        // legendPlugin.addLegend(l1);

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(ColorPalette.alpha(RosePalette.LEMONPEEL, 20));
        bp.addPaint(ColorPalette.alpha(RosePalette.MELON, 20));
        stripes.setStripePalette(bp);
        w2d.registerPlugin(stripes);

        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.setGridColor(new Color(255, 255, 255, 60));
        w2d.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.setGridColor(new Color(255, 255, 255, 60));
        w2d.registerPlugin(gridLayout2);

        // OBJECTIF PLUGIN and Widgets
        ZoomObjectifPlugin zoomObjectif = new ZoomObjectifPlugin();
        // create two objectif for x and y dimension
        ObjectifX ox = new ObjectifX();
        ObjectifY oy = new ObjectifY();
        // register widget in zoom objectif plugin
        zoomObjectif.registerWidget(ox);
        zoomObjectif.registerWidget(oy);

        ox.setOutlineColor(Color.BLACK);
        ox.setButton1DrawColor(RosePalette.CALYPSOBLUE);
        ox.setButton2DrawColor(RosePalette.CALYPSOBLUE);
        oy.setOutlineColor(Color.BLACK);
        oy.setButton1DrawColor(RosePalette.CALYPSOBLUE);
        oy.setButton2DrawColor(RosePalette.CALYPSOBLUE);

        w2d.registerPlugin(zoomObjectif);

        // pre lock selected widget (selectable widget need a lock)
        zoomObjectif.lockSelected();

        return v2d;
    }

    @Portfolio(name = "donut2d")
    public static View2D getDonut2DView() {
        final View2D view = new View2D();

        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);

        Window2D w2d = new Window2D.Linear(-2, 2, -2, 2);
        w2d.setName("donuts2D window");
        w2d.setThemeColor(Color.WHITE);
        w2d.registerPlugin(new OutlinePlugin(RosePalette.MELON));

        w2d.registerPlugin(new ZoomWheelPlugin());

        TranslatePlugin translatePlugin = new TranslatePlugin();
        translatePlugin.registerContext(new TranslateDefaultDeviceContext());
        w2d.registerPlugin(translatePlugin);

        Donut2DPlugin donut2DPlugin = new Donut2DPlugin();
        w2d.registerPlugin(donut2DPlugin);

        Donut2D donut1 = new Donut2D();
        donut1.setNature(Donut2DNature.Donut2DUser);
        donut1.setCenterX(0);
        donut1.setCenterY(0);
        donut1.setInnerRadius(40);
        donut1.setOuterRadius(60);
        donut1.setStartAngleDegree(50);

        final Donut2DSlice s1 = new Donut2DSlice("s1", new Color(139, 196, 40));
        s1.setAlpha(0.5f);
        s1.setValue(20.0);

        final Donut2DSlice s2 = new Donut2DSlice("s2", new Color(213, 222, 35));
        s2.setValue(20.0);
        s2.setAlpha(0.5f);

        final Donut2DSlice s3 = new Donut2DSlice("s3", new Color(78, 148, 44));
        s3.setValue(20.0);
        s3.setAlpha(0.5f);

        donut1.addSlice(s1);
        donut1.addSlice(s2);
        donut1.addSlice(s3);

        donut2DPlugin.addDonut(donut1);

        Donut2D donut2 = new Donut2D();
        donut2.setNature(Donut2DNature.Donut2DUser);
        donut2.setCenterX(0);
        donut2.setCenterY(0);
        donut2.setInnerRadius(40);
        donut2.setOuterRadius(60);
        donut2.setStartAngleDegree(120);

        final Donut2DSlice s4 = new Donut2DSlice("s4", new Color(0, 176, 217));
        s4.setValue(30.0);
        s4.setAlpha(0.4f);
        final Donut2DSlice s5 = new Donut2DSlice("s5", new Color(174, 221, 227));
        s5.setValue(10.0);
        s5.setAlpha(0.7f);
        final Donut2DSlice s6 = new Donut2DSlice("s6", new Color(28, 152, 183));
        s6.setValue(20.0);
        s6.setAlpha(0.7f);

        donut2.addSlice(s4);
        donut2.addSlice(s5);
        donut2.addSlice(s6);

        Donut2D donut3 = new Donut2D();
        donut3.setNature(Donut2DNature.Donut2DUser);
        donut3.setCenterX(1);
        donut3.setCenterY(0);
        donut3.setInnerRadius(40);
        donut3.setOuterRadius(60);
        donut3.setStartAngleDegree(120);

        final Donut2DSlice s7 = new Donut2DSlice("S7", new Color(240, 90, 40));
        s7.setValue(40.0);
        s7.setAlpha(0.7f);
        final Donut2DSlice s8 = new Donut2DSlice("s8", new Color(251, 148, 9));
        s8.setValue(25);
        s8.setAlpha(0.7f);

        final Donut2DSlice s9 = new Donut2DSlice("s9", new Color(251, 174, 66));
        s9.setValue(25);
        s9.setAlpha(0.5f);

        donut3.addSlice(s7);
        donut3.addSlice(s8);
        donut3.addSlice(s9);

        view.registerWindow2D(w2d);
        return view;
    }

    @Portfolio(name = "band")
    public static View2D getBandView() {
        View2D view = new View2D();
        RoundViewFill roundViewFill = new RoundViewFill();
        Shader s = new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(32, 39, 55), Color.BLACK });
        roundViewFill.setShader(s);
        roundViewFill.setOutlineStroke(new BasicStroke(2.5f));
        // roundViewFill.setOutlineColor(Spectral.SPECTRAL_BLUE2);
        roundViewFill.setOutlineColor(Color.WHITE);

        view.setBackgroundPainter(roundViewFill);

        Window2D window2D = new Window2D.Linear(0, 9, 0, 18);
        window2D.setThemeColor(Spectral.SPECTRAL_PURPLE1);

        StripePlugin stripes = new StripePlugin.MultiplierStripe.H(0, 2.5);
        StripePalette bp = new StripePalette();
        bp.addPaint(new Color(255, 255, 255, 80));
        // bp.addPaint(new Color(40,40,40,40));
        bp.addPaint(ColorPalette.alpha(TangoPalette.SCARLETRED3, 80));
        stripes.setStripePalette(bp);
        stripes.setAlpha(0.3f);
        window2D.registerPlugin(stripes);

        GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Horizontal);
        gridLayout.setGridColor(new Color(255, 255, 255, 60));
        window2D.registerPlugin(gridLayout);

        GridPlugin gridLayout2 = new GridPlugin.MultiplierGrid(0, 2.5, GridOrientation.Vertical);
        gridLayout2.setGridColor(new Color(255, 255, 255, 60));
        window2D.registerPlugin(gridLayout2);

        view.registerWindow2D(window2D);

        window2D.registerPlugin(new OutlinePlugin());

        Legend legend = new Legend("Band");
        legend.setFont(InputFonts.getFont(InputFonts.ELEMENT, 14));
        legend.setLegendFill(new LegendGradientFill(Color.WHITE, window2D
                .getThemeColor()));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        LegendPlugin lgendL = new LegendPlugin(legend);
        window2D.registerPlugin(lgendL);

        ZoomBoxPlugin zoomPlugin = new ZoomBoxPlugin();
        window2D.registerPlugin(zoomPlugin);

        TranslatePlugin translatePlugin = new TranslatePlugin();
        window2D.registerPlugin(translatePlugin);

        ZoomWheelPlugin wheelPlugin = new ZoomWheelPlugin();
        window2D.registerPlugin(wheelPlugin);

        ZoomObjectifPlugin objectifPlugin = new ZoomObjectifPlugin();
        window2D.registerPlugin(objectifPlugin);

        ZoomPercentPlugin percentPlugin = new ZoomPercentPlugin();
        window2D.registerPlugin(percentPlugin);

        MarkerPlugin crossPlugin = new MarkerPlugin();
        window2D.registerPlugin(crossPlugin);

        return view;
    }
}
