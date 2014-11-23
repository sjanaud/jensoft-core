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
import com.jensoft.core.plugin.function.area.Area;
import com.jensoft.core.plugin.function.curve.Curve;
import com.jensoft.core.plugin.function.scatter.Scatter;
import com.jensoft.core.plugin.function.scatter.Scatter.ScatterPoint;
import com.jensoft.core.projection.ProjectionEvent;
import com.jensoft.core.projection.ProjectionListener;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>FunctionPlugin</code>
 * 
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
    public static class AreaFunction extends FunctionPlugin<Area> {

        /**
         * Create Area Function Plug-in
         */
        public AreaFunction() {
            super();
            setName(AreaFunction.class.getCanonicalName());
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
         */
        @Override
        protected void paintFunctions(View view, Graphics2D g2d, ViewPart viewPart) {
            if (viewPart != ViewPart.Device) {
                return;
            }

            for (Area area : getFunctions()) {
                if (area.getSourceFunction() == null) {
                    continue;
                }

                area.getPathFunction().setSource(area.getSourceFunction());
                if (!area.getPathFunction().getSource().equals(area.getSourceFunction())) {
                    area.getPathFunction().setSolveGeometryRequest(true);
                }

                area.getPathFunction().setProjection(getProjection());
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
    public static class CurveFunction extends FunctionPlugin<Curve> {

        public CurveFunction() {
            super();
            setName(CurveFunction.class.getCanonicalName());
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
         */
        @Override
        protected void paintFunctions(View view, Graphics2D g2d, ViewPart viewPart) {
            if (viewPart != ViewPart.Device) {
                return;
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            
            for (int c = 0; c < getFunctions().size(); c++) {
                Curve curve = getFunctions().get(c);
                curve.getPathFunction().setProjection(getProjection());
                curve.getPathFunction().setFontRenderContext(g2d.getFontRenderContext());
                curve.getCurveDraw().paintCurve(g2d, curve);
            }

        }

    }

    /**
     * @author Sebastien Janaud
     */
    public static class ScatterFunction extends FunctionPlugin<Scatter> {

        public ScatterFunction() {
            super();
            setName(ScatterFunction.class.getCanonicalName());
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.FunctionPlugin#paintFunctions(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
         */
        @Override
        protected void paintFunctions(View view, Graphics2D g2d, ViewPart viewPart) {
            if (viewPart != ViewPart.Device) {
                return;
            }
            for (int i = 0; i < getFunctions().size(); i++) {
                Scatter scatterCurve = getFunctions().get(i);
                scatterCurve.getPathFunction().setProjection(getProjection());
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
     * @see com.jensoft.core.plugin.AbstractPlugin#onProjectionRegister()
     */
    @Override
    public void onProjectionRegister() {

        getProjection().addProjectionListener(new ProjectionListener() {

            /* (non-Javadoc)
             * @see com.jensoft.core.projection.ProjectionListener#projectionUnlockActive(com.jensoft.core.projection.ProjectionEvent)
             */
            @Override
            public void projectionUnlockActive(ProjectionEvent w2dEvent) {
            }

            /* (non-Javadoc)
             * @see com.jensoft.core.projection.ProjectionListener#projectionLockActive(com.jensoft.core.projection.ProjectionEvent)
             */
            @Override
            public void projectionLockActive(ProjectionEvent w2dEvent) {
            }

            /* (non-Javadoc)
             * @see com.jensoft.core.projection.ProjectionListener#projectionBoundChanged(com.jensoft.core.projection.ProjectionEvent)
             */
            @Override
            public void projectionBoundChanged(ProjectionEvent w2dEvent) {
                for (Function function : functions) {

                    /*
                     * this action code mean that the next time geometry will get, a new geometry path will be created
                     * for path
                     * Indeed when a projection bound has change the function projection is dirty and need new solve.
                     */

                    function.getPathFunction().setSolveGeometryRequest(true);

                }

            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.projection.ProjectionListener#projectionResized(com.jensoft.core.projection.ProjectionEvent)
             */
            @Override
            public void projectionResized(ProjectionEvent w2dEvent) {
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
    	if(!functions.contains(function)){
    		function.setHost(this);
            functions.add(function);	
    	}        
    }

    /**
     * @param view
     * @param g2d
     * @param viewPart
     */
    protected abstract void paintFunctions(View view, Graphics2D g2d, ViewPart viewPart);

    /**
     * paint the {@link GlyphMetric} from {@link MetricsPathFunction}
     * 
     * @param v2d
     * @param g2d
     * @param viewPart
     */
    protected void paintMetricsGlyphFunction(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
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
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        paintFunctions(view, g2d, viewPart);
        paintMetricsGlyphFunction(view, g2d, viewPart);
    }

}
