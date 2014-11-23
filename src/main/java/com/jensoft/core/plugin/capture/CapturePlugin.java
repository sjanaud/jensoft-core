/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capture;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * Capture Plugin
 */
public class CapturePlugin extends AbstractPlugin {

    public CapturePlugin() {
        setName("CapturePlugin");
    }

    /**
     * defines capture image file format
     */
    public enum ViewImageFormat {
        Png("PNG"), Bmp("BMP"), Jpeg("JPEG"), Wbmp("WBMP"), Gif("GIF");

        private String format;

        private ViewImageFormat(String format) {
            this.format = format;
        }

        /**
         * @return the format
         */
        public String getFormat() {
            return format;
        }

    }

    /**
     * capture the host view with as image file in the specified image file
     * format
     * 
     * @param dir
     *            the file image destination directory
     * @param fileName
     *            the file image name
     * @param width
     *            the image width
     * @param height
     *            the image height
     * @param viewImageFormat
     *            the image file format
     * @throws IOException
     */
    public final void captureViewAsImageFile(String dir, String fileName,
            int width, int height, ViewImageFormat viewImageFormat)
            throws IOException {

        File rootDir = new File(dir);
        rootDir.mkdirs();

        if (viewImageFormat == null) {
            viewImageFormat = ViewImageFormat.Png;
        }

        if (fileName == null) {
            fileName = "jensoft-view-capture." + viewImageFormat.getFormat();
        }
        else {
            if (!fileName.endsWith("." + viewImageFormat.getFormat())
                    || !fileName.endsWith("."
                            + viewImageFormat.getFormat().toLowerCase())) {
                fileName = fileName + "."
                        + viewImageFormat.getFormat().toLowerCase();
            }
        }

        String imageFilePath = dir + File.separator + fileName;

        File saveFile = new File(imageFilePath);
        BufferedImage viewImage = getProjection().getView().createViewEmitter().getImageView(width,
                                                                         height);
        viewImage.flush();
        ImageIO.write(viewImage, viewImageFormat.getFormat(), saveFile);
    }

    /**
     * capture view as buffered image
     * 
     * @param width
     *            the image view
     * @param height
     *            the image height
     * @param viewImageFormat
     *            view image format
     * @return view as buffered image
     */
    public final BufferedImage captureViewAsImage(int width, int height,
            ViewImageFormat viewImageFormat) {
        BufferedImage viewImage = getProjection().getView().createViewEmitter().getImageView(width,
                                                                         height);
        viewImage.flush();
        return viewImage;
    }

    /**
     * return new instance of a capture action
     * 
     * @param format
     *            the preset image format
     * @return capture action
     */
    public CaptureAction getCaptureAction(ViewImageFormat format) {
        return new CaptureAction(format.getFormat());
    }

    /**
     * return new instance of a capture action
     * 
     * @param format
     *            format as a string
     * @return capture action
     */
    public CaptureAction getCaptureAction(String format) {
        return new CaptureAction(format);
    }

    /**
     * Capture Action
     * 
     * @author Sebosaure
     */
    class CaptureAction implements ActionListener {

        private String format;

        public CaptureAction(String format) {
            this.format = format;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                View v2d = getProjection().getView();
                final BufferedImage viewImage = getProjection().getView().createViewEmitter()
                        .getImageView(v2d.getWidth(), v2d.getHeight());
                viewImage.flush();
                File saveFile = new File("savedimage." + format);
                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(saveFile);
                int rval = chooser.showSaveDialog(getProjection().getView());
                if (rval == JFileChooser.APPROVE_OPTION) {
                    saveFile = chooser.getSelectedFile();

                    try {
                        ImageIO.write(viewImage, format, saveFile);
                    }
                    catch (IOException ex) {
                    }
                }
            }
            catch (HeadlessException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
    }

}
