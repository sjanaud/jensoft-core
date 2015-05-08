package org.jensoft.core.plugin.plot;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jensoft.core.device.ContextEntry;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.AbstractPlugin.OnDragListener;
import org.jensoft.core.plugin.AbstractPlugin.OnPressListener;
import org.jensoft.core.plugin.AbstractPlugin.OnReleaseListener;
import org.jensoft.core.plugin.plot.spline.AbstractPlot;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

public class PlotAnchorsPlugin extends AbstractPlugin implements OnPressListener, OnReleaseListener, OnDragListener {

	private List<AbstractPlot> plots = new ArrayList<AbstractPlot>();
	private AbstractPlot selectedPlot = null;
	private Map<String, AbstractPlot> mapping = new HashMap<String, AbstractPlot>();

	public PlotAnchorsPlugin() {
		registerContext(new PlotAnchorContext());
		setOnPressListener(this);
		setOnReleaseListener(this);
		setOnDragListener(this);

	}

	private void selectPlot(String key) {
		selectedPlot = mapping.get(key);
		//System.out.println("select plot : " + key + " -->" + selectedPlot);
	}

	class PlotAnchorContext extends ContextEntry<PlotAnchorsPlugin> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.device.ContextEntry#buildContext()
		 */
		@Override
		public void buildContext() {
			JMenu plotAnchorRoot = new JMenu("Plot Anchor");
			int count = 0;
			mapping.clear();
			for (AbstractPlot plot : plots) {
				JMenuItem itemPlot = new JMenuItem("Plot-" + count);
				final int callbackIndex = count++;
				final String key = "Plot-" + callbackIndex;
				mapping.put(key, plot);
				itemPlot.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						selectPlot(key);
					}

				});
				plotAnchorRoot.add(itemPlot);
			}

			setGroup("Plot");
			setItem(plotAnchorRoot);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.device.ContextEntry#isCompatiblePlugin()
		 */
		@Override
		public boolean isCompatiblePlugin() {
			return true;
		}

	}

	/**
	 * register the given plot
	 * 
	 * @param plot
	 *            the plot to paint
	 */
	public void addPlot(AbstractPlot plot) {
		plots.add(plot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java
	 * .awt.event.MouseEvent)
	 */
	@Override
	public void onRelease(MouseEvent me) {
		if (me.isShiftDown()) {
			selectedPlot.removePoint(); // Shift Click removes control points
			getProjection().getView().repaintDevice();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public void onDrag(MouseEvent me) {
		if (selectedPlot != null) {
			selectedPlot.updateAnchorPoint(me.getX(), me.getY());
			getProjection().getView().repaintDevice();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public void onPress(MouseEvent me) {
		if (selectedPlot != null) {
			//selectedPlot.getPlotAnchor(me.getX(), me.getY());
			selectedPlot.selectPlotAnchor(me.getX(), me.getY());
			//System.out.println("select point index : " + selectedIndex);
		}

		// if (selection != null && selection.selectPoint(me.getX(), me.getY())
		// == -1) { /*
		// * no point selected
		// * add new one
		// */
		//
		//
		// selection.addPoint(me.getX(), me.getY());
		// getProjection().getView().repaintDevice();
		// System.out.println("on press plot!");
		// }
		// else{
		// System.err.println("Bad selection");
		// }
	}

	@Override
	protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
	}

}
