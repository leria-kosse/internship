package com.gamewerks.bgm.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.gamewerks.bgm.engine.Engine;
import com.gamewerks.bgm.engine.Piece;
import com.gamewerks.bgm.util.Constants;
import com.gamewerks.bgm.util.Position;

/** The panel on which we render an instance of the Blocky game engine. */
public class BlockyPanel extends JPanel {
    private static final int BLOCK_SIZE = 32;
    
    private int width;
    private int height;
    private Engine game;

    /**
     * Constructs a new panel that renders the given game.
     * @param game the game engine to render
     */
    public BlockyPanel(Engine game) {
        width = Constants.BOARD_WIDTH * BLOCK_SIZE;
        height = (Constants.BOARD_HEIGHT - 3) * BLOCK_SIZE;
        this.game = game;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Draws the state of the panel's underlying engine. Note that this method
     * is not called directly. The Swing library calls this method to render
     * the panel when it deems appropriate to do so.
     */
    @Override
    public void paintComponent(Graphics g) {
        boolean[][] well = game.getWell();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.BLUE);
        Piece activePiece = game.getActivePiece();
        if (activePiece != null) {
            g.setColor(activePiece.getColor());
            boolean[][] layout = activePiece.getLayout();
            Position activePos = activePiece.getPosition();
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (layout[row][col]) {
                        g.fillRect((activePos.col() + col) * BLOCK_SIZE,
                                    (Constants.BOARD_HEIGHT - activePos.row() - row - 1)
                                    * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }
        
        g.setColor(Color.GREEN);
        for (int row = 0; row < Constants.BOARD_HEIGHT; row++) {
            for (int col = 0; col < Constants.BOARD_WIDTH; col++) {
                if (well[row][col]) {
                    g.fillRect(col * BLOCK_SIZE, (Constants.BOARD_HEIGHT - row - 4)
                                * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
}
