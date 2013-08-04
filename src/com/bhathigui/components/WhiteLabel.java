/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhathigui.components;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author A
 */
public class WhiteLabel extends JLabel {

    public WhiteLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        update();
    }

    public WhiteLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        update();
    }

    public WhiteLabel(String text) {
        super(text);
        update();
    }

    public WhiteLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        update();
    }

    public WhiteLabel(Icon image) {
        super(image);
        update();
    }

    public WhiteLabel() {
        super();
        update();
    }
    private void update(){
        setFont(new java.awt.Font("Tahoma", 1, 11));
        setForeground(new java.awt.Color(255, 255, 255));
    }
}
