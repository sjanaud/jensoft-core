package com.jensoft.core.plugin.gauge.arcautobinder;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.gauge.RadialGaugePlugin;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.binder.path.PathArcAutoBinder;
import com.jensoft.core.plugin.gauge.core.binder.path.PathAutoBinder;
import com.jensoft.core.plugin.gauge.core.binder.path.PathAutoBinder.Direction;
import com.jensoft.core.plugin.gauge.core.binder.path.PathQuadAutoBinder;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

public class GaugeQuadraticAutoBinderAnimatorDemo extends View2D {

	private class GaugeArcAutoBinder extends RadialGauge {
		public GaugeArcAutoBinder() {
			super(0, 0, 90);
			GaugeMetricsPath path = new GaugeMetricsPath();
			PathAutoBinder autoArcBinder = new PathQuadAutoBinder(120, 140, 0);
			path.setPathBinder(autoArcBinder);
			registerGaugeMetricsPath(path);

			PathAnimator animator = new PathAnimator(path);
			animator.start();
		}

	}

	private class PathAnimator extends Thread {

		private GaugeMetricsPath path;

		public PathAnimator(GaugeMetricsPath path) {
			this.path = path;
			System.out.println("create thread");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
				while (true) {
					for (int polarAngle = 0; polarAngle < 360; polarAngle = polarAngle + 30) {
						for (int radius = 0; radius < 300; radius = radius + 5) {
							PathQuadAutoBinder quadBinder = new PathQuadAutoBinder(radius, 200, polarAngle);
							quadBinder.setControlOffsetRadius(100);
							path.setPathBinder(quadBinder);
							repaintView();
							Thread.sleep(40);
						}

					}
					for (int polarAngle = 0; polarAngle < 360; polarAngle = polarAngle + 30) {
						for (int radius = 0; radius < 300; radius = radius + 5) {
							PathQuadAutoBinder quadBinder = new PathQuadAutoBinder(radius, 200, polarAngle, Direction.AntiClockwise);
							quadBinder.setControlOffsetRadius(100);
							path.setPathBinder(quadBinder);
							repaintView();
							Thread.sleep(40);
						}

					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		final ViewFrameUI demoFrame = new ViewFrameUI(new GaugeQuadraticAutoBinderAnimatorDemo());
	}

	public GaugeQuadraticAutoBinderAnimatorDemo() {
		super(20);

		Window2D w2d = new Window2D.Linear.Identity();
		registerWindow2D(w2d);

		GaugeArcAutoBinder gauge = new GaugeArcAutoBinder();
		RadialGaugePlugin gaugePlugin = new RadialGaugePlugin(gauge);
		w2d.registerPlugin(gaugePlugin);

		TranslatePlugin translate = new TranslatePlugin();
		translate.registerContext(new TranslateDefaultDeviceContext());
		w2d.registerPlugin(translate);

	}

}
