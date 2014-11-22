/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.jensoft.core.device.DevicePartComponent;

/**
 * <code>ViewEmitter</code>
 * <p>
 * takes the responsibility to emit view a variety of format
 * </p>
 * 
 * @author sebastien janaud
 */
public class ViewEmitter {

    /** view2D */
    private View view;

    /**
     * create empty emitter
     */
    public ViewEmitter() {
    }

    /**
     * create view emitter for the given view
     * 
     * @param view
     */
    public ViewEmitter(View view) {
        this.view = view;
    }
    
    
    /**
	 * get the view as an image outside swing UI.
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
		

		// image view
		BufferedImage viewImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics viewGraphics = viewImage.getGraphics();

		Graphics2D g2d = (Graphics2D) viewGraphics;

		
		Dimension old = view.getSize();
		
		view.setSize(new Dimension(width, height));

		// component part
		ViewPartComponent northPart = (ViewPartComponent) view.getWindowComponent(ViewPart.North);
		ViewPartComponent southPart = (ViewPartComponent) view.getWindowComponent(ViewPart.South);
		ViewPartComponent eastPart = (ViewPartComponent) view.getWindowComponent(ViewPart.East);
		ViewPartComponent westPart = (ViewPartComponent) view.getWindowComponent(ViewPart.West);
		DevicePartComponent devicePart = (DevicePartComponent) view.getWindowComponent(ViewPart.Device);

		// size component
		northPart.setSize(width, view.getPlaceHolderAxisNorth());
		southPart.setSize(width, view.getPlaceHolderAxisSouth());
		eastPart.setSize(view.getPlaceHolderAxisEast(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth());
		westPart.setSize(view.getPlaceHolderAxisWest(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth());
		devicePart.setSize(view.getWidth() - view.getPlaceHolderAxisWest() - view.getPlaceHolderAxisEast(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth());

		// buffered image

		BufferedImage northImage = null;
		if (view.getPlaceHolderAxisNorth() > 0) {
			northImage = new BufferedImage(view.getWidth(), view.getPlaceHolderAxisNorth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D ng2d = (Graphics2D) northImage.getGraphics();
			northPart.paintComponent(ng2d);
			ng2d.dispose();

		}

		BufferedImage southImage = null;
		if (view.getPlaceHolderAxisSouth() > 0) {
			southImage = new BufferedImage(view.getWidth(), view.getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D sg2d = (Graphics2D) southImage.getGraphics();
			southPart.paintComponent(sg2d);
			sg2d.dispose();

		}

		BufferedImage eastImage = null;
		if (view.getPlaceHolderAxisEast() > 0) {
			eastImage = new BufferedImage(view.getPlaceHolderAxisEast(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D eg2d = (Graphics2D) eastImage.getGraphics();
			eastPart.paintComponent(eg2d);
			eg2d.dispose();

		}

		BufferedImage westImage = null;
		if (view.getPlaceHolderAxisWest() > 0) {
			westImage = new BufferedImage(view.getPlaceHolderAxisWest(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D wg2d = (Graphics2D) westImage.getGraphics();
			westPart.paintComponent(wg2d);
			wg2d.dispose();

		}

		BufferedImage deviceImage = null;
		if (view.getWidth() - view.getPlaceHolderAxisWest() - view.getPlaceHolderAxisEast() > 0 && view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth() > 0) {
			deviceImage = new BufferedImage(view.getWidth() - view.getPlaceHolderAxisWest() - view.getPlaceHolderAxisEast(), view.getHeight() - view.getPlaceHolderAxisNorth() - view.getPlaceHolderAxisSouth(), BufferedImage.TYPE_INT_ARGB);
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

		if (view.getBackgroundPainter() != null) {
			view.getBackgroundPainter().paintViewBackground(view,width,height, g2d);
		}

		if (northImage != null) {
			g2d.drawImage(northImage, 0, 0, northImage.getWidth(), northImage.getHeight(), null);
		}

		if (southImage != null) {
			g2d.drawImage(southImage, 0, view.getHeight() - view.getPlaceHolderAxisSouth(), southImage.getWidth(), southImage.getHeight(), null);
			southImage.flush();
		}

		if (eastImage != null) {
			g2d.drawImage(eastImage, view.getWidth() - view.getPlaceHolderAxisEast(), view.getPlaceHolderAxisNorth(), eastImage.getWidth(), eastImage.getHeight(), null);
			eastImage.flush();
		}

		if (westImage != null) {
			g2d.drawImage(westImage, 0, view.getPlaceHolderAxisNorth(), westImage.getWidth(), westImage.getHeight(), null);
			westImage.flush();
		}

		if (deviceImage != null) {
			g2d.drawImage(deviceImage, view.getPlaceHolderAxisWest(), view.getPlaceHolderAxisNorth(), deviceImage.getWidth(), deviceImage.getHeight(), null);
			deviceImage.flush();
		}

		g2d.dispose();
		viewImage.flush();

		
		if(old != null)
			view.setSize(old);
		return viewImage;
	}

    /**
     * emit the template as {@link BufferedImage}
     * 
     * @return the template as buffered image
     */
    public BufferedImage emitAsBufferedImage() {
        return getImageView(view.getWidth(), view.getHeight());
    }

