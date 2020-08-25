package code.utils;

import javax.microedition.rms.RecordStore;

/**
 *
 * @author Roman Lahin
 */
public class RmsUtils {
    
    public static boolean removeStore(String name) {
        try {
            RecordStore.deleteRecordStore(name);
            return true;
        } catch(Exception e) {}
        
        return false;
    }
    
    public static byte[] openStore(String name) {
        byte[] output = null;
        
        try {
            RecordStore rs = RecordStore.openRecordStore(name, false);
            output = rs.getRecord(1);
            rs.closeRecordStore();
        } catch(Exception e) {}
        
        
        return output;
    }
    
    public static boolean saveStore(String name, byte[] data) {
        
        try {
            RecordStore rs = RecordStore.openRecordStore(name, true);
            rs.addRecord(data, 0, data.length);
            rs.closeRecordStore();
            return true;
        } catch(Exception e) {}
        
        
        return false;
    }
    
    public static boolean hasStore(String name) {
        try {
            RecordStore rs = RecordStore.openRecordStore(name, false);
            boolean hasStore = false;
            try { hasStore = rs.getNumRecords() > 0; } catch(Exception e) {}
            rs.closeRecordStore();
            return hasStore;
        } catch(Exception e) {}
        
        return false;
    }
}
