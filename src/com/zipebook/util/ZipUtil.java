package com.zipebook.util;


import com.bhathigui.components.EasyTree.SimpleTreeNode;
import com.bhathigui.components.EasyTree.TreeDataObject;
import com.zipebook.gui.ZipEbook;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.ImageIcon;


public class ZipUtil {
    public static final ImageIcon FOLDER    = new ImageIcon(ZipEbook.class.getResource("/res/Folder.png"));
    public static final ImageIcon VIEWABLE_FILE  = new ImageIcon(ZipEbook.class.getResource("/res/Document-New.png"));
    public static final ImageIcon ANY_FILE   = new ImageIcon(ZipEbook.class.getResource("/res/Document-02.png"));
    private static ZipFile zipFile=null;
    private static ArrayList<ZipEntry> fileList=null;
    /**
     * self loading constructor
     */
    public ZipUtil() {
        fileList = new ArrayList<ZipEntry>();
        loadSelf();

    }
    
    /**
     * get input stream of current file
     * @param path path inside zip
     * @return InputStream
     */
    public InputStream getInputStream(String path){
        try {
            ZipEntry entry = zipFile.getEntry(path.substring(1));
            if(entry!=null){
                return zipFile.getInputStream(entry);
            }
            return new ByteArrayInputStream("Not Found".getBytes());
        } catch (Exception ex) {
            ZipEbook.getSysLogPane().addToSystemLog(ex);
        }

        return null;
    }
    
    /**
     * update current loaded zip file with a new zip file
     * @param path path of the new zip file
     */
    public static void updateZip(String path){
        
        try {
            if(zipFile!=null){
                zipFile.close();
            }
            zipFile = new ZipFile(path);
            if(fileList!=null){
                fileList.clear();
            }else{
                fileList = new ArrayList<ZipEntry>();
            }
        } catch (Exception ex) {
            ZipEbook.getSysLogPane().addToSystemLog(ex);
        }
    }
    
    /**
     * load Self as zip
     */
    public static void loadSelf(){
        
        try {
            URL url = ZipUtil.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
            updateZip(jarPath);
        } catch (Exception ex) {
            ZipEbook.getSysLogPane().addToSystemLog(ex);
        }
    }    
    private static int addtoTree(SimpleTreeNode root,int pos,boolean breakChild){
        String name;
        SimpleTreeNode node;
        ZipEntry entry;
        Stack<Pair<String,SimpleTreeNode>> stack= new Stack<Pair<String,SimpleTreeNode>>();
        SimpleTreeNode currentParent;
        Pair<String,SimpleTreeNode> pair;
        stack.add(new Pair<String, SimpleTreeNode>("", root));
        for(int p=pos;p<fileList.size();p++){
            entry = fileList.get(p);
            name = entry.getName();
            if(name.toLowerCase().endsWith("/")){
                node = new SimpleTreeNode(new TreeDataObject(getName(entry), FOLDER,entry.getName())); 
                pair=stack.pop();
                while(!isParent(name,pair.getFirst())){
                    pair=stack.pop();
                }
                stack.push(pair);
                currentParent = pair.getSecond();
                currentParent.add(node);
                stack.push(new Pair<String, SimpleTreeNode>(name, node));
            }else{
                if(name.toLowerCase().endsWith(".htm")
                       || name.toLowerCase().endsWith(".html") || name.toLowerCase().endsWith(".xhtml")
                       || name.toLowerCase().endsWith(".xml") || name.toLowerCase().endsWith(".txt") 
                        ){
                    node = new SimpleTreeNode(new TreeDataObject(getName(entry), VIEWABLE_FILE,entry.getName()));  
                }else{
                    node = new SimpleTreeNode(new TreeDataObject(getName(entry), ANY_FILE,entry.getName()));  
                }
                pair=stack.pop();
                while(!isParent(name,pair.getFirst())){
                    pair=stack.pop();
                }
                stack.push(pair);
                currentParent = pair.getSecond();
                currentParent.add(node);
            }
        }
        return 0;
    }
    public static SimpleTreeNode createNode(SimpleTreeNode root)  
    {  
        try{
            ZipFile file = zipFile;
            for (Enumeration<? extends ZipEntry> e = file.entries(); e.hasMoreElements(); )  
            {  
                fileList.add(e.nextElement());
            }
            addtoTree(root, 0,false);
        }catch(Exception e){
            ZipEbook.getSysLogPane().addToSystemLog(e);
        }
        
        return root;  
    }  
      

    private static String getName(ZipEntry entry){
        String entname=entry.getName();
        int pos;
        if(entry.isDirectory()){
            entname = entname.substring(0, entname.length()-1);
            pos = entname.lastIndexOf("/");
            if(pos>0){
                entname = entname.substring(pos+1); 
            }
        }else{
            pos = entname.lastIndexOf("/");
            if(pos>0){
                entname = entname.substring(pos+1); 
            }
        }
        return entname;
    }
    
    private static boolean isParent(String child,String parent){
        String toTest=child;
        if(child.endsWith("/")){
            toTest = child.substring(0,child.length()-1);
        }
        String remainder=toTest.replace(parent, "");
        
        return (toTest.startsWith(parent) && remainder.indexOf("/")==-1);
    
    }
    
    private static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            super();
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            int hashFirst = first != null ? first.hashCode() : 0;
            int hashSecond = second != null ? second.hashCode() : 0;

            return (hashFirst + hashSecond) * hashSecond + hashFirst;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Pair) {
                    Pair otherPair = (Pair) other;
                    return 
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                              this.first.equals(otherPair.first))) &&
                     (	this.second == otherPair.second ||
                            ( this.second != null && otherPair.second != null &&
                              this.second.equals(otherPair.second))) );
            }

            return false;
        }

        @Override
        public String toString()
        { 
               return "(" + first + ", " + second + ")"; 
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }
        
    }
}
