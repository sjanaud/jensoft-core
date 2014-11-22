/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.map.layer.highway.GroupRenderingProperties;
import com.jensoft.core.map.layer.highway.Highway;
import com.jensoft.core.map.layer.highway.HighwayGroup;
import com.jensoft.core.map.layer.highway.HighwayLayer;
import com.jensoft.core.map.layer.highway.HighwayNature;
import com.jensoft.core.map.primitive.Node;
import com.jensoft.core.map.primitive.Primitive;
import com.jensoft.core.map.primitive.Way;
import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.View;
import com.jensoft.core.view.background.RoundViewFill;


public class TraceDemo extends View {

	// GeoPosition g1 = new GeoPosition(25.8575551, -80.1284813);
	// GeoPosition g2 = new GeoPosition(25.8574182, -80.1287591);
	// GeoPosition g3 = new GeoPosition(25.857202, -80.1291024);
	// GeoPosition g4 = new GeoPosition(25.8571247, -80.1295144);
	// GeoPosition g5 = new GeoPosition(25.8571711, -80.1299436);
	// GeoPosition g6 = new GeoPosition(25.8571865, -80.1305787);
	// GeoPosition g7 = new GeoPosition(25.8572483, -80.1313512);
	// GeoPosition g8 = new GeoPosition(25.8571865, -80.1320636);
	// GeoPosition g9 = new GeoPosition(25.8569548, -80.1326043);
	//
	// GeoPosition g10 = new GeoPosition(25.8567076, -80.1331365);
	// GeoPosition g11 = new GeoPosition(25.8563369, -80.1336944);
	// GeoPosition g12 = new GeoPosition(25.8560357, -80.1341836);
	// GeoPosition g13 = new GeoPosition(25.8556031, -80.1348445);
	// GeoPosition g14 = new GeoPosition(25.8552015, -80.135514);
	// GeoPosition g15 = new GeoPosition(25.8548925, -80.1362178);
	GeoPosition g16 = new GeoPosition(25.8546608, -80.1372134);
	GeoPosition g17 = new GeoPosition(25.8544677, -80.1380718);
	GeoPosition g18 = new GeoPosition(25.8541819, -80.1390331);
	GeoPosition g19 = new GeoPosition(25.8540583, -80.1397626);
	GeoPosition g20 = new GeoPosition(25.8540583, -80.1408527);

	GeoPosition g21 = new GeoPosition(25.8540429, -80.1418483);
	GeoPosition g22 = new GeoPosition(25.8538884, -80.1426551);
	GeoPosition g23 = new GeoPosition(25.8536567, -80.1435821);
	GeoPosition g24 = new GeoPosition(25.8534404, -80.1445262);
	GeoPosition g25 = new GeoPosition(25.8531911, -80.1453899);
	GeoPosition g26 = new GeoPosition(25.8522642, -80.1450037);
	GeoPosition g27 = new GeoPosition(25.8516463, -80.1446604);
	GeoPosition g28 = new GeoPosition(25.8509511, -80.1440166);
	GeoPosition g29 = new GeoPosition(25.8504104, -80.1434158);
	GeoPosition g30 = new GeoPosition(25.8498311, -80.1426433);
	GeoPosition g31 = new GeoPosition(25.8494835, -80.141785);
	GeoPosition g32 = new GeoPosition(25.8491359, -80.1407551);
	GeoPosition g33 = new GeoPosition(25.8489042, -80.1395963);
	GeoPosition g34 = new GeoPosition(25.8488655, -80.1384376);
	GeoPosition g35 = new GeoPosition(25.84902, -80.1373647);
	GeoPosition g36 = new GeoPosition(25.8492131, -80.1363348);
	GeoPosition g37 = new GeoPosition(25.8495993, -80.1353906);
	GeoPosition g38 = new GeoPosition(25.8502559, -80.1342319);
	GeoPosition g39 = new GeoPosition(25.8509511, -80.133159);
	GeoPosition g40 = new GeoPosition(25.8514531, -80.1323866);
	GeoPosition g41 = new GeoPosition(25.8520325, -80.1313995);
	GeoPosition g42 = new GeoPosition(25.8522642, -80.1304125);
	GeoPosition g43 = new GeoPosition(25.8523414, -80.1290392);
	GeoPosition g44 = new GeoPosition(25.8524573, -80.1280521);
	GeoPosition g45 = new GeoPosition(25.8524959, -80.1270651);
	GeoPosition g46 = new GeoPosition(25.8525345, -80.1262926);
	GeoPosition g47 = new GeoPosition(25.852689, -80.1258634);
	GeoPosition g48 = new GeoPosition(25.8529593, -80.1256489);
	GeoPosition g49 = new GeoPosition(25.8534614, -80.1255201);
	// GeoPosition g50 = new GeoPosition(25.8538862, -80.1256489);
	// GeoPosition g51 = new GeoPosition(25.8543497, -80.126078);
	// GeoPosition g52 = new GeoPosition(25.8547745, -80.1264213);
	// GeoPosition g53 = new GeoPosition(25.8553306, -80.1267647);
	// GeoPosition g54 = new GeoPosition(25.8557631, -80.1270393);
	// GeoPosition g55 = new GeoPosition(25.8561262, -80.1273483);
	// GeoPosition g56 = new GeoPosition(25.8564583, -80.1275801);
	// GeoPosition g57 = new GeoPosition(25.8568599, -80.1278976);
	// GeoPosition g58 = new GeoPosition(25.8569989, -80.1280521);
	// GeoPosition g59 = new GeoPosition(25.8572307, -80.1282238);
	// GeoPosition g60 = new GeoPosition(25.8575551, -80.1284813);

