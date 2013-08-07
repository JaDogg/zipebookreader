package com.bhathigui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * create a panel with background repeated image
 * @author Bhathiya Perera
 */
public class RepeatPanel extends javax.swing.JPanel {
    
    //------------------------------------------------------------
    private int w=8;
    private int h=8;
    private String url="/com/bhathigui/res/back.png";
    //------------------------------------------------------------
    
    //------------------------------------------------------------
    private Rectangle r=null;
    private BufferedImage buf=null;
    private TexturePaint paint=null;
    private boolean mouseEnterBorder=false;
     //------------------------------------------------------------
    
    //------------------------------------------------------------
    private static final Border emptyBorder  
            = BorderFactory.createEmptyBorder(1,1,1,1);
    
    private static final Border selectBorder 
            = BorderFactory.createLineBorder(Color.white,1);
    
            private MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Point p = e.getPoint();
                setBorder(selectBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                 setBorder(emptyBorder);
            }          
            
        };
    //------------------------------------------------------------
            
    /**
     * create default repeat panel
     */        
    public RepeatPanel() {
        super();
        updateAll();
        handleHighlighter();
        
    }
    /**
     * Create a Repeat panel with given options
     * @param w width
     * @param h height
     * @param url  resource path
     */
    public RepeatPanel(int w, int h, String url) {
        super();
        this.w = w;
        this.h = h;
        this.url = url;
        updateAll();
        handleHighlighter();
    }
    /**
     * create default repeat panel
     */ 
    public RepeatPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        updateAll();
        handleHighlighter();
    }
    /**
     * create default repeat panel
     */ 
    public RepeatPanel(LayoutManager layout) {
        super(layout);
        updateAll();
        handleHighlighter();
    }
    /**
     * create default repeat panel
     */ 
    public RepeatPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        updateAll();
        handleHighlighter();
    }
    
    /**
     * @return width of image
     */
    public int getW() {
        return w;
    }

    /**
     * @param w width of image
     */
    public void setW(int w) {
        this.w = w;
        updateRect();
    }

    /**
     * 
     * @return height of image
     */
    public int getH() {
        return h;
    }

    /**
     * 
     * @param h height of image
     */
    public void setH(int h) {
        this.h = h;
        updateRect();
    }

    /**
     * 
     * @return image path of image resource <br>
     * default "/com/bhathigui/res/back.png"
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url image path of image resource
     */
    public void setUrl(String url) {
        this.url = url;
        updateImage();
    }

    public boolean isMouseEnterBorder() {
        return mouseEnterBorder;
    }

    public void setMouseEnterBorder(boolean mouseEnterBorder) {
        this.mouseEnterBorder = mouseEnterBorder;
        handleHighlighter();
    }
    
    private void updateRect(){
        r = new Rectangle(0, 0, w, h);//width and height of img
        paint = new TexturePaint(buf,r);
    }
    private void updateImage(){
        try {
            buf = ImageIO.read(getClass().getResource(url));//img
            paint = new TexturePaint(buf,r);
        } catch (IOException ex) {
            
        }
    }
    
    private void updateAll(){
         try {
            r = new Rectangle(0, 0, w, h);//width and height of img
            buf = ImageIO.read(getClass().getResource(url));//img
            paint = new TexturePaint(buf,r);
        } catch (IOException ex) {
            
        }
        setBorder(emptyBorder);
    }
    
    private void handleHighlighter(){
        if(isMouseEnterBorder()){
            this.addMouseListener(mouseListener);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;        
        g2.setPaint(paint);
        g2.fill(this.getVisibleRect());

  }               

}
