package code.utils;

import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public class Sound {
    
    private static boolean useMP3;
    
    static {
        
        String[] conts = Manager.getSupportedContentTypes(null);
        
        for(int i=0; i<conts.length; i++) {
            if(conts[i].equals("audio/mp3")) {
                useMP3 = true; break;
            }
        }
        
    }
    
    //private InputStream is = null;
    private Player player = null;
    public String file;
    private int volume = 100;
    private int loopCount = 1;
    
    public Sound() {}
    
    public Sound(String file) {
        loadFile(file);
    }
    
    public static Sound createMusicPlayer() {
        Sound snd = new Sound();
        snd.loopCount = -1;
        return snd;
    }
    
    public void loadFile(String file) {
        try {
            destroy();

            String lcFile = file.toLowerCase();
            if(lcFile.equals("noone")) return;
            
            this.file = file;
            String format = formatCheck(lcFile);    

            InputStream is = (new Object()).getClass().getResourceAsStream(file);
            player = Manager.createPlayer(is, format);
            player.realize();
            player.prefetch();
            is.close();
        } catch(Exception e) {}
    }
    
    public void destroy() {
        file = null;
        
        stop();
        try {
            
            if(player != null) player.close();
            player = null;
            
        } catch (Exception exc) {}
        
        /*try {
            is.close();
        } catch(Exception e) {}*/
    }
    
    public int getLoopCount(int count) {
        return loopCount;
    }
    
    public void setLoopCount(int count) {
        loopCount = count;
        
        if(player == null) return;
        try {
            player.setLoopCount(loopCount);
        } catch (Exception e) {}
    }

    public void start() {
        start(0);
    }
    
    public void start(long time) {
        if(player == null) return;
        
        try {
            setVolume(volume);
            player.setMediaTime(time);
            player.setLoopCount(loopCount);
            player.start();
        } catch (Exception exc) {}
    }

    public void stop() {
        if(player == null) return;
        
        try {
            player.stop();
        } catch (MediaException exc) {}
    }
    
    public long getTime() {
        if(player == null) return 0L;

        long t = player.getMediaTime();
        if(t == -1) t = 0;
        return t;
    }

    public void setTime(long time) {
        if(player == null) return;

        try {
            player.setMediaTime(time);
        } catch (MediaException exc) {}
    }


    public void setVolume(int i) {
        if(player == null) return;

        volume = i>100?100:(i<0?0:i);

        VolumeControl control = (VolumeControl) player.getControl("VolumeControl");
        if(control != null) control.setLevel(volume);
    }
    
    public boolean hasPlayer() {
        return player!=null;
    }
    
    public int getState() {
        if(player == null) return -1;
        
        return player.getState();
    }

    public static final String formatCheck(String file) {
        if(file.endsWith(".wav") || file.endsWith(".wave")) return "audio/x-wav";
        if(file.endsWith(".midi") || file.endsWith(".mid")) return "audio/midi";
        if(file.endsWith(".mp3")) return useMP3?"audio/mp3":"audio/mpeg";
        if(file.endsWith(".amr")) return "audio/amr";
        if(file.endsWith(".jts")) return "audio/x-tone-seq";
        if(file.endsWith(".mxmf")) return "audio/mobile-xmf";
        if(file.endsWith(".mmf")) return "application/vnd.yamaha.smaf-audio";
        return "";
    }
}
