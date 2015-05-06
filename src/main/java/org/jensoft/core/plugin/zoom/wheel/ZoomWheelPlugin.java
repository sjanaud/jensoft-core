/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.wheel;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>ZoomWheelPlugin</code>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public class ZoomWheelPlugin extends AbstractPlugin implements AbstractPlugin.OnWheelListener {

	/** listeners */
	private EventListenerList zoomWheelListenerList;

	/** enabled zoom wheel */
	private boolean zoomEnabled = true;

	/** ZoomMode allow to zoom on Dimension X and Y, only X,or only Y */
	public ZoomWheelType zoomWheelType = ZoomWheelType.ZoomXY;

	/** internal lock indicate that the wheel is in process */
	boolean lockWheel = false;

	/** zoom wheel factor */
	private int factor = 30;

	/** zoom message */
	private String zoomMessage = null;

	/** zoom message cleaner */
	private MessageCleaner messageCleaner;

	/**
	 * Zoom Wheel behavior mode
	 */
	public enum ZoomWheelType {
		ZoomXY, ZoomX, ZoomY;
	}

	/**
	 * create Zoom Wheel Plugin
	 */
	public ZoomWheelPlugin() {
		setName(ZoomWheelPlugin.class.getCanonicalName());
		setSelectable(false);
		setOnWheelListener(this);
		setTextAntialising(TextAntialiasing.On);
		zoomWheelListenerList = new EventListenerList();
		setPriority(100);
	}

	/**
	 * create Zoom Wheel Plugin with given zoom mode
	 * 
	 * @param zoomWheelType
	 *            the wheel type
	 */
	public ZoomWheelPlugin(ZoomWheelType zoomWheelType) {
		this();
		setZoomWheelType(zoomWheelType);
	}

	/**
	 * @return the zoomWheelMode
	 */
	public ZoomWheelType getZoomWheelType() {
		return zoomWheelType;
	}

	/**
	 * @param zoomWheelMode
	 *            the zoomWheelMode to set
	 */
	public void setZoomWheelType(ZoomWheelType zoomWheelMode) {
		this.zoomWheelType = zoomWheelMode;
	}

	/**
	 * synchronize zoom wheel plug in when new zoom wheel event occurs in any
	 * registered zoom wheel plug in
	 * 
	 * @param zoomWheels
	 *            the zoom wheels plug ins to synchronize
	 */
	public static ZoomWheelSynchronizer createSynchronizer(ZoomWheelPlugin... zoomWheels) {
		ZoomWheelSynchronizer synchronizer = new ZoomWheelSynchronizer(zoomWheels);
		return synchronizer;
	}

	/**
	 * synchronize zoom wheel plug in when new zoom wheel event occurs in any
	 * registered zoom wheel plug in
	 * 
	 * @param zoomWheels
	 *            the zoom wheels plug ins to synchronize
	 */
	public static ZoomWheelSynchronizer createSynchronizer(List<ZoomWheelPlugin> zoomWheels) {
		ZoomWheelSynchronizer synchronizer = new ZoomWheelSynchronizer(zoomWheels);
		return synchronizer;
	}

	/**
	 * add plug in listener
	 * 
	 * @param listener
	 */
	public void addZoomWheelListener(ZoomWheelListener listener) {
		zoomWheelListenerList.add(ZoomWheelListener.class, listener);
	}

	/**
	 * remove plug in listener
	 * 
	 * @param listener
	 */
	public void removeZoomBoxListener(ZoomWheelListener listener) {
		zoomWheelListenerList.remove(ZoomWheelListener.class, listener);
	}

	/**
	 * fire zoomIn
	 */
	public void fireZoomIn() {
		Object[] listeners = zoomWheelListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ZoomWheelListener.class) {
					((ZoomWheelListener) listeners[i + 1]).zoomIn(new ZoomWheelEvent(this));
				}
			}
		}
	}

	/**
	 * fire zoomOut
	 */
	public void fireZoomOut() {
		Object[] listeners = zoomWheelListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ZoomWheelListener.class) {
					((ZoomWheelListener) listeners[i + 1]).zoomOut(new ZoomWheelEvent(this));
				}
			}
		}
	}

	/**
	 * return true is lock, false otherwise
	 * 
	 * @return true is lock, false otherwise
	 */
	public boolean isLockWheel() {
		return lockWheel;
	}

	/**
	 * lock zoom wheel
	 */
	public void lockWheel() {
		lockWheel = true;
	}

	/**
	 * unlock zoom wheel
	 */
	public void unlockWheel() {
		lockWheel = false;
	}

	/**
	 * get zoom factor
	 * 
	 * @return the zoom factor
	 */
	public int getZoomFactor() {
		return factor;
	}

	/**
	 * set zoom factor
	 * 
	 * @param factor
	 */
	public void setZoomFactor(int factor) {
		this.factor = factor;
	}

	/**
	 * bound zoom in
	 */
	private void boundZoomIn() {

		double w = getProjection().getDevice2D().getDeviceWidth();
		double h = getProjection().getDevice2D().getDeviceHeight();

		Point2D pMinXMinYDevice = null;
		Point2D pMaxXMaxYDevice = null;

		if (zoomWheelType == ZoomWheelType.ZoomXY) {
			pMinXMinYDevice = new Point2D.Double(w / factor, h - h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w - w / factor, h / factor);
		} else if (zoomWheelType == ZoomWheelType.ZoomX) {
			pMinXMinYDevice = new Point2D.Double(w / factor, h);
			pMaxXMaxYDevice = new Point2D.Double(w - w / factor, 0);
		} else if (zoomWheelType == ZoomWheelType.ZoomY) {
			pMinXMinYDevice = new Point2D.Double(0, h - h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w, h / factor);
		}

		Point2D pMinXMinYUser = getProjection().pixelToUser(pMinXMinYDevice);
		Point2D pMaxXMaxYUser = getProjection().pixelToUser(pMaxXMaxYDevice);
		if (getProjection() instanceof Projection.Linear) {
			Projection.Linear wl = (Projection.Linear) getProjection();
			wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
		}

	}

	/**
	 * bound zoom out
	 */
	private void boundZoomOut() {

		Projection w2d = getProjection();

		double w = w2d.getDevice2D().getDeviceWidth();
		double h = w2d.getDevice2D().getDeviceHeight();

		Point2D pMinXMinYDevice = null;
		Point2D pMaxXMaxYDevice = null;

		if (zoomWheelType == ZoomWheelType.ZoomXY) {
			pMinXMinYDevice = new Point2D.Double(-w / factor, h + h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w + w / factor, -h / factor);
		} else if (zoomWheelType == ZoomWheelType.ZoomX) {
			pMinXMinYDevice = new Point2D.Double(-w / factor, h);
			pMaxXMaxYDevice = new Point2D.Double(w + w / factor, 0);
		} else if (zoomWheelType == ZoomWheelType.ZoomY) {
			pMinXMinYDevice = new Point2D.Double(0, h + h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w, -h / factor);
		}

		Point2D pMinXMinYUser = w2d.pixelToUser(pMinXMinYDevice);
		Point2D pMaxXMaxYUser = w2d.pixelToUser(pMaxXMaxYDevice);
		if (getProjection() instanceof Projection.Linear) {
			Projection.Linear wl = (Projection.Linear) getProjection();
			wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
		}

	}

	/**
	 * zoom in
	 */
	public void zoomIn() {
		boundZoomIn();
		zoomMessage = "ZOOM IN";
		getProjection().getDevice2D().repaintDevice();
	}

	/**
	 * zoom out
	 */
	public void zoomOut() {
		boundZoomOut();
		zoomMessage = "ZOOM OUT";
		getProjection().getDevice2D().repaintDevice();
	}

	/**
	 * Utility class to enabled or disabled zoom wheel
	 */
	class ZoomWheelEnabledAction implements ActionListener {

		private boolean enabled;

		public ZoomWheelEnabledAction(boolean status) {
			enabled = status;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					zoomEnabled = enabled;
				}
			});
		}

	}

	@Override
	public void onWheel(MouseWheelEvent mwe) {

		if (!zoomEnabled) {
			return;
		}

		//TODO : check projection nature

		lockWheel();
		int rotation = mwe.getWheelRotation();
		if (rotation < 0) {

			int count = -rotation;
			for (int i = 0; i < count; i++) {
				zoomIn();
				fireZoomIn();
				mwe.consume();
			}

		} else {

			int count = rotation;
			for (int i = 0; i < count; i++) {
				zoomOut();
				fireZoomOut();
				mwe.consume();
			}
		}

	}

	/**
	 * message cleaner, clean message after 1 second, set message to null,
	 * unlock zoom wheel
	 */
	class MessageCleaner extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				unlockWheel();
				zoomMessage = null;
				if (getProjection() != null && getProjection().getView() != null) {
					getProjection().getView().getViewPartComponent(ViewPart.North).repaint();
				}

			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * paint zoom wheel
	 * 
	 * @param g2d
	 */
	private void paintZooming(Graphics2D g2d) {
		// if (isLockWheel()) {
		if (zoomMessage != null) {
			g2d.setColor(getProjection().getThemeColor().darker());
			Font f = new Font("Dialog", Font.PLAIN, 12);
			g2d.setFont(f);
			JComponent comp = getProjection().getView().getViewPartComponent(ViewPart.North);

			if (zoomMessage != null) {
				g2d.drawString(zoomMessage, getProjection().getView().getPlaceHolderAxisWest(), comp.getHeight() - 5);
			}

			if (messageCleaner == null) {
				messageCleaner = new MessageCleaner();
				messageCleaner.start();
			} else {
				if (!messageCleaner.isAlive()) {
					messageCleaner = new MessageCleaner();
					messageCleaner.start();
				}
			}

		}
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	public void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
		// if (viewPart != ViewPart.North) {
		// return;
		// }
		// paintZooming(g2d);
	}

}
