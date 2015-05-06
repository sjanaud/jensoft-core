/*
 * JenSoft API - Charting Framework
 * http://www.jensoft.org
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package org.jensoft.core.x2d.template.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jensoft.core.catalog.nature.JenSoftView;
import org.jensoft.core.graphics.Shader;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.palette.color.Spectral;
import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PiePlugin;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.PieToolkit;
import org.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import org.jensoft.core.plugin.pie.painter.effect.PieReflectionEffect;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.Portfolio;
import org.jensoft.core.view.View;
import org.jensoft.core.view.background.ViewDefaultBackground;
import org.jensoft.core.x2d.X2D;
import org.jensoft.core.x2d.X2DException;
import org.w3c.dom.Document;

@JenSoftView
public class PieDeflateTest extends View {

	private static final long serialVersionUID = 156889765687899L;

	@Portfolio(name = "PieReflectionEffectDemo", width = 800, height = 600)
	public static View getPortofolio() {
		PieDeflateTest demo = new PieDeflateTest();

		ViewDefaultBackground viewBackground = new ViewDefaultBackground();
		Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		viewBackground.setShader(s);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));

		demo.setBackgroundPainter(viewBackground);
		return demo;
	}
	
	public static void main(String[] args) {
		PieDeflateTest v = new PieDeflateTest();
		v.deflate();
	}
	
	public void deflate(){
		try {
			X2D x2d = new X2D();
			x2d.registerView(this);
			Document x2ddoc = x2d.getX2dDocument();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource source = new DOMSource(x2ddoc);
			StreamResult result = new StreamResult(new File("C:"+File.separator+"usr"+File.separator+"temp"+File.separator+getName()+".xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
			
		} catch (X2DException e) {
			
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PieDeflateTest() {
		super(0);

		
		setName("pie-deflate");
		ViewDefaultBackground viewBackground = new ViewDefaultBackground();
		viewBackground.setShader(Shader.Night);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));
		setBackgroundPainter(viewBackground);

		Projection window = new Projection.Linear(-1, 1, -3, 3);
		registerProjection(window);

		PiePlugin piePlugin = new PiePlugin();
		window.registerPlugin(piePlugin);
		// window.registerPlugin(new OutlinePlugin(Color.black));
		Pie pie = PieToolkit.createPie("pie", 70);
		pie.setCenterY(1);
		pie.setStartAngleDegree(25);

		PieLinearEffect linearFX = new PieLinearEffect(90);
		linearFX.setOffsetRadius(4);
		pie.setPieEffect(linearFX);

		PieReflectionEffect reflectionFX = new PieReflectionEffect();
		reflectionFX.setLength(0.6f);
		reflectionFX.setOpacity(0.3f);
		pie.setPieEffect(reflectionFX);

		PieSlice s1 = PieToolkit.createSlice("s1", new Color(240, 240, 240, 240), 30, 0);
		PieSlice s2 = PieToolkit.createSlice("s2", RosePalette.AMETHYST, 15, 0);
		PieSlice s3 = PieToolkit.createSlice("s3", RosePalette.LEMONPEEL, 10, 0);
		PieSlice s4 = PieToolkit.createSlice("s4", NanoChromatique.ORANGE, 5, 0);
		PieSlice s5 = PieToolkit.createSlice("s5", Spectral.SPECTRAL_BLUE2, 20, 20);

		PieToolkit.pushSlices(pie, s1, s2, s3, s4, s5);
		piePlugin.addPie(pie);

	}

}
