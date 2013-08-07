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

/**
 * create a button with single color click-able button
 * 
 * @author Bhathiya Perera
 */
public class ClickDotButton extends javax.swing.JButton {
    
    //------------------------------------------------------------
    private Color normal = Color.DARK_GRAY;
    private Color clicked = Color.LIGHT_GRAY;
    //------------------------------------------------------------
    
   
    //------------------------------------------------------------
    
    
    private MouseListener mouseListener = new MouseAdapter() {
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
  
     public ClickDotButton(Icon icon) {
        super(icon);
        whiteLabel();
    }

    public ClickDotButton(String text) {
        super(text);
        whiteLabel();
    }

    public ClickDotButton(Action a) {
        super(a);
        whiteLabel();
    }

    public ClickDotButton(String text, Icon icon) {
        super(text, icon);
        whiteLabel();
    }
    
            
    /**
     * create default click button
     */        
    public ClickDotButton() {
        super();
        whiteLabel();
        
    }

    private void whiteLabel(){ 
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setIcon(new ImageIcon(getClass().getResource("/com/bhathigui/res/Shape18.png")));
        setForeground(new java.awt.Color(255, 255, 255));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(mouseListener);
        setOpaque(true);
        setBackground(normal);
    }

}
