/**
 * 
 */
package com.jensoft.core.plugin.function;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.function.area.AreaFunction;
import com.jensoft.core.plugin.function.curve.CurveFunction;
import com.jensoft.core.plugin.function.scatter.ScatterFunction;
import com.jensoft.core.plugin.function.scatter.ScatterFunction.ScatterPoint;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2DEvent;
import com.jensoft.core.window.Window2DListener;
import com.jensoft.core.window.WindowPart;

/**
 * @author sebastien janaud
 */
public abstract class FunctionPlugin<F extends Function> extends AbstractPlugin {

    /** functions */
    private List<F> functions = new ArrayList<F>();

    /**
     * Create new <code>FunctionPlugin</code>
     */
    public FunctionPlugin() {
        setAntialiasing(Antialiasing.On);
        setFractionalMetrics(Fractional.On);
        setTextAntialising(TextAntialiasing.On);
        setAlphaInterpolation(AlphaInterpolation.Quality);
    }

    /**
     * <code>Area</code> <br>
     * <center><img src="doc-files/simpleAreaCurve.png"/></center> <br>
     * <br>
     * <br>
     * <center><img src="doc-files/multipleAreaCurve.png"/></center> <br>
     * 
     * @author sebastien janaud
     */
    public static class AreaFunctionPlugin extends FunctionPlugin<AreaFunction> {

        /**
         * Create Area Function Plug-in
         */
        public AreaFunctionPlugin() {
            super();
            setName(AreaFunctionPlugin.class.getCanonicalName());
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
         */
        @Override
        protected void paintFunctions(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
            if (windowPart != WindowPart.Device) {
                return;
            }

            for (AreaFunction area : getFunctions()) {
                if (area.getSourceFunction() == null) {
                    continue;
                }

                area.getPathFunction().setSource(area.getSourceFunction());
                if (!area.getPathFunction().getSource().equals(area.getSourceFunction())) {
                    area.getPathFunction().setSolveGeometryRequest(true);
                }

                area.getPathFunction().setWindow2d(getWindow2D());
                area.getPathFunction().setFontRenderContext(g2d.getFontRenderContext());

                area.solveGeometry();

                if (area.getAreaFill() != null) {
                    area.getAreaFill().paintArea(g2d, area);
                }
                if (area.getAreaDraw() != null) {
                    area.getAreaDraw().paintArea(g2d, area);
                }
            }

        }

    }

    /**
     * <code>Curve</code><br>
     * <center><img src="doc-files/CurvePlugin1.png"></center> <br>
     * <br>
     * <center><img src="doc-files/CurvePlugin2.png"></center> <br>
     * <br>
     * 
     * @author sebastien janaud
     */
    public static class CurveFunctionPlugin extends FunctionPlugin<CurveFunction> {

        public CurveFunctionPlugin() {
            super();
            setName(CurveFunctionPlugin.class.getCanonicalName());
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
         */
        @Override
        protected void paintFunctions(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
            if (windowPart != WindowPart.Device) {
                return;
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            
            for (int c = 0; c < getFunctions().size(); c++) {
                CurveFunction curve = getFunctions().get(c);
                curve.getPathFunction().setWindow2d(getWindow2D());
                curve.getPathFunction().setFontRenderContext(g2d.getFontRenderContext());
                curve.getCurveDraw().paintCurve(g2d, curve);
            }

        }

    }

    /**
     * @author Sebastien Janaud
     */
    public static class ScatterFunctionPlugin extends FunctionPlugin<ScatterFunction> {

        public ScatterFunctionPlugin() {
            super();
            setName(ScatterFunctionPlugin.class.getCanonicalName());
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
         */
        @Override
        protected void paintFunctions(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
            if (windowPart != WindowPart.Device) {
                return;
            }
            for (int i = 0; i < getFunctions().size(); i++) {
                ScatterFunction scatterCurve = getFunctions().get(i);
                scatterCurve.getPathFunction().setWindow2d(getWindow2D());
                scatterCurve.getPathFunction().setFontRenderContext(g2d.getFontRenderContext());
                scatterCurve.solveScatter();
                for (ScatterPoint scatter : scatterCurve.getScatters()) {

                    BufferedImage buffer = scatter.getPrimitive();
                    int x = (int) scatter.getDevicePoint().getX();
                    int y = (int) scatter.getDevicePoint().getY();
                    g2d.drawImage(buffer, x, y, null);

                    // if(scatterCurve.getScatterFill() != null){
                    // scatterCurve.getScatterFill().paintScatter(g2d, scatter);
                    // }
                    // if(scatterCurve.getScatterDraw() != null){
                    // scatterCurve.getScatterDraw().paintScatter(g2d, scatter);
                    // }

                }
            }

        }

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#onWindowRegister()
     */
    @Override
    public void onWindowRegister() {

        getWindow2D().addWindow2DListener(new Window2DListener() {

           
            /* (non-Javadoc)
             * @see com.jensoft.core.window.Window2DListener#window2DUnlockActive(com.jensoft.core.window.Window2DEvent)
             */
            @Override
            public void window2DUnlockActive(Window2DEvent w2dEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.window.Window2DListener#window2DLockActive(com.jensoft.core.window.Window2DEvent)
             */
            @Override
            public void window2DLockActive(Window2DEvent w2dEvent) {
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.window.Window2DListener#window2DBoundChanged(com.jensoft.core.window.Window2DEvent)
             */
            @Override
            public void window2DBoundChanged(Window2DEvent w2dEvent) {
                for (Function function : functions) {

                    /*
                     * this action code mean that the next time geometry will get, a new geometry path will be created
                     * for path
                     * Indeed when a window bound has change the function projection is dirty and need new solve.
                     */

                    function.getPathFunction().setSolveGeometryRequest(true);

                }

            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.window.Window2DListener#window2DResized(com.jensoft.core.window.Window2DEvent)
             */
            @Override
            public void window2DResized(Window2DEvent w2dEvent) {
                for (Function function : functions) {

                    /*
                     * this action code mean that the next time geometry will get, a new geometry path will be created
                     * for path
                     * Indeed when a window size pixel has change the function projection is dirty and need new solve.
                     */

                    function.getPathFunction().setSolveGeometryRequest(true);

                }

            }

        });
    }

    /**
     * register function
     * 
     * @param function
     */
    public void addFunction(F function) {
        function.setHost(this);
        functions.add(function);
    }

    /**
     * @param v2d
     * @param g2d
     * @param windowPart
     */
    protected abstract void paintFunctions(View2D v2d, Graphics2D g2d, WindowPart windowPart);

    /**
     * paint the {@link GlyphMetric} from {@link MetricsPathFunction}
     * 
     * @param v2d
     * @param g2d
     * @param windowPart
     */
    protected void paintMetricsGlyphFunction(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        if (windowPart != WindowPart.Device) {
            return;
        }
        for (int c = 0; c < functions.size(); c++) {
            List<GlyphMetric> metrics = functions.get(c).getPathFunction().getMetrics();
            for (GlyphMetric glyphMetric : metrics) {
                if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
                    glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d, glyphMetric);
                }

                if (glyphMetric.getGlyphMetricFill() != null) {
                    glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d, glyphMetric);
                }

                if (glyphMetric.getGlyphMetricDraw() != null) {
                    glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d, glyphMetric);
                }
            }
        }

    }

    /**
     * @return the functions
     */
    public List<F> getFunctions() {
        return functions;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        paintFunctions(v2d, g2d, windowPart);
        paintMetricsGlyphFunction(v2d, g2d, windowPart);
    }

}
