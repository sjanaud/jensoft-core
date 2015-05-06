/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.message;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.Spectral;
import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.message.ui.MessageScrollbarUI;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;

/**
 * <code>Message<code>
 * 
 * @author Sebastien Janaud
 */
public class Message extends JComponent {

    /** uid */
    static final long serialVersionUID = 2615212624870835049L;

    /** messge area */
    private MessageArea area;

    /** host plugin */
    private MessagePlugin host;

    private boolean visibleMessage = false;

    /** text message */
    private String text;

    /** title message */
    private String title;

    /** message width */
    private int messageWidth = 300;

    /** message height */
    private int messageHeight = 150;

    /** message header */
    private Header header;

    /** x start for move operation */
    int xStartMove;

    /** y start for move operation */
    int yStartMove;

    /** move lock flag */
    boolean move = false;

    /** resize lock flag */
    boolean resize = false;

    /** message text area */
    private JTextArea textArea;

    /** delete background color */
    private boolean lockDeleteBackground = false;

    /** minimize background color */
    private boolean lockMinimizeBackground = false;

    /** maximize background color */
    private boolean lockMaximizeBackground = false;

    /** lock minimize */
    private boolean lockMinimize = true;

    /** lock maximize */
    private boolean lockMaximize = false;

    /** mini frame shape */
    private Rectangle2D miniFrame;

    /** mini roll over flag */
    private boolean lockMiniFrameRollover = false;

    private SizerComponent NSizer;
    private SizerComponent SSizer;
    private SizerComponent WSizer;
    private SizerComponent ESizer;
    private SizerComponent NESizer;
    private SizerComponent NWSizer;
    private SizerComponent SESizer;
    private SizerComponent SWSizer;