	private List<GeoPosition> testPositions = new ArrayList<GeoPosition>();

	public TraceDemo() {

		super();
		createPositions();
		final Projection w2d = new Projection.Map();
		w2d.setName("map window");
		w2d.setThemeColor(Color.WHITE);
		registerProjection(w2d);

		// TracePlugin tracePlugin = new TracePlugin();
		// w2d.registerPlugin(tracePlugin);

		MapLayerPlugin mapLayerPlugin = new MapLayerPlugin();
		w2d.registerPlugin(mapLayerPlugin);

		HighwayLayer highwayLayer = new HighwayLayer();
		mapLayerPlugin.registerLayer(highwayLayer);

		// configure map layers for this level
		HighwayGroup residentialGroup = highwayLayer.getGroup(HighwayNature.RESIDENTIAL);
		GroupRenderingProperties residentialProperties = new GroupRenderingProperties(14, Color.WHITE);
		residentialGroup.setRenderingProperties(residentialProperties);

		HighwayGroup unclassifiedGroup = highwayLayer.getGroup(HighwayNature.UNCLASSIFIED);
		GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(14, Color.WHITE);
		unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

		HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
		GroupRenderingProperties roadProperties = new GroupRenderingProperties(14, Color.WHITE);
		roadGroup.setRenderingProperties(roadProperties);

		HighwayGroup primaryGroup = highwayLayer.getGroup(HighwayNature.PRIMARY);
		GroupRenderingProperties primaryProperties = new GroupRenderingProperties(14, Color.WHITE);
		primaryGroup.setRenderingProperties(primaryProperties);

		HighwayGroup secondaryGroup = highwayLayer.getGroup(HighwayNature.SECONDARY);
		GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(14, Color.WHITE);
		secondaryGroup.setRenderingProperties(secondaryProperties);

		HighwayGroup tertiaryGroup = highwayLayer.getGroup(HighwayNature.TERTIARY);
		GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(14, Color.WHITE);
		tertiaryGroup.setRenderingProperties(tertiaryProperties);

		HighwayGroup serviceGroup = highwayLayer.getGroup(HighwayNature.SERVICE);
		GroupRenderingProperties serviceProperties = new GroupRenderingProperties(6, Color.WHITE);
		serviceGroup.setRenderingProperties(serviceProperties);

		HighwayGroup motorwayGroup = highwayLayer.getGroup(HighwayNature.MOTORWAY);
		GroupRenderingProperties motorwayProperties = new GroupRenderingProperties(14, Color.WHITE);
		motorwayGroup.setRenderingProperties(motorwayProperties);

		HighwayGroup motorwayLinkGroup = highwayLayer.getGroup(HighwayNature.MOTORWAY_LINK);
		GroupRenderingProperties motorwayLinkProperties = new GroupRenderingProperties(14, Color.WHITE);
		motorwayLinkGroup.setRenderingProperties(motorwayLinkProperties);

		HighwayGroup trunkGroup = highwayLayer.getGroup(HighwayNature.TRUNK);
		GroupRenderingProperties trunkProperties = new GroupRenderingProperties(14, Color.WHITE);
		trunkGroup.setRenderingProperties(trunkProperties);

		HighwayGroup trunkLinkGroup = highwayLayer.getGroup(HighwayNature.TRUNK_LINK);
		GroupRenderingProperties trunkLinkProperties = new GroupRenderingProperties(14, Color.WHITE);
		trunkLinkGroup.setRenderingProperties(trunkLinkProperties);

		HighwayGroup pedestrianGroup = highwayLayer.getGroup(HighwayNature.PEDESTRIAN);
		GroupRenderingProperties pedestrianProperties = new GroupRenderingProperties(6, Color.WHITE);
		pedestrianGroup.setRenderingProperties(pedestrianProperties);

		Highway highway = new Highway(1, HighwayNature.RESIDENTIAL);
		highway.setName("jensoft API highway");

		Way w = new Way();
		w.setId(1);
		List<Node> nodes = new ArrayList<Node>();
		w.setNodes(nodes);

		int count = 0;
		for (GeoPosition position : testPositions) {
			Node n = new Node(count++);
			n.setGeoPosition(position);
			nodes.add(n);
		}

		Primitive p = new Primitive(w);
		highway.setPrimitive(p);
		highwayLayer.registerHighway(highway);

		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					// wait for view obtain a size from ui
					Thread.sleep(2000);
					System.out.println("min x " + w2d.getMinX());
					System.out.println("max x " + w2d.getMaxX());
					System.out.println("min y " + w2d.getMinY());
					System.out.println("max y " + w2d.getMaxY());
				} catch (InterruptedException e) {
				}
			}
		};
		t.start();

	}

	private void createPositions() {
		// testPositions.add(g1);
		// testPositions.add(g2);
		// testPositions.add(g3);
		// testPositions.add(g4);
		// testPositions.add(g5);
		// testPositions.add(g6);
		// testPositions.add(g7);
		// testPositions.add(g8);
		// testPositions.add(g9);
		// testPositions.add(g10);
		// testPositions.add(g11);
		// testPositions.add(g12);
		// testPositions.add(g13);
		// testPositions.add(g14);
		// testPositions.add(g15);
		testPositions.add(g16);
		testPositions.add(g17);
		testPositions.add(g18);
		testPositions.add(g19);
		testPositions.add(g20);

		testPositions.add(g21);
		testPositions.add(g22);
		testPositions.add(g23);
		testPositions.add(g24);
		testPositions.add(g25);
		testPositions.add(g26);
		testPositions.add(g27);
		testPositions.add(g28);
		testPositions.add(g29);
		testPositions.add(g30);
		testPositions.add(g31);
		testPositions.add(g32);
		testPositions.add(g33);
		testPositions.add(g34);
		testPositions.add(g35);
		testPositions.add(g36);
		testPositions.add(g37);
		testPositions.add(g38);
		testPositions.add(g39);
		testPositions.add(g40);
		testPositions.add(g41);
		testPositions.add(g42);
		testPositions.add(g43);
		testPositions.add(g44);
		testPositions.add(g45);
		testPositions.add(g46);
		testPositions.add(g47);
		testPositions.add(g48);
		testPositions.add(g49);
		// testPositions.add(g50);
		// testPositions.add(g51);
		// testPositions.add(g52);
		// testPositions.add(g53);
		// testPositions.add(g54);
		// testPositions.add(g55);
		// testPositions.add(g56);
		// testPositions.add(g57);
		// testPositions.add(g58);
		// testPositions.add(g59);
		// testPositions.add(g60);
	}

	@Portfolio(name = "FreeGridDemo", width = 500, height = 250)
	public static View getPortofolio() {
		TraceDemo demo = new TraceDemo();

		RoundViewFill viewBackground = new RoundViewFill();
		Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		viewBackground.setShader(s);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));

		demo.setBackgroundPainter(viewBackground);
		return demo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final TraceDemo demo = new TraceDemo();

		final ViewFrameUI demoFrame = new ViewFrameUI(demo);
		

	}

}
