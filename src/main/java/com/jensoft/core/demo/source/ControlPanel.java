/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.source;

import java.awt.AWTError;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextPane;

public class ControlPanel extends JComponent {

	private ClipboardService clipboardService;

	private Clipboard clipboardSystem;

	public ControlPanel(final JTextPane textPane) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);
		try {
			clipboardService = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
		} catch (UnavailableServiceException e) {
		}

		try {
			clipboardSystem = Toolkit.getDefaultToolkit().getSystemClipboard();
		} catch (HeadlessException ex) {
		} catch (AWTError err) {
		}

		if(clipboardService != null || clipboardSystem != null){
			JButton copy = new JButton("copy");
			copy.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
	
					try {
						StringSelection data;
						data = new StringSelection(textPane.getText());
						if (clipboardService != null) {
							System.out.println("copy to service clipboard");
							clipboardService.setContents(data);
						}
						else if(clipboardSystem != null){
							System.out.println("copy to system clipboard");
							clipboardSystem.setContents(data, null);
						}
					} catch (Exception e1) {
					}
	
				}
			});
			
			add(Box.createGlue());
			add(copy);
			add(Box.createHorizontalStrut(40));
		}
		
	}

	private static final long serialVersionUID = -120338937746225277L;

}
