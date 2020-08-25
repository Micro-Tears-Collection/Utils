package code.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class IniFile {

    final Hashtable hashtable;

    public static IniFile createFromResource(String file) {
        return createFromResource(file, false);
    }
    
    public static IniFile createFromResource(String file, boolean keys) {
        file = StringTools.getStringFromResource(file); // записывает все строки из файла в одну строку, потом
        return new IniFile(file, keys);               // по этой строке заполняет hashtable
    }
    
    public static Object[] createGroups(String file) {
        file = StringTools.getStringFromResource(file);
        String[] lines = StringTools.cutOnStrings(file, '\n');
        
        Vector names = new Vector();
        Vector groups = new Vector();
        
        Hashtable tmp = null;
        
        for(int i = 0; i < lines.length; ++i) {
            if(lines[i].length() <= 0) continue;
            
            int charIndex;
            if(lines[i].charAt(0) == '[') {
                names.addElement( lines[i].substring(1, lines[i].length() - 1) );
                tmp = new Hashtable();
                groups.addElement(new IniFile(tmp));
            } else if ((charIndex = lines[i].indexOf('=')) >= 0) {
                String key = lines[i].substring(0, charIndex).trim();
                String val = lines[i].substring(charIndex + 1).trim();
                tmp.put(key, val);
            }
        }
        
        String[] namesM = new String[names.size()];
        IniFile[] groupsM = new IniFile[namesM.length];
        
        for(int i=0; i<namesM.length; i++) {
            namesM[i] = (String)names.elementAt(i);
            groupsM[i] = (IniFile)groups.elementAt(i);
        }
        
        return new Object[]{namesM, groupsM};
    }
   
    public IniFile(Hashtable hash) {
        hashtable = hash;
    }
    
    public IniFile(String str, boolean keys) {
        hashtable = new Hashtable();
        String[] lines = StringTools.cutOnStrings(str, '\n');
        set(lines, keys);
    }
    
    private void set(String[] lines, boolean keys) {
        Hashtable tmp = hashtable;
        for(int i = 0; i < lines.length; ++i) {
            if(lines[i].length() <= 0) continue;
            
            int charIndex;
            if(lines[i].charAt(0) == '[' && keys) {
                String key = lines[i].substring(1, lines[i].length() - 1);
                tmp = new Hashtable();
                hashtable.put(key,tmp);
            } else if ((charIndex = lines[i].indexOf('=')) >= 0) {
                String key = lines[i].substring(0, charIndex).trim();
                String val = lines[i].substring(charIndex + 1).trim();
                tmp.put(key, val);
            }
        }
    }

    //Ключи и группы
    
    public String[] keys() {
        String[] out = new String[hashtable.size()];
        Enumeration keys = hashtable.keys();
        
        for(int i=0;i<out.length;i++) {
            out[i] = (String)keys.nextElement();
        }
        
        return out;
    }
    
    public Hashtable[] hashtables() {
        Hashtable[] out = new Hashtable[hashtable.size()];
        Enumeration hashtables = hashtable.elements();
        
        for(int i=0;i<out.length;i++) {
            out[i] = (Hashtable)hashtables.nextElement();
        }
        
        return out;
    }
    
    public void put(String group, String key, String value) {
        Object val = hashtable.get(key);
        if(val instanceof Hashtable) ((Hashtable)val).put(key, value);
        else {
            Hashtable n = new Hashtable();
            n.put(key, value);
            hashtable.put(group, n);
        }
    }
    
    public void put(String key, String value) {
        hashtable.put(key, value);
    }
    
    public boolean groupExists(String group) {
        return hashtable.get(group) != null;
    }
    
    public String get(String group, String key) {
        Object val = hashtable.get(group);
        if(val!=null && val instanceof Hashtable) return (String)((Hashtable)val).get(key);
        
        return null;
    }
    
    public String getDef(String group, String key, String def) {
        String val = null;
        Object hash = hashtable.get(group);
        if(hash!=null && hash instanceof Hashtable) val = (String)((Hashtable)hash).get(key);
        
        if(val == null) return def;
        return val;
    }

    public byte getByte(String group, String key) {
        return StringTools.parseByte(get(group,key));
    }
    
    public float getFloat(String group, String key) {
        return StringTools.parseFloat(get(group,key));
    }

    public int getInt(String group, String key) {
        return StringTools.parseInt(get(group,key));
    }
    
    public long getLong(String group, String key) {
        return StringTools.parseLong(get(group,key));
    }
    
    public float getFloat(String group, String key, float def) {
        String tmp = get(group,key);
        if(tmp == null) return def;
        return StringTools.parseFloat(tmp);
    }

    public int getInt(String group, String key, int def) {
        String tmp = get(group,key);
        if(tmp == null) return def;
        return StringTools.parseInt(tmp);
    }
    
    //Только ключи
    public String get(String key) {
        Object val = hashtable.get(key);
        if(val!=null && val instanceof String) return (String)val;
        
        return null;
    }
    
    public String getDef(String key, String def) {
        Object val = hashtable.get(key);
        if(val!=null && val instanceof String) return (String)val;
        
        return def;
    }

    public byte getByte(String key) {
        return StringTools.parseByte(get(key));
    }
    
    public float getFloat(String key) {
        return StringTools.parseFloat(get(key));
    }
    
    public int getInt(String key) {
        return StringTools.parseInt(get(key));
    }
    
    public long getLong(String key) {
        return StringTools.parseLong(get(key));
    }
    
    public float getFloat(String key, float def) {
        String tmp = get(key);
        if(tmp == null) return def;
        return StringTools.parseFloat(tmp);
    }

    public int getInt(String key, int def) {
        String tmp = get(key);
        if(tmp == null) return def;
        return StringTools.parseInt(tmp);
    }

    /* What the hell is this
    public static String removeSpaces(String s) {
        char[] array = s.toCharArray();

        int sub = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                int back = 0;
                for (int l = i; l >= 0; l--) {
                    if (array[l] == '"') back++;
                }

                if (back % 2 != 0) sub++;
            }
        }

        char[] array2 = new char[array.length - sub];
        sub = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                int back = 0;
                for (int l = i; l >= 0; l--) {
                    if (array[l] == '"') {
                        back++;
                    }
                }

                if (back % 2 == 0) {
                    array2[sub] = array[i];
                    sub++;
                }
            } else {
                array2[sub] = array[i];
                sub++;
            }
        }
        return new String(array2);
    }
    */
}
