
package com.bhathigui.components;

import com.bhathigui.components.util.SimpleScrollUI;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


public class SimpleScrollPane extends JScrollPane{

    private static boolean isLoadedOnce=false;
    
    public SimpleScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        update();       
    }

    public SimpleScrollPane(Component view) {
        super(view);
        update();
    }

    public SimpleScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        update();
    }

    public SimpleScrollPane() {
        super();
        update();
    }
    
    
    private void update(){
        if(!isLoadedOnce){
            //UIManager.put("MetalScrollBarUI", "com.bhathigui.components.util.SimpleScrollUI");
            isLoadedOnce=true;
        }
        this.getHorizontalScrollBar().setUI(new SimpleScrollUI());
        this.getVerticalScrollBar().setUI(new SimpleScrollUI());
        
    }
}