    /**
     * create message
     */
    public Message() {

        NSizer = new SizerComponent(SizerPart.N);
        SSizer = new SizerComponent(SizerPart.S);
        WSizer = new SizerComponent(SizerPart.W);
        ESizer = new SizerComponent(SizerPart.E);
        NESizer = new SizerComponent(SizerPart.NE);
        NWSizer = new SizerComponent(SizerPart.NW);
        SESizer = new SizerComponent(SizerPart.SE);
        SWSizer = new SizerComponent(SizerPart.SW);

        area = new MessageArea();
        area.getArea().setForeground(Color.BLACK);
        area.getArea().setFont(new Font("Tahoma", Font.PLAIN, 11));
        area.getArea().setSelectionColor(new Alpha(Spectral.SPECTRAL_RED.brighter(), 100));

        header = new Header();
        header.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (move) {
                    int tx = e.getX();
                    int ty = e.getY();
                    int x = (int) (getBounds().getX() + (tx - xStartMove));
                    int y = (int) (getBounds().getY() + (ty - yStartMove));
                    int width = (int) getBounds().getWidth();
                    int height = (int) getBounds().getHeight();
                    if (x < 5) {
                        x = 5;
                    }
                    if (x + width > getHost().getProjection().getDevice2D().getDeviceWidth() - 5) {
                        x = (getHost().getProjection().getDevice2D().getDeviceWidth() - width - 5);
                    }
                    if (y < 5) {
                        y = 5;
                    }
                    if (y + height > getHost().getProjection().getDevice2D().getDeviceHeight() - 5) {
                        y = (getHost().getProjection().getDevice2D().getDeviceHeight() - height - 5);
                    }
                    Rectangle newBound = new Rectangle(x, y, width, height);
                    Message.this.setBounds(newBound);
                }
            }
        });

        header.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                move = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                xStartMove = e.getX();
                yStartMove = e.getY();
                move = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                move = true;
                getHost().getProjection().getView().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                getHost().getProjection().getView().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    /**
     * set the text scroller color
     * 
     * @param color
     *            the color to set to scroller
     */
    public void setScrollerColor(Color color) {
        area.getVerticalScrollBar().setUI(new MessageScrollbarUI(color));
        area.getHorizontalScrollBar().setUI(new MessageScrollbarUI(color));
        area.updateUI();
    }

    /**
     * set the message dimension
     * 
     * @param width
     *            the message width to set
     * @param height
     *            the message height to set
     */
    public void setMessageDimension(int width, int height) {
        messageWidth = width;
        messageHeight = height;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(java.awt.Dimension)
     */
    @Override
    public void setSize(Dimension d) {
        setMessageDimension((int) d.getWidth(), (int) d.getHeight());
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(int, int)
     */
    @Override
    public void setSize(int width, int height) {
        setMessageDimension(width, height);
    }

    /**
     * @return the messageWidth
     */
    public int getMessageWidth() {
        return messageWidth;
    }

    /**
     * @param messageWidth
     *            the messageWidth to set
     */
    public void setMessageWidth(int messageWidth) {
        this.messageWidth = messageWidth;
    }

    /**
     * @return the messageHeight
     */
    public int getMessageHeight() {
        return messageHeight;
    }

    /**
     * @param messageHeight
     *            the messageHeight to set
     */
    public void setMessageHeight(int messageHeight) {
        this.messageHeight = messageHeight;
    }

    /**
     * @return the minimizedFrame
     */
    public Rectangle2D getMiniFrame() {
        return miniFrame;
    }

    /**
     * set message font
     * 
     * @param f
     * @see javax.swing.JTextArea#setFont(java.awt.Font)
     */
    public void setMessageFont(Font f) {
        textArea.setFont(f);
    }

    /**
     * set message foreground
     * 
     * @param fg
     * @see javax.swing.JComponent#setForeground(java.awt.Color)
     */
    public void setMessageForeground(Color fg) {
        textArea.setForeground(fg);
    }

    /**
     * set message foreground
     * 
     * @param fg
     * @see javax.swing.JComponent#setForeground(java.awt.Color)
     */
    public void setMessageTitleColor(Color fg) {
        header.titleLabel.setForeground(fg);
    }

    /**
     * @param miniFrame
     *            the mini frame to set
     */
    public void setMiniFrame(Rectangle2D miniFrame) {
        this.miniFrame = miniFrame;
    }

    /**
     * @return the lockDeleteBackground
     */
    public boolean isLockDeleteBackground() {
        return lockDeleteBackground;
    }

    /**
     * @param lockDeleteBackground
     *            the lockDeleteBackground to set
     */
    public void setLockDeleteBackground(boolean lockDeleteBackground) {
        this.lockDeleteBackground = lockDeleteBackground;
    }

    /**
     * @return the lockMinimizeBackground
     */
    public boolean isLockMinimizeBackground() {
        return lockMinimizeBackground;
    }

    /**
     * @param lockMinimizeBackground
     *            the lockMinimizeBackground to set
     */
    public void setLockMinimizeBackground(boolean lockMinimizeBackground) {
        this.lockMinimizeBackground = lockMinimizeBackground;
    }

    /**
     * @return the lockMaximize
     */
    public boolean isLockMaximize() {
        return lockMaximize;
    }

    /**
     * @param lockMaximize
     *            the lockMaximize to set
     */
    public void setLockMaximize(boolean lockMaximize) {
        this.lockMaximize = lockMaximize;
    }

    /**
     * @return the lockMaximizeBackground
     */
    public boolean isLockMaximizeBackground() {
        return lockMaximizeBackground;
    }

    /**
     * @param lockMaximizeBackground
     *            the lockMaximizeBackground to set
     */
    public void setLockMaximizeBackground(boolean lockMaximizeBackground) {
        this.lockMaximizeBackground = lockMaximizeBackground;
    }

    /**
     * @return the lockMinimize
     */
    public boolean isLockMinimize() {
        return lockMinimize;
    }

    /**
     * @param lockMinimize
     *            the lockMinimize to set
     */
    public void setLockMinimize(boolean lockMinimize) {
        this.lockMinimize = lockMinimize;
    }

    /**
     * @return the lockMinimizedFrameRollover
     */
    public boolean isLockMiniFrameRollover() {
        return lockMiniFrameRollover;
    }

    /**
     * @param lockMinimizedFrameRollover
     *            the lockMinimizedFrameRollover to set
     */
    public void setLockMiniFrameRollover(boolean lockMinimizedFrameRollover) {
        lockMiniFrameRollover = lockMinimizedFrameRollover;
    }

    /**
     * create message
     */
    public Message(String text) {
        this();
        setText(text);
    }

    /**
     * create message
     */
    public Message(String title, String text) {
        this();
        setText(text);
        setTitle(title);
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
        textArea.setText(text);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
        header.setTitle(title);
    }

    /**
     * @return the visibleMessage
     */
    public boolean isVisibleMessage() {
        return visibleMessage;
    }

    /**
     * @param visibleMessage
     *            the visibleMessage to set
     */
    public void setVisibleMessage(boolean visibleMessage) {
        this.visibleMessage = visibleMessage;
    }

    /**
     * override this method to listen t he message closing
     */
    public void onClose() {
    }

    /**
     * message header component
     * 
     * @author Sebastien Janaud
     */
    class Header extends JComponent {

        private static final long serialVersionUID = -6679725693988970902L;
        JLabel titleLabel;
        JLabel close;
        JLabel maximize;
        JLabel minimize;

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            titleLabel.setBounds(10, y, 260, height);
            minimize.setBounds(width - 65, y, 20, height);
            maximize.setBounds(width - 45, y, 20, height);
            close.setBounds(width - 25, y, 20, height);

            add(titleLabel);
            add(minimize);
            add(maximize);
            add(close);
        }

        /**
         * set header title
         * 
         * @param title
         */
        public void setTitle(String title) {
            titleLabel.setText(title);
        }

        /**
         * create header
         */
        public Header() {
            ImageIcon iconClose = SharedIcon.getCommon(Common.CLOSE_SQUARE);
            ImageIcon iconMaximize = SharedIcon.getCommon(Common.MAXIMIZE_SQUARE);
            ImageIcon iconMinimize = SharedIcon.getCommon(Common.MINIMIZE_SQUARE);
            titleLabel = new JLabel("");
            titleLabel.setForeground(NanoChromatique.ORANGE);
            add(titleLabel);

            close = new JLabel("");
            close.setIcon(iconClose);
            add(close);
            close.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    getHost().closeCurrentMessage();
                    onClose();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    lockDeleteBackground = true;
                    if (getHost() != null) {
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    lockDeleteBackground = false;
                    if (getHost() != null) {
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    lockDeleteBackground = false;
                    getHost().repaintDevice();
                }
            });

            maximize = new JLabel("");
            maximize.setIcon(iconMaximize);
            add(maximize);
            maximize.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    lockMaximize = true;
                    lockMinimize = false;
                    lockMaximizeBackground = false;
                    getHost().maximizeMessage(Message.this);
                    getHost().repaintDevice();
                    maximize.setEnabled(false);
                    minimize.setEnabled(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    if (!lockMaximize) {
                        lockMaximizeBackground = true;
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if (!lockMaximize) {
                        lockMaximizeBackground = false;
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    lockMaximizeBackground = false;
                    getHost().repaintDevice();
                }
            });

            minimize = new JLabel("");
            minimize.setIcon(iconMinimize);
            minimize.setEnabled(false);
            add(minimize);
            minimize.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    lockMinimize = true;
                    lockMinimizeBackground = false;
                    if (lockMaximize) {
                        getHost().unMaximizeMessage(Message.this);
                        lockMaximize = false;
                    }
                    maximize.setEnabled(true);
                    minimize.setEnabled(false);
                    getHost().repaintDevice();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    if (!lockMinimize) {
                        lockMinimizeBackground = true;
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if (!lockMinimize) {
                        lockMinimizeBackground = false;
                        getHost().repaintDevice();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    lockMinimizeBackground = false;
                    getHost().repaintDevice();
                }
            });

        }

        /*
         * (non-Javadoc)
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
        }
    }

    public enum SizerPart {
        N,
        S,
        W,
        E,
        NE,
        NW,
        SE,
        SW;
    }

    class SizerComponent extends JComponent {

        private SizerPart part;

        private int startX;
        private int startY;

        private int currentX;
        private int currentY;

        public SizerComponent(SizerPart part) {
            this.part = part;
            createSizerListener();
        }

        private void resizeMessage() {
            int deltaX = currentX - startX;
            int deltaY = currentY - startY;

            Rectangle r = Message.this.getBounds();
            Rectangle newBound = null;
            if (part == SizerPart.N) {
                newBound = new Rectangle((int) r.getX(), (int) (r.getY() + deltaY), (int) r.getWidth(),
                                         (int) (r.getHeight() - deltaY));
            }
            else if (part == SizerPart.S) {
                newBound = new Rectangle((int) r.getX(), (int) r.getY(), (int) r.getWidth(),
                                         (int) (r.getHeight() + deltaY));
            }
            else if (part == SizerPart.W) {
                newBound = new Rectangle((int) (r.getX() + deltaX), (int) r.getY(), (int) (r.getWidth() - deltaX),
                                         (int) r.getHeight());
            }
            else if (part == SizerPart.E) {
                newBound = new Rectangle((int) r.getX(), (int) r.getY(), (int) (r.getWidth() + deltaX),
                                         (int) r.getHeight());
            }
            else if (part == SizerPart.NE) {
                newBound = new Rectangle((int) r.getX(), (int) (r.getY() + deltaY), (int) (r.getWidth() + deltaX),
                                         (int) (r.getHeight() - deltaY));
            }
            else if (part == SizerPart.NW) {
                newBound = new Rectangle((int) (r.getX() + deltaX), (int) (r.getY() + deltaY),
                                         (int) (r.getWidth() - deltaX), (int) (r.getHeight() - deltaY));
            }
            else if (part == SizerPart.SE) {
                newBound = new Rectangle((int) r.getX(), (int) r.getY(), (int) (r.getWidth() + deltaX),
                                         (int) (r.getHeight() + deltaY));
            }
            else if (part == SizerPart.SW) {
                newBound = new Rectangle((int) (r.getX() + deltaX), (int) r.getY(), (int) (r.getWidth() - deltaX),
                                         (int) (r.getHeight() + deltaY));
            }

            if (newBound.getX() < 5
                    || newBound.getX() + newBound.getWidth() > getHost().getProjection().getDevice2D().getDeviceWidth() - 5) {
               // System.out.println("return 3");
                return;
            }
            if (newBound.getY() < 5
                    || newBound.getY() + newBound.getHeight() > getHost().getProjection().getDevice2D().getDeviceHeight() - 5) {
                //System.out.println("return 4");
                return;
            }

            Message.this.setBounds(newBound);

        }

        private void createSizerListener() {
            addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseMoved(MouseEvent e) {
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (!resize) {
                        return;
                    }
                    if (part == SizerPart.N) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.S) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.W) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.E) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.NE) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.NW) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.SE) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.SW) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                    }
                    currentX = e.getX();
                    currentY = e.getY();
                    resizeMessage();
                }
            });

            addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    resize = false;
                }

                @Override
                public void mousePressed(MouseEvent e) {

                    resize = true;

                    startX = e.getX();
                    startY = e.getY();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (resize) {
                        return;
                    }
                    getHost().getProjection().getView().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (resize) {
                        return;
                    }
                    if (part == SizerPart.N) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.S) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.W) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.E) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.NE) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.NW) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.SE) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                    }
                    else if (part == SizerPart.SW) {
                        getHost().getProjection().getView()
                                .setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                    }

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Graphics2D g2d = (Graphics2D)g;
            // g2d.setColor(Color.BLUE);
            // g2d.draw(new Rectangle2D.Double(0, 0, getWidth()-1, getHeight()-1));
        }
    }

    /**
     * message content
     * 
     * @author sebastien janaud
     */
    class MessageArea extends JScrollPane {

        private static final long serialVersionUID = 7540521627693732418L;

        public MessageArea() {
            setName("Jensoft MessageArea");
            textArea = new JTextArea();
            textArea.setOpaque(false);

            setLayout(new ScrollPaneLayout.UIResource());
            setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            setViewport(createViewport());

            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            getViewport().setOpaque(false);
            setOpaque(false);
            setVerticalScrollBar(createVerticalScrollBar());
            setHorizontalScrollBar(createHorizontalScrollBar());
            getVerticalScrollBar().setUI(new MessageScrollbarUI());
            getHorizontalScrollBar().setUI(new MessageScrollbarUI());
            getVerticalScrollBar().setPreferredSize(new Dimension(12, 1000));
            getHorizontalScrollBar().setPreferredSize(new Dimension(1000, 12));
            setViewportView(textArea);

            if (!getComponentOrientation().isLeftToRight()) {
                viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
            }

            updateUI();
        }

        /**
         * get the text area of message
         * 
         * @return text area message
         */
        public JTextArea getArea() {
            return textArea;
        }

        /*
         * (non-Javadoc)
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
        }

    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setBounds(int, int, int, int)
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        try {
            super.setBounds(x, y, width, height);

            // --------------------------------------//
            // 6 (north sizer) |
            // --------------------------------------//
            // 6 west | 30 (header) | 6
            // |---------------------|
            // | (content) |
            // --------------------------------------//
            // 6 (south sizer)
            // --------------------------------------//

            int sizerSensible = 6;
            int headerHeight = 30;

            area.setBounds(sizerSensible, headerHeight + sizerSensible, width - 2 * sizerSensible, height
                    - headerHeight - 2 * sizerSensible);
            header.setBounds(sizerSensible, sizerSensible, width - 2 * sizerSensible, headerHeight);

            NSizer.setBounds(sizerSensible, 0, width - 2 * sizerSensible, sizerSensible);
            SSizer.setBounds(sizerSensible, height - sizerSensible, width - 2 * sizerSensible, sizerSensible);
            WSizer.setBounds(0, sizerSensible, sizerSensible, height - 2 * sizerSensible);
            ESizer.setBounds(width - sizerSensible, sizerSensible, sizerSensible, height - 2 * sizerSensible);

            NESizer.setBounds(width - sizerSensible, 0, sizerSensible, sizerSensible);
            NWSizer.setBounds(0, 0, sizerSensible, sizerSensible);
            SESizer.setBounds(width - sizerSensible, height - sizerSensible, sizerSensible, sizerSensible);
            SWSizer.setBounds(0, height - sizerSensible, sizerSensible, sizerSensible);

            add(NSizer);
            add(SSizer);
            add(WSizer);
            add(ESizer);

            add(NESizer);
            add(NWSizer);
            add(SESizer);
            add(SWSizer);

            add(area);
            add(header);
            area.updateUI();

        }
        catch (Exception e) {
        }
    }

    /**
     * @return the host
     */
    public MessagePlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(MessagePlugin host) {
        this.host = host;
    }

    /**
     * @return the area
     */
    public MessageArea getArea() {
        return area;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Shape r = null;
        if (!isLockMaximize()) {
            r = new RoundRectangle2D.Double(0, 0, getWidth() - 2, getHeight() - 2, 20, 20);
        }
        else {
            r = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2d = (Graphics2D) g;
        if (lockDeleteBackground) {
            g2d.setColor(new Alpha(Color.red, 80));
        }
        else if (lockMinimizeBackground) {
            g2d.setColor(new Alpha(Color.YELLOW, 80));
        }
        else if (lockMaximizeBackground) {
            g2d.setColor(new Alpha(TangoPalette.ORANGE3, 80));
        }
        else {
            g2d.setColor(new Alpha(Spectral.SPECTRAL_BLUE2, 80));
        }

        g2d.fill(r);

        g2d.setStroke(new BasicStroke(1f));

        if (lockDeleteBackground) {
            g2d.setColor(new Alpha(Color.red, 250));
        }
        else if (lockMinimizeBackground) {
            g2d.setColor(new Alpha(Color.WHITE, 240));
            // g2d.draw(r);
        }
        else {
            if (!isLockMaximize()) {
                g2d.setColor(new Alpha(Color.WHITE, 240));
                g2d.draw(r);
            }
        }

    }

}
