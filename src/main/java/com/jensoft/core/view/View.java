/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import com.jensoft.core.device.DevicePartComponent;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.projection.ProjectionEvent;
import com.jensoft.core.projection.ProjectionListener;
import com.jensoft.core.view.background.BackgroundPainter;
import com.jensoft.core.widget.WidgetFolder;

/**
 * <code>View</code> defines the end chart view.
 * 
 * @since 1.0
 * @author Sebastien Janaud
 */
public class View extends JComponent implements ProjectionListener, ComponentListener, FocusListener, MouseListener {

	private final static long serialVersionUID = 1l;

	/** place holder axis west in pixel */
	private int placeHolderAxisWest = 40;

	/** place holder axis east in pixel */
	private int placeHolderAxisEast = 40;

	/** place holder axis south in pixel */
	private int placeHolderAxisSouth = 40;

	/** place holder axis north in pixel */
	private int placeHolderAxisNorth = 40;

	/** window part south */
	private ViewPartComponent axisSouth;

	/** window part north */
	private ViewPartComponent axisNorth;

	/** window part west */
	private ViewPartComponent axisWest;

	/** window part east */
	private ViewPartComponent axisEast;

	/** window device part */
	private DevicePartComponent device2D;

	/** window container */
	private JPanel windowContainer;

	/** view header container */
	private JPanel headerContainer;

	/** view footer container */
	private JPanel footerContainer;

	/** painter that takes responsibility to paint the view background component */
	private BackgroundPainter backgroundPainter;

	/**
	 * the widget plug-in is a specific plug-in to handle widget and window meta
	 * data
	 */
	private WidgetPlugin widgetPlugin = new WidgetPlugin();

	/** registered view listener */
	protected EventListenerList view2DListenerList = new EventListenerList();

	/** the widget folder guard interval */
	private int folderGuardInterval = 4;

	/** projections registry */
	private List<Projection> projections = new Vector<Projection>();

	/** the active window */
	private Projection activeProjection;

	/** view key */
	private String viewKey;

	/** API key */
	private String apiKey;

	/**
	 * create default view
	 */
	public View() {
		initComponent();
	}

	/**
	 * create a view with specified place holder weight in pixel for each parts.
	 * 
	 * @param placeHolderAxisWest
	 * @param placeHolderAxisEast
	 * @param placeHolderAxisSouth
	 * @param placeHolderAxisNorth
	 */
	public View(int placeHolderAxisWest, int placeHolderAxisEast, int placeHolderAxisSouth, int placeHolderAxisNorth) {
		super();
		this.placeHolderAxisWest = placeHolderAxisWest;
		this.placeHolderAxisEast = placeHolderAxisEast;
		this.placeHolderAxisSouth = placeHolderAxisSouth;
		this.placeHolderAxisNorth = placeHolderAxisNorth;
		initComponent();
	}

	/**
	 * create a view with global place holder weight in pixel
	 * 
	 * @param placeHolderAxis
	 */
	public View(int placeHolderAxis) {
		super();
		placeHolderAxisWest = placeHolderAxis;
		placeHolderAxisEast = placeHolderAxis;
		placeHolderAxisSouth = placeHolderAxis;
		placeHolderAxisNorth = placeHolderAxis;
		initComponent();
	}

	/**
	 * get the view header
	 * 
	 * @return the header container
	 */
	public JPanel getHeaderContainer() {
		return headerContainer;
	}

	/**
	 * get the view footer
	 * 
	 */
	public JPanel getFooterContainer() {
		return footerContainer;
	}

	/**
	 * get the device component
	 * 
	 * @return device ârt
	 */
	public DevicePartComponent getDevice2D() {
		return device2D;
	}

	/**
	 * repaint the device
	 */
	public void repaintDevice() {
		device2D.repaintDevice();
	}

	/**
	 * repaint the device with the specified framing parameters
	 */
	public void repaintDevice(int x, int y, int width, int height) {
		device2D.repaint(x - 2, y - 2, width + 4, height + 4);
	}

	/**
	 * repaint the device with the specified framing rectangle
	 */
	public void repaintDevice(Rectangle rect) {
		device2D.repaint((int) (rect.getX() - 2), (int) (rect.getY() - 2), (int) (rect.getWidth() + 4), (int) (rect.getHeight() + 4));
	}

