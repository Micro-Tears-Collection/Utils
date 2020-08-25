package code.utils;

import java.io.InputStream;
import java.util.Vector;

public class StringTools {

    public static String getStringFromResource(String file) {
        InputStream is = null;

        try {
            is = (new Object()).getClass().getResourceAsStream(file);
            StringBuffer str = new StringBuffer();

            int ch;
            while((ch = is.read()) != -1) {
                ch = ch >= 192 && ch <= 255?ch + 848:ch; //windows 1251 to utf8
                if(ch != '\r') str.append((char) ch);
            }

            is.close();
            return str.toString();
        } catch (Exception exc) {
            System.out.println("Reading txt error: " + exc);
            if(is != null) {
                try {
                    is.close();
                } catch (Exception ex) {}
            }

            return null;
        }
    }
    
    private static Vector fragments(String text, char divider) {
        Vector fragments = new Vector();
      
        int start = 0;
        while(start<text.length()) {
            int end = text.indexOf(divider, start);
            if(end == -1) end = text.length();
            
            if(start<end) fragments.addElement(text.substring(start,end));
            
            start = end+1;
        }

        return fragments;
    }

    public static String[] cutOnStrings(String str, char d) {
        if(str == null) return null;
        
        Vector fragments = fragments(str, d);
        String[] out = new String[fragments.size()];
        fragments.copyInto(out);
        return out;
    }
    
    public static int[] cutOnInts(String str, char d) {
        if(str == null) return null;
        
        Vector fragments = fragments(str, d);
        int[] out = new int[fragments.size()];
        
        for(int i=0;i<out.length;i++) {
            out[i] = parseInt((String)fragments.elementAt(i));
        }
        
        return out;
    }
    //#if DefaultConfiguration
    public static float[] cutOnFloats(String str, char d) {
        if(str == null) return null;
        
        Vector fragments = fragments(str, d);
        float[] out = new float[fragments.size()];
        
        for(int i=0;i<out.length;i++) {
            out[i] = parseFloat((String)fragments.elementAt(i));
        }
        
        return out;
    }
    
    public static float parseFloat(String val) {
        return Float.parseFloat(val.trim());
    }
    //#endif
    public static int parseInt(String val) {
        return Integer.parseInt(val.trim());
    }
    
    public static int cleverParseInt(String val) {
        val = val.trim();
        int ind = val.indexOf('.');
        if(ind>-1) val = val.substring(0,ind);
        return Integer.parseInt(val);
    }
    
    public static byte parseByte(String val) {
        return Byte.parseByte(val.trim());
    }
    
    public static long parseLong(String val) {
        return Long.parseLong(val.trim());
    }
    
    public static boolean isNumeric(String s) {
        if(s == null) return false;
        s = s.trim();
        for(int i=0;i<s.length();i++) {
            char ch = s.charAt(i);
            if(ch!='-' && !Character.isDigit(ch)) return false;
        }
        return true;
    }
    
    public static String deleteNonNumeric(String s) {
        while (s.length() > 0 && s.charAt(0) != '-' && !Character.isDigit(s.charAt(0))) {
            s = s.substring(1, s.length());
        }
        while (s.length() > 0 && s.charAt(s.length() - 1) != '-' && !Character.isDigit(s.charAt(s.length()-1))) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
    
    public static int getRGB(String rgbs, char div) {
        int[] rgb = cutOnInts(rgbs,div);
        return (rgb[0]<<16) | (rgb[1]<<8) | rgb[2];
    }
    
    public static int parseXMLColor(String argb) {
        int col = 0;
        argb = argb.toUpperCase();
        
        for(int i=1;i<argb.length();i++) {
            char ch = argb.charAt(i);
            int val;
            
            if(ch>='0' && ch<='9') val = ch-'0';
            else val = ch-'A'+10;
            
            col |= val<<((argb.length()-i-1)*4);
        }
        return col;
    }

}
