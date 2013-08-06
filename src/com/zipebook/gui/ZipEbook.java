package com.zipebook.gui;



import chrriis.common.UIUtils;
import chrriis.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import com.bhathigui.components.EasyTree;
import com.bhathigui.components.EasyTree.SimpleTreeNode;
import com.bhathigui.components.EasyTree.TreeDataObject;
import com.bhathigui.components.RSyntaxEmbedComponent;
import com.bhathigui.components.SysLogPane;
import java.awt.BorderLayout;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.zipebook.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ZipEbook extends JFrame {

    public  static ZipEbook frame;
    public  static ZipUtil zipfile;
    private static JWebBrowser webBrowser;
    private static SysLogPane sysLogPane;
    private static LeftBar left;
    private static JPanel book;
    private static javax.swing.JMenuBar jMenuBar;
    private static javax.swing.JMenuItem mitAbout;
    private static javax.swing.JMenuItem mitClose;
    //private static javax.swing.JMenuItem mitLoadMySelf;
    private static javax.swing.JMenuItem mitOpen;
    private static javax.swing.JMenu mnuFile;
    private static javax.swing.JMenu mnuHelp;
    private static JTabbedPane tabbedPane;
    private static RSyntaxEmbedComponent syntaxEmbedComponent;
    
    public ZipEbook() {
      initComponents();
    }

    public static SysLogPane getSysLogPane() {
        return sysLogPane;
    }
                         
    private void initComponents() {
        
        //################################################################
        //web browser component
        
        book = new JPanel(new BorderLayout());
        sysLogPane = new SysLogPane();
        zipfile = new ZipUtil();
        book.setBorder(BorderFactory.createEmptyBorder());
        tabbedPane = new JTabbedPane();   
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); 
        tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        webBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());
        webBrowser.setMenuBarVisible(false);
        syntaxEmbedComponent = new RSyntaxEmbedComponent();
        //################################################################
        //custom webserver comtent receiver through zip util
        WebServer.getDefaultWebServer().addContentProvider(new WebServer.WebServerContentProvider() {

            public WebServer.WebServerContent getWebServerContent(final WebServer.HTTPRequest httpr) {
                System.out.println(httpr.getURLPath());
                WebServer.WebServerContent content;
                content = new WebServer.WebServerContent() {
                  @Override
                  public InputStream getInputStream() {
                      return  zipfile.getInputStream(httpr.getURLPath());
                  }
                };
               return content;
            }
        });

        //################################################################
        //title change and first load inner html file
        webBrowser.navigate(WebServer.getDefaultWebServer().getURLPrefix()+"/docs/index.html");
        addWebBrowserListener(tabbedPane, webBrowser);             
        //tabbedPane.addTab("Log", sysLogPane);   
        createTabBrowserWithClose(0, webBrowser, "Startup page",true,Color.RED);
        createTabPanel(1,syntaxEmbedComponent,"Syntax Viewer",Color.BLUE);
        tabbedPane.setSelectedIndex(0);
        book.add(tabbedPane, BorderLayout.CENTER);

        
        //################################################################
        // add leftBar
        left = new LeftBar();
        
                
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = left.getEasyTree().getRowForLocation(e.getX(), e.getY());
                TreePath selPath = left.getEasyTree().getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {
                        //mySingleClick(selRow, selPath);
                    }
                    else if(e.getClickCount() == 2) {
                        TreeDataObject t = (TreeDataObject)((SimpleTreeNode)selPath.getLastPathComponent()).getUserObject();
                        if(tabbedPane.getSelectedIndex() == 1){
                            syntaxEmbedComponent.setText(zipfile.getInputStream("/" + t.getObject().toString()));
                        }else{
//                                                        ((JWebBrowser)
//                                    tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).navigate(
//                                    WebServer.getDefaultWebServer().getURLPrefix()+"/" + t.getObject());
                            webBrowser.navigate(WebServer.getDefaultWebServer().getURLPrefix()+"/" + t.getObject());
                        }
                    }
                }
            }
        };
        
        left.getEasyTree().addMouseListener(ml);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


        JSplitPane jSplitPane = new JSplitPane();
        jSplitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        jSplitPane.setLeftComponent(left);
        //add(left, java.awt.BorderLayout.LINE_START);
        
        //################################################################
        
        JSplitPane jSplitPaneright = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneright.setBorder(new EmptyBorder(0, 0, 0, 0));
        jSplitPaneright.setResizeWeight(0.85);
        jSplitPaneright.setTopComponent(book);
        
        jSplitPaneright.setRightComponent(sysLogPane);
        
        jSplitPane.setRightComponent(jSplitPaneright);
        
        add(jSplitPane, java.awt.BorderLayout.CENTER);
        //################################################################
        //menu
        jMenuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mitOpen = new javax.swing.JMenuItem();
        //mitLoadMySelf = new javax.swing.JMenuItem();
        mitClose = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mitAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mnuFile.setText("File");

        mitOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mitOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/Open.png"))); // NOI18N
        mitOpen.setText("Open Zip File");
        mitOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitOpenActionPerformed(evt);
            }
        });
        mnuFile.add(mitOpen);

        mnuFile.add(new javax.swing.JPopupMenu.Separator());

        mitClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mitClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/Cancel.png"))); // NOI18N
        mitClose.setText("Close");
        mnuFile.add(mitClose);

        jMenuBar.add(mnuFile);

        mnuHelp.setText("Help");

        mitAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/Dialog-Box-About.png"))); // NOI18N
        mitAbout.setText("About");
        mnuHelp.add(mitAbout);

        jMenuBar.add(mnuHelp);

        setJMenuBar(jMenuBar);
        //##########################################################################
    }  
    
    //#################################################################
    //tab stuff
    private static void addWebBrowserListener(final JTabbedPane tabbedPane, final JWebBrowser webBrowser) {   
      webBrowser.addWebBrowserListener(new WebBrowserAdapter() {   
        @Override  
        public void titleChanged(WebBrowserEvent e) {   
          for(int i=0; i<tabbedPane.getTabCount(); i++) {   
            if(tabbedPane.getComponentAt(i) == webBrowser) {   
              if(i == 0) {   
                return;   
              }   
              tabbedPane.setTitleAt(i, webBrowser.getPageTitle());   
              ((TabPanel)tabbedPane.getTabComponentAt(i)).setLblTitle(webBrowser.getPageTitle());
              break;   
            }   
          }   
        }   
        @Override  
        public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {   
          JWebBrowser newWebBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());   
          addWebBrowserListener(tabbedPane, newWebBrowser); 
          createTabBrowserWithClose(tabbedPane.getTabCount(), newWebBrowser, "New Tab",false,Color.BLACK);
          //tabbedPane.addTab("New Tab", newWebBrowser);   
          e.setNewWebBrowser(newWebBrowser); 
          
        }   
        @Override  
        public void windowOpening(WebBrowserWindowOpeningEvent e) {   
          e.getWebBrowser().setMenuBarVisible(false);   
        }   
        
      });   
    }  
    //#################################################################
    
    
    //#################################################################
    //tab stuff
    private static void createTabBrowserWithClose(int index,JWebBrowser brows,String title,boolean hideClose,Color color){
        tabbedPane.addTab(title, brows);
        TabPanel pnlTab= new TabPanel(title,hideClose,color);
        tabbedPane.setTabComponentAt(index, pnlTab);
       
        pnlTab.getBtnClose().addActionListener(new TabCloseActionHandler(brows, index));
        
    }
    private static void createTabPanel(int index,JPanel item,String title,Color color){
        tabbedPane.addTab(title, item);
        TabPanel pnlTab= new TabPanel(title,true,color);
        tabbedPane.setTabComponentAt(index, pnlTab);

        
    }
    //#################################################################
    
    //#################################################################
    //open command
    private void mitOpenActionPerformed(java.awt.event.ActionEvent evt) {                                        
        JFileChooser openFile = new JFileChooser();
        openFile.showOpenDialog(this);
        if(openFile.getSelectedFile()!=null){
            ZipUtil.updateZip(openFile.getSelectedFile().getAbsolutePath());
            left.getEasyTree().getRoot().removeAllChildren();
            left.getEasyTree().getRoot().setUserObject(new EasyTree.TreeDataObject(openFile.getName(openFile.getSelectedFile()), IconProvider.archiveIcon));
            ZipUtil.createNode(left.getEasyTree().getRoot());
            ((DefaultTreeModel)left.getEasyTree().getModel()).reload();
        }
    }  
    //#################################################################
    
    //#################################################################
    //main
    public static void main(String args[]) {
        NativeInterface.open();   
        UIUtils.setPreferredLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new ZipEbook();
                
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        if (JOptionPane.showConfirmDialog(frame, 
                            "Are you sure to close this window?", "Really Closing?", 
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                            webBrowser.disposeNativePeer(false);
                            WebServer.stopDefaultWebServer();
                            System.exit(0);
                        }
                    }
                });
                frame.setTitle("ZipEbook Reader");
                frame.setSize(800, 600);
                
                frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                frame.setVisible(true);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        NativeInterface.runEventPump();
    }
    //#################################################################
    
    
    //#################################################################
    //tab stuff
    private static class TabCloseActionHandler implements ActionListener {
        private JWebBrowser brows;
        private int index;

        public TabCloseActionHandler(JWebBrowser brows, int index) {
            this.brows = brows;
            this.index = index;
        }
        public void actionPerformed(ActionEvent evt) {
                tabbedPane.removeTabAt(index);
                brows.disposeNativePeer(false);
        }
    }     
    
    //tabpanel to settabcomponent
    private static class TabPanel extends JPanel{
        private static ImageIcon closerImage = new ImageIcon(ZipEbook.class.getResource("/res/closer.gif"));
        private static ImageIcon closerRolloverImage = new ImageIcon(ZipEbook.class.getResource("/res/closer_rollover.gif"));
        private static ImageIcon closerPressedImage = new ImageIcon(ZipEbook.class.getResource("/res/closer_pressed.gif"));
        private JLabel lblTitle;
        private JButton closeButton;
        
        public TabPanel(String title,boolean hideClose,Color color) {
            super(new BorderLayout());
            initComponents(title,hideClose,color);
        }
        private void initComponents(String title,boolean hideClose,Color color){
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
            lblTitle = new JLabel(title);
            lblTitle.setForeground(color);
            closeButton = new JButton(closerImage);
            closeButton.setRolloverIcon(closerRolloverImage);
            closeButton.setPressedIcon(closerPressedImage);
            closeButton.setBorderPainted(false);
            closeButton.setBorder(BorderFactory.createEmptyBorder());
            closeButton.setFocusPainted(false);
            closeButton.setRolloverEnabled(true);
            closeButton.setOpaque(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setPreferredSize(new Dimension(closerImage.getIconWidth(), closerImage.getIconHeight()));
            closeButton.setSize(new Dimension(closerImage.getIconWidth(), closerImage.getIconHeight()));
            lblTitle.setOpaque(false);

            add(lblTitle, BorderLayout.CENTER);
            add(closeButton, BorderLayout.EAST);
            closeButton.setVisible(!hideClose);
        }


        public void setLblTitle(String lblTitle) {
            this.lblTitle.setText(lblTitle);
        }

        public JButton getBtnClose() {
            return closeButton;
        }       
        
    }
    //#################################################################
}
