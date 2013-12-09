/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.WidgetPlugin.PushingBehavior;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>ZoomObjectifPlugin</code>
 * 
 * @author sebastien janaud
 */
public class ZoomObjectifPlugin extends AbstractPlugin implements AbstractPlugin.OnReleaseListener {

	/** zoom int lock flag */
	private boolean zoomInLock = false;

	/** zoom out lock flag */
	private boolean zoomOutLock = false;

	/** zoom milli tempo */
	private int zoomMiliTempo = 100;

	/** zoom factor */
	private int factor = 30;

	/** current zoom process nature */
	private ZoomNature processNature;

	/** zoom out process with xy nature */
	private ZoomOutProcess zoomOutXY;

	/** zoom out process with x nature */
	private ZoomOutProcess zoomOutX;

	/** zoom out process with y nature */
	private ZoomOutProcess zoomOutY;

	/** zoom in process with xy nature */
	private ZoomInProcess zoomInXY;

	/** zoom in process with x nature */
	private ZoomInProcess zoomInX;

	/** zoom in process with y nature */
	private ZoomInProcess zoomInY;

	private ZoomLensType zoomLensType = ZoomLensType.ZoomXY;

	/**
	 * Zoom Wheel behavior mode
	 */
	public enum ZoomLensType {
		ZoomXY, ZoomX, ZoomY;
	}

	/** listeners */
	private EventListenerList zoomObjectifListenerList;

	/**
	 * defines a zoom nature, on x and y dimension or only one dimension, x or y
	 */
	public enum ZoomNature {
		ZoomXY, ZoomX, ZoomY;
	}

	/**
	 * @return the processNature
	 */
	protected ZoomNature getProcessNature() {
		return processNature;
	}

	/**
	 * get the lens type
	 * @return lens type
	 */
	public ZoomLensType getZoomLensType() {
		return zoomLensType;
	}

	/**
	 * set lens type
	 * @param zoomLensType
	 * 			the lens type to set
	 */
	public void setZoomLensType(ZoomLensType zoomLensType) {
		this.zoomLensType = zoomLensType;
	}

	/**
	 * create zoom objectif plugin
	 */
	public ZoomObjectifPlugin() {
		setName("ObjectifPlugin");
		setSelectable(true);
		createProcess();
		setOnReleaseListener(this);
		setTextAntialising(TextAntialiasing.On);
		zoomObjectifListenerList = new EventListenerList();
		setPriority(100);
	}

	/**
	 * add plug in listener
	 * 
	 * @param listener
	 */
	public void addZoomObjectifListener(ZoomObjectifListener listener) {
		zoomObjectifListenerList.add(ZoomObjectifListener.class, listener);
	}

	/**
	 * remove plug in listener
	 * 
	 * @param listener
	 */
	public void removeZoomObjectifListener(ZoomObjectifListener listener) {
		zoomObjectifListenerList.remove(ZoomObjectifListener.class, listener);
	}

