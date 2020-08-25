package code.utils.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author DDDENISSS
 */
public class MainCanvas extends Canvas {
    
    private static MainCanvas mainCanvas;
    public static int pointerX, pointerY;
    public static int pointerMovedX, pointerMovedY;
    public static int mouseX, mouseY;
    public static int emulatorScreenWidth, emulatorScreenHeight;
    public static boolean pstros;
    
    private MyCanvas screen;

    public MainCanvas(MIDlet midlet) {
        setFullScreenMode(true);
        mainCanvas = this;
        Display.getDisplay(midlet).setCurrent(this);
    }

    public void setScreen(MyCanvas newScreen) {
        if(screen != null) screen.hideNotify();
        screen = newScreen;
        screen.showNotify();
    }
    
    public MyCanvas getScreen() {
        return screen;
    }
    

    static int getMainWidth() {
        return mainCanvas.getWidth();
    }
    
    static String getMainKeyName(int key) {
        return mainCanvas.getKeyName(key);
    }
    
    static int getMainKeyCode(int action) {
        return mainCanvas.getKeyCode(action);
    }
    
    static int getMainHeight() {
        return mainCanvas.getHeight();
    }
    
    static void mainRepaint(MyCanvas screen) {
        if(mainCanvas.screen == screen) mainCanvas.repaint();
    }
    
    static void mainRepaint(MyCanvas screen, int x, int y, int width, int height) {
        if(mainCanvas.screen == screen) mainCanvas.repaint(x, y, width, height);
    }
    
    static void mainServiceRepaints(MyCanvas screen) {
        if(mainCanvas.screen == screen) mainCanvas.serviceRepaints();
    }

    protected void paint(Graphics g) {
        if(screen != null) screen.paint(g);
    }

    protected void keyPressed(int keyCode) {
        if(screen != null) screen.keyPressed(keyCode);
    }

    protected void keyRepeated(int keyCode) {
        if(screen != null) screen.keyRepeated(keyCode);
    }

    protected void keyReleased(int keyCode) {
        if(screen != null) screen.keyReleased(keyCode);
    }

    protected void pointerPressed(int x, int y) {
        pointerX = x; pointerY = y; pointerMovedX = pointerMovedY = 0;
        if(screen != null) screen.pointerPressed(x, y);
    }

    protected void pointerReleased(int x, int y) {
        if(screen != null) screen.pointerReleased(x, y);
        if(pointerMovedX<=4 && pointerMovedY<=4 && screen != null) screen.pointerClicked(x, y);
    }

    protected void pointerDragged(int x, int y) {
        pointerMovedX += Math.abs(x-pointerX); pointerMovedY += Math.abs(y-pointerY);
        pointerX = x; pointerY = y; 
        if(screen != null) screen.pointerDragged(x, y);
    }
    
    protected void sizeChanged(int x, int y) {
        if(screen != null) screen.sizeChanged(x, y);
    }
    
    protected void updateMousePos(int x ,int y) {
        mouseX = x; mouseY = y;
    }
    
    protected void mouseScrollDown() {
        if(screen != null) screen.mouseScrollDown();
    }
    
    protected void mouseScrollUp() {
        if(screen != null) screen.mouseScrollUp();
    }
    
    protected void thisIsPstros() {
        pstros = true;
    }
    
    protected void updateScreenSize(int w, int h) {
        emulatorScreenWidth = w;
        emulatorScreenHeight = h;
    }
}