	/**
	 * deviceBand define two type of band fragment, XBand and YBand
	 */
	public enum DeviceBand {
		YBand, XBand;
	}

	/**
	 * repaint device band fragment
	 * 
	 * @param deviceBand
	 *            a XBand or YBand type
	 * @param start
	 *            the start fragment coordinate
	 * @param delta
	 *            the delta in pixel
	 */
	public void repaintDeviceBand(DeviceBand deviceBand, int start, int delta) {
		if (deviceBand == DeviceBand.XBand) {
			device2D.repaint(start, 0, delta, device2D.getHeight());
		}
		if (deviceBand == DeviceBand.YBand) {
			device2D.repaint(0, start, device2D.getWidth(), delta);
		}
	}

	/**
	 * repaint the view
	 */
	public void repaintView() {
		// device2D.repaintDevice();
		windowContainer.repaint();
	}

	/**
	 * repaint specified part
	 */
	public void repaintPart(ViewPart viewPart) {
		if (viewPart == ViewPart.North) {
			axisNorth.repaint();
		} else if (viewPart == ViewPart.South) {
			axisSouth.repaint();
		} else if (viewPart == ViewPart.West) {
			axisWest.repaint();
		} else if (viewPart == ViewPart.East) {
			axisEast.repaint();
		} else if (viewPart == ViewPart.Device) {
			device2D.repaintDevice();
		}

		windowContainer.repaint();
	}

	/**
	 * initialize view
	 */
	private void initComponent() {
		setLayout(new BorderLayout());
		setFocusable(true);

		windowContainer = new JPanel();
		windowContainer.setLayout(new BorderLayout());
		windowContainer.setOpaque(false);
		createView();

		add(windowContainer, BorderLayout.CENTER);

		headerContainer = new JPanel();
		headerContainer.setLayout(new BorderLayout());
		headerContainer.setOpaque(false);
		add(headerContainer, BorderLayout.NORTH);

		footerContainer = new JPanel();
		footerContainer.setLayout(new BorderLayout());
		footerContainer.setOpaque(false);
		add(footerContainer, BorderLayout.SOUTH);

		widgetPlugin.setView2D(this);

		addComponentListener(this);
		addFocusListener(this);
		addMouseListener(this);
	}

	/**
	 * get active projection
	 * 
	 * @return active projection
	 */
	public Projection getActiveProjection() {
		return activeProjection;
	}

	/**
	 * set active projection
	 * 
	 * @param active
	 *            the projection to active
	 */
	public void setActiveProjection(Projection active) {
		for (Projection w2d : getProjections()) {
			w2d.unlockActive();
		}
		activeProjection = active;
		if (active != null) {
			activeProjection.lockActive();
			fireProjectionSelected();
		}
	}

	/**
	 * add a view listener
	 * 
	 * @param listener
	 */
	public void addView2DListener(ViewListener listener) {
		view2DListenerList.add(ViewListener.class, listener);
	}

	/**
	 * remove the view listener
	 * 
	 * @param listener
	 */
	public void removeView2DListener(ViewListener listener) {
		view2DListenerList.remove(ViewListener.class, listener);
	}

