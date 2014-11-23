/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.shell.test;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.shell.Shell;
import com.jensoft.core.plugin.shell.ShellPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;


public class ShellTest extends View {

	public ShellTest() {
		super();

		Projection proj = new Projection.Linear(-10, 10, -10, 10);
		registerProjection(proj);

		final ShellPlugin shellPlugin = new ShellPlugin();
		proj.registerPlugin(shellPlugin);

		// messagePlugin.registerWidget(new MessageSelecterWidget());
		// messagePlugin.registerContext(new MessageDefaultDeviceContext());
		final Shell msg = new Shell("shell") {

			@Override
			protected JComponent createShellEditor() {
				JPanel editor = new JPanel();
				editor.setBackground(Color.YELLOW);
				return editor;
			}
		};

		// msg.getEditor().setLayout(new BorderLayout());
		// JPanel yellow = new JPanel();
		// yellow.setBackground(Color.YELLOW);
		// msg.getEditor().add(yellow,BorderLayout.CENTER);

		// shellPlugin.showVolatileMessage(msg);
		// Message msg2 = new Message("Demo 2", "message demo 2");
		// msg2.setMessageForeground(Spectral.SPECTRAL_GREEN);
		// msg2.setMessageTitleColor(Color.CYAN);
		// msg2.setMessageFont(InputFonts.getNeuropol(12));
		// msg2.setScrollerColor(Color.CYAN);
		// Message msg3 = new Message("titre 3", "message 3");
		// Message msg4 = new Message("titre 4", "message 4");
		// msg4.setMessageDimension(400, 200);
		// Message msg5 = new Message("titre 5", "message 5");
		// Message msg6 = new Message("titre 6", "message 6");
		// Message msg7 = new Message("titre 7", "message 7");
		//
		// messagePlugin.registerMessage(msg);
		// messagePlugin.registerMessage(msg2);
		// messagePlugin.registerMessage(msg3);
		// messagePlugin.registerMessage(msg4);
		// messagePlugin.registerMessage(msg5);
		// messagePlugin.registerMessage(msg6);
		// messagePlugin.registerMessage(msg7);

		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					shellPlugin.showVolatileMessage(msg);
				} catch (InterruptedException e) {
				}
			}
		};
		t.start();

		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final ShellTest demo = new ShellTest();

		final ViewFrameUI demoFrame = new ViewFrameUI(demo);
		
	}

}
