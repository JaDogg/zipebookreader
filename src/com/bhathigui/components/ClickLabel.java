package com.bhathigui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

/**
 * create a label with single color click-able label
 * 
 * @author Bhathiya Perera
 */
public class ClickLabel extends javax.swing.JLabel {
    
    //------------------------------------------------------------
    private Color normal = Color.DARK_GRAY;
    private Color clicked = Color.LIGHT_GRAY;
    //------------------------------------------------------------
    
   
    //------------------------------------------------------------
    private static final Border emptyBorder  
            = BorderFactory.createEmptyBorder(1,1,1,1);
    
    private static final Border selectBorder 
            = BorderFactory.createLineBorder(Color.white,1);
    
            private MouseListener mouseListener = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(selectBorder);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                     setBorder(emptyBorder);
                }          

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(clicked);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(normal);
                }

          
            
        };

    public Color getNormal() {
        return normal;
    }

    public void setNormal(Color normal) {
        this.normal = normal;
        setBackground(normal);
    }

    public Color getClicked() {
        return clicked;
    }

    public void setClicked(Color clicked) {
        this.clicked = clicked;
    }
            
    
    //------------------------------------------------------------
    /**
     * create default click label
     */  
     public ClickLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        whiteLabel();
    }
    /**
     * create default click label
     */  
    public ClickLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        whiteLabel();
    }
    /**
     * create default click label
     */  
    public ClickLabel(String text) {
        super(text);
        whiteLabel();
        
    }
    /**
     * create default click label
     */  
    public ClickLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        whiteLabel();
    }
    /**
     * create default click label
     */        
    public ClickLabel(Icon image) {
        super(image);
        whiteLabel();
    }
            
    /**
     * create default click label
     */        
    public ClickLabel() {
        super();
        whiteLabel();
        
    }

    private void whiteLabel(){
        setBorder(emptyBorder);
        setFont(new java.awt.Font("Tahoma", 1, 11));
        setForeground(new java.awt.Color(255, 255, 255));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(mouseListener);
        setOpaque(true);
        setBackground(normal);
    }

}
