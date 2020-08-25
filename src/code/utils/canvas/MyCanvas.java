package code.utils.canvas;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author DDDENISSS
 */
public abstract class MyCanvas {

    public boolean pointerPressed = false;
    
    public MyCanvas() {
    }
    
    public void showNotify() {
        repaint();
    }

    public void hideNotify() {
    }
    
    public final void repaint(int x, int y, int width, int height) {
        MainCanvas.mainRepaint(this, x, y, width, height);
    }

    public final void repaint() {
        MainCanvas.mainRepaint(this);
    }

    public final void serviceRepaints() {
        MainCanvas.mainServiceRepaints(this);
    }

    protected abstract void paint(Graphics g);

    protected void keyPressed(int keyCode) {
    }

    protected void keyRepeated(int keyCode) {
    }

    protected void keyReleased(int keyCode) {
    }

    protected void pointerPressed(int x, int y) {
        pointerPressed=true;
    }
    
    protected void mouseScrollDown() {
    }
    
    protected void mouseScrollUp() {
    }

    protected void pointerReleased(int x, int y) {
        pointerPressed=false;
    }

    protected void pointerDragged(int x, int y) {
    }
    
    protected void pointerClicked(int x, int y) {
    }

    protected void sizeChanged(int x, int y) {}
    
    public int getWidth() {
        return MainCanvas.getMainWidth();
    }
    
    public int getHeight() {
        return MainCanvas.getMainHeight();
    }
    
    public String getKeyName(int keyCode)  {
        return MainCanvas.getMainKeyName(keyCode);
    }
    
    public int getKeyCode(int action)  {
        return MainCanvas.getMainKeyCode(action);
    }
    
    
}
