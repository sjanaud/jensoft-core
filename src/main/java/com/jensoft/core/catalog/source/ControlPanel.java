/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.source;

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

	//private Clipboard clipboardSystem;

	public ControlPanel(final JTextPane textPane) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);
		try {
			clipboardService = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
		} catch (UnavailableServiceException e) {
		}

//		if(clipboardService == null){
//			try {
//				clipboardSystem = Toolkit.getDefaultToolkit().getSystemClipboard();
//			} catch (HeadlessException ex) {
//			} catch (AWTError err) {
//			}
//		}

		if(clipboardService != null){
			JButton copy = new JButton("copy");
			copy.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
	
					try {
						StringSelection data;
						data = new StringSelection(textPane.getText());
						if (clipboardService != null) {
							clipboardService.setContents(data);
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