    /**
     * emit the template as {@link BufferedImage}
     * 
     * @param width
     *            the view width
     * @param height
     *            the view height
     * @return the template as buffered image
     */
    public BufferedImage emitAsBufferedImage(int width, int height) {
        return getImageView(width, height);
    }

    /**
     * emit the template as image string base 64
     * 
     * @return the template as buffered image in string base 64
     */
    public String emitAsImageBase64() {
        BufferedImage img = emitAsBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", os);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Base64 b64 = new Base64();
        String result = b64.encode(os.toByteArray());
        try {
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * emit the template as view
     * 
     * @return the template as buffered image
     */
    public View emitAsView() {
        return view;
    }
    
    /**
     * emit PNG image on the given {@link OutputStream} with the current view size
     * 
     * @throws IOException
     */
    public void emitPNGImageOnStream(OutputStream outputStream)throws IOException{        
        ImageIO.write(emitAsBufferedImage(), "png", outputStream);
    }
    
    /**
     * emit PNG image on the given {@link OutputStream} with given image size
     * 
     * @throws IOException
     */
    public void emitPNGImageOnStream(OutputStream outputStream,int width,int height)throws IOException{        
        ImageIO.write(emitAsBufferedImage(width,height), "png", outputStream);
    }
    
    /**
     * emit JPG image on the given {@link OutputStream}
     * 
     * @throws IOException
     */
    public void emitJPGImageOnStream(OutputStream outputStream)throws IOException{        
        ImageIO.write(emitAsBufferedImage(), "jpg", outputStream);
    }
    
    /**
     * emit JPG image on the given {@link OutputStream}
     * 
     * @throws IOException
     */
    public void emitGIFImageOnStream(OutputStream outputStream)throws IOException{        
        ImageIO.write(emitAsBufferedImage(), "gif", outputStream);
    }

    /**
     * emit the template as image file with the specified image file name in the
     * specified directory
     * 
     * @param directoryName
     *            the directory where the image should be emit
     * @param imageFileName
     *            the image file name
     * @throws IOException
     *             if {@link ImageIO} throws an exception during writing
     *             operation
     */
    public void emitAsImageFile(String directoryName, String imageFileName)
            throws IOException {

        File directoryFile = new File(directoryName);
        if (!directoryFile.exists()) {
            directoryFile.mkdir();
        }

        ImageIO.write(emitAsBufferedImage(), "png", new FileOutputStream(
                                                                         directoryName + File.separator + imageFileName
                                                                                 + ".png"));

    }

}

/**
 * <code>Base64</code> process base 64 operation
 * 
 * @author Sebastien Janaud
 */
class Base64 {

    private String lineSeparator;
    private int lineLength;

    public Base64() {
        lineSeparator = System.getProperty("line.separator");
        lineLength = 0;
    }

    public String encode(byte[] bin) {
        return encode(bin, 0, bin.length);
    }

    /**
     * Encode an array of bytes as Base64. It will be broken into lines if the
     * line length is not 0. If broken into lines, the last line is not
     * terminated with a line separator.
     * param ba The byte array to encode.
     */
    public String encode(byte[] bin, int str, int len) {
        int ol; // output length
        StringBuffer sb; // string buffer for output(must be local for recursion
                         // to work).
        int lp; // line position(must be local for recursion to work).
        int el; // even multiple of 3 length
        int ll; // leftover length

        // CREATE OUTPUT BUFFER
        ol = (len + 2) / 3 * 4;
        if (lineLength != 0) {
            int lines = (ol + lineLength - 1) / lineLength - 1;
            if (lines > 0) {
                ol += lines * lineSeparator.length();
            }
        }
        sb = new StringBuffer(ol);
        lp = 0;

        // EVEN MULTIPLES OF 3
        el = len / 3 * 3;
        ll = len - el;
        for (int xa = 0; xa < el; xa += 3) {
            int cv;
            int c0, c1, c2, c3;

            if (lineLength != 0) {
                lp += 4;
                if (lp > lineLength) {
                    sb.append(lineSeparator);
                    lp = 4;
                }
            }

            // get next three bytes in unsigned form lined up, in big-endian
            // order
            cv = bin[xa + str + 0] & 0xFF;
            cv <<= 8;
            cv |= bin[xa + str + 1] & 0xFF;
            cv <<= 8;
            cv |= bin[xa + str + 2] & 0xFF;

            // break those 24 bits into a 4 groups of 6 bits, working LSB to
            // MSB.
            c3 = cv & 0x3F;
            cv >>>= 6;
            c2 = cv & 0x3F;
            cv >>>= 6;
            c1 = cv & 0x3F;
            cv >>>= 6;
            c0 = cv & 0x3F;

            // Translate into the equivalent alpha character emitting them in
            // big-endian order.
            sb.append(ENCODE[c0]);
            sb.append(ENCODE[c1]);
            sb.append(ENCODE[c2]);
            sb.append(ENCODE[c3]);
        }

        // LEFTOVERS
        if (lineLength != 0 && ll > 0) {
            lp += 4;
            if (lp > lineLength) {
                sb.append(lineSeparator);
                lp = 4;
            }
        }
        if (ll == 1) {
            sb.append(
                      encode(new byte[] { bin[el + str], 0, 0 }).substring(0, 2))
                    .append("=="); // Use recursion so escaping logic is not
                                   // repeated, replacing last 2 chars with
                                   // "==".
        }
        else if (ll == 2) {
            sb.append(
                      encode(new byte[] { bin[el + str], bin[el + str + 1], 0 })
                              .substring(0, 3)).append("="); // Use recursion so
                                                             // escaping logic is
                                                             // not repeated,
                                                             // replacing last
                                                             // char and "=".
        }
        if (ol != sb.length()) {
            throw new RuntimeException(
                                       "Error in Base64 encoding method: Calculated output length of "
                                               + ol + " did not match actual length of "
                                               + sb.length());
        }
        return sb.toString();
    }

    public byte[] decode(String b64) {
        return decode(b64, 0, b64.length());
    }

    /**
     * Decode a Base64 string to an array of bytes. The string must have a
     * length evenly divisible by 4 (not counting line separators and other
     * ignorable characters, like whitespace).
     */
    public byte[] decode(String b64, int str, int len) {
        byte[] ba; // target byte array
        int dc; // decode cycle (within sequence of 4 input chars).
        int rv; // reconstituted value
        int ol; // output length
        int pc; // padding count

        ba = new byte[len / 4 * 3]; // create array to largest possible size.
        dc = 0;
        rv = 0;
        ol = 0;
        pc = 0;

        for (int xa = 0; xa < len; xa++) {
            int ch = b64.charAt(xa + str);
            int value = ch <= 255 ? DECODE[ch] : IGNORE;
            if (value != IGNORE) {
                if (value == PAD) {
                    value = 0;
                    pc++;
                }
                switch (dc) {
                    case 0: {
                        rv = value;
                        dc = 1;
                    }
                        break;

                    case 1: {
                        rv <<= 6;
                        rv |= value;
                        dc = 2;
                    }
                        break;

                    case 2: {
                        rv <<= 6;
                        rv |= value;
                        dc = 3;
                    }
                        break;

                    case 3: {
                        rv <<= 6;
                        rv |= value;

                        // Completed a cycle of 4 chars, so recombine the four 6-bit
                        // values in big-endian order
                        ba[ol + 2] = (byte) rv;
                        rv >>>= 8;
                        ba[ol + 1] = (byte) rv;
                        rv >>>= 8;
                        ba[ol] = (byte) rv;
                        ol += 3;
                        dc = 0;
                    }
                        break;
                }
            }
        }
        if (dc != 0) {
            throw new ArrayIndexOutOfBoundsException(
                                                     "Base64 data given as input was not an even multiple of 4 characters (should be padded with '=' characters).");
        }
        ol -= pc;
        if (ba.length != ol) {
            byte[] b2 = new byte[ol];
            System.arraycopy(ba, 0, b2, 0, ol);
            ba = b2;
        }
        return ba;
    }

    /**
     * Set maximum line length for encoded lines. Ignored by decode.
     * 
     * @param len
     *            Length of each line. 0 means no newlines inserted. Must be a
     *            multiple of 4.
     */
    public void setLineLength(int len) {
        lineLength = len / 4 * 4;
    }

    /**
     * Set the line separator sequence for encoded lines. Ignored by decode.
     * Usually contains only a combination of chars \n and \r, but could be any
     * chars except 'A'-'Z', 'a'-'z', '0'-'9', '+' and '/'.
     * 
     * @param linsep
     *            Line separator - may be "" but not null.
     */
    public void setLineSeparator(String linsep) {
        lineSeparator = linsep;
    }

    static private final char[] ENCODE = new char[64]; // translation array for
                                                       // encoding
    static private final int[] DECODE = new int[256]; // translation array for
                                                      // decoding
    static private final int IGNORE = -1; // flag for values to ignore when
                                          // decoding
    static private final int PAD = -2; // flag value for padding value when
                                       // decoding

    static private final Base64 BASE64 = new Base64(); // default converter

    static {
        for (int xa = 0; xa <= 25; xa++) {
            ENCODE[xa] = (char) ('A' + xa);
        } // 0..25 -> 'A'..'Z'
        for (int xa = 0; xa <= 25; xa++) {
            ENCODE[xa + 26] = (char) ('a' + xa);
        } // 26..51 -> 'a'..'z'
        for (int xa = 0; xa <= 9; xa++) {
            ENCODE[xa + 52] = (char) ('0' + xa);
        } // 52..61 -> '0'..'9'
        ENCODE[62] = '+';
        ENCODE[63] = '/';

        for (int xa = 0; xa < 256; xa++) {
            DECODE[xa] = IGNORE;
        } // set all chars to IGNORE, first
        for (int xa = 0; xa < 64; xa++) {
            DECODE[ENCODE[xa]] = xa;
        } // set the Base 64 chars to their integer byte values
        DECODE['='] = PAD;
    }

    static public String toString(byte[] dta) {
        return BASE64.encode(dta);
    }

    static public String toString(byte[] dta, int str, int len) {
        return BASE64.encode(dta, str, len);
    }

    static public byte[] toBytes(String b64) {
        return BASE64.decode(b64);
    }

    static public byte[] toBytes(String b64, int str, int len) {
        return BASE64.decode(b64, str, len);
    }

} // END PUBLIC CLASS

