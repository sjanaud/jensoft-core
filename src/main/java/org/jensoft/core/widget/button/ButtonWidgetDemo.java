/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jensoft.core.catalog.ui.ViewFrameUI;
import org.jensoft.core.graphics.Shader;
import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.palette.color.ShaderPalette;
import org.jensoft.core.plugin.marker.MarkerPlugin;
import org.jensoft.core.plugin.marker.context.MarkerDefaultDeviceContext;
import org.jensoft.core.plugin.outline.OutlinePlugin;
import org.jensoft.core.plugin.translate.TranslateCompassWidget;
import org.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import org.jensoft.core.plugin.translate.TranslatePlugin;
import org.jensoft.core.plugin.translate.TranslateX;
import org.jensoft.core.plugin.translate.TranslateY;
import org.jensoft.core.plugin.zoom.lens.LensDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.lens.LensX;
import org.jensoft.core.plugin.zoom.lens.LensY;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin;
import org.jensoft.core.plugin.zoom.percent.ZoomPercentDefaultDeviceContext;
import org.jensoft.core.plugin.zoom.percent.ZoomPercentPlugin;
import org.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;
import org.jensoft.core.sharedicon.marker.Marker;
import org.jensoft.core.view.View;

public class ButtonWidgetDemo extends View {

   

	 public ButtonWidgetDemo() {
	    super();
        Projection w2d = new Projection.Linear(0, 10, 0, 18);
        setBackground(Color.WHITE);

        // //TRANSLATE
        // TranslatePlugin translatePlugin = new TranslatePlugin();
        // w2d.registerPlugin(translatePlugin);
        //
        // //use compass widget with objectif
        // TranslateCompass compass = new TranslateCompass();
        // translatePlugin.registerWidget(compass);

        ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
        w2d.registerPlugin(zoomWheel);

        // test button
        ButtonWidget bw = new ButtonWidget("myButton", 40, 16, 8, 0);
        bw.setOutlineColor(Color.WHITE);
        bw.setOutlineRound(10);
        bw.setShader(ShaderPalette.CALYPSOBLUEShader3);
        zoomWheel.registerWidget(bw);

        ButtonWidget bw2 = new ButtonWidget("myButton", 40, 16, 9, 0);
        bw2.setOutlineColor(Color.WHITE);
        bw2.setOutlineRound(10);
        bw2.setShader(ShaderPalette.CALYPSOBLUEShader3);
        zoomWheel.registerWidget(bw2);

        ButtonWidget bw3 = new ButtonWidget("myButton", 40, 16, 10, 0);
        bw3.setOutlineColor(Color.WHITE);
        bw3.setOutlineRound(20);
        bw3.setShader(ShaderPalette.AZALEAShader);
        zoomWheel.registerWidget(bw3);

        ButtonWidget bw4 = new ButtonWidget("myButton", 40, 16, 11, 0);
        bw4.setOutlineColor(Color.WHITE);
        bw4.setOutlineRound(20);
        bw4.setShader(ShaderPalette.EMERALDShader);
        zoomWheel.registerWidget(bw4);

        PullDownButtonWidget bw5 = new PullDownButtonWidget("myButton", 60, 24,
                                                            100, 100);
        bw5.setOutlineColor(ColorPalette.BLACK);
        bw5.setOutlineRound(8);
        bw5.setPullFillColor(NanoChromatique.GREEN);
        bw5.setOutlineStroke(new BasicStroke(1.2f));
        bw5.setShader(new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(10, 10, 10, 255), new Color(30, 30, 30, 140) }));

        PullItem item1 = new PullItem("lock zoom box",
                                      SharedIcon.getCommon(Common.BOX2));

        item1.setListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("lock zoom box");

            }
        });

        PullItem item2 = new PullItem("lock zoom lens",
                                      SharedIcon.getMarker(Marker.MARKER_ROUNDED_RED));
        item2.setListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("lock zoom lens");
            }
        });

        bw5.addPullAction(item1);
        bw5.addPullAction(item2);

        zoomWheel.registerWidget(bw5);

        // TRANSLATE
        TranslatePlugin translatePlugin = new TranslatePlugin();
        w2d.registerPlugin(translatePlugin);

        // use pad widget with objectif
        TranslateX tx = new TranslateX(120, 14);
        TranslateY ty = new TranslateY(14, 120);
        tx.setOutlineColor(ColorPalette.BLACK);
        tx.setOutlineStroke(new BasicStroke(1.2f));
        tx.setButtonFillColor(RosePalette.CALYPSOBLUE);
        tx.setButtonDrawColor(null);
        tx.setShader(new Shader(new float[] { 0f, 1f }, new Color[] {
                new Color(30, 30, 30, 140), new Color(10, 10, 10, 255) }));

        translatePlugin.registerWidget(tx, ty);
        translatePlugin.registerContext(new TranslateDefaultDeviceContext());

        TranslateCompassWidget compass = new TranslateCompassWidget();
        translatePlugin.registerWidget(compass);
        compass.setRingFillColor(new Alpha(RosePalette.EMERALD, 150));
        compass.setRingDrawColor(Color.WHITE);
        compass.setRingNeedleFillColor(new Alpha(RosePalette.EMERALD, 150));
        compass.setRingNeedleDrawColor(Color.WHITE);

        // plugin/objectif
        ZoomLensPlugin zoomObjectif = new ZoomLensPlugin();

        // plugin/objectif/widget
        LensX ox = new LensX(80, 12);
        ox.setOutlineColor(Color.WHITE);
        ox.setButton1DrawColor(RosePalette.REDWOOD);
        ox.setButton2DrawColor(RosePalette.REDWOOD);

        LensY oy = new LensY(12, 80);
        oy.setOutlineColor(Color.WHITE);
        oy.setButton1DrawColor(RosePalette.REDWOOD);
        oy.setButton2DrawColor(RosePalette.REDWOOD);

        zoomObjectif.registerWidget(ox);
        zoomObjectif.registerWidget(oy);

        // plugin/objectif/context
        zoomObjectif.registerContext(new LensDefaultDeviceContext());

        w2d.registerPlugin(zoomObjectif);

        // pre lock selected widget (selectable widget need a lock)
        zoomObjectif.lockSelected();

        // ZOOM PERCENT
        ZoomPercentPlugin zoomPercent = new ZoomPercentPlugin();
        zoomPercent.registerContext(new ZoomPercentDefaultDeviceContext());
        w2d.registerPlugin(zoomPercent);

        MarkerPlugin markerPlugin = new MarkerPlugin();
        markerPlugin.registerContext(new MarkerDefaultDeviceContext());
        w2d.registerPlugin(markerPlugin);

        OutlinePlugin outline = new OutlinePlugin(NanoChromatique.ORANGE);
        // w2d.registerPlugin(outline);
        // outline.inflateOutline(2000);

        registerProjection(w2d);

        // pre lock selected
        // translatePlugin.lockSelected();

       
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final ViewFrameUI demoFrame = new ViewFrameUI(new ButtonWidgetDemo());
       
    }

}
