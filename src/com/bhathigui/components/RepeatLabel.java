package com.bhathigui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 * create a label with background repeated image
 * this is like a mix of WhiteLabel and RepeatPanel
 * 
 * @author Bhathiya Perera
 */
public class RepeatLabel extends javax.swing.JLabel {
    
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
     * create default repeat label
     */  
     public RepeatLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        updateAll();
        handleHighlighter();      
        whiteLabel();
    }
    /**
     * create default repeat label
     */  
    public RepeatLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        updateAll();
        handleHighlighter();       
        whiteLabel();
    }
    /**
     * create default repeat label
     */  
    public RepeatLabel(String text) {
        super(text);
        updateAll();
        handleHighlighter();   
        whiteLabel();
    }
    /**
     * create default repeat label
     */  
    public RepeatLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        updateAll();
        handleHighlighter();
        whiteLabel();
    }
    /**
     * create default repeat label
     */        
    public RepeatLabel(Icon image) {
        super(image);
        updateAll();
        handleHighlighter();    
        whiteLabel();
    }
            
    /**
     * create default repeat label
     */        
    public RepeatLabel() {
        super();
        updateAll();
        handleHighlighter();
        whiteLabel();
        
    }
    /**
     * Create a Repeat label with given options
     * @param w width
     * @param h height
     * @param url  resource path
     */
    public RepeatLabel(int w, int h, String url) {
        super();
        this.w = w;
        this.h = h;
        this.url = url;
        updateAll();
        handleHighlighter();
        whiteLabel();
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
    private void whiteLabel(){
        setFont(new java.awt.Font("Tahoma", 1, 11));
        setForeground(new java.awt.Color(255, 255, 255));
        setBorder(emptyBorder);
    }
    private void updateAll(){
         try {
            r = new Rectangle(0, 0, w, h);//width and height of img
            buf = ImageIO.read(getClass().getResource(url));//img
            paint = new TexturePaint(buf,r);
        } catch (IOException ex) {
            
        }
    }
    
    private void handleHighlighter(){
        if(isMouseEnterBorder()){
            this.addMouseListener(mouseListener);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        final Graphics2D g2 = (Graphics2D) g;        
        g2.setPaint(paint);
        g2.fill(this.getVisibleRect());
        super.paintComponent(g);
  }               

}
