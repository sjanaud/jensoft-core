/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.palette.color;

import java.awt.Color;

import org.jensoft.core.graphics.Shader;

/**
 * <code>ShaderPalette</code>
 * 
 * @author Sebastien Janaud
 */
public class ShaderPalette {

	public static Shader BlackShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150), new Color(0, 0, 0, 200), new Color(0, 0, 0, 150), new Color(0, 0, 0, 20) });
	public static Shader BlackShader2 = new Shader(new float[] { 0f, 1f }, new Color[] { ColorPalette.darker(RosePalette.BLACK, 0.5f), ColorPalette.brighter(RosePalette.BLACK, 0.4f) });
	public static Shader AEGEANBLUEShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.AEGEANBLUE, 60), new Alpha(RosePalette.AEGEANBLUE, 150), new Alpha(RosePalette.AEGEANBLUE, 200), new Alpha(RosePalette.AEGEANBLUE, 150), new Alpha(RosePalette.AEGEANBLUE, 20) });
	public static Shader AMETHYSTShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.AMETHYST, 60), new Alpha(RosePalette.AMETHYST, 150), new Alpha(RosePalette.AMETHYST, 200), new Alpha(RosePalette.AMETHYST, 150), new Alpha(RosePalette.AMETHYST, 60) });
	public static Shader AZALEAShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.AZALEA, 60), new Alpha(RosePalette.AZALEA, 150), new Alpha(RosePalette.AZALEA, 200), new Alpha(RosePalette.AZALEA, 150), new Alpha(RosePalette.AZALEA, 60) });
	public static Shader BORDEAUXShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.BORDEAUX, 60), new Alpha(RosePalette.BORDEAUX, 150), new Alpha(RosePalette.BORDEAUX, 200), new Alpha(RosePalette.BORDEAUX, 150), new Alpha(RosePalette.BORDEAUX, 60) });
	public static Shader CALYPSOBLUEShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.CALYPSOBLUE, 60), new Alpha(RosePalette.CALYPSOBLUE, 150), new Alpha(RosePalette.CALYPSOBLUE, 200), new Alpha(RosePalette.CALYPSOBLUE, 150), new Alpha(RosePalette.CALYPSOBLUE, 60) });
	public static Shader CALYPSOBLUEShader2 = new Shader(new float[] { 0f, 1f }, new Color[] { ColorPalette.brighter(RosePalette.CALYPSOBLUE, 0.9f), ColorPalette.brighter(RosePalette.CALYPSOBLUE, 0.4f) });
	public static Shader CALYPSOBLUEShader3 = new Shader(new float[] { 0f, 1f }, new Color[] { ColorPalette.darker(RosePalette.CALYPSOBLUE, 0.9f), ColorPalette.brighter(RosePalette.CALYPSOBLUE, 0.4f) });
	public static Shader EMERALDShader = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { new Alpha(RosePalette.EMERALD, 120), new Alpha(RosePalette.EMERALD, 200), new Alpha(RosePalette.EMERALD, 255), new Alpha(RosePalette.EMERALD, 200), new Alpha(RosePalette.EMERALD, 120) });
	public static Shader EMERALDShader2 = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { RosePalette.EMERALD.brighter(), RosePalette.EMERALD, RosePalette.EMERALD.darker(), RosePalette.EMERALD, RosePalette.EMERALD.brighter() });
	public static Shader EMERALDShader3 = new Shader(new float[] { 0f, 0.3f, 0.5f, 0.7f, 1f }, new Color[] { RosePalette.EMERALD.darker(), RosePalette.EMERALD, RosePalette.EMERALD.brighter(), RosePalette.EMERALD, RosePalette.EMERALD.darker() });
	public static Shader EMERALDShader4 = new Shader(new float[] { 0f, 1f }, new Color[] { ColorPalette.brighter(RosePalette.EMERALD, 0.9f), ColorPalette.brighter(RosePalette.EMERALD, 0.3f) });

}
