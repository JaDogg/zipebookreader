/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zipebook.gui;

import com.bhathigui.components.util.IconProvider;
import com.bhathigui.components.EasyTree;
import com.bhathigui.components.FileTree;


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

    public FileTree getFileTree() {
        return fileTree;
    }

    public void setFileTree(FileTree fileTree) {
        this.fileTree = fileTree;
    }

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelZip = new javax.swing.JPanel();
        jspTree = new javax.swing.JScrollPane();
        easyTree = new EasyTree(IconProvider.archiveIcon, "root");
        jPanelFile = new javax.swing.JPanel();
        fileTree = new com.bhathigui.components.FileTree();

        jspTree.setViewportView(easyTree);

        javax.swing.GroupLayout jPanelZipLayout = new javax.swing.GroupLayout(jPanelZip);
        jPanelZip.setLayout(jPanelZipLayout);
        jPanelZipLayout.setHorizontalGroup(
            jPanelZipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
            .addGroup(jPanelZipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jspTree, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
        );
        jPanelZipLayout.setVerticalGroup(
            jPanelZipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
            .addGroup(jPanelZipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jspTree, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Archieve Browser", jPanelZip);

        javax.swing.GroupLayout jPanelFileLayout = new javax.swing.GroupLayout(jPanelFile);
        jPanelFile.setLayout(jPanelFileLayout);
        jPanelFileLayout.setHorizontalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileTree, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );
        jPanelFileLayout.setVerticalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileTree, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("System Browser", jPanelFile);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bhathigui.components.EasyTree easyTree;
    private com.bhathigui.components.FileTree fileTree;
    private javax.swing.JPanel jPanelFile;
    private javax.swing.JPanel jPanelZip;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JScrollPane jspTree;
    // End of variables declaration//GEN-END:variables
}
