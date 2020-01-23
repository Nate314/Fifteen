package com.nathangawith.umkc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

/**
 * Key Listener used to capture key presses durring gameplay
 */
public class MyKeyListener implements KeyListener {

    private Consumer<MyKey> function;

    /**
     * MyKeyListener constructor
     * @param func function to run when an arrow key is pressed
     */
    public MyKeyListener(Consumer<MyKey> func) {
        function = func;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                function.accept(MyKey.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                function.accept(MyKey.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                function.accept(MyKey.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                function.accept(MyKey.RIGHT);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }
}