	/**
	 * fire zoomIn
	 */
	public void fireZoomIn() {
		Object[] listeners = zoomObjectifListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ZoomObjectifListener.class) {
					((ZoomObjectifListener) listeners[i + 1]).zoomIn(new ZoomObjectifEvent(this));
				}
			}
		}
	}

	/**
	 * fire zoomOut
	 */
	public void fireZoomOut() {
		Object[] listeners = zoomObjectifListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ZoomObjectifListener.class) {
					((ZoomObjectifListener) listeners[i + 1]).zoomOut(new ZoomObjectifEvent(this));
				}
			}
		}
	}

	/**
	 * get a lock/unlock action
	 * 
	 * @return lock unlock action
	 */
	public ObjectifLockUnlockAction getObjectifLockUnlockAction() {
		return new ObjectifLockUnlockAction();
	}

	/**
	 * synchronize lens
	 * 
	 * @param objectifs
	 */
	public static ZoomObjectifSynchronizer createSynchronizer(ZoomObjectifPlugin... objectifs) {
		ZoomObjectifSynchronizer synchronizer = new ZoomObjectifSynchronizer(objectifs);
		return synchronizer;
	}

	/**
	 * translate select action
	 */
	class ObjectifLockUnlockAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (isLockSelected()) {
						unlockSelected();
					} else {
						lockSelected();
					}
				}
			});
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.AbstractPlugin#lockSelected()
	 */
	@Override
	public void lockSelected() {
		super.lockSelected();
		if (getWindow2D() == null || getWindow2D().getView2D() == null) {
			return;
		}
		getWindow2D().getView2D().getWidgetPlugin().pushMessage("LOCK OBJECTIF", this, PushingBehavior.Fast);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.AbstractPlugin#unlockSelected()
	 */
	@Override
	public void unlockSelected() {
		super.unlockSelected();
		if (getWindow2D() == null) {
			return;
		}
		getWindow2D().getDevice2D().repaintDevice();

	}

	/**
	 * create in/out process for all zooming nature
	 */
	private void createProcess() {
		zoomOutXY = new ZoomOutProcess(ZoomNature.ZoomXY);
		zoomOutX = new ZoomOutProcess(ZoomNature.ZoomX);
		zoomOutY = new ZoomOutProcess(ZoomNature.ZoomY);
		zoomInXY = new ZoomInProcess(ZoomNature.ZoomXY);
		zoomInX = new ZoomInProcess(ZoomNature.ZoomX);
		zoomInY = new ZoomInProcess(ZoomNature.ZoomY);
	}

	/**
	 * ZoomOutProcess
	 */
	class ZoomOutProcess implements Runnable {

		/** process nature */
		private ZoomNature processNature;

		/**
		 * create zoom out process
		 * 
		 * @param processNature
		 */
		public ZoomOutProcess(ZoomNature processNature) {
			this.processNature = processNature;
		}

		@Override
		public void run() {
			try {
				while (zoomOutLock) {
					zoomOut(processNature);
					fireZoomOut();
					getWindow2D().getDevice2D().repaintDevice();
					Thread.sleep(zoomMiliTempo);
				}
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	/**
	 * ZoomInProcess
	 */
	class ZoomInProcess implements Runnable {

		/** process nature */
		private ZoomNature processNature;

		/**
		 * create zoom in process
		 * 
		 * @param processNature
		 */
		public ZoomInProcess(ZoomNature processNature) {
			this.processNature = processNature;
		}

		@Override
		public void run() {
			try {

				while (zoomInLock) {
					zoomIn(processNature);
					fireZoomIn();
					getWindow2D().getDevice2D().repaintDevice();
					Thread.sleep(zoomMiliTempo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
			}
		}
	}

	/**
	 * zoom in with in the specified nature
	 * 
	 * @param zoomNature
	 */
	public void zoomIn(ZoomNature zoomNature) {
		this.processNature = zoomNature;
		Window2D w2d = getWindow2D();

		double w = getWindow2D().getDevice2D().getDeviceWidth();
		double h = getWindow2D().getDevice2D().getDeviceHeight();
		//System.out.println("w : " + w);
		//System.out.println("h : " + h);

		Point2D pMinXMinYDevice = null;
		Point2D pMaxXMaxYDevice = null;

		if (zoomNature == ZoomNature.ZoomXY) {
			pMinXMinYDevice = new Point2D.Double(w / factor, h - h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w - w / factor, h / factor);
		} else if (zoomNature == ZoomNature.ZoomX) {
			pMinXMinYDevice = new Point2D.Double(w / factor, h);
			pMaxXMaxYDevice = new Point2D.Double(w - w / factor, 0);
		} else if (zoomNature == ZoomNature.ZoomY) {
			pMinXMinYDevice = new Point2D.Double(0, h - h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w, h / factor);
		}

		//System.out.println("pMinXMinYDevice : " + pMinXMinYDevice);

		Point2D pMinXMinYUser = w2d.pixelToUser(pMinXMinYDevice);
		Point2D pMaxXMaxYUser = w2d.pixelToUser(pMaxXMaxYDevice);
		if (w2d instanceof Window2D.Linear) {
			Window2D.Linear wl = (Window2D.Linear) w2d;
			if(zoomLensType == ZoomLensType.ZoomXY){
				wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
			}
			else if(zoomLensType == ZoomLensType.ZoomX){
				//identity on y
				wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), wl.getMinY(), wl.getMaxY());
			}
			else if(zoomLensType == ZoomLensType.ZoomY){
				//identity on x
				wl.bound(wl.getMinX(), wl.getMaxX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
			}
			
		}else{
			System.out.println("not implemented");
		}

	}

	/**
	 * zoom out with in the specified nature
	 * 
	 * @param zoomNature
	 */
	public void zoomOut(ZoomNature zoomNature) {
		this.processNature = zoomNature;
		double w = getWindow2D().getDevice2D().getDeviceWidth();
		double h = getWindow2D().getDevice2D().getDeviceHeight();

		Point2D pMinXMinYDevice = null;
		Point2D pMaxXMaxYDevice = null;

		if (zoomNature == ZoomNature.ZoomXY) {
			pMinXMinYDevice = new Point2D.Double(-w / factor, h + h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w + w / factor, -h / factor);
		} else if (zoomNature == ZoomNature.ZoomX) {

			pMinXMinYDevice = new Point2D.Double(-w / factor, h);
			pMaxXMaxYDevice = new Point2D.Double(w + w / factor, 0);
		} else if (zoomNature == ZoomNature.ZoomY) {

			pMinXMinYDevice = new Point2D.Double(0, h + h / factor);
			pMaxXMaxYDevice = new Point2D.Double(w, -h / factor);
		}

		Point2D pMinXMinYUser = getWindow2D().pixelToUser(pMinXMinYDevice);
		Point2D pMaxXMaxYUser = getWindow2D().pixelToUser(pMaxXMaxYDevice);

		if (getWindow2D() instanceof Window2D.Linear) {
			Window2D.Linear wl = (Window2D.Linear) getWindow2D();
			//wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
			if(zoomLensType == ZoomLensType.ZoomXY){
				wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
			}
			else if(zoomLensType == ZoomLensType.ZoomX){
				//identity on y
				wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(), wl.getMinY(), wl.getMaxY());
			}
			else if(zoomLensType == ZoomLensType.ZoomY){
				//identity on x
				wl.bound(wl.getMinX(), wl.getMaxX(), pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
			}
		}

	}

	/**
	 * start zoom out with in the specified nature
	 * 
	 * @param zoomNature
	 */
	public void startZoomOut(ZoomNature zoomNature) {
		zoomOutLock = true;
		Thread zoomOutThread = new Thread(getZoomOutProcess(zoomNature), "zoom out run");
		zoomOutThread.start();
	}

	/**
	 * get the zoom out process for the specified zoom nature
	 * 
	 * @param zoomNature
	 */
	private ZoomOutProcess getZoomOutProcess(ZoomNature zoomNature) {
		if (zoomNature == ZoomNature.ZoomXY) {
			return zoomOutXY;
		} else if (zoomNature == ZoomNature.ZoomX) {
			return zoomOutX;
		} else if (zoomNature == ZoomNature.ZoomY) {
			return zoomOutY;
		}
		return null;
	}

	/**
	 * stop zoom out
	 */
	public void stopZoomOut() {
		zoomOutLock = false;
	}

	/**
	 * start zoom in with in the specified nature
	 * 
	 * @param zoomNature
	 */
	public void startZoomIn(ZoomNature zoomNature) {
		zoomInLock = true;
		Thread zoomInThread = new Thread(getZoomInProcess(zoomNature), "zoom in run");
		zoomInThread.start();
	}

	/**
	 * get the zoom in process for the specified zoom nature
	 * 
	 * @param zoomNature
	 */
	private ZoomInProcess getZoomInProcess(ZoomNature zoomNature) {
		if (zoomNature == ZoomNature.ZoomXY) {
			return zoomInXY;
		} else if (zoomNature == ZoomNature.ZoomX) {
			return zoomInX;
		} else if (zoomNature == ZoomNature.ZoomY) {
			return zoomInY;
		}

		return null;
	}

	/**
	 * stop zoom in
	 */
	public void stopZoomIn() {
		zoomInLock = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java
	 * .awt.event.MouseEvent)
	 */
	@Override
	public void onRelease(MouseEvent me) {
		stopZoomIn();
		stopZoomOut();
	}

}
