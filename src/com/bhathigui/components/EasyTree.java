package com.bhathigui.components;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public final class EasyTree extends JTree {

    //private JTree jTree;
    //private SimpleScrollPane simpleScrollPane;
    private SimpleTreeNode root;
    private TreeModel model;
    
    public static final ImageIcon ROUND_RED    = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/round-red.png"));
    public static final ImageIcon ROUND_GREEN  = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/round-green.png"));
    public static final ImageIcon ROUND_BLUE   = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/round-blue.png"));
    public static final ImageIcon ROUND_ORANGE = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/round-orange.png"));
    
    public static final ImageIcon SQUARE_RED    = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/square-red.png"));
    public static final ImageIcon SQUARE_GREEN  = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/square-green.png"));
    public static final ImageIcon SQUARE_BLUE   = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/square-blue.png"));
    public static final ImageIcon SQUARE_ORANGE = new ImageIcon(EasyTree.class.getResource("/com/bhathigui/res/square-orange.png"));
    
    /**
     * Creates new form EasyTree
     */
    public EasyTree() {
        //initComponents();
        
        super();

        root = new SimpleTreeNode(new TreeDataObject("[Root]", ROUND_RED));

        model = new DefaultTreeModel(root);
        setModel(model);
        setCellRenderer(new IconTreeRenderer());
        setUI(new BasicTreeUI(){
            @Override
            public void setExpandedIcon(Icon newG) {
                super.setExpandedIcon(
                        new ImageIcon(
                        getClass().getResource("/com/bhathigui/res/collap.png"))); 
            }

            @Override
            public void setCollapsedIcon(Icon newG) {
                super.setCollapsedIcon(
                        new ImageIcon(
                        getClass().getResource("/com/bhathigui/res/expanded.png"))); 

            }
            
        });
      
    }
    /**
     * Creates new form EasyTree
     */
    public EasyTree(ImageIcon rootIcon,String rootText) {
        //initComponents();
        
        super();

        root = new SimpleTreeNode(new TreeDataObject(rootText, rootIcon));

        model = new DefaultTreeModel(root);
        setModel(model);
        setCellRenderer(new IconTreeRenderer());
        setUI(new BasicTreeUI(){
            @Override
            public void setExpandedIcon(Icon newG) {
                super.setExpandedIcon(
                        new ImageIcon(
                        getClass().getResource("/com/bhathigui/res/collap.png"))); 
            }

            @Override
            public void setCollapsedIcon(Icon newG) {
                super.setCollapsedIcon(
                        new ImageIcon(
                        getClass().getResource("/com/bhathigui/res/expanded.png"))); 

            }
            
        });
      
    }
    public SimpleTreeNode getRoot() {
        return root;
    }
    
    public void hideDefaultRoot(){
        setShowsRootHandles(true);
        setRootVisible(false);
        expandPath(new TreePath(root.getPath()));
    }
    /**
     * add a node to given parent
     * @param parent
     * @param text
     * @param icon
     * @return added node
     */
    public SimpleTreeNode add(SimpleTreeNode parent,String text, ImageIcon icon){
        SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon));
        parent.add(node);
        return node; 
    }
    /**
     * add a node to root
     * @param text
     * @param icon
     * @return added node
     */
    public SimpleTreeNode add(String text, ImageIcon icon){
        SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon));
        root.add(node);        
        return node; 
    }  
    /**
     * add a node to given parent
     * @param parent
     * @param text
     * @param icon
     * @return added node
     */
    
    public SimpleTreeNode add(SimpleTreeNode parent,String text, ImageIcon icon,Object object){
        SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon,object));
        parent.add(node);
        return node; 
    }
    /**
     * add a node to root
     * @param text
     * @param icon
     * @return added node
     */
    public SimpleTreeNode add(String text, ImageIcon icon,Object object){
        SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon,object));
        root.add(node);        
        return node; 
    }  
    public static class TreeDataObject{
        
        private final String text;
        private final ImageIcon icon;
        private final Object object;

        public TreeDataObject(String text, ImageIcon icon) {
            this.text = text;
            this.icon = icon;
            this.object = "NULL";
        }

        public TreeDataObject(String text, ImageIcon icon, Object object) {
            this.text = text;
            this.icon = icon;
            this.object = object;
        }

        @Override
        public String toString() {
            return text;
        }

        public String getText() {
            return text;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public Object getObject() {
            return object;
        }
        
        
    }
    private static class IconTreeRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);

            setIcon((Icon)((TreeDataObject)((SimpleTreeNode)value).getUserObject()).getIcon());
            
            return this;
        }          
    }
    public static class SimpleTreeNode extends DefaultMutableTreeNode{

        public SimpleTreeNode() {
            super();
        }

        public SimpleTreeNode(Object userObject) {
            super(userObject);
        }

        public SimpleTreeNode(Object userObject, boolean allowsChildren) {
            super(userObject, allowsChildren);
        }
        /**
         * add a node to this node
         * @param text
         * @param icon
         * @return added node
         */
        public SimpleTreeNode child(String text, ImageIcon icon){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon));
            add(node);        
            return node; 
        }
       
        /**
         * add a node to parent node of current node
         * @param text
         * @param icon
         * @return added node
         */
        public SimpleTreeNode sibling(String text, ImageIcon icon){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon));
            ((SimpleTreeNode) getParent()).add(node);        
            return node; 
        }              
        
        /**
         * add a node to parents parent node of current node
         * @param text
         * @param icon
         * @return added node
         */
        public SimpleTreeNode uncle(String text, ImageIcon icon){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon));
            ((SimpleTreeNode)(((SimpleTreeNode) getParent()).getParent())).add(node);        
            return node; 
        }  
        
        public SimpleTreeNode child(String text, ImageIcon icon,Object object){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon, object));
            add(node);        
            return node; 
        }
       
        public SimpleTreeNode sibling(String text, ImageIcon icon,Object object){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon,object));
            ((SimpleTreeNode) getParent()).add(node);        
            return node; 
        }              

        public SimpleTreeNode uncle(String text, ImageIcon icon,Object object){
            SimpleTreeNode node = new SimpleTreeNode(new TreeDataObject(text, icon,object));
            ((SimpleTreeNode)(((SimpleTreeNode) getParent()).getParent())).add(node);        
            return node; 
        }          
        
        public SimpleTreeNode child(TreeDataObject object){
            SimpleTreeNode node = new SimpleTreeNode(object);
            add(node);        
            return node; 
        }
        
        public SimpleTreeNode childsib(TreeDataObject object){
            SimpleTreeNode node = new SimpleTreeNode(object);
            add(node);        
            return this; 
        }     
        public SimpleTreeNode sibling(TreeDataObject object){
            SimpleTreeNode node = new SimpleTreeNode(object);
            ((SimpleTreeNode) getParent()).add(node);        
            return node; 
        }              
        
        public SimpleTreeNode uncle(TreeDataObject object){
            SimpleTreeNode node = new SimpleTreeNode(object);
            ((SimpleTreeNode)(((SimpleTreeNode) getParent()).getParent())).add(node);        
            return node; 
        }          
        
        
        /**
         * 
         * @return parent node of current node
         */
        public SimpleTreeNode pop(){    
            return ((SimpleTreeNode) getParent()); 
        }
        
        public SimpleTreeNode popX2(){    
            return pop().pop();
        }
        
        public SimpleTreeNode popX3(){    
            return pop().pop().pop();
        }
        
        public SimpleTreeNode popX4(){    
            return pop().pop().pop().pop();
        }
        
        
        
    }
}
