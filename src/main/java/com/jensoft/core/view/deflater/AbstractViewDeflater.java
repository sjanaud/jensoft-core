package com.jensoft.core.view.deflater;

import org.w3c.dom.Document;

import com.jensoft.core.view.View;

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
    private View view2D;
    
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
    public AbstractViewDeflater(View view2D) {
        this.view2D = view2D;
    }

    /**
     * @return the view
     */
    public View getView() {
        return view2D;
    }

    /**
     * @param view
     *            the view to set
     */
    public void setView(View view) {
        this.view2D = view;
    }

    /**
     * deflate the view in document
     * 
     * @return view as document
     */
    public abstract Document deflate();

}
