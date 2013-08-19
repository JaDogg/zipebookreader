package com.zipebook.util;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Stack;




/**
 * A set of important utilities and classes in a single class
 * @author Bhathiya
 */
public class StandardUtils {
    
    /**
     * screen width 
     * @return 
     */
    public static int getScreenWidth(){
       GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
       int width = gd.getDisplayMode().getWidth();
       return Math.max(Toolkit.getDefaultToolkit().getScreenSize().width,width);
    }
    /**
     * 
     * @return screen height 
     */
     public static int getScreenHeight(){
       GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
       int height = gd.getDisplayMode().getHeight();
       return Math.max(Toolkit.getDefaultToolkit().getScreenSize().height,height);
    }   
    /**
     * is null or empty
     * @param string
     * @return 
     */
    public static boolean isNullOrEmpty( String string) {
      return string == null || string.length() == 0; // string.isEmpty() in Java 6
    }
    
    /**
     * str is [0-9]+
     * @param str
     * @return 
     */
    public static boolean isPositiveNumber(String str){
         return (str.matches("[0-9]+"));
    }
    /**
     * taken from
     * http://seanshou.blogspot.com.au/2012/09/java-better-performance-string-formatter.html
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s}
     *     placeholders.
     * @param args the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}. Arguments can be null.
     */
    public static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"
        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));
 
        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }
 
        return builder.toString();
    }
 
    
    /**
     * convert inputstream to string
     * got from http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
     * @param is
     * @param bufferSize
     * @return 
     */
    public static String InputStreamToString(final InputStream is, final int bufferSize){
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try {
            final Reader in = new InputStreamReader(is, "UTF-8");
            try {
              for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                  break;
                out.append(buffer, 0, rsz);
              }
            }
            finally {
              in.close();
            }
        }
        catch (UnsupportedEncodingException ex) {
        /* ... */
        }
        catch (IOException ex) {
          /* ... */
        }
        return out.toString();
    }
    
    /**
     * unmodifiable for both parameters getters only pair class
     * taken from someplace ? 
     * @param <A>
     * @param <B> 
     */
    public static class Pair<A, B> {
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
    
    /**
    * taken from and modified
    * 
    * http://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream
    * 
    * ByteBufferBackedInputStream.read() returns a 
    * sign extended int representation of the byte it 
    * reads, which is wrong (value should be in range [-1..255])
    * ByteBufferBackedInputStream.read(byte[], int, int) 
    * does not return -1 when there are no bytes remaining 
    * in the buffer, as per the API spec
    */
    public class ByteBufferBackedInputStream extends InputStream {

        ByteBuffer buf;

        public ByteBufferBackedInputStream(ByteBuffer buf) {
            this.buf = buf;
        }

        public int read() throws IOException {
            if (!buf.hasRemaining()) {
                return -1;
            }
            return buf.get() & 0xFF;
        }

        @Override
        public int read(byte[] bytes, int off, int len)
                throws IOException {
            if (!buf.hasRemaining()) {
                return -1;
            }

            len = Math.min(len, buf.remaining());
            buf.get(bytes, off, len);
            return len;
        }

        @Override
        public String toString() {
            return "ByteBufferBackedInputStream{" + super.toString() + "}" ;
        }

        @Override
        public int hashCode() {
            return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ByteBufferBackedInputStream other = (ByteBufferBackedInputStream) obj;
            if (this.buf != other.buf && (this.buf == null || !this.buf.equals(other.buf))) {
                return false;
            }
            return true;
        }
  
    }

    
    public static class PeekToEndStack<T> extends Stack<T>{
        int peekPos=0;

        public synchronized void resetPeekToEnd(){
            peekPos=size()-1;
        }
        public synchronized T peekToEnd(){
            return elementAt(peekPos--);
        }
        public synchronized boolean isPeekPosNegative(){
            return peekPos<=0;
        }
        
    }
}
