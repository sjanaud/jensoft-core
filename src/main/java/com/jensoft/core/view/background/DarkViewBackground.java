package com.jensoft.core.view.background;

import java.awt.BasicStroke;
import java.awt.Color;

import com.jensoft.core.graphics.Shader;

public class DarkViewBackground extends RoundViewFill {

	public DarkViewBackground() {
		Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		setShader(sb);
		setOutlineStroke(new BasicStroke(2.5f));
	}

}
