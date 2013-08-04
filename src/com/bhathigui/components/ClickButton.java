package com.bhathigui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 * create a button with single color click-able button
 * 
 * @author Bhathiya Perera
 */
public class ClickButton extends javax.swing.JButton {
    
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
  
     public ClickButton(Icon icon) {
        super(icon);
        whiteLabel();
    }

    public ClickButton(String text) {
        super(text);
        whiteLabel();
    }

    public ClickButton(Action a) {
        super(a);
        whiteLabel();
    }

    public ClickButton(String text, Icon icon) {
        super(text, icon);
        whiteLabel();
    }
    
            
    /**
     * create default click button
     */        
    public ClickButton() {
        super();
        whiteLabel();
        
    }

    private void whiteLabel(){ 
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setForeground(new java.awt.Color(255, 255, 255));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(mouseListener);
        setOpaque(true);
        setBackground(normal);
    }

}
