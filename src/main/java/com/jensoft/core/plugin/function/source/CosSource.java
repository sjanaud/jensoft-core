/**
 * 
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.List;


/**
 * <code>CosSource</code>
 * @author Sebastien Janaud
 *
 */
public class CosSource implements SourceFunction {

    
    /**
     * Create Cosinus source function
     */
    public CosSource(double minX, double maxX, double delta){        
    }
    
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getSource()
     */
    @Override
    public List<Point2D> getSource() {
       
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#select(double, double)
     */
    @Override
    public List<Point2D> select(double startX, double endX) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#evaluate(double)
     */
    @Override
    public Point2D evaluate(double x) {
        return new Point2D.Double(x, Math.cos(x));
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#next(double)
     */
    @Override
    public Point2D next(double x) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#previous(double)
     */
    @Override
    public Point2D previous(double x) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#min()
     */
    @Override
    public Point2D min() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#max()
     */
    @Override
    public Point2D max() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#minY()
     */
    @Override
    public Point2D minY() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getId()
     */
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        // TODO Auto-generated method stub

    }

}
