package com.jensoft.core.view.deflater;

import org.w3c.dom.Document;

import com.jensoft.core.view.View2D;

/**
 * <code>AbstractViewDeflater</code>
 * <p>
 * takes the responsibility to transform a view in XML Document.
 * </p>
 * 
 * @author sebastien janaud
 */
public abstract class AbstractViewDeflater {

    /** the deflate view */
    private View2D view2D;
    
    /**
     * create view Deflater
     */
    public AbstractViewDeflater() {
    }

    /**
     * create Deflater
     * 
     * @param view2D
     */
    public AbstractViewDeflater(View2D view2D) {
        this.view2D = view2D;
    }

    /**
     * @return the view2D
     */
    public View2D getView2D() {
        return view2D;
    }

    /**
     * @param view2d
     *            the view2D to set
     */
    public void setView2D(View2D view2d) {
        view2D = view2d;
    }

    /**
     * deflate the view in document
     * 
     * @return view as document
     */
    public abstract Document deflate();

}
