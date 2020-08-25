package code.utils;



import javax.microedition.lcdui.Canvas;

/**
 *
 * @author Roman Lahin
 */

public class Keyboard {

    public final int LEFT, RIGHT,
            UP, DOWN,
            FIRE,
            SOFT_LEFT, SOFT_RIGHT;
    
    public boolean up,down,left,right,key1,key3,key7,key9,star,pound,key0,fire;
    public long upAction, downAction, leftAction, rightAction, okAction;

    public Keyboard(Canvas c) {
        if (isSelect(c,-26)) { // (Siemens)
            LEFT = -61;
            RIGHT = -62;
            DOWN = -60;
            UP = -59;
            FIRE = -26;
            SOFT_LEFT = -1;
            SOFT_RIGHT = -4;
        } else if (isSelect(c,-20)) { // (Motorola)
            LEFT = -2;
            RIGHT = -5;
            DOWN = -6;
            UP = -1;
            FIRE = -20;
            SOFT_LEFT = -21;
            SOFT_RIGHT = -22;
        } else { // default (Nokia, SE)
            LEFT = -3;
            RIGHT = -4;
            DOWN = -2;
            UP = -1;
            FIRE = -5;
            SOFT_LEFT = -6;
            SOFT_RIGHT = -7;
        }
        reset();
    }

    private static boolean isSelect(Canvas c, int i) {
        try {
            return c.getKeyName(i).toUpperCase().indexOf("SELECT") != -1;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public void reset() {
        up = down = left = right = key1 = key3 = key7 = key9 = star = pound = fire = false;
    }
    
    public void keyPressed(int keyCode) { keyInput(keyCode, true); }
    public void keyReleased(int keyCode) { keyInput(keyCode, false); }
    
    public void keyInput(int keyCode, boolean pressed) {
        if(keyCode == UP || keyCode == Canvas.KEY_NUM2) { up = pressed; upAction = FPS.currentTime; }
        if(keyCode == DOWN || keyCode == Canvas.KEY_NUM8) { down = pressed; downAction = FPS.currentTime; } else
        if(keyCode == LEFT || keyCode == Canvas.KEY_NUM4) { left = pressed; leftAction = FPS.currentTime; } else
        if(keyCode == RIGHT || keyCode == Canvas.KEY_NUM6) { right = pressed; rightAction = FPS.currentTime; } else
        if(keyCode == FIRE || keyCode == Canvas.KEY_NUM5) { fire = pressed; okAction = FPS.currentTime; } else
        
        if(keyCode == Canvas.KEY_NUM1) key1 = pressed; else
        if(keyCode == Canvas.KEY_NUM3) key3 = pressed; else
        if(keyCode == Canvas.KEY_NUM7) key7 = pressed; else
        if(keyCode == Canvas.KEY_NUM9) key9 = pressed; else
        if(keyCode == Canvas.KEY_NUM0) key0 = pressed; else
        if(keyCode == Canvas.KEY_STAR) star = pressed; else
        if(keyCode == Canvas.KEY_POUND) pound = pressed;
    }
}
