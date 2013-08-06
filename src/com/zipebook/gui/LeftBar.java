/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zipebook.gui;

import com.bhathigui.components.EasyTree;


/**
 *
 * @author A
 */
public class LeftBar extends javax.swing.JPanel {
    
    /**
     * Creates new form LeftBar
     */
    public LeftBar() {
        initComponents();
    }

    public EasyTree getEasyTree() {
        return easyTree;
    }

    public void setEasyTree(EasyTree easyTree) {
        this.easyTree = easyTree;
    }

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspTree = new javax.swing.JScrollPane();
        easyTree = new EasyTree(IconProvider.archiveIcon, "root");

        jspTree.setBorder(javax.swing.BorderFactory.createTitledBorder("ZipViewer"));
        jspTree.setViewportView(easyTree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTree, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTree, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bhathigui.components.EasyTree easyTree;
    private javax.swing.JScrollPane jspTree;
    // End of variables declaration//GEN-END:variables
}
