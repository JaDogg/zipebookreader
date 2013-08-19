package com.zipebook.util;


import com.bhathigui.components.EasyTree.SimpleTreeNode;
import com.bhathigui.components.EasyTree.TreeDataObject;
import com.bhathigui.components.util.IconProvider;
import com.zipebook.gui.ZipEbook;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.zipebook.util.StandardUtils.*;
/**
 * 
 * a class for handling zip files 
 * and related tree methods
 * 
 * @author Bhathiya 
 */
public class ZipUtil {

    private static ZipFile zipFile=null;
    private static ArrayList<ZipEntry> fileList=null;
    /**
     * self loading constructor
     */
    public ZipUtil() {
        fileList = new ArrayList<ZipEntry>();
        //loadSelf();

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
    private static int addtoTree(SimpleTreeNode root,int pos){
        String name;
        SimpleTreeNode node;
        ZipEntry entry;
        PeekToEndStack<Pair<String,SimpleTreeNode>> stack= new PeekToEndStack<Pair<String,SimpleTreeNode>>();
        SimpleTreeNode currentParent;
        Pair<String,SimpleTreeNode> pair;
        stack.add(new Pair<String, SimpleTreeNode>("", root));
        stack.add(new Pair<String, SimpleTreeNode>("", root));
        stack.add(new Pair<String, SimpleTreeNode>("", root));
        for(int p=pos;p<fileList.size();p++){

            entry = fileList.get(p);
            name = entry.getName();
            
            stack.resetPeekToEnd();
            if(entry.isDirectory()){ 
                node = new SimpleTreeNode(new TreeDataObject(getName(entry), IconProvider.folderIcon,entry.getName())); 
                pair=stack.peekToEnd();
                while(!isParent(name,pair.getFirst())){
                    pair=stack.peekToEnd();
                }
                stack.push(pair);
                currentParent = pair.getSecond();
                currentParent.add(node);
                stack.push(new Pair<String, SimpleTreeNode>(name, node));
            }else{
                node = new SimpleTreeNode(new TreeDataObject(getName(entry), IconProvider.provide(name),entry.getName()));  

                pair=stack.peekToEnd();
                while(!isParent(name,pair.getFirst())){
                    pair=stack.peekToEnd();
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
            {  ZipEntry z = e.nextElement();
                fileList.add(z);
                System.out.println(z.getName());
            }
            addtoTree(root, 0);
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
        if (parent.isEmpty()) return true;
        return parent.equals(getParent(child));
    
    }
    private static String getParent(String child){
        String toTest=child;
        if(child.endsWith("/")){
            toTest = child.substring(0,child.length()-1);
        }
       
        return toTest.substring(0, toTest.lastIndexOf("/")-1);
        }    
    
}
