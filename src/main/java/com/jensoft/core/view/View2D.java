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
import com.jensoft.core.view.background.BackgroundPainter;
import com.jensoft.core.widget.WidgetFolder;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.Window2DEvent;
import com.jensoft.core.window.Window2DListener;
import com.jensoft.core.window.WindowPart;
import com.jensoft.core.window.WindowPartComponent;

/**
 * <code>View2D</code> defines the end chart view.
 * 
 * @author Sebastien Janaud
 */
public class View2D extends JComponent implements Window2DListener, ComponentListener, FocusListener, MouseListener {

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
	private WindowPartComponent axisSouth;

	/** window part north */
	private WindowPartComponent axisNorth;

	/** window part west */
	private WindowPartComponent axisWest;

	/** window part east */
	private WindowPartComponent axisEast;

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

	/** registering windows */
	private List<Window2D> windows = new Vector<Window2D>();

	/** the active window */
	private Window2D activeWindow;

	/** view emitter */
	private View2DEmitter viewEmitter;

	/** view key */
	private String viewKey;

	/** API key */
	private String apiKey;

	/**
	 * create default view
	 */
	public View2D() {
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
	public View2D(int placeHolderAxisWest, int placeHolderAxisEast, int placeHolderAxisSouth, int placeHolderAxisNorth) {
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
	public View2D(int placeHolderAxis) {
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
	public void repaintPart(WindowPart windowPart) {
		if (windowPart == WindowPart.North) {
			axisNorth.repaint();
		} else if (windowPart == WindowPart.South) {
			axisSouth.repaint();
		} else if (windowPart == WindowPart.West) {
			axisWest.repaint();
		} else if (windowPart == WindowPart.East) {
			axisEast.repaint();
		} else if (windowPart == WindowPart.Device) {
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
	 * get the active window
	 * 
	 * @return the active window
	 */
	public Window2D getActiveWindow() {
		return activeWindow;
	}

	/**
	 * set the specified window active
	 * 
	 * @param active
	 *            the window to active
	 */
	public void setActiveWindow(Window2D active) {
		for (Window2D w2d : getRegisterWindow()) {
			w2d.unlockActive();
		}
		Window2D old = getActiveWindow();
		activeWindow = active;
		if (active != null) {
			activeWindow.lockActive();
			fireWindow2DSelected();
		}
		firePropertyChange("activeWindow", old, getActiveWindow());
	}

	/**
	 * add a view listener
	 * 
	 * @param listener
	 */
	public void addView2DListener(View2DListener listener) {
		view2DListenerList.add(View2DListener.class, listener);
	}

	/**
	 * remove the view listener
	 * 
	 * @param listener
	 */
	public void removeView2DListener(View2DListener listener) {
		view2DListenerList.remove(View2DListener.class, listener);
	}

	/**
	 * fire listener that the window has changed
	 */
	private void fireWindow2DSelected() {
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewWindow2DSelected(v2dEvent);
				}
			}
		}
	}

	/**
	 * get the registered window in this view
	 * 
	 * @return the windows collection
	 */
	public List<Window2D> getRegisterWindow() {
		return windows;
	}

	/**
	 * register a window in this view
	 * 
	 * @param window2d
	 */
	public void registerWindow2D(Window2D window2d) {
		if (windows.contains(window2d)) {
			return;
		}
		if (window2d.getName() == null) {
			window2d.setName("Window " + windows.size());
		}

		windows.add(window2d);
		window2d.setDevice2D(device2D);
		window2d.setView2D(this);
		window2d.addWindow2DListener(this);

		setActiveWindow(window2d);

		device2D.registerWindow(window2d);

		window2d.onView2DRegister();

	}

	/**
	 * unregister the specified window from this view
	 * 
	 * @param window2d
	 *            the window2D to remove
	 */
	public void unregisterWindow2D(Window2D window2d) {
		if (window2d == null) {
			return;
		}
		if (window2d.equals(getActiveWindow())) {
			setActiveWindow(null);
		}
		windows.remove(window2d);
		device2D.unregisterWindow(window2d);
		if (windows.size() > 0) {
			setActiveWindow(windows.get(windows.size() - 1));
		}
	}

	/**
	 * get the window border component for the specified part, west, east,
	 * north, south or device
	 * 
	 * @param windowPart
	 * @return the window part component
	 */
	public JComponent getWindowComponent(WindowPart windowPart) {
		if (windowPart == WindowPart.North) {
			return axisNorth;
		} else if (windowPart == WindowPart.South) {
			return axisSouth;
		} else if (windowPart == WindowPart.West) {
			return axisWest;
		} else if (windowPart == WindowPart.East) {
			return axisEast;
		} else if (windowPart == WindowPart.Device) {
			return device2D;
		}

		return null;
	}

	/**
	 * view creation
	 */
	private void createView() {
		setDoubleBuffered(false);
		
		axisEast = new WindowPartComponent(WindowPart.East, this);
		Dimension dimMarginRight = new Dimension(placeHolderAxisEast, 10);
		axisEast.setPreferredSize(dimMarginRight);
		axisEast.setDoubleBuffered(false);

		axisWest = new WindowPartComponent(WindowPart.West, this);
		Dimension dimMarginLeft = new Dimension(placeHolderAxisWest, 10);
		axisWest.setPreferredSize(dimMarginLeft);
		axisWest.setDoubleBuffered(false);

		axisNorth = new WindowPartComponent(WindowPart.North, this);
		Dimension dimMarginNorth = new Dimension(10, placeHolderAxisNorth);
		axisNorth.setPreferredSize(dimMarginNorth);
		axisNorth.setDoubleBuffered(false);

		axisSouth = new WindowPartComponent(WindowPart.South, this);
		Dimension dimMarginSouth = new Dimension(10, placeHolderAxisSouth);
		axisSouth.setPreferredSize(dimMarginSouth);
		axisSouth.setDoubleBuffered(false);

		device2D = new DevicePartComponent();
		device2D.setDoubleBuffered(false);
		
		
		device2D.setView2D(this);

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
	public void setPartBackground(Color partBackground, WindowPart... windowParts) {
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
		setPartBackground(bg, WindowPart.Device, WindowPart.East, WindowPart.West, WindowPart.North, WindowPart.South);
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
	public void window2DBoundChanged(Window2DEvent w2dEvent) {
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
	public void window2DLockActive(Window2DEvent w2dEvent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DUnlockActive(com.jensoft
	 * .core.window.Window2DEvent)
	 */
	@Override
	public void window2DUnlockActive(Window2DEvent w2dEvent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.window.Window2DListener#window2DResized(com.jensoft.
	 * core.window.Window2DEvent)
	 */
	@Override
	public void window2DResized(Window2DEvent w2dEvent) {
	}

	/**
	 * set the place holder for the specified window part except device. the
	 * place holder is the weight in pixel for the specified part,
	 * {@link WindowPart#East}, {@link WindowPart#West},
	 * {@link WindowPart#North} and {@link WindowPart#South}.
	 * 
	 * @param placeHolder
	 *            the place holder to set
	 * @param windowParts
	 *            the window parts
	 */
	public void setPlaceHolder(int placeHolder, WindowPart... windowParts) {
		for (int i = 0; i < windowParts.length; i++) {
			WindowPart windowPart = windowParts[i];
			if (windowPart == WindowPart.North) {
				setPlaceHolderAxisNorth(placeHolder);
			} else if (windowPart == WindowPart.South) {
				setPlaceHolderAxisSouth(placeHolder);
			} else if (windowPart == WindowPart.West) {
				setPlaceHolderAxisWest(placeHolder);
			} else if (windowPart == WindowPart.East) {
				setPlaceHolderAxisEast(placeHolder);
			}
		}

	}

	/**
	 * set the place holder for the specified window part except device. the
	 * place holder is the weight in pixel for the specified part,
	 * {@link WindowPart#East}, {@link WindowPart#West},
	 * {@link WindowPart#North} and {@link WindowPart#South}.
	 * 
	 * @param placeHolder
	 *            the place holder to set to the given window part
	 * @param windowPart
	 *            the window part
	 */
	public void setPlaceHolder(int placeHolder, WindowPart windowPart) {
		if (windowPart == WindowPart.North) {
			setPlaceHolderAxisNorth(placeHolder);
		} else if (windowPart == WindowPart.South) {
			setPlaceHolderAxisSouth(placeHolder);
		} else if (windowPart == WindowPart.West) {
			setPlaceHolderAxisWest(placeHolder);
		} else if (windowPart == WindowPart.East) {
			setPlaceHolderAxisEast(placeHolder);
		}
	}

	/**
	 * set the place holder for all border parts. the place holder is the weight
	 * in pixel for the specified part, {@link WindowPart#East},
	 * {@link WindowPart#West}, {@link WindowPart#North} and
	 * {@link WindowPart#South}.
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

		View2DEvent v2dEvent = new View2DEvent(this);
		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewResized(v2dEvent);
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
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewMoved(v2dEvent);
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
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewShown(v2dEvent);
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
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewHidden(v2dEvent);
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
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewFocusGained(v2dEvent);
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
		View2DEvent v2dEvent = new View2DEvent(this);

		Object[] listeners = view2DListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == View2DListener.class) {
					((View2DListener) listeners[i + 1]).viewFocusLost(v2dEvent);
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
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
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
	public View2DEmitter createViewEmitter() {
		return new View2DEmitter(this);
	}

	/**
	 * get the view as an image outside swing ui.
	 * 
	 * @param width
	 *            the width to set
	 * @param height
	 *            the height to set
	 * @return the image view
	 */
	public BufferedImage getImageView(int width, int height) {

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("view width and view height should be greater than zero");
		}
		
		//Create new View ? clone plugin ?

		// image view
		BufferedImage viewImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics viewGraphics = viewImage.getGraphics();

		Graphics2D g2d = (Graphics2D) viewGraphics;

		
		Dimension old = getSize();
		
		setSize(new Dimension(width, height));

		// component part
		WindowPartComponent northPart = (WindowPartComponent) getWindowComponent(WindowPart.North);
		WindowPartComponent southPart = (WindowPartComponent) getWindowComponent(WindowPart.South);
		WindowPartComponent eastPart = (WindowPartComponent) getWindowComponent(WindowPart.East);
		WindowPartComponent westPart = (WindowPartComponent) getWindowComponent(WindowPart.West);
		DevicePartComponent devicePart = (DevicePartComponent) getWindowComponent(WindowPart.Device);

		// size component
		northPart.setSize(width, getPlaceHolderAxisNorth());
		southPart.setSize(width, getPlaceHolderAxisSouth());
		eastPart.setSize(getPlaceHolderAxisEast(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth());
		westPart.setSize(getPlaceHolderAxisWest(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth());
		devicePart.setSize(getWidth() - getPlaceHolderAxisWest() - getPlaceHolderAxisEast(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth());

		// buffered image

		BufferedImage northImage = null;
		if (getPlaceHolderAxisNorth() > 0) {
			northImage = new BufferedImage(getWidth(), getPlaceHolderAxisNorth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D ng2d = (Graphics2D) northImage.getGraphics();
			northPart.paintComponent(ng2d);
			ng2d.dispose();

		}

		BufferedImage southImage = null;
		if (getPlaceHolderAxisSouth() > 0) {
			southImage = new BufferedImage(getWidth(), getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D sg2d = (Graphics2D) southImage.getGraphics();
			southPart.paintComponent(sg2d);
			sg2d.dispose();

		}

		BufferedImage eastImage = null;
		if (getPlaceHolderAxisEast() > 0) {
			eastImage = new BufferedImage(getPlaceHolderAxisEast(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D eg2d = (Graphics2D) eastImage.getGraphics();
			eastPart.paintComponent(eg2d);
			eg2d.dispose();

		}

		BufferedImage westImage = null;
		if (getPlaceHolderAxisWest() > 0) {
			westImage = new BufferedImage(getPlaceHolderAxisWest(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D wg2d = (Graphics2D) westImage.getGraphics();
			westPart.paintComponent(wg2d);
			wg2d.dispose();

		}

		BufferedImage deviceImage = null;
		if (getWidth() - getPlaceHolderAxisWest() - getPlaceHolderAxisEast() > 0 && getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth() > 0) {
			deviceImage = new BufferedImage(getWidth() - getPlaceHolderAxisWest() - getPlaceHolderAxisEast(), getHeight() - getPlaceHolderAxisNorth() - getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D dg2d = (Graphics2D) deviceImage.getGraphics();
			devicePart.paintComponent(dg2d);
			dg2d.dispose();

		}

		// paint in image view

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		qualityHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 qualityHints.put(RenderingHints.KEY_DITHERING,
		 RenderingHints.VALUE_DITHER_ENABLE);
		// qualityHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
		// RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		// qualityHints.put(RenderingHints.KEY_RENDERING,
		// RenderingHints.VALUE_RENDER_QUALITY);
		// qualityHints.put(RenderingHints.KEY_COLOR_RENDERING,
		// RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		// qualityHints.put(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		// qualityHints.put(RenderingHints.KEY_FRACTIONALMETRICS,
		// RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHints(qualityHints);

		if (backgroundPainter != null) {
			backgroundPainter.paintViewBackground(this,width,height, g2d);
		}

		if (northImage != null) {
			g2d.drawImage(northImage, 0, 0, northImage.getWidth(), northImage.getHeight(), null);
		}

		if (southImage != null) {
			g2d.drawImage(southImage, 0, getHeight() - getPlaceHolderAxisSouth(), southImage.getWidth(), southImage.getHeight(), null);
			southImage.flush();
		}

		if (eastImage != null) {
			g2d.drawImage(eastImage, getWidth() - getPlaceHolderAxisEast(), getPlaceHolderAxisNorth(), eastImage.getWidth(), eastImage.getHeight(), null);
			eastImage.flush();
		}

		if (westImage != null) {
			g2d.drawImage(westImage, 0, getPlaceHolderAxisNorth(), westImage.getWidth(), westImage.getHeight(), null);
			westImage.flush();
		}

		if (deviceImage != null) {
			g2d.drawImage(deviceImage, getPlaceHolderAxisWest(), getPlaceHolderAxisNorth(), deviceImage.getWidth(), deviceImage.getHeight(), null);
			deviceImage.flush();
		}

		g2d.dispose();
		viewImage.flush();

		
		if(old != null)
			setSize(old);
		return viewImage;
	}

}
