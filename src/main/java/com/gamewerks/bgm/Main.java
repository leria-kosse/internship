package com.gamewerks.bgm;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.gamewerks.bgm.engine.Engine;
import com.gamewerks.bgm.engine.KeyKind;
import com.gamewerks.bgm.gfx.BlockyPanel;

/** The main class for Blocky the Grandmaster. */
public class Main {
    /** The framerate (frames per second) of the game. */
    private static final int FPS = 60;
    /** The rate that frames are played (in seconds per frame). */
    private static final double SPF = 1000000000.0 / FPS;
    
    /** 
     * The main entry point to Blocky the Grandmaster.
     * @param args the command-line arguments to the program (ignored)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Blocky the Grandmaster");
        
        Engine game = new Engine();
        BlockyPanel panel = new BlockyPanel(game);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
       
        // Setting up key listeners
        // Fixed bindings:
        // + W/Down: soft drop
        // + A/Left: move left
        // + Space/Up: hard drop
        // + D/Right: move right
        // + J/Z: rotate counterclockwise
        // + K/X: rotate clockwise
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: game.keyDown(KeyKind.SOFT_DROP); return;
                    case KeyEvent.VK_A: game.keyDown(KeyKind.MOVE_LEFT); return;
                    case KeyEvent.VK_SPACE: game.keyDown(KeyKind.SONIC_DROP); return;
                    case KeyEvent.VK_D: game.keyDown(KeyKind.MOVE_RIGHT); return;
                    case KeyEvent.VK_J: game.keyDown(KeyKind.ROTATE_COUNTERCLOCKWISE); return;
                    case KeyEvent.VK_K: game.keyDown(KeyKind.ROTATE_CLOCKWISE); return;

                    case KeyEvent.VK_LEFT: game.keyDown(KeyKind.MOVE_LEFT); return;
                    case KeyEvent.VK_RIGHT: game.keyDown(KeyKind.MOVE_RIGHT); return;
                    case KeyEvent.VK_UP: game.keyDown(KeyKind.SONIC_DROP); return;
                    case KeyEvent.VK_DOWN: game.keyDown(KeyKind.SOFT_DROP); return;
                    case KeyEvent.VK_Z: game.keyDown(KeyKind.ROTATE_COUNTERCLOCKWISE); return;
                    case KeyEvent.VK_X: game.keyDown(KeyKind.ROTATE_CLOCKWISE); return;
                }
            }
            
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: game.keyUp(KeyKind.SOFT_DROP); return;
                    case KeyEvent.VK_A: game.keyUp(KeyKind.MOVE_LEFT); return;
                    case KeyEvent.VK_SPACE: game.keyUp(KeyKind.SONIC_DROP); return;
                    case KeyEvent.VK_D: game.keyUp(KeyKind.MOVE_RIGHT); return;
                    case KeyEvent.VK_J: game.keyUp(KeyKind.ROTATE_COUNTERCLOCKWISE); return;
                    case KeyEvent.VK_K: game.keyUp(KeyKind.ROTATE_CLOCKWISE); return;

                    case KeyEvent.VK_LEFT: game.keyUp(KeyKind.MOVE_LEFT); return;
                    case KeyEvent.VK_RIGHT: game.keyUp(KeyKind.MOVE_RIGHT); return;
                    case KeyEvent.VK_UP: game.keyUp(KeyKind.SONIC_DROP); return;
                    case KeyEvent.VK_DOWN: game.keyUp(KeyKind.SOFT_DROP); return;
                    case KeyEvent.VK_Z: game.keyUp(KeyKind.ROTATE_COUNTERCLOCKWISE); return;
                    case KeyEvent.VK_X: game.keyUp(KeyKind.ROTATE_CLOCKWISE); return;
                }
            }
        });

        // Spawn the game loop in a separate thread so it does not block
        // the UI from rendering or processing inputs.
        new Thread(() -> {
            long timeElapsed = 0;
            long prevTime = System.nanoTime();
            while (true) {
                long currentTime = System.nanoTime();
                timeElapsed += currentTime - prevTime;
                prevTime = currentTime;
                if (timeElapsed > SPF) {
                    game.step();
                    panel.paintImmediately(
                        new Rectangle(0, 0, panel.getWidth(), panel.getHeight()));
                    timeElapsed = (long) (timeElapsed - SPF);
                }
            }
        }).start();
    }
}
