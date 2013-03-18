package com.jensoft.core.demo.source;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jnlp.ClipboardService;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextPane;

public class ControlPanel extends JComponent {
	

	public ControlPanel(final JTextPane textPane,final ClipboardService cs) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);

		JButton copy = new JButton("copy");
		copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					StringSelection data;
					data = new StringSelection(textPane.getText());
					cs.setContents(data);
				} catch (Exception e1) {
				}

			}
		});
		add(Box.createGlue());
		add(copy);
		add(Box.createHorizontalStrut(40));
	}

	private static final long serialVersionUID = -120338937746225277L;

}