	/**
	 * fire listener that the projection has changed
	 */
	private void fireProjectionSelected() {
		ViewEvent v2dEvent = new ViewEvent(this);
		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewProjectionSelected(v2dEvent);
				}
			}
		}
	}

	/**
	 * get the registered window in this view
	 * 
	 * @return the windows collection
	 */
	public List<Projection> getProjections() {
		return projections;
	}

	/**
	 * register a projection in this view
	 * 
	 * @param projection
	 */
	public void registerProjection(Projection proj) {
		if (projections.contains(proj)) {
			return;
		}
		if (proj.getName() == null) {
			proj.setName("Proj " + projections.size());
		}

		projections.add(proj);
		proj.setDevice2D(device2D);
		proj.setView2D(this);
		proj.addProjectionListener(this);

		setActiveProjection(proj);

		device2D.registerProjection(proj);

		proj.onView2DRegister();

	}

	/**
	 * unregister the specified projection from this view
	 * 
	 * @param proj
	 *            the proj to remove
	 */
	public void unregisterProjection(Projection proj) {
		if (proj == null) {
			return;
		}
		if (proj.equals(getActiveProjection())) {
			setActiveProjection(null);
		}
		projections.remove(proj);
		device2D.unregisterProjection(proj);
		if (projections.size() > 0) {
			setActiveProjection(projections.get(projections.size() - 1));
		}
	}

	/**
	 * get the window border component for the specified part, west, east,
	 * north, south or device
	 * 
	 * @param viewPart
	 * @return the view part component
	 */
	public JComponent getWindowComponent(ViewPart viewPart) {
		if (viewPart == ViewPart.North) {
			return axisNorth;
		} else if (viewPart == ViewPart.South) {
			return axisSouth;
		} else if (viewPart == ViewPart.West) {
			return axisWest;
		} else if (viewPart == ViewPart.East) {
			return axisEast;
		} else if (viewPart == ViewPart.Device) {
			return device2D;
		}

		return null;
	}

	/**
	 * view creation
	 */
	private void createView() {
		setDoubleBuffered(false);
		
		axisEast = new ViewPartComponent(ViewPart.East, this);
		Dimension dimMarginRight = new Dimension(placeHolderAxisEast, 10);
		axisEast.setPreferredSize(dimMarginRight);
		axisEast.setDoubleBuffered(false);

		axisWest = new ViewPartComponent(ViewPart.West, this);
		Dimension dimMarginLeft = new Dimension(placeHolderAxisWest, 10);
		axisWest.setPreferredSize(dimMarginLeft);
		axisWest.setDoubleBuffered(false);

		axisNorth = new ViewPartComponent(ViewPart.North, this);
		Dimension dimMarginNorth = new Dimension(10, placeHolderAxisNorth);
		axisNorth.setPreferredSize(dimMarginNorth);
		axisNorth.setDoubleBuffered(false);

		axisSouth = new ViewPartComponent(ViewPart.South, this);
		Dimension dimMarginSouth = new Dimension(10, placeHolderAxisSouth);
		axisSouth.setPreferredSize(dimMarginSouth);
		axisSouth.setDoubleBuffered(false);

		device2D = new DevicePartComponent();
		device2D.setDoubleBuffered(false);
		
		
		device2D.setView(this);

		addView2DListener(device2D);
		windowContainer.add(axisNorth, BorderLayout.NORTH);
		windowContainer.add(axisSouth, BorderLayout.SOUTH);
		windowContainer.add(axisEast, BorderLayout.EAST);
		windowContainer.add(axisWest, BorderLayout.WEST);
		windowContainer.add(device2D, BorderLayout.CENTER);

	}

	/**
	 * set the device background, no effect if device is non opaque
	 * 
	 * @param deviceBackground
	 *            the device background to set
	 */
	public void setDeviceBackground(Color deviceBackground) {
		device2D.setOpaque(true);
		device2D.setBackground(deviceBackground);

	}

	/**
	 * set the parts background, no effect if device is non opaque
	 * 
	 * @param partBackground
	 *            the fill color
	 * @param windowParts
	 *            the part array to fill
	 */
	public void setPartBackground(Color partBackground, ViewPart... windowParts) {
		for (int i = 0; i < windowParts.length; i++) {
			JComponent compPart = getWindowComponent(windowParts[i]);
			if (compPart != null) {
				compPart.setOpaque(true);
				compPart.setBackground(partBackground);
			}
		}

	}

	/**
	 * set the specified color for all view2D parts
	 */
	@Override
	public void setBackground(Color bg) {
		setPartBackground(bg, ViewPart.Device, ViewPart.East, ViewPart.West, ViewPart.North, ViewPart.South);
	}

	/**
	 * get the widget plugin
	 * 
	 * @return the widget plugin
	 */
	public WidgetPlugin getWidgetPlugin() {
		return widgetPlugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DBoundChanged(com.jensoft
	 * .core.window.Window2DEvent)
	 */
	@Override
	public void projectionBoundChanged(ProjectionEvent w2dEvent) {
		axisEast.repaint();
		axisWest.repaint();
		axisNorth.repaint();
		axisSouth.repaint();
		device2D.repaintDevice();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DLockActive(com.jensoft
	 * .core.window.Window2DEvent)
	 */
	@Override
	public void projectionLockActive(ProjectionEvent w2dEvent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DUnlockActive(com.jensoft
	 * .core.window.Window2DEvent)
	 */
	@Override
	public void projectionUnlockActive(ProjectionEvent w2dEvent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DResized(com.jensoft.
	 * core.window.Window2DEvent)
	 */
	@Override
	public void projectionResized(ProjectionEvent w2dEvent) {
	}

	/**
	 * set the place holder for the specified window part except device. the
	 * place holder is the weight in pixel for the specified part,
	 * {@link ViewPart#East}, {@link ViewPart#West},
	 * {@link ViewPart#North} and {@link ViewPart#South}.
	 * 
	 * @param placeHolder
	 *            the place holder to set
	 * @param windowParts
	 *            the window parts
	 */
	public void setPlaceHolder(int placeHolder, ViewPart... windowParts) {
		for (int i = 0; i < windowParts.length; i++) {
			ViewPart viewPart = windowParts[i];
			if (viewPart == ViewPart.North) {
				setPlaceHolderAxisNorth(placeHolder);
			} else if (viewPart == ViewPart.South) {
				setPlaceHolderAxisSouth(placeHolder);
			} else if (viewPart == ViewPart.West) {
				setPlaceHolderAxisWest(placeHolder);
			} else if (viewPart == ViewPart.East) {
				setPlaceHolderAxisEast(placeHolder);
			}
		}

	}

	/**
	 * set the place holder for the specified window part except device. the
	 * place holder is the weight in pixel for the specified part,
	 * {@link ViewPart#East}, {@link ViewPart#West},
	 * {@link ViewPart#North} and {@link ViewPart#South}.
	 * 
	 * @param placeHolder
	 *            the place holder to set to the given window part
	 * @param windowPart
	 *            the window part
	 */
	public void setPlaceHolder(int placeHolder, ViewPart viewPart) {
		if (viewPart == ViewPart.North) {
			setPlaceHolderAxisNorth(placeHolder);
		} else if (viewPart == ViewPart.South) {
			setPlaceHolderAxisSouth(placeHolder);
		} else if (viewPart == ViewPart.West) {
			setPlaceHolderAxisWest(placeHolder);
		} else if (viewPart == ViewPart.East) {
			setPlaceHolderAxisEast(placeHolder);
		}
	}

	/**
	 * set the place holder for all border parts. the place holder is the weight
	 * in pixel for the specified part, {@link ViewPart#East},
	 * {@link ViewPart#West}, {@link ViewPart#North} and
	 * {@link ViewPart#South}.
	 * 
	 * @param placeHolder
	 *            the place holder to set to the given window part
	 */
	public void setPlaceHolder(int placeHolder) {
		setPlaceHolderAxisEast(placeHolder);
		setPlaceHolderAxisWest(placeHolder);
		setPlaceHolderAxisNorth(placeHolder);
		setPlaceHolderAxisSouth(placeHolder);
	}

	/**
	 * return the place holder for the west window part border component
	 * 
	 * @return the place holder west
	 */
	public int getPlaceHolderAxisWest() {
		return placeHolderAxisWest;
	}

	/**
	 * set the place holder for the window border west component
	 * 
	 * @param placeHolderAxisWest
	 */
	public void setPlaceHolderAxisWest(int placeHolderAxisWest) {
		int old = getPlaceHolderAxisWest();
		this.placeHolderAxisWest = placeHolderAxisWest;
		Dimension dimMarginLeft = new Dimension(placeHolderAxisWest, 10);
		axisWest.setPreferredSize(dimMarginLeft);
		axisWest.setSize(dimMarginLeft);
		firePropertyChange("placeHolderAxisWEST", old, getPlaceHolderAxisWest());
		revalidate();
	}

	/**
	 * return the place holder for the east window border component
	 * 
	 * @return the place holder east
	 */
	public int getPlaceHolderAxisEast() {
		return placeHolderAxisEast;
	}

	/**
	 * set the place holder for the window border east component
	 * 
	 * @param placeHolderAxisEast
	 */
	public void setPlaceHolderAxisEast(int placeHolderAxisEast) {
		int old = getPlaceHolderAxisEast();
		this.placeHolderAxisEast = placeHolderAxisEast;
		Dimension dimMarginRight = new Dimension(placeHolderAxisEast, 10);
		axisEast.setPreferredSize(dimMarginRight);
		axisEast.setSize(dimMarginRight);
		firePropertyChange("placeHolderAxisEAST", old, getPlaceHolderAxisEast());
		revalidate();
	}

	/**
	 * return the place holder for the south window border component
	 * 
	 * @return the place holder south
	 */
	public int getPlaceHolderAxisSouth() {
		return placeHolderAxisSouth;
	}

	/**
	 * set the place holder for the window border south component
	 * 
	 * @param placeHolderAxisSouth
	 */
	public void setPlaceHolderAxisSouth(int placeHolderAxisSouth) {
		int old = getPlaceHolderAxisSouth();
		this.placeHolderAxisSouth = placeHolderAxisSouth;
		Dimension dimMarginSouth = new Dimension(10, placeHolderAxisSouth);
		axisSouth.setPreferredSize(dimMarginSouth);
		axisSouth.setSize(dimMarginSouth);
		firePropertyChange("placeHolderAxisSOUTH", old, getPlaceHolderAxisSouth());
		revalidate();
	}

	/**
	 * return the place holder for the north window border component
	 * 
	 * @return the place holder north
	 */
	public int getPlaceHolderAxisNorth() {
		return placeHolderAxisNorth;
	}

	/**
	 * set the place holder for the window border north component
	 * 
	 * @param placeHolderAxisNorth
	 */
	public void setPlaceHolderAxisNorth(int placeHolderAxisNorth) {
		int old = getPlaceHolderAxisNorth();
		this.placeHolderAxisNorth = placeHolderAxisNorth;
		Dimension dimMarginNorth = new Dimension(10, placeHolderAxisNorth);
		axisNorth.setPreferredSize(dimMarginNorth);
		axisNorth.setSize(dimMarginNorth);
		firePropertyChange("placeHolderAxisNORTH", old, getPlaceHolderAxisNorth());
		revalidate();
	}

	/**
	 * return the folder instance for the specified position
	 * 
	 * @param id
	 *            the widget id
	 * @param width
	 *            the widget width
	 * @param height
	 *            the widget height
	 * @param xp
	 *            the x position
	 * @param yp
	 *            the y position
	 * @return the folder instance
	 */
	public WidgetFolder newFolderIntanceByPosition(String id, double width, double height, int xp, int yp) {
		double deviceWidth = device2D.getWidth();
		double deviceHeight = device2D.getHeight();

		int folderMaxX = (int) (deviceWidth / (width + 2 * folderGuardInterval));
		int folderMaxY = (int) (deviceHeight / (height + 2 * folderGuardInterval));

		List<WidgetFolder> volatilesFolders = new ArrayList<WidgetFolder>();
		for (int x = 0; x <= folderMaxX; x++) {
			for (int y = 0; y <= folderMaxY; y++) {
				WidgetFolder folder = new WidgetFolder();
				folder.setId(id);
				folder.setWidth(width);
				folder.setHeight(height);
				folder.setxIndex(x);
				folder.setyIndex(y);
				folder.setGuardInterval(folderGuardInterval);
				volatilesFolders.add(folder);
				if (x < folderMaxX && y < folderMaxY) {
					folder.setX((width + 2 * folderGuardInterval) * x + folderGuardInterval);
					folder.setY((height + 2 * folderGuardInterval) * y + folderGuardInterval);
				} else if (x == folderMaxX && y == folderMaxY) {
					folder.setX(deviceWidth - width - folderGuardInterval);
					folder.setY(deviceHeight - height - folderGuardInterval);
				} else if (x < folderMaxX && y == folderMaxY) {
					folder.setX((width + 2 * folderGuardInterval) * x + folderGuardInterval);
					folder.setY(deviceHeight - height - folderGuardInterval);
				} else if (x == folderMaxX && y < folderMaxY) {
					folder.setX(deviceWidth - width - folderGuardInterval);
					folder.setY((height + 2 * folderGuardInterval) * y + folderGuardInterval);
				}
			}
		}

		for (WidgetFolder vdf : volatilesFolders) {
			if (xp > vdf.getX() && xp < vdf.getX() + vdf.getWidth() && yp > vdf.getY() && yp < vdf.getY() + vdf.getHeight()) {
				return vdf;
			}
		}
		return null;
	}

	/**
	 * create a new widget folder instance
	 * 
	 * @param id
	 *            the widget id
	 * @param width
	 *            the widget width
	 * @param height
	 *            the widget height
	 * @param xIndex
	 *            the widget folder x index
	 * @param yIndex
	 *            the widget folder y index
	 * @return widget folder
	 */
	public WidgetFolder newWidgetFolderIntance(String id, double width, double height, int xIndex, int yIndex) {
		double deviceWidth = device2D.getWidth();
		double deviceHeight = device2D.getHeight();

		int folderMaxX = (int) (deviceWidth / (width + 2 * folderGuardInterval));
		int folderMaxY = (int) (deviceHeight / (height + 2 * folderGuardInterval));

		if (xIndex < 0) {
			xIndex = 0;
		}
		if (xIndex > folderMaxX) {
			xIndex = folderMaxX;
		}

		if (yIndex < 0) {
			yIndex = 0;
		}
		if (yIndex > folderMaxY) {
			yIndex = folderMaxY;
		}

		WidgetFolder folder = new WidgetFolder();
		folder.setId(id);
		folder.setWidth(width);
		folder.setHeight(height);
		folder.setxIndex(xIndex);
		folder.setyIndex(yIndex);
		folder.setGuardInterval(folderGuardInterval);
		if (xIndex < folderMaxX && yIndex < folderMaxY) {
			folder.setX((width + 2 * folderGuardInterval) * xIndex + folderGuardInterval);
			folder.setY((height + 2 * folderGuardInterval) * yIndex + folderGuardInterval);
		} else if (xIndex == folderMaxX && yIndex == folderMaxY) {
			folder.setX(deviceWidth - width - folderGuardInterval);
			folder.setY(deviceHeight - height - folderGuardInterval);
		} else if (xIndex < folderMaxX && yIndex == folderMaxY) {
			folder.setX((width + 2 * folderGuardInterval) * xIndex + folderGuardInterval);
			folder.setY(deviceHeight - height - folderGuardInterval);
		} else if (xIndex == folderMaxX && yIndex < folderMaxY) {
			folder.setX(deviceWidth - width - folderGuardInterval);
			folder.setY((height + 2 * folderGuardInterval) * yIndex + folderGuardInterval);
		}

		return folder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		fireViewResized();
	}

	/**
	 * fire listener that the window has resized
	 */
	private void fireViewResized() {

		ViewEvent v2dEvent = new ViewEvent(this);
		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewResized(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		fireViewMoved();
	}

	/**
	 * fire listener that the window has moved
	 */
	private void fireViewMoved() {
		ViewEvent v2dEvent = new ViewEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewMoved(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent
	 * )
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		fireViewShown();
	}

	/**
	 * fire listener that the window has shwon
	 */
	private void fireViewShown() {
		ViewEvent v2dEvent = new ViewEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewShown(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.
	 * ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		fireViewHidden();
	}

	/**
	 * fire listener that the window is hidden
	 */
	private void fireViewHidden() {
		ViewEvent v2dEvent = new ViewEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewHidden(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		fireViewFocusGained();
	}

	/**
	 * fire listener that the window is hidden
	 */
	private void fireViewFocusGained() {
		ViewEvent v2dEvent = new ViewEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewFocusGained(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		fireViewFocusLost();
	}

	/**
	 * fire listener that the window is hidden
	 */
	private void fireViewFocusLost() {
		ViewEvent v2dEvent = new ViewEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ViewListener.class) {
					((ViewListener) listeners[i + 1]).viewFocusLost(v2dEvent);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * @return the backgroundPainter
	 */
	public BackgroundPainter getBackgroundPainter() {
		return backgroundPainter;
	}

	/**
	 * @param backgroundPainter
	 *            the backgroundPainter to set
	 */
	public void setBackgroundPainter(BackgroundPainter backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	}

	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (backgroundPainter != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			//g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			//g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			backgroundPainter.paintViewBackground(this,getWidth(),getHeight(), g2d);
		}
	}

	/**
	 * @return the viewKey
	 */
	public String getViewKey() {
		return viewKey;
	}

	/**
	 * @param viewKey
	 *            the viewKey to set
	 */
	public void setViewKey(String viewKey) {
		this.viewKey = viewKey;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * create a view emitter
	 * 
	 * @return the viewEmitter
	 */
	public ViewEmitter createViewEmitter() {
		return new ViewEmitter(this);
	}


}
