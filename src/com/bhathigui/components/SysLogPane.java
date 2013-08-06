package com.bhathigui.components;

/*
 * SysLogPane.java
 *
 * A simple system log box 
 *
 * Created on Apr 22, 2012, 4:39:18 PM
 */




import java.awt.Color;
import java.awt.Component;
//import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.StringTokenizer;

import javax.swing.*;
/**
 *
 * @author Bhathiya
 */
public class SysLogPane extends javax.swing.JPanel {

    /** Creates new form SysLogPane */
    public SysLogPane() {
        initComponents();
    }
    static Icon errIco=null; //bad
    static Icon imdIco=null; //goodcont
    static Icon nrmIco=null; //good
    static Icon ibdIco=null; //badcont
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspLog = new javax.swing.JScrollPane();
        lstLog = new javax.swing.JList();

        setBackground(new java.awt.Color(204, 204, 255));

        jspLog.setBackground(new java.awt.Color(204, 204, 255));
        jspLog.setBorder(null);
        jspLog.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N

        lstLog.setBackground(new java.awt.Color(204, 204, 255));
        lstLog.setForeground(new java.awt.Color(110, 110, 110));
        lstLog.setModel(new DefaultListModel());
        lstLog.setPreferredSize(new java.awt.Dimension(0, 250));
        lstLog.setSelectionBackground(new java.awt.Color(204, 255, 153));
        lstLog.setSelectionForeground(new java.awt.Color(255, 153, 153));
        jspLog.setViewportView(lstLog);
        lstLog.setCellRenderer(new IconListRenderer());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLog, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLog, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private Object createListItemObj(String strText,String messageLevel){
        /*
         * [messageLevel]
         *
         * "good"
         * "goodcont"
         *
         * "bad"
         * "badcontd"
         *
         */
        
         

        if(errIco==null) errIco=new ImageIcon(getClass().getResource("/res/bad.png"));
        if(imdIco==null) imdIco=new ImageIcon(getClass().getResource("/res/bad.png"));
        if(nrmIco==null) nrmIco=new ImageIcon(getClass().getResource("/res/bad.png"));
        if(ibdIco==null) ibdIco=new ImageIcon(getClass().getResource("/res/bad.png"));


        
        Object itm = (Object)(new Object[]{messageLevel,strText});
        return itm;
    }
    private String getTodayStr(){
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("MMM/dd hh:mm:ss");
        return df.format(d);
    }
    private void addToSystemLogP(String msg,boolean isError){

        DefaultListModel model = (DefaultListModel)lstLog.getModel();

        if(msg==null||msg.isEmpty()){
            return; // invalid
        }
        //always break em
        StringTokenizer st = new StringTokenizer(msg,"\n");
        String[] msgForLog = new String[st.countTokens()];
        int i = 0;
        while(st.hasMoreTokens()){
            msgForLog[i++] = st.nextToken();
        }
        


        for(int j=i-1;j>=0;j--){
            if(j==0){
                if(isError){
                    model.add(0,createListItemObj(getTodayStr() + " -> " + msgForLog[j],"bad"));
                }else{
                    model.add(0,createListItemObj(getTodayStr() + " -> " + msgForLog[j],"good"));
                }
            }else{
                if(isError){
                    model.add(0,createListItemObj(msgForLog[j],"badcont"));
                }else{
                    model.add(0,createListItemObj(msgForLog[j],"goodcont"));
                }
            }
        }        
//        for(int j=i-1;j>=0;j--){
//            if(isError)
//                model.add(0,createListItemObj(msgForLog[j],"badcont"));
//            else
//                model.add(0,createListItemObj(msgForLog[j],"goodcont"));
//        }
//        if(isError){
//            model.add(0,createListItemObj(getTodayStr(),"bad"));
//        }else{
//            model.add(0,createListItemObj(getTodayStr(),"good"));
//        }
        ((DefaultListModel) lstLog.getModel()).setSize(Math.min(((DefaultListModel) lstLog.getModel()).getSize(), 50));
        lstLog.setSelectedIndex(0);

    }
    public void addToSystemLog(String msg){
        //non error msg
        addToSystemLogP(msg,false);
    }
    public void addToSystemLog(Exception exc){
        //error
        addToSystemLogP(exc.getMessage(),true);
    }

    class IconListRenderer extends JLabel  implements ListCellRenderer {

            public IconListRenderer() {
                setOpaque(true);
            }

            public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected, boolean cellHasFocus) {


                //list.seasdasdasdasdasd
               
                Object[] values = (Object[])list.getModel().getElementAt(index);
                String icoState = (String)(values[0]);
                
                setIcon(null);
                if(icoState.equals("bad")){ //Error
                    setIcon(errIco);
                    setBackground(list.getSelectionForeground()); //RED
                    setForeground(Color.BLACK);
                    setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
                } else if (icoState.equals("good")){ //Ok
                    setIcon(nrmIco);
                    setBackground(list.getSelectionBackground()); //GREEN
                    setForeground(Color.BLACK);
                    setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
                } else if (icoState.equals("goodcont")){ //Ok
                    setIcon(imdIco);
                    setBackground(list.getSelectionBackground()); //GREEN
                    setForeground(Color.BLACK);
                    setBorder(BorderFactory.createLineBorder(list.getSelectionBackground(), 1));
                } else{
                    setIcon(ibdIco); //Errorcont
                    setBackground(list.getSelectionForeground()); //RED
                    setForeground(Color.BLACK);
                    setBorder(BorderFactory.createLineBorder(list.getSelectionForeground(), 1));
                }
                //-------------
//                if (isSelected) {
//                    setForeground(new Color(200,200,200));
//                }
                //-------------
                setText((String)(values[1]));
                setFont(list.getFont());
                return this;
            }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jspLog;
    private javax.swing.JList lstLog;
    // End of variables declaration//GEN-END:variables

}
