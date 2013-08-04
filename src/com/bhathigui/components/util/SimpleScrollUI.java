
package com.bhathigui.components.util;

import com.bhathigui.components.ClickDotButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class SimpleScrollUI extends BasicScrollBarUI {

    public SimpleScrollUI() {
        super();
    }
    
    
    @Override
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.LIGHT_GRAY);
        g2.clearRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(Color.DARK_GRAY);
        g2.clearRect(thumbBounds.x+4, thumbBounds.y+4, thumbBounds.width-7, thumbBounds.height-7);
    }

    @Override
    public JButton createIncreaseButton(int orientation) {
        return (JButton)(new ClickDotButton());
    }

    @Override
    public JButton createDecreaseButton(int orientation) {
        return (JButton)(new ClickDotButton());
    }

    
    
    
    
    
}
